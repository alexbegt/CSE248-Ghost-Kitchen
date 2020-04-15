package com.alexbegt.ghostkitchen.security.login;

import com.alexbegt.ghostkitchen.security.user.ActiveUserStorage;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

@Component
public class LoggedInUser implements HttpSessionBindingListener {

  private String username;
  private ActiveUserStorage activeUserStorage;

  public LoggedInUser() {
  }

  public LoggedInUser(String username, ActiveUserStorage activeUserStorage) {
    this.username = username;
    this.activeUserStorage = activeUserStorage;
  }

  /**
   * When the logged user event is bounded, it adds a user to the active user storage
   *
   * @param event the http event
   */
  @Override
  public void valueBound(HttpSessionBindingEvent event) {
    List<String> users = this.activeUserStorage.getUsers();
    LoggedInUser user = (LoggedInUser) event.getValue();

    if (!users.contains(user.getUsername())) {
      users.add(user.getUsername());
    }
  }

  /**
   * When the logged user event is unbounded, it subtracts a user from the active user storage
   *
   * @param event the http event
   */
  @Override
  public void valueUnbound(HttpSessionBindingEvent event) {
    List<String> users = this.activeUserStorage.getUsers();
    LoggedInUser user = (LoggedInUser) event.getValue();

    if (user != null && user.getUsername() != null) {
      users.remove(user.getUsername());
    }
  }

  /**
   * Gets the username associated with the selected logged user
   *
   * @return the username associated with the LoggedUser
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Sets the username associated with the selected logged user
   *
   * @param username the new username
   */
  public void setUsername(String username) {
    this.username = username;
  }
}
