package com.alexbegt.ghostkitchen.util;

import java.util.regex.Pattern;

public class Defaults {

  public static final String READ_PRIVILEGE = "READ_PRIVILEGE";
  public static final String WRITE_PRIVILEGE = "WRITE_PRIVILEGE";
  public static final String CHANGE_PASSWORD_PRIVILEGE = "CHANGE_PASSWORD_PRIVILEGE";

  public static final String ADMIN_ROLE = "ADMIN_ROLE";
  public static final String USER_ROLE = "USER_ROLE";

  public static final String ADMIN_FIRST_NAME = "ADMIN";
  public static final String ADMIN_LAST_NAME = "ADMIN";
  public static final String ADMIN_EMAIL = "admin@admin.com";
  public static final String ADMIN_PASSWORD = "admin";

  public static final int MAX_LOGIN_ATTEMPTS = 10;
  public static final int MAX_CAPTCHA_ATTEMPTS = 4;
  public static final int TOKEN_EXPIRATION_TIME = 60 * 24;

  public static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
  public static final String RECAPTCHA_URL_TEMPLATE = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s";
}
