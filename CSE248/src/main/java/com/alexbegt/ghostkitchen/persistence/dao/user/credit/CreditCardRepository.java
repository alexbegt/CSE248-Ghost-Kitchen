package com.alexbegt.ghostkitchen.persistence.dao.user.credit;

import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.persistence.model.user.credit.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

  /**
   * Finds an credit card by the user.
   *
   * @param user  the user to use
   * @return the address if found
   */
  CreditCard findByUser(User user);

  /**
   * Deletes all the credit card that are owned by the given user.
   *
   * @param user the user to delete by
   */
  @Modifying
  @Query("delete from CreditCard t where t.user = ?1")
  void deleteAllByUser(User user);
}
