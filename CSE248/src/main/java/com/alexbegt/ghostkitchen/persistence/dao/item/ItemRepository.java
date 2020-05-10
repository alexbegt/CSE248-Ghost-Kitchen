package com.alexbegt.ghostkitchen.persistence.dao.item;

import com.alexbegt.ghostkitchen.persistence.model.menu.Item;
import com.alexbegt.ghostkitchen.persistence.model.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

  /**
   * Find a item by its name.
   *
   * @param name the name to use to find the item
   * @return the item if found
   */
  Item findByName(String name);

  /**
   * Finds a item by its id.
   *
   * @param id the id to look by
   * @return the item if found
   */
  Item findItemById(Long id);

  /**
   * Deletes a given item.
   *
   * @param item must not be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  void delete(Item item);
}

