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
    registry.addViewController("/").setViewName("/login/login");

    registry.addViewController("/login").setViewName("/login/login");
    registry.addViewController("/logout").setViewName("/login/login");
    registry.addViewController("/forgot-password").setViewName("/login/forgot-password");
    registry.addViewController("/reset-password").setViewName("/login/reset-password");

    registry.addViewController("/sign-up").setViewName("/registration/sign-up");
    registry.addViewController("/two-factor-qr").setViewName("/registration/two-factor-qr");
    registry.addViewController("/invalid-verification-token").setViewName("/registration/invalid-verification-token");

    registry.addViewController("/home").setViewName("/user/home");
    registry.addViewController("/change-password").setViewName("/user/change-password");
    registry.addViewController("/change-address").setViewName("/user/change-address");
    registry.addViewController("/change-credit-card").setViewName("/user/change-credit-card");
    registry.addViewController("/view-orders").setViewName("/user/view-orders");
    registry.addViewController("/view-order-details").setViewName("/user/view-order-details");

    registry.addViewController("/view-restaurants").setViewName("/restaurant/view-restaurants");
    registry.addViewController("/view-menu").setViewName("/restaurant/view-menu");
    registry.addViewController("/view-cart").setViewName("/restaurant/view-cart");
    registry.addViewController("/add-item-to-cart").setViewName("/restaurant/add-item-to-cart");
    registry.addViewController("/remove-item-from-cart").setViewName("/restaurant/remove-item-from-cart");
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

    localeChangeInterceptor.setParamName("language");
    registry.addInterceptor(localeChangeInterceptor);
  }

  @Bean
  public LocaleResolver localeResolver() {
    final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();

    cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
    cookieLocaleResolver.setCookieMaxAge(24 * 60 * 60);

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
