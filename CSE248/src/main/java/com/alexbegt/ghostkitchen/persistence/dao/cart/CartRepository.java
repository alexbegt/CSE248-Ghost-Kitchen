package com.alexbegt.ghostkitchen.persistence.dao.cart;

import com.alexbegt.ghostkitchen.persistence.model.cart.Cart;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

  /**
   * Find a cart by its user.
   *
   * @param user the user to find the cart
   * @return the cart if found
   */
  Cart findByUser(User user);

  /**
   * Deletes a given cart.
   *
   * @param cart must not be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  void delete(Cart cart);
}
