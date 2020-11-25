package ru.sfedu.Aisova.umbrello;

import com.opencsv.bean.CsvBindByName;

import java.util.Date;
import java.util.Objects;

/**
 * Class Order
 */
public class Order {

  //
  // Fields
  //

  @CsvBindByName
  private Long id;

  @CsvBindByName
  private Date created;

  @CsvBindByName
  private OrderItem item;

  @CsvBindByName
  private Double cost;

  @CsvBindByName
  private OrderStatus status;

  @CsvBindByName
  private Customer customer;

  @CsvBindByName
  private Date lastUpdated;

  @CsvBindByName
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
   * Set the value of created
   * @param created the new value of created
   */
  public void setCreated (Date created) {
    this.created = created;
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
   * @param item the new value of item
   */
  public void setItem (OrderItem item) {
    this.item = item;
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
   * Set the value of status
   * @param status the new value of status
   */
  public void setStatus (OrderStatus status) {
    this.status = status;
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
   * @param customer the new value of customer
   */
  public void setCustomer (Customer customer) {
    this.customer = customer;
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
   * @param lastUpdated the new value of lastUpdated
   */
  public void setLastUpdated (Date lastUpdated) {
    this.lastUpdated = lastUpdated;
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
   * @param completed the new value of completed
   */
  public void setCompleted (Date completed) {
    this.completed = completed;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return id.equals(order.id) &&
            created.equals(order.created) &&
            item.equals(order.item) &&
            cost.equals(order.cost) &&
            status == order.status &&
            customer.equals(order.customer) &&
            Objects.equals(lastUpdated, order.lastUpdated) &&
            completed.equals(order.completed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, created, item, cost, status, customer, lastUpdated, completed);
  }

  @Override
  public String toString() {
    return "Order{" +
            "id=" + id +
            ", created=" + created +
            ", item=" + item +
            ", cost=" + cost +
            ", status=" + status +
            ", customer=" + customer +
            ", lastUpdated=" + lastUpdated +
            ", completed=" + completed +
            '}';
  }
}
enum OrderStatus{
  CREATED,
  PROCESSING,
  COMPLETED,
  CANCELED
}
