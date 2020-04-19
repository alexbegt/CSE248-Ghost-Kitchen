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
  public static final String ADMIN_STREET_ADDRESS = "123 ADMIN STREET";
  public static final String ADMIN_CITY = "ADMIN";
  public static final String ADMIN_STATE = "ADMIN";
  public static final String ADMIN_ZIP = "11111";

  public static final String USER_FIRST_NAME = "USER";
  public static final String USER_LAST_NAME = "USER";
  public static final String USER_EMAIL = "user@user.com";
  public static final String USER_PASSWORD = "user";
  public static final String USER_STREET_ADDRESS = "123 USER STREET";
  public static final String USER_CITY = "USER";
  public static final String USER_STATE = "USER";
  public static final String USER_ZIP = "11111";

  public static final int MAX_LOGIN_ATTEMPTS = 10;
  public static final int MAX_CAPTCHA_ATTEMPTS = 4;
  public static final int TOKEN_EXPIRATION_TIME = 60 * 24;

  public static Double TAX_PERCENT = 0.08625;

  public static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
  public static final String RECAPTCHA_URL_TEMPLATE = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s";

  public static final String PASSWORD_RESET_TOKEN_INVALID = "invalidPasswordResetToken";
  public static final String PASSWORD_RESET_TOKEN_EXPIRED = "expiredPasswordResetToken";

  public static final String VERIFICATION_TOKEN_INVALID = "invalidVerificationToken";
  public static final String VERIFICATION_TOKEN_EXPIRED = "expiredVerificationToken";
  public static final String VERIFICATION_TOKEN_VALID = "validVerificationToken";

  public static final String TWO_STEP_TOKEN_INVALID = "invalidTwoStepToken";

  public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
  public static String APP_NAME = "GhostKitchen";
}
