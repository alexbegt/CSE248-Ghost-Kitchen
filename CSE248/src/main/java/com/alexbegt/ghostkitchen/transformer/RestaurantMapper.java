package com.alexbegt.ghostkitchen.transformer;

import com.alexbegt.ghostkitchen.entity.RestaurantEntity;
import com.alexbegt.ghostkitchen.model.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RestaurantMapper {

  RestaurantEntity mapRestaurantToRestaurantEntity(Restaurant restaurant);

  Restaurant mapRestaurantEntityToRestaurant(RestaurantEntity restaurantEntity);

  List<Restaurant> mapRestaurantEntityListToUserList(List<RestaurantEntity> restaurantEntities);
}
