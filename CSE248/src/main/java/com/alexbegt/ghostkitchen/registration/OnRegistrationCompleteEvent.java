package com.alexbegt.ghostkitchen.registration;

import com.alexbegt.ghostkitchen.persistence.model.user.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

  private final String appUrl;
  private final Locale locale;
  private final User user;

  public OnRegistrationCompleteEvent(final User user, final Locale locale, final String appUrl) {
    super(user);

    this.user = user;
    this.locale = locale;
    this.appUrl = appUrl;
  }

  /**
   * Gets the app url from the registration complete event
   *
   * @return the app url
   */
  public String getAppUrl() {
    return this.appUrl;
  }

  /**
   * Gets the locale to use from the registration complete event
   *
   * @return the locale used
   */
  public Locale getLocale() {
    return this.locale;
  }

  /**
   * Gets the user from the registration complete event
   *
   * @return the user
   */
  public User getUser() {
    return this.user;
  }

}
