package com.alexbegt.ghostkitchen.model;

import com.alexbegt.ghostkitchen.entity.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Item {

  private Long id;
  private UUID uuid;
  private String name;
  private BigDecimal price;
  private Set<Restaurant> restaurants;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 2;

    return prime * result + (name == null ? 0 : name.hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ItemEntity) {
      ItemEntity itemEntity = (ItemEntity) obj;

      return this.name.equals(itemEntity.getName()) && this.price.equals(itemEntity.getPrice());
    }

    return false;
  }
}
