package ru.sfedu.Aisova.umbrello;

/**
 * Class NewCustomer
 */
public class NewCustomer extends Customer {

  //
  // Fields
  //

  private Integer discount;
  
  //
  // Constructors
  //
  public NewCustomer () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of discount
   * @param newVar the new value of discount
   */
  public void setDiscount (Integer newVar) {
    discount = newVar;
  }

  /**
   * Get the value of discount
   * @return the value of discount
   */
  public Integer getDiscount () {
    return discount;
  }

  //
  // Other methods
  //

}
