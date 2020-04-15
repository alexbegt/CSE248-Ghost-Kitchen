package com.alexbegt.ghostkitchen.spring;

import com.alexbegt.ghostkitchen.validation.email.EmailValidator;
import com.alexbegt.ghostkitchen.validation.password.PasswordMatchesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
@ComponentScan(basePackages = { "com.alexbegt.ghostkitchen.web" })
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

  @Autowired
  private MessageSource messageSource;

  public MvcConfig() {
    super();
  }

  @Override
  public void addViewControllers(final ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("forward:/welcome");

    registry.addViewController("/welcome");

    registry.addViewController("/login").setViewName("/login/login");
    registry.addViewController("/logout").setViewName("/login/logout");
    registry.addViewController("/forgot-password").setViewName("/login/forgot-password");

    registry.addViewController("/sign-up").setViewName("/registration/sign-up");

    registry.addViewController("/admin-panel");

    registry.addViewController("/invalid-session").setViewName("/invalid-session");
  }

  @Override
  public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**", "/webjars/**", "/css/**", "/images/**").addResourceLocations("/", "/resources/", "/webjars/", "/css/", "/images/");
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();

    localeChangeInterceptor.setParamName("lang");
    registry.addInterceptor(localeChangeInterceptor);
  }

  @Bean
  public LocaleResolver localeResolver() {
    final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();

    cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);

    return cookieLocaleResolver;
  }

  @Bean
  public EmailValidator usernameValidator() {
    return new EmailValidator();
  }

  @Bean
  public PasswordMatchesValidator passwordMatchesValidator() {
    return new PasswordMatchesValidator();
  }

  @Bean
  @ConditionalOnMissingBean(RequestContextListener.class)
  public RequestContextListener requestContextListener() {
    return new RequestContextListener();
  }

  @Override
  public Validator getValidator() {
    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.setValidationMessageSource(this.messageSource);

    return validator;
  }
}
