package ru.sfedu.Aisova.umbrello;

import com.opencsv.bean.CsvBindByName;

import javax.xml.ws.Service;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Class Master
 */
public class Master implements Serializable {

  //
  // Fields
  //

  @CsvBindByName
  private Long id;

  @CsvBindByName
  private String firstName;

  @CsvBindByName
  private String lastName;

  @CsvBindByName
  private String position;

  @CsvBindByName
  private List<Service> serviceList;

  @CsvBindByName
  private String phone;

  @CsvBindByName
  private Double salary;
  
  //
  // Constructors
  //
  public Master () { };
  
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
  public void setId (Long id) {
    this.id = id;
  }

  /**
   * Get the value of id
   * @return the value of id
   */
  public Long getId () {
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
   * Set the value of position
   * @param position the new value of position
   */
  public void setPosition (String position) {
    this.position = position;
  }

  /**
   * Get the value of position
   * @return the value of position
   */
  public String getPosition () {
    return position;
  }

  /**
   * Set the value of serviceList
   * @param serviceList the new value of serviceList
   */
  public void setServiceList (List<Service> serviceList) {
    this.serviceList = serviceList;
  }

  /**
   * Get the value of serviceList
   * @return the value of serviceList
   */
  public List<Service> getServiceList () {
    return serviceList;
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
   * Set the value of salary
   * @param salary the new value of salary
   */
  public void setSalary (Double salary) {
    this.salary = salary;
  }

  /**
   * Get the value of salary
   * @return the value of salary
   */
  public Double getSalary () {
    return salary;
  }

  //
  // Other methods
  //


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Master master = (Master) o;
    return id.equals(master.id) &&
            firstName.equals(master.firstName) &&
            lastName.equals(master.lastName) &&
            position.equals(master.position) &&
            serviceList.equals(master.serviceList) &&
            phone.equals(master.phone) &&
            Objects.equals(salary, master.salary);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, position, serviceList, phone, salary);
  }

  @Override
  public String toString() {
    return "Master{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", position='" + position + '\'' +
            ", serviceList=" + serviceList +
            ", phone='" + phone + '\'' +
            ", salary=" + salary +
            '}';
  }
}
