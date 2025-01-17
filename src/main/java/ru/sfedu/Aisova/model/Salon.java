package ru.sfedu.Aisova.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.Aisova.converters.MastersConverter;

import java.util.List;
import java.util.Objects;

/**
 * Class Salon
 */
@Root(name = "Salon")
public class Salon {

  //
  // Fields
  //

  @Attribute
  @CsvBindByPosition(position = 0)
  private long id;

  @Element
  @CsvBindByPosition(position = 1)
  private String address;

  @ElementList
  @CsvCustomBindByPosition(position = 2, converter = MastersConverter.class)
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
   * Set the value of address
   * @param address the new value of address
   */
  public void setAddress (String address) {
    this.address = address;
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
   * @param listMaster the new value of listMaster
   */
  public void setListMaster (List<Master> listMaster) {
    this.listMaster = listMaster;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Salon salon = (Salon) o;
    return id == salon.id &&
            address.equals(salon.address) &&
            listMaster.equals(salon.listMaster);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, address, listMaster);
  }

  @Override
  public String toString() {
    return "Salon{" +
            "id=" + id +
            ", address='" + address + '\'' +
            ", listMaster=" + listMaster +
            '}';
  }
}
