/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.Aisova;

import com.opencsv.bean.CsvBindByName;
import java.io.Serializable;
import java.util.Objects;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author sp2
 */
//Аннотация для xml
@Root
public class Bean1 implements Serializable{
    //Задаём id в xml, как атрибут
    @Attribute
    @CsvBindByName
    long id;
    //Задаём name в xml, как элемент
    @Element
    @CsvBindByName
    String name;

    public Bean1() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bean1 other = (Bean1) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bean1{" + "id=" + id + ", name=" + name + '}';
    }
    
    
}
