package com.alexbegt.ghostkitchen.captcha;

import com.alexbegt.ghostkitchen.util.Defaults;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("reCaptchaAttemptService")
public class ReCaptchaAttemptService {

  /**
   * The attempt cache.
   */
  private final LoadingCache<String, Integer> attemptsCache;

  public ReCaptchaAttemptService() {
    super();

    this.attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(4, TimeUnit.HOURS).build(new CacheLoader<>() {
      @Override
      public Integer load(final String key) {
        return 0;
      }
    });
  }

  /**
   * When the recaptcha is successful, clear the cache for the key passed.
   *
   * @param key the recaptcha key passed
   */
  public void reCaptchaSucceeded(final String key) {
    this.attemptsCache.invalidate(key);
  }

  /**
   * When the recaptcha fails, add an attempt to the key's cache to be used to check if the recaptcha is blocked.
   *
   * @param key the recaptcha key passed
   */
  public void reCaptchaFailed(final String key) {
    int attempts = this.attemptsCache.getUnchecked(key);
    attempts++;

    this.attemptsCache.put(key, attempts);
  }

  /**
   * Check if the recaptcha key is blocked from failing too many times.
   *
   * @param key the recaptcha key passed
   * @return if the attempt should be blocked from failing Defaults.MAX_CAPTCHA_ATTEMPTS times.
   */
  public boolean isBlocked(final String key) {
    return this.attemptsCache.getUnchecked(key) >= Defaults.MAX_CAPTCHA_ATTEMPTS;
  }
}
