package com.alexbegt.ghostkitchen.persistence.dao.device;

import com.alexbegt.ghostkitchen.persistence.model.device.UserLocation;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

  /**
   * Looks up a user location from the users country and user class.
   *
   * @param country the country to look up
   * @param user the user to look up
   * @return if found, returns the UserLocation
   */
  UserLocation findByCountryAndUser(String country, User user);

}
