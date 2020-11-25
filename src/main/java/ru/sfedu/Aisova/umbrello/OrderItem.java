package ru.sfedu.Aisova.umbrello;

/**
 * Class OrderItem
 */
public class OrderItem {

  //
  // Fields
  //

  private Long number;
  private Service service;
  private Double cost;
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
   * @param newVar the new value of number
   */
  public void setNumber (Long newVar) {
    number = newVar;
  }

  /**
   * Get the value of number
   * @return the value of number
   */
  public Long getNumber () {
    return number;
  }

  /**
   * Set the value of service
   * @param newVar the new value of service
   */
  public void setService (Service newVar) {
    service = newVar;
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
   * Set the value of quantity
   * @param newVar the new value of quantity
   */
  public void setQuantity (Integer newVar) {
    quantity = newVar;
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

}
