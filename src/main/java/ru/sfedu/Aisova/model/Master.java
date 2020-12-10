package ru.sfedu.Aisova.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.converters.ServiceListConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Class Master
 */
public class Master implements Serializable {
  private static Logger log = LogManager.getLogger(Master.class);

  //
  // Fields
  //

  @CsvBindByPosition(position = 0)
  private long id;

  @CsvBindByPosition(position = 1)
  private String firstName;

  @CsvBindByPosition(position = 2)
  private String lastName;

  @CsvBindByPosition(position = 3)
  private String position;

  @CsvBindByPosition(position = 4)
  private String phone;

  @CsvBindByPosition(position = 5)
  private Double salary;

  @CsvCustomBindByPosition(position = 6, converter = ServiceListConverter.class)
  private List<Service> listService;
  
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
  public void setId (long id) {
    this.id = id;
  }

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
   * Set the value of listService
   * @param listService the new value of listService
   */
  public void setListService (List<Service> listService) {this.listService = listService;
  }

  /**
   * Get the value of listService
   * @return the value of listService
   */
  public List<Service> getListService () {return this.listService;
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
    return id == master.id &&
            firstName.equals(master.firstName) &&
            lastName.equals(master.lastName) &&
            Objects.equals(position, master.position) &&
            listService.equals(master.listService) &&
            phone.equals(master.phone) &&
            Objects.equals(salary, master.salary);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, position, listService, phone, salary);
  }

  @Override
  public String toString() {
    return "Master{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", position='" + position + '\'' +
            ", listService=" + listService +
            ", phone='" + phone + '\'' +
            ", salary=" + salary +
            '}';
  }
}
