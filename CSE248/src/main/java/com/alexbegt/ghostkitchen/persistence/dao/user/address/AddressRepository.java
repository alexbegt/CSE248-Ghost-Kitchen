package com.alexbegt.ghostkitchen.persistence.dao.user.address;

import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.persistence.model.user.address.Address;
import com.alexbegt.ghostkitchen.persistence.model.user.token.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface AddressRepository extends JpaRepository<Address, Long> {

  /**
   * Finds an address by the user.
   *
   * @param user  the user to use
   * @return the address if found
   */
  Address findByUser(User user);

  /**
   * Deletes all the address that are owned by the given user.
   *
   * @param user the user to delete by
   */
  @Modifying
  @Query("delete from Address t where t.user = ?1")
  void deleteAllByUser(User user);
}
