package com.alexbegt.ghostkitchen.persistence.model.role;

import com.alexbegt.ghostkitchen.persistence.model.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToMany(mappedBy = "roles")
  private Collection<User> users;

  @ManyToMany
  @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
  private Collection<Privilege> privileges;

  private String name;

  public Role() {
  }

  public Role(final String name) {
    this.name = name;
  }

  /**
   * Get the unique id.
   *
   * @return the unique id.
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Sets the unique id for the role.
   *
   * @param id   the new unique id to use
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the role.
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name of the role
   *
   * @param name   the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Get the collection of users assigned to the selected role.
   *
   * @return the collection of users the role is assigned to
   */
  public Collection<User> getUsers() {
    return this.users;
  }

  /**
   * Sets the collection of users the selected role is assigned to.
   *
   * @param users   the new collection of users to use
   */
  public void setUsers(final Collection<User> users) {
    this.users = users;
  }

  /**
   * Get the privileges the role is assigned to.
   *
   * @return the privileges the role is assigned to
   */
  public Collection<Privilege> getPrivileges() {
    return this.privileges;
  }

  /**
   * Set the collection of privileges to a new set.
   *
   * @param privileges   the new set of privileges.
   */
  public void setPrivileges(final Collection<Privilege> privileges) {
    this.privileges = privileges;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());

    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (this.getClass() != obj.getClass()) {
      return false;
    }

    final Role role = (Role) obj;

    return this.name.equals(role.name);
  }

  @Override
  public String toString() {
    return "Role [name=" + this.name +
      ", id=" + this.id +
      "]";
  }
}
