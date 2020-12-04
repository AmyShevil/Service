package ru.sfedu.Aisova.model;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class Customer
 */
public class Customer implements Serializable {

  //
  // Fields
  //

  @CsvBindByName
  private long id;

  @CsvBindByName
  private String firstName;

  @CsvBindByName
  private String lastName;

  @CsvBindByName
  private String phone;

  @CsvBindByName
  private String email;
  
  //
  // Constructors
  //
  public Customer () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of id
   * @param id the new value of id
   */
  public void setId (long id) { this.id = id; }

  /**
   * Get the value of id
   * @return the value of id
   */
  public long getId () {
    return id;
  }

  /**
   * Set the value of firstName
   * @param firstName the new value of firstName
   */
  public void setFirstName (String firstName) {
    this.firstName = firstName;
  }

  /**
   * Get the value of firstName
   * @return the value of firstName
   */
  public String getFirstName () {
    return firstName;
  }

  /**
   * Set the value of lastName
   * @param lastName the new value of lastName
   */
  public void setLastName (String lastName) {
    this.lastName = lastName;
  }

  /**
   * Get the value of lastName
   * @return the value of lastName
   */
  public String getLastName () {
    return lastName;
  }

  /**
   * Set the value of phone
   * @param phone the new value of phone
   */
  public void setPhone (String phone) {
    this.phone = phone;
  }

  /**
   * Get the value of phone
   * @return the value of phone
   */
  public String getPhone () {
    return phone;
  }

  /**
   * Set the value of email
   * @param email the new value of email
   */
  public void setEmail (String email) {
    this.email = email;
  }

  /**
   * Get the value of email
   * @return the value of email
   */
  public String getEmail () {
    return email;
  }

  //
  // Other methods
  //


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;
    return id == customer.id &&
            firstName.equals(customer.firstName) &&
            lastName.equals(customer.lastName) &&
            phone.equals(customer.phone) &&
            Objects.equals(email, customer.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, phone, email);
  }

  @Override
  public String toString() {
    return "Customer{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", phone='" + phone + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
