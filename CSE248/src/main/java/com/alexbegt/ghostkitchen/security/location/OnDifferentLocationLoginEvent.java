package com.alexbegt.ghostkitchen.security.location;

import com.alexbegt.ghostkitchen.persistence.model.device.NewLocationToken;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnDifferentLocationLoginEvent extends ApplicationEvent {

  private final Locale locale;
  private final String username;
  private final String ip;
  private final NewLocationToken token;
  private final String appUrl;

  /**
   * Create a new {@code ApplicationEvent}.
   * @param locale the object on which the event initially occurred or with
   * which the event is associated (never {@code null})
   */
  public OnDifferentLocationLoginEvent(Locale locale, String username, String ip, NewLocationToken token, String appUrl) {
    super(locale);

    this.locale = locale;
    this.username = username;
    this.ip = ip;
    this.token = token;
    this.appUrl = appUrl;
  }

  /**
   * Gets the locale associated with the event
   *
   * @return the locale associated
   */
  public Locale getLocale() {
    return this.locale;
  }

  /**
   * Gets the username associated with the event
   *
   * @return the username associated
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Gets the ip associated with the event
   *
   * @return the ip associated
   */
  public String getIp() {
    return this.ip;
  }

  /**
   * Gets the token associated with the event
   *
   * @return the token associated
   */
  public NewLocationToken getToken() {
    return this.token;
  }

  /**
   * Gets the app url associated with the event
   *
   * @return the app url associated
   */
  public String getAppUrl() {
    return this.appUrl;
  }
}
