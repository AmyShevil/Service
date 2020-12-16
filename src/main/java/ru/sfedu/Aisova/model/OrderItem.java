package ru.sfedu.Aisova.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.Aisova.converters.ServiceConverter;

import java.util.Objects;

/**
 * Class OrderItem
 */
@Root(name = "OrderItem")
public class OrderItem {

  //
  // Fields
  //

  @Attribute
  @CsvBindByPosition(position = 0)
  private long id;

  @Element
  @CsvCustomBindByPosition(position = 1, converter = ServiceConverter.class)
  private Service service;

  @Element
  @CsvBindByPosition(position = 2)
  private Double cost;

  @Element
  @CsvBindByPosition(position = 3)
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
   * @param id the new value of number
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Get the value of number
   * @return the value of number
   */
  public long getId() {
    return id;
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
    return id == orderItem.id &&
            service.equals(orderItem.service) &&
            cost.equals(orderItem.cost) &&
            quantity.equals(orderItem.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, service, cost, quantity);
  }

  @Override
  public String toString() {
    return "OrderItem{" +
            "number=" + id +
            ", service=" + service +
            ", cost=" + cost +
            ", quantity=" + quantity +
            '}';
  }

}
