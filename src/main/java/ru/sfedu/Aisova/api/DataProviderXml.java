package ru.sfedu.Aisova.api;

import ru.sfedu.Aisova.enums.OrderStatus;
import ru.sfedu.Aisova.model.*;

import java.util.List;

public class DataProviderXml implements DataProvider{
    @Override
    public boolean createService(String name, Double price, String description) {
        return false;
    }

    @Override
    public boolean editService(long id, Service editedService) {
        return false;
    }

    @Override
    public boolean deleteService(long id) {
        return false;
    }

    @Override
    public Service getServiceById(long id) {
        return null;
    }

    @Override
    public boolean createNewCustomer(String firstName, String lastName, String phone, String email, Integer discount) {
        return false;
    }

    @Override
    public boolean editNewCustomer(long id, NewCustomer editCustomer) {
        return false;
    }

    @Override
    public boolean deleteNewCustomer(long id) {
        return false;
    }

    @Override
    public NewCustomer getNewCustomerById(long id) {
        return null;
    }

    @Override
    public boolean createRegularCustomer(String firstName, String lastName, String phone, String email, Integer countOfOrder) {
        return false;
    }

    @Override
    public boolean editRegularCustomer(long id, RegularCustomer editCustomer) {
        return false;
    }

    @Override
    public boolean deleteRegularCustomer(long id) {
        return false;
    }

    @Override
    public RegularCustomer getRegularCustomerById(long id) {
        return null;
    }

    @Override
    public boolean createMaster(String firstName, String lastName, String position, List<Service> listService, String phone, Double salary) {
        return false;
    }

    @Override
    public boolean editMaster(long id, Master editedMaster) {
        return false;
    }

    @Override
    public boolean deleteMaster(long id) {
        return false;
    }

    @Override
    public Master getMasterById(long id) {
        return null;
    }

    @Override
    public boolean createSalon(String address, List<Master> listMaster) {
        return false;
    }

    @Override
    public boolean editSalon(long id, Salon editedSalon) {
        return false;
    }

    @Override
    public boolean deleteSalon(long id) {
        return false;
    }

    @Override
    public Salon getSalonById(long id) {
        return null;
    }

    @Override
    public boolean createOrderItem(long number, Service service, Double cost, Integer quantity) {
        return false;
    }

    @Override
    public boolean editOrderItem(long id, OrderItem editedOrderItem) {
        return false;
    }

    @Override
    public boolean deleteOrderItem(long id) {
        return false;
    }

    @Override
    public OrderItem getOrderItemById(long id) {
        return null;
    }

    @Override
    public boolean createOrder(long id, String created, List<OrderItem> item, Double cost, OrderStatus status, Customer customer, String lastUpdated, String completed) {
        return false;
    }

    @Override
    public boolean editOrder(long id, Order editedOrder) {
        return false;
    }

    @Override
    public boolean deleteOrder(long id) {
        return false;
    }

    @Override
    public Order getOrderById(long id) {
        return null;
    }

    @Override
    public Double calculateOrderValue() {
        return null;
    }

    @Override
    public List<Order> viewOrderHistory() {
        return null;
    }

    @Override
    public List<Order> getListOfCurrentOrders() {
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
