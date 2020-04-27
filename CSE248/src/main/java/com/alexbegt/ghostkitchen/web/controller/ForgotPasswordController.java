package com.alexbegt.ghostkitchen.web.controller;

import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.security.user.ISecurityUserService;
import com.alexbegt.ghostkitchen.service.IUserService;
import com.alexbegt.ghostkitchen.web.dto.password.ResetPasswordDto;
import com.alexbegt.ghostkitchen.web.error.UserNotFoundException;
import com.alexbegt.ghostkitchen.web.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ForgotPasswordController extends ControllerUtility {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  @Autowired
  private IUserService userService;

  @Autowired
  private ISecurityUserService securityUserService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private Environment env;

  @PostMapping("/api/reset-password")
  @ResponseBody
  public GenericResponse resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) throws UserNotFoundException {
    final User user = this.userService.findUserByEmail(userEmail);

    if (user != null) {
      final String token = UUID.randomUUID().toString();

      this.userService.createPasswordResetTokenForUser(user, token);
      this.mailSender.send(this.constructResetTokenEmail(this.getAppUrl(request), LocaleContextHolder.getLocale(), token, user));
    }
    else {
      throw new UserNotFoundException("There is no account with that email address: " + userEmail);
    }

    return new GenericResponse(this.messageSource.getMessage("message.resetPassword", null, LocaleContextHolder.getLocale()));
  }

  @PostMapping("/api/save-password")
  @ResponseBody
  public GenericResponse savePassword(@Valid ResetPasswordDto passwordDto) {
    final String result = this.securityUserService.validatePasswordResetToken(passwordDto.getToken());

    if (result != null) {
      return new GenericResponse(this.messageSource.getMessage("message." + result, null, LocaleContextHolder.getLocale()));
    }

    Optional<User> user = userService.getUserByPasswordResetToken(passwordDto.getToken());

    if (user.isPresent()) {
      userService.changeUserPassword(user.get(), passwordDto.getPassword());
      return new GenericResponse(this.messageSource.getMessage("message.passwordWasReset", null, LocaleContextHolder.getLocale()));
    }
    else {
      return new GenericResponse(this.messageSource.getMessage("message.invalidPasswordResetToken", null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * Constructs the url for the password reset email.
   *
   * @param contextPath the url path in
   * @param locale the language in
   * @param token the token in
   * @param user the user in
   * @return the email to send.
   */
  private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
    final String url = contextPath + "/reset-password?token=" + token;
    final String subject = this.messageSource.getMessage("message.resetPasswordSubject", null, locale);
    final String message = this.messageSource.getMessage("message.resetPasswordEmail", new Object[] { user.getEmail(), url, this.env.getProperty("support.email") }, locale);

    return this.constructEmail(subject, message, user);
  }
}
