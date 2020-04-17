package com.alexbegt.ghostkitchen.captcha;

import com.alexbegt.ghostkitchen.captcha.error.ReCaptchaInvalidException;
import com.alexbegt.ghostkitchen.util.Defaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public abstract class AbstractCaptchaService implements ICaptchaService {

  private final static Logger LOGGER = LoggerFactory.getLogger(AbstractCaptchaService.class);

  @Autowired
  protected HttpServletRequest request;

  @Autowired
  protected CaptchaSettings captchaSettings;

  @Autowired
  protected ReCaptchaAttemptService reCaptchaAttemptService;

  @Autowired
  protected RestOperations restTemplate;

  /**
   * Get the ReCaptcha site id
   *
   * @return the ReCaptcha Site Id
   */
  @Override
  public String getReCaptchaSite() {
    return this.captchaSettings.getSite();
  }

  /**
   * Get the ReCaptcha secret id
   *
   * @return the ReCaptcha secret Id
   */
  @Override
  public String getReCaptchaSecret() {
    return this.captchaSettings.getSecret();
  }

  /**
   * Performs a security check on the captcha check
   *
   * @param response the response to check
   */
  protected void securityCheck(final String response) {
    LOGGER.debug("Attempting to validate response {}", response);

    if (this.reCaptchaAttemptService.isBlocked(this.getClientIP())) {
      throw new ReCaptchaInvalidException("Client exceeded maximum number of failed attempts");
    }

    if (!this.responseSanityCheck(response)) {
      throw new ReCaptchaInvalidException("Response contains invalid characters");
    }
  }

  /**
   * Checks if the given response is valid or not
   *
   * @param response the response to check
   * @return if the response is valid
   */
  protected boolean responseSanityCheck(final String response) {
    return StringUtils.hasLength(response) && Defaults.RESPONSE_PATTERN.matcher(response).matches();
  }

  /**
   * Get's the client ip
   *
   * @return the client ip
   */
  protected String getClientIP() {
    final String xfHeader = this.request.getHeader("X-Forwarded-For");

    if (xfHeader == null) {
      return this.request.getRemoteAddr();
    }

    return xfHeader.split(",")[0];
  }
}
