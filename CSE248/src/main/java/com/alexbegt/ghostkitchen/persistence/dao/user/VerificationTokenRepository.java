package com.alexbegt.ghostkitchen.persistence.dao.user;

import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.persistence.model.user.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

  /**
   * Finds a verification token by the token.
   *
   * @param token  the token id to use
   * @return the verification token if found
   */
  VerificationToken findByToken(String token);

  /**
   * Finds a verification token by the user.
   *
   * @param user  the user to use
   * @return the verification token if found
   */
  VerificationToken findByUser(User user);

  /**
   * Deletes all the expired tokens that expired either that day or the previous day.
   *
   * @param now the date to delete by
   */
  @Modifying
  @Query("delete from VerificationToken t where t.expirationDate <= ?1")
  void deleteAllExpiredSince(Date now);
}
