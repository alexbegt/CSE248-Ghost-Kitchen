package com.alexbegt.ghostkitchen.listener;

import com.alexbegt.ghostkitchen.entity.UuidEntity;

import javax.persistence.PrePersist;
import java.util.UUID;

public class UuidEntityListener {

  @PrePersist
  public void prePersistUUID(UuidEntity uuidEntity) {
    uuidEntity.setUuid(UUID.randomUUID());
  }
}
