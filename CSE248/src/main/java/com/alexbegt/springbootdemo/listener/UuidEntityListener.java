package com.alexbegt.springbootdemo.listener;

import com.alexbegt.springbootdemo.entity.UuidEntity;

import javax.persistence.PrePersist;
import java.util.UUID;

public class UuidEntityListener {

  @PrePersist
  public void prePersistUUID(UuidEntity uuidEntity) {
    uuidEntity.setUuid(UUID.randomUUID());
  }
}
