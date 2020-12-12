package ru.sfedu.Aisova.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.model.*;

import java.util.List;

public class DataProviderJdbc implements DataProvider{

    private static Logger log = LogManager.getLogger(DataProviderJdbc.class);

    @Override
    public boolean createService(String name, Double price, String description) throws Exception {
        return false;
    }

    @Override
    public boolean editService(long id, String name, Double price, String description) throws Exception {
        return false;
    }

    @Override
    public boolean deleteService(long id) throws Exception {
        return false;
    }

    @Override
    public Service getServiceById(long id) throws Exception {
        return null;
    }

    @Override
    public boolean createNewCustomer(String firstName, String lastName, String phone, String email, Integer discount) throws Exception {
        return false;
    }

    @Override
    public boolean editNewCustomer(long id, String firstName, String lastName, String phone, String email, Integer discount) throws Exception {
        return false;
    }

    @Override
    public boolean deleteNewCustomer(long id) throws Exception {
        return false;
    }

    @Override
    public NewCustomer getNewCustomerById(long id) throws Exception {
        return null;
    }

    @Override
    public boolean createRegularCustomer(String firstName, String lastName, String phone, String email, Integer countOfOrder) throws Exception {
        return false;
    }

    @Override
    public boolean editRegularCustomer(long id, String firstName, String lastName, String phone, String email, Integer countOfOrder) throws Exception {
        return false;
    }

    @Override
    public boolean deleteRegularCustomer(long id) throws Exception {
        return false;
    }

    @Override
    public RegularCustomer getRegularCustomerById(long id) throws Exception {
        return null;
    }

    @Override
    public boolean createMaster(String firstName, String lastName, String position, String phone, Double salary, List<Service> listService) throws Exception {
        return false;
    }

    @Override
    public boolean editMaster(long id, String firstName, String lastName, String position, String phone, Double salary, List<Service> listService) throws Exception {
        return false;
    }

    @Override
    public boolean deleteMaster(long id) throws Exception {
        return false;
    }

    @Override
    public Master getMasterById(long id) throws Exception {
        return null;
    }

    @Override
    public boolean createSalon(String address, List<Master> listMaster) throws Exception {
        return false;
    }

    @Override
    public boolean editSalon(long id, String address, List<Master> listMaster) throws Exception {
        return false;
    }

    @Override
    public boolean deleteSalon(long id) throws Exception {
        return false;
    }

    @Override
    public Salon getSalonById(long id) throws Exception {
        return null;
    }

    @Override
    public boolean createOrderItem(Service service, Double cost, Integer quantity) throws Exception {
        return false;
    }

    @Override
    public boolean editOrderItem(long id, Service service, Double cost, Integer quantity) throws Exception {
        return false;
    }

    @Override
    public boolean deleteOrderItem(long id) throws Exception {
        return false;
    }

    @Override
    public OrderItem getOrderItemById(long id) throws Exception {
        return null;
    }

    @Override
    public boolean createOrder(String created, List<OrderItem> item, Double cost, String status, Customer customer, String lastUpdated, String completed) throws Exception {
        return false;
    }

    @Override
    public boolean editOrder(long id, String created, List<OrderItem> item, Double cost, String status, Customer customer, String lastUpdated, String completed) throws Exception {
        return false;
    }

    @Override
    public boolean deleteOrder(long id) throws Exception {
        return false;
    }

    @Override
    public Order getOrderById(long id) throws Exception {
        return null;
    }

    @Override
    public Double calculateOrderValue(long orderId) {
        return null;
    }

    @Override
    public List<Order> viewOrderHistory(Order order) {
        return null;
    }

    @Override
    public List<Order> getListOfCurrentOrders(Order order, String status) {
        return null;
    }

    @Override
    public StringBuffer createCustomerReport() {
        return null;
    }

    @Override
    public List<Master> changeTheLisOfMaster() {
        return null;
    }

    @Override
    public Double calculateSalaryOfTheMaster() {
        return null;
    }

    @Override
    public boolean assignService(long serviceId, long masterId) {
        return false;
    }

    @Override
    public StringBuffer createMasterProgressReport() {
        return null;
    }
}
