package ru.sfedu.Aisova.umbrello;

import java.util.List;

/**
 * Class Salon
 */
public class Salon {

  //
  // Fields
  //

  private Long id;
  private String address;
  private List<Master> listMaster;
  
  //
  // Constructors
  //
  public Salon () { };
  
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
   * Set the value of address
   * @param newVar the new value of address
   */
  public void setAddress (String newVar) {
    address = newVar;
  }

  /**
   * Get the value of address
   * @return the value of address
   */
  public String getAddress () {
    return address;
  }

  /**
   * Set the value of listMaster
   * @param newVar the new value of listMaster
   */
  public void setListMaster (List<Master> newVar) {
    listMaster = newVar;
  }

  /**
   * Get the value of listMaster
   * @return the value of listMaster
   */
  public List<Master> getListMaster () {
    return listMaster;
  }

  //
  // Other methods
  //

}
