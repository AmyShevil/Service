package ru.sfedu.Aisova.umbrello;

import javax.xml.ws.Service;
import java.util.List;

/**
 * Class Master
 */
public class Master {

  //
  // Fields
  //

  private Long id;
  private String firstName;
  private String lastName;
  private String position;
  private List<Service> serviceList;
  private String phone;
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
   * @param newVar the new value of id
   */
  public void setId (Long newVar) {
    id = newVar;
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
   * @param newVar the new value of firstName
   */
  public void setFirstName (String newVar) {
    firstName = newVar;
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
   * @param newVar the new value of lastName
   */
  public void setLastName (String newVar) {
    lastName = newVar;
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
   * @param newVar the new value of position
   */
  public void setPosition (String newVar) {
    position = newVar;
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
   * @param newVar the new value of serviceList
   */
  public void setServiceList (List<Service> newVar) {
    serviceList = newVar;
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
   * @param newVar the new value of phone
   */
  public void setPhone (String newVar) {
    phone = newVar;
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
   * @param newVar the new value of salary
   */
  public void setSalary (Double newVar) {
    salary = newVar;
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

}
