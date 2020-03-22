package com.alexbegt.ghostkitchen.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RestaurantEntity extends UuidEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "street_address")
  private String streetAddress;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "zip_code")
  private String zipCode;

  @Column(name = "phone_number")
  private String phoneNumber;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "restaurant_items",
    joinColumns = @JoinColumn(name = "restaurant_id"),
    inverseJoinColumns = @JoinColumn(name = "item_id")
  )
  private Set<ItemEntity> items;
}
