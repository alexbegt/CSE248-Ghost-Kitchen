package com.alexbegt.ghostkitchen.persistence.dao.user;

import com.alexbegt.ghostkitchen.persistence.model.user.PasswordResetToken;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

  /**
   * Finds a password reset token by the token.
   *
   * @param token  the token id to use
   * @return the password reset token if found
   */
  PasswordResetToken findByToken(String token);

  /**
   * Finds a password reset token by the user.
   *
   * @param user  the user to use
   * @return the password reset token if found
   */
  PasswordResetToken findByUser(User user);

  /**
   * Deletes all the expired tokens that expired either that day or the previous day.
   *
   * @param now the date to delete by
   */
  @Modifying
  @Query("delete from PasswordResetToken t where t.expirationDate <= ?1")
  void deleteAllExpiredSince(Date now);
}
