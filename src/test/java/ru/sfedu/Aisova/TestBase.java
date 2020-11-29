package ru.sfedu.Aisova;

import ru.sfedu.Aisova.bean.*;

import java.util.Date;
import java.util.List;

public class TestBase {

    public User createUser (long id, String name){
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }

    public Customer createCustomer(long id, String firstName, String lastName, String phone, String email){
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhone(phone);
        customer.setEmail(email);
        return customer;
    }

    public NewCustomer createNewCustomer (Integer discount){
        NewCustomer newCustomer = new NewCustomer();
        newCustomer.setDiscount(discount);
        return newCustomer;
    }

    public RegularCustomer createRegularCustomer (Integer numberOfOrders){
        RegularCustomer regularCustomer = new RegularCustomer();
        regularCustomer.setNumberOfOrders(numberOfOrders);
        return regularCustomer;
    }


    public Master createMaster (long id, String firstName, String lastName, String position, List<Service> listService, String phone, Double salary){
        Master master = new Master();
        master.setId(id);
        master.setFirstName(firstName);
        master.setLastName(lastName);
        master.setPosition(position);
        master.setServiceList(listService);
        master.setPhone(phone);
        master.setSalary(salary);
        return master;
    }

    public Order createOrder (long id, String created, OrderItem item, Double cost, Order.OrderStatus status, Customer customer, String lastUpdated, String completed){
        Order order = new Order();
        order.setId(id);
        order.setCreated(created);
        order.setItem(item);
        order.setCost(cost);
        order.setStatus(status);
        order.setCustomer(customer);
        order.setLastUpdated(lastUpdated);
        order.setCompleted(completed);
        return order;
    }

    public OrderItem createOrderItem (long number, Service service, Double cost, Integer quantity){
        OrderItem orderItem = new OrderItem();
        orderItem.setNumber(number);
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

    public Service createService (long id, String name, Double price, String description){
        Service service = new Service();
        service.setId(id);
        service.setName(name);
        service.setPrice(price);
        service.setDescription(description);
        return service;
    }
}


