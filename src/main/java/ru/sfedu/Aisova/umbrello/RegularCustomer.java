package ru.sfedu.Aisova.umbrello;

/**
 * Class RegularCustomer
 */
public class RegularCustomer extends Customer {

  //
  // Fields
  //

  private Integer numberOfOrders;
  
  //
  // Constructors
  //
  public RegularCustomer () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of numberOfOrders
   * @param newVar the new value of numberOfOrders
   */
  public void setNumberOfOrders (Integer newVar) {
    numberOfOrders = newVar;
  }

  /**
   * Get the value of numberOfOrders
   * @return the value of numberOfOrders
   */
  public Integer getNumberOfOrders () {
    return numberOfOrders;
  }

  //
  // Other methods
  //

}
