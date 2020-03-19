package com.alexbegt.ghostkitchen.repository;

import com.alexbegt.ghostkitchen.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemEntityRepository extends JpaRepository<ItemEntity, Long> {

  Optional<ItemEntity> findByName(String name);
}
