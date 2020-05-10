package com.alexbegt.ghostkitchen.persistence.dao.user.order;

import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.persistence.model.user.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

  /**
   * Find orders by its user.
   *
   * @param user the user to find the cart
   * @return the orders if found
   */
  Orders findByUser(User user);

  @Modifying
  @Query("delete from Orders t where t.user = ?1")
  void deleteAllByUser(User user);
}
