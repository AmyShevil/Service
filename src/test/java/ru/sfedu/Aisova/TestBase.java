package ru.sfedu.Aisova;

import ru.sfedu.Aisova.model.*;
import java.util.List;


public class TestBase {

    public Customer createCustomer(long id, String firstName, String lastName, String phone, String email){
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhone(phone);
        customer.setEmail(email);
        return customer;
    }

    public NewCustomer createNewCustomer (long id, String firstName, String lastName, String phone, String email, Integer discount){
        NewCustomer newCustomer = new NewCustomer();
        newCustomer.setId(id);
        newCustomer.setFirstName(firstName);
        newCustomer.setLastName(lastName);
        newCustomer.setPhone(phone);
        newCustomer.setEmail(email);
        newCustomer.setDiscount(discount);
        return newCustomer;
    }

    public RegularCustomer createRegularCustomer (long id, String firstName, String lastName, String phone, String email,Integer numberOfOrders){
        RegularCustomer regularCustomer = new RegularCustomer();
        regularCustomer.setId(id);
        regularCustomer.setFirstName(firstName);
        regularCustomer.setLastName(lastName);
        regularCustomer.setPhone(phone);
        regularCustomer.setEmail(email);
        regularCustomer.setNumberOfOrders(numberOfOrders);
        return regularCustomer;
    }


    public static Master createMaster(long id, String firstName, String lastName, String position, String phone, Double salary){
        Master master = new Master();
        master.setId(id);
        master.setFirstName(firstName);
        master.setLastName(lastName);
        master.setPosition(position);
        master.setPhone(phone);
        master.setSalary(salary);
        return master;
    }

    public Order createOrder (long id, String created, List<OrderItem> item, String status, long customerId){
        Order order = new Order();
        order.setId(id);
        order.setCreated(created);
        order.setItem(item);
        order.setStatus(status);
        order.setCustomerId(customerId);
        return order;
    }

    public static OrderItem createOrderItem(long number, Service service, Double cost, Integer quantity){
        OrderItem orderItem = new OrderItem();
        orderItem.setId(number);
        orderItem.setService(service);
        orderItem.setCost(cost);
        orderItem.setQuantity(quantity);
        return orderItem;
    }

    public Salon createSalon (long id, String address, List<Master> listMaster){
        Salon salon = new Salon();
        salon.setId(id);
        salon.setAddress(address);
        salon.setListMaster(listMaster);
        return salon;
    }

    public static Service createService(long id, String name, Double price, String description){
        Service service = new Service();
        service.setId(id);
        service.setName(name);
        service.setPrice(price);
        service.setDescription(description);
        return service;
    }
}


