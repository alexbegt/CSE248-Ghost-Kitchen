package com.alexbegt.ghostkitchen.service.restaurant;


import com.alexbegt.ghostkitchen.model.Restaurant;

import java.util.List;

public interface RestaurantService {

  void createRestaurant(Restaurant user);

  Restaurant getRestaurantById(Long id);

  void deleteRestaurantById(Long id);

  Restaurant getRestaurantByName(String name);

  Restaurant getRestaurantByPhoneNumber(String phoneNumber);

  List<Restaurant> getAllRestaurants();
}
