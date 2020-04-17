package com.alexbegt.ghostkitchen.captcha;

import com.alexbegt.ghostkitchen.captcha.error.ReCaptchaInvalidException;

public interface ICaptchaService {

  /**
   * Processes the captcha response
   *
   * @param response the response spring
   * @throws ReCaptchaInvalidException if the captcha is invalid
   */
  default void processResponse(final String response) throws ReCaptchaInvalidException {}

  /**
   * Processes the captcha response
   *
   * @param response the response spring
   * @param action the action to do
   * @throws ReCaptchaInvalidException  if the captcha is invalid
   */
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
