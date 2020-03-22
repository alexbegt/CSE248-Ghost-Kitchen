package com.alexbegt.ghostkitchen.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Restaurant {

  private Long id;
  private UUID uuid;
  private String name;
  private String streetAddress;
  private String city;
  private String state;
  private String zipCode;
  private String phoneNumber;
  private Set<Item> items;
}
