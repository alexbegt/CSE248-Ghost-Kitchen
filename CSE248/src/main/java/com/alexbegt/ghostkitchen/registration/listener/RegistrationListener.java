package com.alexbegt.ghostkitchen.registration.listener;

import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.registration.OnRegistrationCompleteEvent;
import com.alexbegt.ghostkitchen.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

  @Autowired
  private IUserService service;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private Environment env;

  @Override
  public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
    final User user = event.getUser();
    final String token = UUID.randomUUID().toString();

    this.service.createVerificationTokenForUser(user, token);

    final SimpleMailMessage email = this.constructEmailMessage(event, user, token);

    this.mailSender.send(email);
  }

  private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user, final String token) {
    final String confirmationUrl = event.getAppUrl() + "/confirm-registration?token=" + token;
    final String recipientAddress = user.getEmail();
    final String subject = this.messageSource.getMessage("message.registrationEmailSubject", null, event.getLocale());

    final String message = this.messageSource.getMessage("message.registrationEmail", new Object[] { user.getEmail(), confirmationUrl, this.env.getProperty("support.email") }, event.getLocale());
    final SimpleMailMessage email = new SimpleMailMessage();

    email.setTo(recipientAddress);
    email.setSubject(subject);
    email.setText(message);
    email.setFrom(this.env.getProperty("support.email"));

    return email;
  }
}
