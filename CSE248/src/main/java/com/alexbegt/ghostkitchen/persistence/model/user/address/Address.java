package com.alexbegt.ghostkitchen.persistence.model.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;



  @OneToOne(mappedBy = "cart")
  private User address;
}
