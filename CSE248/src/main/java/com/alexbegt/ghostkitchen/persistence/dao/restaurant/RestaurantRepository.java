package com.alexbegt.ghostkitchen.persistence.dao.restaurant;

import com.alexbegt.ghostkitchen.persistence.model.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

  /**
   * Find a restaurant by its name.
   *
   * @param name the name to use to find the restaurant
   * @return the restaurant if found
   */
  Restaurant findByName(String name);

  /**
   * Finds a restaurant by its id.
   *
   * @param id the id to look by
   * @return the restaurant if found
   */
  Restaurant findRestaurantById(Long id);

  /**
   * Deletes a given restaurant user.
   *
   * @param restaurant must not be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  void delete(Restaurant restaurant);
}

