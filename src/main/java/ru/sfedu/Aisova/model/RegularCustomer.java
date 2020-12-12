package ru.sfedu.Aisova.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;

import java.util.Objects;

/**
 * Class RegularCustomer
 */
public class RegularCustomer extends Customer {

  //
  // Fields
  //

  @Element
  @CsvBindByPosition(position = 5)
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
   * @param numberOfOrders the new value of numberOfOrders
   */
  public void setNumberOfOrders (Integer numberOfOrders) {
    this.numberOfOrders = numberOfOrders;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    RegularCustomer that = (RegularCustomer) o;
    return numberOfOrders.equals(that.numberOfOrders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), numberOfOrders);
  }

  @Override
  public String toString() {
    return "RegularCustomer{" +
            "numberOfOrders=" + numberOfOrders +
            '}';
  }
}
