package com.alexbegt.ghostkitchen.persistence.dao.device;

import com.alexbegt.ghostkitchen.persistence.model.device.DeviceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

  /**
   * Finds all the DeviceMetadata linked to a userId.
   *
   * @param userId the given user id to look up by
   * @return if found, a list of DeviceMetadata
   */
  List<DeviceMetadata> findByUserId(Long userId);
}
