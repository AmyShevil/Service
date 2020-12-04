package ru.sfedu.Aisova.model;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

/**
 * Class OrderItem
 */
public class OrderItem {

  //
  // Fields
  //

  @CsvBindByName
  private long number;

  @CsvBindByName
  private Service service;

  @CsvBindByName
  private Double cost;

  @CsvBindByName
  private Integer quantity;
  
  //
  // Constructors
  //
  public OrderItem () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of number
   * @param number the new value of number
   */
  public void setNumber (long number) {
    this.number = number;
  }

  /**
   * Get the value of number
   * @return the value of number
   */
  public long getNumber () {
    return number;
  }

  /**
   * Set the value of service
   * @param service the new value of service
   */
  public void setService (Service service) {
    this.service = service;
  }

  /**
   * Get the value of service
   * @return the value of service
   */
  public Service getService () {
    return service;
  }

  /**
   * Set the value of cost
   * @param cost the new value of cost
   */
  public void setCost (Double cost) {
    this.cost = cost;
  }

  /**
   * Get the value of cost
   * @return the value of cost
   */
  public Double getCost () {
    return cost;
  }

  /**
   * Set the value of quantity
   * @param quantity the new value of quantity
   */
  public void setQuantity (Integer quantity) {
    this.quantity = quantity;
  }

  /**
   * Get the value of quantity
   * @return the value of quantity
   */
  public Integer getQuantity () {
    return quantity;
  }

  //
  // Other methods
  //


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderItem orderItem = (OrderItem) o;
    return number == orderItem.number &&
            service.equals(orderItem.service) &&
            cost.equals(orderItem.cost) &&
            quantity.equals(orderItem.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, service, cost, quantity);
  }

  @Override
  public String toString() {
    return "OrderItem{" +
            "number=" + number +
            ", service=" + service +
            ", cost=" + cost +
            ", quantity=" + quantity +
            '}';
  }

}
