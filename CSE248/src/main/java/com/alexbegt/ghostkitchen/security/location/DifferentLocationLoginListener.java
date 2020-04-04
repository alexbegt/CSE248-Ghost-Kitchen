package com.alexbegt.ghostkitchen.security.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DifferentLocationLoginListener implements ApplicationListener<OnDifferentLocationLoginEvent> {

  private final MessageSource messageSource;

  private final JavaMailSender mailSender;

  private final Environment env;

  @Autowired
  public DifferentLocationLoginListener(MessageSource messageSource, JavaMailSender mailSender, Environment env) {
    this.messageSource = messageSource;
    this.mailSender = mailSender;
    this.env = env;
  }

  /**
   * Handles when a new location login event is sent.
   *
   * @param event the event
   */
  @Override
  public void onApplicationEvent(final OnDifferentLocationLoginEvent event) {
    final String enableLocUri = event.getAppUrl() + "/new-location-detected?token=" + event.getToken().getToken();

    final String changePassUri = event.getAppUrl() + "/change-password";
    final String recipientAddress = event.getUsername();

    final String subject = "We have detected a login attempt from an unknown device";
    final String message = this.messageSource.getMessage("message.differentLocation", new Object[] { new Date().toString(), event.getToken().getUserLocation().getCountry(), event.getIp(), enableLocUri, changePassUri }, event.getLocale());

    final SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(recipientAddress);
    email.setSubject(subject);
    email.setText(message);
    email.setFrom(this.env.getProperty("support.email"));

    System.out.println(message);

    this.mailSender.send(email);
  }
}
