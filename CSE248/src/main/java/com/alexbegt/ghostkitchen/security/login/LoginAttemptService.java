package com.alexbegt.ghostkitchen.security.login;

import com.alexbegt.ghostkitchen.util.Defaults;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
  private final LoadingCache<String, Integer> attemptsCache;

  public LoginAttemptService() {
    super();

    this.attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<>() {
      @Override
      public Integer load(final String key) {
        return 0;
      }
    });
  }

  /**
   * When the user is logged in successful, reset their cache on the login key.
   *
   * @param key the login key
   */
  public void loginSucceeded(final String key) {
    this.attemptsCache.invalidate(key);
  }

  /**
   * When the user fails to log into any account, update the attempt cache to include the failed attempt.
   *
   * @param key the login key
   */
  public void loginFailed(final String key) {
    int attempts = 0;

    try {
      attempts = this.attemptsCache.get(key);
    }
    catch (final ExecutionException ignored) {
    }

    attempts++;

    this.attemptsCache.put(key, attempts);
  }

  /**
   * Checks to see if the given key should be blocked due to too many failed attempts.
   *
   * @param key the key to check
   * @return if the login attempt should be blocked due to failing 10 times
   */
  public boolean isBlocked(final String key) {
    try {
      return this.attemptsCache.get(key) >= Defaults.MAX_LOGIN_ATTEMPTS;
    }
    catch (final ExecutionException e) {
      return false;
    }
  }
}
