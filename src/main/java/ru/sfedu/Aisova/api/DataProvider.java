package ru.sfedu.Aisova.api;

import ru.sfedu.Aisova.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The interface Data provider.
 */
public interface DataProvider {

    /**
     * Create service.
     *
     * @param name the value of name
     * @param price the value of price
     * @param description the value of description
     * @return is created
     * @throws NullPointerException when input variables are null
     */
    boolean createService (String name, Double price, String description) throws Exception;

    /**
     * Edit service.
     *
     * @param id the value of id
     * @param name the value of name
     * @param price the value of price
     * @param description the value of description
     * @return is edited
     * @throws NullPointerException when input variables are null
     */
    boolean editService (long id, String name, Double price, String description) throws Exception;

    /**
     * Delete service.
     *
     * @param id the value of id
     * @return is deleted
     * @throws NullPointerException when input variables are null
     */
    boolean deleteService (long id) throws Exception;

    /**
     * Get service by id.
     *
     * @param id the value of id
     * @return the service
     * @throws NullPointerException when input variables are null
     */
    Service getServiceById(long id) throws Exception;

    /**
     * Create new customer.
     *
     * @param firstName the value of first name
     * @param lastName the value of last name
     * @param phone the value of phone
     * @param email the value of email
     * @param discount the value of discount
     * @return is created
     * @throws NullPointerException when input variables are null
     */
    boolean createNewCustomer(String firstName, String lastName, String phone, String email, Integer discount) throws Exception;

    /**
     * Edit new customer.
     *
     * @param id the value of id
     * @param firstName the value of first name
     * @param lastName the value of last name
     * @param phone the value of phone
     * @param email the value of email
     * @param discount the value of discount
     * @return is edited
     * @throws NullPointerException when input variables are null
     */
    boolean editNewCustomer(long id, String firstName, String lastName, String phone, String email, Integer discount) throws Exception;

    /**
     * Delete new customer.
     *
     * @param id the value of id
     * @return is deleted
     * @throws NullPointerException when input variables are null
     */
    boolean deleteNewCustomer(long id) throws Exception;

    /**
     * Get new customer by id.
     *
     * @param id the value of id
     * @return the new customer
     * @throws NullPointerException when input variables are null
     */
    Optional<NewCustomer> getNewCustomerById(long id) throws Exception;

    /**
     * Create regular customer.
     *
     * @param firstName the value of first name
     * @param lastName the value of last name
     * @param phone the value of phone
     * @param email the value of email
     * @param countOfOrder the value of discount
     * @return is created
     * @throws NullPointerException when input variables are null
     */
    boolean createRegularCustomer(String firstName, String lastName, String phone, String email, Integer countOfOrder) throws Exception;

    /**
     * Edit regular customer.
     *
     * @param id the value of id
     * @param firstName the value of first name
     * @param lastName the value of last name
     * @param phone the value of phone
     * @param email the value of email
     * @param countOfOrder the value of discount
     * @return is edited
     * @throws NullPointerException when input variables are null
     */
    boolean editRegularCustomer(long id, String firstName, String lastName, String phone, String email, Integer countOfOrder) throws Exception;

    /**
     * Delete regular customer.
     *
     * @param id the value of id
     * @return is deleted
     * @throws NullPointerException when input variables are null
     */
    boolean deleteRegularCustomer(long id) throws Exception;

    /**
     * Get regular customer by id.
     *
     * @param id the value of id
     * @return the regular customer
     * @throws NullPointerException when input variables are null
     */
    Optional<RegularCustomer> getRegularCustomerById(long id) throws Exception;

    /**
     * Create master.
     *
     * @param firstName the value of first name
     * @param lastName the value of last name
     * @param position the value of position
     * @param phone the value of phone
     * @param salary the value of salary
     * @param listService the value of list service
     * @return is created
     * @throws NullPointerException when input variables are null
     */
    boolean createMaster (String firstName, String lastName, String position, String phone, Double salary, List<Service> listService) throws Exception;

    /**
     * Edit master.
     *
     * @param id the value of id
     * @param firstName the value of first name
     * @param lastName the value of last name
     * @param position the value of position
     * @param phone the value of phone
     * @param salary the value of salary
     * @param listService the value of list service
     * @return is edited
     * @throws NullPointerException when input variables are null
     */
    boolean editMaster (long id, String firstName, String lastName, String position, String phone, Double salary, List<Service> listService) throws Exception;

    /**
     * Delete master.
     *
     * @param id the value of id
     * @return is deleted
     * @throws NullPointerException when input variables are null
     */
    boolean deleteMaster (long id) throws Exception;

    /**
     * Get master by id.
     *
     * @param id the value of id
     * @return the master
     */
    Master getMasterById(long id) throws Exception;

    /**
     * Create salon.
     *
     * @param address the value of address
     * @param listMaster the value of list master
     * @return is created
     * @throws NullPointerException when input variables are null
     */
    boolean createSalon (String address, List<Master> listMaster) throws Exception;

    /**
     * Edit salon.
     *
     * @param id the value of id
     * @param address the value of address
     * @param listMaster the value of list master
     * @return is edited
     * @throws NullPointerException when input variables are null
     */
    boolean editSalon (long id, String address, List<Master> listMaster) throws Exception;

    /**
     * Delete salon.
     *
     * @param id the value of id
     * @return is deleted
     * @throws NullPointerException when input variables are null
     */
    boolean deleteSalon (long id) throws Exception;

    /**
     * Get salon by id.
     *
     * @param id the value of id
     * @return the salon
     * @throws NullPointerException when input variables are null
     */
    Salon getSalonById(long id) throws Exception;

    /**
     * Create order item.
     *
     * @param service the value of service
     * @param quantity the value of quantity
     * @return is created
     * @throws NullPointerException when input variables are null
     */
    boolean createOrderItem (Service service, Integer quantity) throws Exception;

    /**
     * Edit order item.
     *
     * @param id the value of id
     * @param service the value of service
     * @param cost the value of cost
     * @param quantity the value of quantity
     * @return is edited
     * @throws NullPointerException when input variables are null
     */
    boolean editOrderItem (long id, Service service, Double cost, Integer quantity) throws Exception;

    /**
     * Delete order item.
     *
     * @param id the value of id
     * @return is deleted
     * @throws NullPointerException when input variables are null
     */
    boolean deleteOrderItem (long id) throws Exception;

    /**
     * Get order item by id.
     *
     * @param id the value of id
     * @return the order item
     * @throws NullPointerException when input variables are null
     */
    OrderItem getOrderItemById(long id) throws Exception;

    /**
     * Create order.
     *
     * @param created the value of created
     * @param item the value of item
     * @param cost the value of cost
     * @param status the value of status
     * @param customerId the value of customer id
     * @param lastUpdated the value of last updated
     * @param completed the value of completed
     * @return is created
     * @throws NullPointerException when input variables are null
     */
    boolean createOrder (String created, List<OrderItem> item, Double cost, String status, long customerId, String lastUpdated, String completed) throws Exception;

    /**
     * Edit order.
     *
     * @param id the value of id
     * @param created the value of created
     * @param item the value of item
     * @param cost the value of cost
     * @param status the value of status
     * @param customerId the value of customer id
     * @param lastUpdated the value of last updated
     * @param completed the value of completed
     * @return is edited
     * @throws NullPointerException when input variables are null
     */
    boolean editOrder (long id, String created, List<OrderItem> item, Double cost, String status, long customerId, String lastUpdated, String completed) throws Exception;

    /**
     * Delete order.
     *
     * @param id the value of id
     * @return is deleted
     * @throws NullPointerException when input variables are null
     */
    boolean deleteOrder (long id) throws Exception;

    /**
     * Get order by id.
     *
     * @param id the value of id
     * @return the order
     * @throws NullPointerException when input variables are null
     */
    Order getOrderById(long id) throws Exception;

    Double calculateOrderValue(long orderId) throws Exception;
    List<Order> viewOrderHistory(long customerId) throws Exception;
    List<Order> getListOfCurrentOrders(long customerId, String status) throws Exception;
    StringBuffer createCustomerReport(long customerId) throws Exception;
    List<Master> changeTheLisOfMaster(long salonId) throws Exception;
    boolean assignService(List<Service> service, long masterId) throws Exception;
    StringBuffer createMasterReport(long masterId) throws Exception;

}
