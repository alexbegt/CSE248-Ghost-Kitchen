package com.alexbegt.ghostkitchen.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component("authenticationFailureHandler")
public class GhostKitchenAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private LocaleResolver localeResolver;

  /**
   * Handles when the authentication fails.
   *
   * @param request the request
   * @param response the responge
   * @param exception the exception thrown
   * @throws IOException if unable to load a file
   * @throws ServletException unable to do a servlet request
   */
  @Override
  public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
    this.setDefaultFailureUrl("/login?error");

    super.onAuthenticationFailure(request, response, exception);

    final Locale locale = this.localeResolver.resolveLocale(request);

    String errorMessage = this.messageSource.getMessage("message.badCredentials", null, locale);

    if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
      errorMessage = this.messageSource.getMessage("auth.message.disabled", null, locale);
    }
    else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
      errorMessage = this.messageSource.getMessage("auth.message.expired", null, locale);
    }
    else if (exception.getMessage().equalsIgnoreCase("blocked")) {
      errorMessage = this.messageSource.getMessage("auth.message.blocked", null, locale);
    }
    else if (exception.getMessage().equalsIgnoreCase("unusual location")) {
      errorMessage = this.messageSource.getMessage("auth.message.unusual.location", null, locale);
    }

    request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
  }
}
