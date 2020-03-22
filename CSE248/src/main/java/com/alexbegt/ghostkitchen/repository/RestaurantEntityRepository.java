package com.alexbegt.ghostkitchen.repository;

import com.alexbegt.ghostkitchen.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantEntityRepository extends JpaRepository<RestaurantEntity, Long> {

  Optional<RestaurantEntity> findByName(String name);

  Optional<RestaurantEntity> findByPhoneNumber(String phoneNumber);
}
