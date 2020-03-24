package com.alexbegt.ghostkitchen.captcha;

import com.alexbegt.ghostkitchen.captcha.error.ReCaptchaInvalidException;
import com.alexbegt.ghostkitchen.captcha.error.ReCaptchaUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.net.URI;

@Service("captchaService")
public class CaptchaService extends AbstractCaptchaService {

  private final static Logger LOGGER = LoggerFactory.getLogger(CaptchaService.class);

  @Override
  public void processResponse(final String response) {
    this.securityCheck(response);

    final URI verifyUri = URI.create(String.format(RECAPTCHA_URL_TEMPLATE, getReCaptchaSecret(), response, this.getClientIP()));

    try {
      final GoogleResponse googleResponse = this.restTemplate.getForObject(verifyUri, GoogleResponse.class);

      LOGGER.debug("Google's response: {} ", googleResponse.toString());

      if (!googleResponse.isSuccess()) {
        if (googleResponse.hasClientError()) {
          this.reCaptchaAttemptService.reCaptchaFailed(this.getClientIP());
        }

        throw new ReCaptchaInvalidException("reCaptcha was not successfully validated");
      }
    } catch (RestClientException rce) {
      throw new ReCaptchaUnavailableException("Registration unavailable at this time.  Please try again later.", rce);
    }

    this.reCaptchaAttemptService.reCaptchaSucceeded(this.getClientIP());
  }
}
