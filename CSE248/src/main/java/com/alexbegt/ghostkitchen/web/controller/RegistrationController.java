package com.alexbegt.ghostkitchen.web.controller;

import com.alexbegt.ghostkitchen.captcha.ICaptchaService;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.persistence.model.user.token.VerificationToken;
import com.alexbegt.ghostkitchen.registration.OnRegistrationCompleteEvent;
import com.alexbegt.ghostkitchen.service.IUserService;
import com.alexbegt.ghostkitchen.util.Defaults;
import com.alexbegt.ghostkitchen.util.UtilityMethods;
import com.alexbegt.ghostkitchen.web.dto.user.UserDto;
import com.alexbegt.ghostkitchen.web.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Controller
public class RegistrationController extends ControllerUtility {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IUserService userService;

  @Autowired
  private ICaptchaService captchaService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private Environment env;

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @PostMapping("/api/register-user")
  @ResponseBody
  public GenericResponse registerUserAccount(@Valid final UserDto userDto, final HttpServletRequest request) {
    final String response = request.getParameter("g-recaptcha-response");
    this.captchaService.processResponse(response);

    LOGGER.debug("Registering user account with information: {}", userDto);

    final User registered = this.userService.registerNewUserAccount(userDto);

    this.userService.addUserLocation(registered, UtilityMethods.getClientIP(request));
    this.eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, LocaleContextHolder.getLocale(), this.getAppUrl(request)));

    return new GenericResponse("success");
  }

  @GetMapping("/confirm-registration")
  public String confirmRegistration(final HttpServletRequest request, final Model model, @RequestParam("token") final String token) throws UnsupportedEncodingException {
    Locale locale = LocaleContextHolder.getLocale();
    final String result = this.userService.validateVerificationToken(token);

    if (result.equals(Defaults.VERIFICATION_TOKEN_VALID)) {
      final User user = this.userService.getUser(token);

      if (user.isUsingTwoFactorAuthentication()) {
        model.addAttribute("qr", this.userService.generateQRUrl(user));
        model.addAttribute("message", this.messageSource.getMessage("message.accountVerifiedQR", null, locale));

        return "redirect:/two-factor-qr";
      }

      model.addAttribute("message", this.messageSource.getMessage("message.accountVerified", null, locale));

      return "redirect:/login";
    }

    model.addAttribute("message", this.messageSource.getMessage("message." + result, null, locale));
    model.addAttribute("expired", Defaults.VERIFICATION_TOKEN_EXPIRED.equals(result));
    model.addAttribute("token", token);

    return "redirect:/invalid-verification-token";
  }

  @GetMapping("/resend-registration-token")
  @ResponseBody
  public GenericResponse resendVerificationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
    final VerificationToken newToken = this.userService.generateNewVerificationToken(existingToken);
    final User user = this.userService.getUser(newToken.getToken());
    Locale locale = LocaleContextHolder.getLocale();

    final SimpleMailMessage email = this.constructEmailMessage(this.getAppUrl(request), user, newToken.getToken(), locale);

    this.mailSender.send(email);

    return new GenericResponse(this.messageSource.getMessage("message.verificationEmailResent", null, locale));
  }

  @GetMapping("/new-login-location-detected")
  public String enableNewLocation(final HttpServletRequest request, final Model model, @RequestParam("token") final String token) {
    Locale locale = LocaleContextHolder.getLocale();
    final String newLocationToken = this.userService.isValidNewLocationToken(token);

    if (newLocationToken != null) {
      model.addAttribute("message", this.messageSource.getMessage("message.newLocationEnabled", null, locale));
    }
    else {
      model.addAttribute("message", this.messageSource.getMessage("message.error", null, locale));
    }

    return "redirect:/login";
  }

  @RequestMapping("favicon.ico")
  public String favicon() {
    return "forward:/resources/images/favicon.ico";
  }


  private SimpleMailMessage constructEmailMessage(final String appUrl, final User user, final String token, Locale locale) {
    final String confirmationUrl = appUrl + "/confirm-registration?token=" + token;
    final String recipientAddress = user.getEmail();
    final String subject = this.messageSource.getMessage("message.registrationEmailSubject", null, locale);

    final String message = this.messageSource.getMessage("message.registrationEmail", new Object[] { user.getEmail(), confirmationUrl, this.env.getProperty("support.email") }, locale);
    final SimpleMailMessage email = new SimpleMailMessage();

    email.setTo(recipientAddress);
    email.setSubject(subject);
    email.setText(message);
    email.setFrom(this.env.getProperty("support.email"));

    return email;
  }
}
