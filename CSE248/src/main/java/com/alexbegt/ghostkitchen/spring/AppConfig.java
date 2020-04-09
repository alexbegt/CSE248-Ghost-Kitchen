package com.alexbegt.ghostkitchen.spring;

import com.alexbegt.ghostkitchen.security.user.ActiveUserStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class AppConfig {

  @Bean
  public ActiveUserStorage activeUserStorage() {
    return new ActiveUserStorage();
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {
    var source = new ResourceBundleMessageSource();

    source.setBasenames("messages");
    source.setUseCodeAsDefaultMessage(true);

    return source;
  }
}
