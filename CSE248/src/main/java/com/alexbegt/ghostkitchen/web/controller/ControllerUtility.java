package com.alexbegt.ghostkitchen.web.controller;

import com.alexbegt.ghostkitchen.persistence.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControllerUtility {

  @Autowired
  private Environment env;

  protected SimpleMailMessage constructEmail(String subject, String body, User user) {
    final SimpleMailMessage email = new SimpleMailMessage();
    email.setSubject(subject);
    email.setText(body);
    email.setTo(user.getEmail());
    email.setFrom(this.env.getProperty("support.email"));

    return email;
  }

  protected String getAppUrl(HttpServletRequest request) {
    return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
  }
}
