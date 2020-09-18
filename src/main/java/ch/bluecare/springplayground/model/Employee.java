package ch.bluecare.springplayground.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

// JPA (Java Persistence API) annotation to make this object ready for storage ina JPA-based data store
@Entity
public class Employee {

  // Primary key which will be automatically populated by the JPA provider
  private @Id @GeneratedValue Long id;
  private String name;
  private String role;
  /*
  Possible expansion: Evolving REST API
  Suddenly there is a need for an employee's name to be split into firstName and lastName.
  However replacing name with new fields firstName and lastName would break existing clients.
  There would be a need to also upgrade them and Downtime = lost money.
  That's why you should follow the old rule: "Never delete a column in a DB"
  So we add the firstName and lastName field while keeping the (deprecated) name field.
  That means we have duplicate information but we support old and new clients. We can upgrade the server without requiring clients to upgrade at the same time.
   */

  Employee() {}

  public Employee(String name, String role) {
    this.name = name;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return Objects.equals(id, employee.id)
        && Objects.equals(name, employee.name)
        && Objects.equals(role, employee.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, role);
  }

  @Override
  public String toString() {
    return "Employee{" + "id=" + id + ", name='" + name + '\'' + ", role='" + role + '\'' + '}';
  }
}
