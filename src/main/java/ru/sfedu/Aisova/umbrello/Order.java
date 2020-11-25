package ru.sfedu.Aisova.umbrello;

import java.util.Date;

/**
 * Class Order
 */
public class Order {

  //
  // Fields
  //

  private Long id;
  private Date created;
  private OrderItem item;
  private Double cost;
  private OrderStatus status;
  private Customer customer;
  private Date lastUpdated;
  private Date completed;
  
  //
  // Constructors
  //
  public Order () { };
  
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
   * Set the value of created
   * @param newVar the new value of created
   */
  public void setCreated (Date newVar) {
    created = newVar;
  }

  /**
   * Get the value of created
   * @return the value of created
   */
  public Date getCreated () {
    return created;
  }

  /**
   * Set the value of item
   * @param newVar the new value of item
   */
  public void setItem (OrderItem newVar) {
    item = newVar;
  }

  /**
   * Get the value of item
   * @return the value of item
   */
  public OrderItem getItem () {
    return item;
  }

  /**
   * Set the value of cost
   * @param newVar the new value of cost
   */
  public void setCost (Double newVar) {
    cost = newVar;
  }

  /**
   * Get the value of cost
   * @return the value of cost
   */
  public Double getCost () {
    return cost;
  }

  /**
   * Set the value of status
   * @param newVar the new value of status
   */
  public void setStatus (OrderStatus newVar) {
    status = newVar;
  }

  /**
   * Get the value of status
   * @return the value of status
   */
  public OrderStatus getStatus () {
    return status;
  }

  /**
   * Set the value of customer
   * @param newVar the new value of customer
   */
  public void setCustomer (Customer newVar) {
    customer = newVar;
  }

  /**
   * Get the value of customer
   * @return the value of customer
   */
  public Customer getCustomer () {
    return customer;
  }

  /**
   * Set the value of lastUpdated
   * @param newVar the new value of lastUpdated
   */
  public void setLastUpdated (Date newVar) {
    lastUpdated = newVar;
  }

  /**
   * Get the value of lastUpdated
   * @return the value of lastUpdated
   */
  public Date getLastUpdated () {
    return lastUpdated;
  }

  /**
   * Set the value of completed
   * @param newVar the new value of completed
   */
  public void setCompleted (Date newVar) {
    completed = newVar;
  }

  /**
   * Get the value of completed
   * @return the value of completed
   */
  public Date getCompleted () {
    return completed;
  }

  //
  // Other methods
  //

}
enum OrderStatus{
  CREATED,
  PROCESSING,
  COMPLETED,
  CANCELED
}
