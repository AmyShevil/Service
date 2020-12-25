package ru.sfedu.Aisova.model;

import com.opencsv.bean.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.Aisova.converters.OrderItemConverter;

import java.util.List;
import java.util.Objects;

/**
 * Class Order
 */
@Root(name = "Order")
public class Order {

  //
  // Fields
  //

  @Attribute
  @CsvBindByPosition(position = 0)
  private long id;

  @Element
  @CsvBindByPosition(position = 1)
  private String created;

  @ElementList
  @CsvCustomBindByPosition(position = 2, converter = OrderItemConverter.class)
  private List<OrderItem> item;

  @Element
  @CsvBindByPosition(position = 3)
  private String status;

  @Element
  @CsvBindByPosition(position = 4)
  private long customerId;

  
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
   * Set the value of created
   * @param created the new value of created
   */
  public void setCreated (String created) {
    this.created = created;
  }

  /**
   * Get the value of created
   * @return the value of created
   */
  public String getCreated () {
    return created;
  }

  /**
   * Set the value of item
   * @param item the new value of item
   */
  public void setItem (List<OrderItem> item) {
    this.item = item;
  }

  /**
   * Get the value of item
   * @return the value of item
   */
  public List<OrderItem> getItem () {
    return item;
  }


  /**
   * Set the value of status
   * @param status the new value of status
   */
  public void setStatus (String status) {
    this.status = status;
  }

  /**
   * Get the value of status
   * @return the value of status
   */
  public String getStatus () {
    return status;
  }

  /**
   * Set the value of customer
   * @param customerId the new value of customerId
   */
  public void setCustomerId (long customerId) {
    this.customerId = customerId;
  }

  /**
   * Get the value of customer
   * @return the value of customer
   */
  public long getCustomerId () {
    return customerId;
  }

  //
  // Other methods
  //


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return id == order.id && customerId == order.customerId && Objects.equals(created, order.created) && Objects.equals(item, order.item) && Objects.equals(status, order.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, created, item, status, customerId);
  }

  @Override
  public String toString() {
    return "Order{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", item=" + item +
            ", status='" + status + '\'' +
            ", customerId=" + customerId +
            '}';
  }
}

