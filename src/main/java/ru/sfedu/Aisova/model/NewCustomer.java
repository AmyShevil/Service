package ru.sfedu.Aisova.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Objects;

/**
 * Class NewCustomer
 */
@Root(name = "NewCustomer")
public class NewCustomer extends Customer {

  //
  // Fields
  //

  @Element
  @CsvBindByPosition(position = 5)
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
   * @param discount the new value of discount
   */
  public void setDiscount (Integer discount) {
    this.discount = discount;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    NewCustomer that = (NewCustomer) o;
    return Objects.equals(discount, that.discount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), discount);
  }

  @Override
  public String toString() {
    return "NewCustomer{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + '\'' +
            ", lastName='" + getLastName() + '\'' +
            ", phone='" + getPhone() + '\'' +
            ", email='" + getEmail() + '\'' +
            ", discount=" + discount +
            '}';
  }
}
