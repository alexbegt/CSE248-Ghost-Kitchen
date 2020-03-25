package com.alexbegt.ghostkitchen.persistence.dao.device;

import com.alexbegt.ghostkitchen.persistence.model.device.NewLocationToken;
import com.alexbegt.ghostkitchen.persistence.model.device.UserLocation;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewLocationTokenRepository extends JpaRepository<NewLocationToken, Long> {

  /**
   * Finds a new location token by the token's id
   *
   * @param token the token to lookup via
   * @return if found, returns a NewLocationToken
   */
  NewLocationToken findByToken(String token);

  /**
   * Finds a new location token by the user's location
   *
   * @param userLocation the users location
   * @return if found, returns a NewLocationToken
   */
  NewLocationToken findByUserLocation(UserLocation userLocation);
}
