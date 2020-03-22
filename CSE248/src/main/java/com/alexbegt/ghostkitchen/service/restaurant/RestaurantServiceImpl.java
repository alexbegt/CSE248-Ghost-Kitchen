package com.alexbegt.ghostkitchen.service.restaurant;

import com.alexbegt.ghostkitchen.model.Restaurant;
import com.alexbegt.ghostkitchen.repository.RestaurantEntityRepository;
import com.alexbegt.ghostkitchen.transformer.RestaurantMapper;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RestaurantServiceImpl implements RestaurantService {

  private final RestaurantEntityRepository repository;
  private final RestaurantMapper mapper = Mappers.getMapper(RestaurantMapper.class);

  @Override
  public void createRestaurant(Restaurant restaurant) {
    repository.save(mapper.mapRestaurantToRestaurantEntity(restaurant));
  }

  @Override
  public Restaurant getRestaurantById(Long id) {
    return mapper.mapRestaurantEntityToRestaurant(repository.findById(id).orElse(null));
  }

  @Override
  public void deleteRestaurantById(Long id) {
    repository.deleteById(id);
  }

  @Override
  public List<Restaurant> getAllRestaurants() {
    return mapper.mapRestaurantEntityListToUserList(repository.findAll());
  }

  @Override
  public Restaurant getRestaurantByName(String name) {
    return mapper.mapRestaurantEntityToRestaurant(repository.findByName(name).orElse(null));
  }

  @Override
  public Restaurant getRestaurantByPhoneNumber(String phoneNumber) {
    return mapper.mapRestaurantEntityToRestaurant(repository.findByPhoneNumber(phoneNumber).orElse(null));
  }
}
