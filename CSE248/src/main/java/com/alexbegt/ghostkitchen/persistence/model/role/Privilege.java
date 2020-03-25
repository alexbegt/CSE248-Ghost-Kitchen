package com.alexbegt.ghostkitchen.persistence.model.role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
public class Privilege {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "privileges")
  private Collection<Role> roles;

  public Privilege() {
  }

  public Privilege(final String name) {
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
   * Sets the unique id for the privilege.
   *
   * @param id   the new unique id to use
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the privilege.
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name of the privilege
   *
   * @param name   the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Get the roles the privilege is assigned to.
   *
   * @return the roles the privilege is assigned to
   */
  public Collection<Role> getRoles() {
    return this.roles;
  }

  /**
   * Set the collection of roles to a new set.
   *
   * @param roles   the new set of roles.
   */
  public void setRoles(final Collection<Role> roles) {
    this.roles = roles;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());

    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) { return true; }

    if (obj == null) { return false; }

    if (this.getClass() != obj.getClass()) { return false; }

    Privilege other = (Privilege) obj;

    if (this.name == null) {
      return other.name == null;
    }
    else {
      return this.name.equals(other.name);
    }
  }

  @Override
  public String toString() {
    return "Privilege [name=" + name + "]" +
      "[id=" + id +
      "]";
  }
}
