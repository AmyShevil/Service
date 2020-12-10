package ru.sfedu.Aisova.api;

import ru.sfedu.Aisova.enums.OrderStatus;
import ru.sfedu.Aisova.model.*;
import java.util.List;

public interface DataProvider {

    boolean createService (String name, Double price, String description);
    boolean editService (long id, String name, Double price, String description);
    boolean deleteService (long id);
    Service getServiceById(long id);

    boolean createNewCustomer(String firstName, String lastName, String phone, String email, Integer discount);
    boolean editNewCustomer(long id, String firstName, String lastName, String phone, String email, Integer discount);
    boolean deleteNewCustomer(long id);
    NewCustomer getNewCustomerById(long id);

    boolean createRegularCustomer(String firstName, String lastName, String phone, String email, Integer countOfOrder);
    boolean editRegularCustomer(long id, String firstName, String lastName, String phone, String email, Integer countOfOrder);
    boolean deleteRegularCustomer(long id);
    RegularCustomer getRegularCustomerById(long id);

    boolean createMaster (String firstName, String lastName, String position, String phone, Double salary, List<Service> service);
    boolean editMaster (long id, String firstName, String lastName, String position, String phone, Double salary, List<Service> listService);
    boolean deleteMaster (long id);
    Master getMasterById(long id);

    boolean createSalon (String address, List<Master> listMaster);
    boolean editSalon (long id, String address, List<Master> listMaster);
    boolean deleteSalon (long id);
    Salon getSalonById(long id);

    boolean createOrderItem (Service service, Double cost, Integer quantity);
    boolean editOrderItem (long id, Service service, Double cost, Integer quantity);
    boolean deleteOrderItem (long id);
    OrderItem getOrderItemById(long id);

    boolean createOrder (String created, List<OrderItem> item, Double cost, String status, Customer customer, String lastUpdated, String completed);
    boolean editOrder (long id, String created, List<OrderItem> item, Double cost, String status, Customer customer, String lastUpdated, String completed);
    boolean deleteOrder (long id);
    Order getOrderById(long id);

    Double calculateOrderValue();
    List<Order> viewOrderHistory();
    List<Order> getListOfCurrentOrders();
    StringBuffer createCustomerReport();
    List<Master> changeTheLisOfMaster();
    Double calculateSalaryOfTheMaster();
    boolean assignService(long serviceId, long masterId);
    StringBuffer createMasterProgressReport();

}
