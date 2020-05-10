package com.alexbegt.ghostkitchen.persistence.dao.user.order;

import com.alexbegt.ghostkitchen.persistence.model.menu.Item;
import com.alexbegt.ghostkitchen.persistence.model.user.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

  /**
   * Find a item by its name.
   *
   * @param item the name to use to find the item
   * @return the item if found
   */
  OrderItem findByItem(Item item);

  /**
   * Find a cart item by it's item and quantity
   *
   * @param item the item
   * @param quantity the quantity to search by
   * @return the item if found
   */
  OrderItem findByItemAndQuantity(Item item, int quantity);

  /**
   * Deletes a given item.
   *
   * @param item must not be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  void delete(OrderItem item);
}

