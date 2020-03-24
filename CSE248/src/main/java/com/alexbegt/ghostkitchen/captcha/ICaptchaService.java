package com.alexbegt.ghostkitchen.captcha;

import com.alexbegt.ghostkitchen.captcha.error.ReCaptchaInvalidException;

public interface ICaptchaService {

  default void processResponse(final String response) throws ReCaptchaInvalidException {}

  default void processResponse(final String response, String action) throws ReCaptchaInvalidException {}

  /**
   * Get the ReCaptcha site id
   *
   * @return the ReCaptcha Site Id
   */
  String getReCaptchaSite();

  /**
   * Get the ReCaptcha secret id
   *
   * @return the ReCaptcha secret Id
   */
  String getReCaptchaSecret();
}
