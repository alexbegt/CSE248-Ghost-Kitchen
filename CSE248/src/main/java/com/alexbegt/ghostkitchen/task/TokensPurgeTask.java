package com.alexbegt.ghostkitchen.task;

import com.alexbegt.ghostkitchen.persistence.dao.user.token.PasswordResetTokenRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.token.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
@Transactional
public class TokensPurgeTask {

  @Autowired
  VerificationTokenRepository verificationTokenRepository;

  @Autowired
  PasswordResetTokenRepository passwordTokenRepository;

  @Scheduled(cron = "${purge.cron.expression}")
  public void purgeExpired() {

    Date now = Date.from(Instant.now());

    this.passwordTokenRepository.deleteAllExpiredSince(now);
    this.verificationTokenRepository.deleteAllExpiredSince(now);
  }
}
