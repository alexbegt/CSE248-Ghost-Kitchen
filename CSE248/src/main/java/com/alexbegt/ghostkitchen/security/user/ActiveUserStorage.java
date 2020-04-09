package com.alexbegt.ghostkitchen.security.user;

import java.util.ArrayList;
import java.util.List;

public class ActiveUserStorage {
  public List<String> users;

  public ActiveUserStorage() {
    this.users = new ArrayList<>();
  }

  /**
   * Gets the list of current users
   *
   * @return the active users
   */
  public List<String> getUsers() {
    return this.users;
  }

  /**
   * Sets the list of users currently active
   *
   * @param users  the new list of users
   */
  public void setUsers(List<String> users) {
    this.users = users;
  }
}
