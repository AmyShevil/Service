package ru.sfedu.Aisova.umbrello;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

/**
 * Class Service
 */
public class Service {

  //
  // Fields
  //

  @CsvBindByName
  private Long id;

  @CsvBindByName
  private String name;

  @CsvBindByName
  private Double price;

  @CsvBindByName
  private String description;
  
  //
  // Constructors
  //
  public Service () { };
  
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
   * Set the value of name
   * @param name the new value of name
   */
  public void setName (String name) {
    this.name = name;
  }

  /**
   * Get the value of name
   * @return the value of name
   */
  public String getName () {
    return name;
  }

  /**
   * Set the value of price
   * @param price the new value of price
   */
  public void setPrice (Double price) {
    this.price = price;
  }

  /**
   * Get the value of price
   * @return the value of price
   */
  public Double getPrice () {
    return price;
  }

  /**
   * Set the value of description
   * @param description the new value of description
   */
  public void setDescription (String description) {
    this.description = description;
  }

  /**
   * Get the value of description
   * @return the value of description
   */
  public String getDescription () {
    return description;
  }

  //
  // Other methods
  //


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Service service = (Service) o;
    return id.equals(service.id) &&
            name.equals(service.name) &&
            price.equals(service.price) &&
            Objects.equals(description, service.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price, description);
  }

  @Override
  public String toString() {
    return "Service{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", description='" + description + '\'' +
            '}';
  }
}
