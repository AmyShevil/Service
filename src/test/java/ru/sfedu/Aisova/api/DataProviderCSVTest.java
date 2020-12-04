package ru.sfedu.Aisova.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import ru.sfedu.Aisova.TestBase;
import ru.sfedu.Aisova.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static sun.management.Agent.error;

public class DataProviderCSVTest extends TestBase {

    public DataProviderCSVTest(){

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    @Test
    public void insertServiceSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertServiceSuccess");
        List<Service> listService = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Service service1 = createService(1, "service1", 1.0, "description1");
        Service service2 = createService(2, "service2", 2.0, "description2");
        Service service3 = createService(3, "service3", 3.0, "description3");
        listService.add(service1);
        listService.add(service2);
        listService.add(service3);
        instance.insertService(listService);
        assertEquals(service1, instance.getServiceById(1));
        assertEquals(service2, instance.getServiceById(2));
        assertEquals(service3, instance.getServiceById(3));
    }

    @Test
    public void insertServiceFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertServiceFail");
        List<Service> listService = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Service service1 = createService(1, "service1", 1.0, "description1");
        Service service2 = createService(2, "service2", 2.0, "description2");
        Service service3 = createService(3, "service3", 3.0, "description3");
        listService.add(service1);
        listService.add(service2);
        listService.add(service3);
        instance.insertService(listService);
        assertNull(instance.getServiceById(4));
    }

    @Test
    public void testGetByIdServiceSuccess() throws IOException {
        System.out.println("testGetByIdServiceSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getServiceById(2));
    }

    @Test
    public void testGetByIdServiceFail() throws IOException {
        System.out.println("testGetByIdServiceFail");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getServiceById(5));
    }

    @Test
    public void deleteServiceSuccess() throws IOException {
        System.out.println("deleteServiceSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteService(3);
        assertNull(instance.getServiceById(3));
    }

    @Test
    public void deleteServiceFail() throws IOException {
        System.out.println("deleteServiceFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteService(21);
        System.out.println(instance.getServiceById(21));
    }

    @Test
    public void rewriteServiceSuccess() throws IOException {
        System.out.println("rewriteServiceSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        long id = 1;
        String name = "rewriteName";
        Double price = 100.0;
        String description = "rewriteDescription";
        instance.rewriteService(id,name,price,description);
        System.out.println(instance.getServiceById(id));
    }

    @Test
    public void rewriteServiceFail() throws IOException {
        System.out.println("rewriteServiceFail");
        DataProviderCSV instance = new DataProviderCSV();
        long id = 20;
        String name = "rewriteName";
        Double price = 100.0;
        String description = "rewriteDescription";
        instance.rewriteService(id,name,price,description);
        System.out.println(instance.getServiceById(id));
    }

/*
    @Test
    public void insertCustomerSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertCustomerSuccess");
        List<Customer> listCustomer = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Customer customer1 = createCustomer(1, "firstName1", "lastName1", "phone1", "email1");
        Customer customer2 = createCustomer(2, "firstName2", "lastName2", "phone2", "email2");
        Customer customer3 = createCustomer(3, "firstName3", "lastName3", "phone3", "email3");
        listCustomer.add(customer1);
        listCustomer.add(customer2);
        listCustomer.add(customer3);
        instance.insertCustomer(listCustomer);
        assertEquals(customer1, instance.getCustomerById(1));
        assertEquals(customer2, instance.getCustomerById(2));
        assertEquals(customer3, instance.getCustomerById(3));
    }

    @Test
    public void insertCustomerFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertCustomerFail");
        List<Customer> listCustomer = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Customer customer1 = createCustomer(1, "firstName1", "lastName1", "phone1", "email1");
        Customer customer2 = createCustomer(2, "firstName2", "lastName2", "phone2", "email2");
        Customer customer3 = createCustomer(3, "firstName3", "lastName3", "phone3", "email3");
        listCustomer.add(customer1);
        listCustomer.add(customer2);
        listCustomer.add(customer3);
        instance.insertCustomer(listCustomer);
        assertNull(instance.getCustomerById(4));
    }

    @Test
    public void testGetByIdCustomer() throws IOException {
        System.out.println("testGetByIdCustomer");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getCustomerById(2));
    }

    @Test
    public void deleteCustomerSuccess() throws IOException {
        System.out.println("deleteCustomerSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteCustomer(3);
    }

    @Test
    public void deleteCustomerFail() throws IOException {
        System.out.println("deleteCustomerFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteCustomer(21);
    }

    @Test
    public void rewriteCustomerSuccess() throws IOException {
        System.out.println("rewriteCustomerSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.rewriteCustomer(1,"rewriteFirstName","rewriteLastName","rewritePhone", "rewriteEmail");
    }

    @Test
    public void rewriteCustomerFail() throws IOException {
        System.out.println("rewriteCustomerFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.rewriteCustomer(25,"rewriteFirstName","rewriteLastName","rewritePhone", "rewriteEmail");

    }

 */

    @Test
    public void insertNewCustomerSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertNewCustomerSuccess");
        List<NewCustomer> listNewCustomer = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        NewCustomer newCustomer1 = createNewCustomer(1, "firstName1", "lastName1", "phone1", "email1", 10);
        NewCustomer newCustomer2 = createNewCustomer(2, "firstName2", "lastName2", "phone2", "email2", 20);
        NewCustomer newCustomer3 = createNewCustomer(3, "firstName3", "lastName3", "phone3", "email3", 30);
        listNewCustomer.add(newCustomer1);
        listNewCustomer.add(newCustomer2);
        listNewCustomer.add(newCustomer3);
        instance.insertNewCustomer(listNewCustomer);
        assertEquals(newCustomer1, instance.getNewCustomerById(1));
        assertEquals(newCustomer2, instance.getNewCustomerById(2));
        assertEquals(newCustomer3, instance.getNewCustomerById(3));
    }

    @Test
    public void insertNewCustomerFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertNewCustomerFail");
        List<NewCustomer> listNewCustomer = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        NewCustomer newCustomer1 = createNewCustomer(1, "firstName1", "lastName1", "phone1", "email1", 10);
        NewCustomer newCustomer2 = createNewCustomer(2, "firstName2", "lastName2", "phone2", "email2", 20);
        NewCustomer newCustomer3 = createNewCustomer(3, "firstName3", "lastName3", "phone3", "email3", 30);
        listNewCustomer.add(newCustomer1);
        listNewCustomer.add(newCustomer2);
        listNewCustomer.add(newCustomer3);
        instance.insertNewCustomer(listNewCustomer);
        assertNull(instance.getNewCustomerById(4));
    }

    @Test
    public void testGetByIdNewCustomerSuccess() throws IOException {
        System.out.println("testGetByIdNewCustomerSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getNewCustomerById(2));
    }

    @Test
    public void testGetByIdNewCustomerFail() throws IOException {
        System.out.println("testGetByIdNewCustomerSuccessFail");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getNewCustomerById(5));
    }

    @Test
    public void deleteNewCustomerSuccess() throws IOException {
        System.out.println("deleteNewCustomerSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteNewCustomer(3);
    }

    @Test
    public void deleteNewCustomerFail() throws IOException {
        System.out.println("deleteNewCustomerFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteNewCustomer(21);
    }

    @Test
    public void rewriteNewCustomerSuccess() throws IOException {
        System.out.println("rewriteNewCustomerSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        long id = 1;
        String firstName = "rewriteFirstName";
        String lastName = "rewriteLastName";
        String phone = "rewritePhone";
        String email = "rewriteEmail";
        Integer discount = 50;
        instance.rewriteNewCustomer(id,firstName,lastName,phone, email, discount);
        System.out.println(instance.getNewCustomerById(id));
    }

    @Test
    public void rewriteNewCustomerFail() throws IOException {
        System.out.println("rewriteNewCustomerFail");
        DataProviderCSV instance = new DataProviderCSV();
        long id = 10;
        String firstName = "rewriteFirstName";
        String lastName = "rewriteLastName";
        String phone = "rewritePhone";
        String email = "rewriteEmail";
        Integer discount = 50;
        instance.rewriteNewCustomer(id,firstName,lastName,phone, email, discount);
        System.out.println(instance.getNewCustomerById(id));
    }

    @Test
    public void insertRegularCustomerSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertRegularCustomerSuccess");
        List<RegularCustomer> listRegularCustomer = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        RegularCustomer regularCustomer1 = createRegularCustomer(1, "firstName1", "lastName1", "phone1", "email1", 1);
        RegularCustomer regularCustomer2 = createRegularCustomer(2, "firstName2", "lastName2", "phone2", "email2", 2);
        RegularCustomer regularCustomer3 = createRegularCustomer(3, "firstName3", "lastName3", "phone3", "email3", 3);
        listRegularCustomer.add(regularCustomer1);
        listRegularCustomer.add(regularCustomer2);
        listRegularCustomer.add(regularCustomer3);
        instance.insertRegularCustomer(listRegularCustomer);
        assertEquals(regularCustomer1, instance.getRegularCustomerById(1));
        assertEquals(regularCustomer2, instance.getRegularCustomerById(2));
        assertEquals(regularCustomer3, instance.getRegularCustomerById(3));
    }

    @Test
    public void insertRegularCustomerFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertRegularCustomerFail");
        List<RegularCustomer> listRegularCustomer = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        RegularCustomer regularCustomer1 = createRegularCustomer(1, "firstName1", "lastName1", "phone1", "email1", 1);
        RegularCustomer regularCustomer2 = createRegularCustomer(2, "firstName2", "lastName2", "phone2", "email2", 2);
        RegularCustomer regularCustomer3 = createRegularCustomer(3, "firstName3", "lastName3", "phone3", "email3", 3);
        listRegularCustomer.add(regularCustomer1);
        listRegularCustomer.add(regularCustomer2);
        listRegularCustomer.add(regularCustomer3);
        instance.insertRegularCustomer(listRegularCustomer);
        assertNull(instance.getRegularCustomerById(4));
    }

    @Test
    public void testGetByIdRegularCustomerSuccess() throws IOException {
        System.out.println("testGetByRegularNewCustomerSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getRegularCustomerById(2));
    }

    @Test
    public void testGetByIdRegularCustomerFail() throws IOException {
        System.out.println("testGetByRegularNewCustomerFail");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getRegularCustomerById(5));
    }

    @Test
    public void deleteRegularCustomerSuccess() throws IOException {
        System.out.println("deleteRegularCustomerSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteRegularCustomer(3);
    }

    @Test
    public void deleteRegularCustomerFail() throws IOException {
        System.out.println("deleteRegularCustomerFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteRegularCustomer(21);
    }

    @Test
    public void rewriteRegularCustomerSuccess() throws IOException {
        System.out.println("rewriteRegularCustomerSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        long id = 1;
        String firstName = "rewriteFirstName";
        String lastName = "rewriteLastName";
        String phone = "rewritePhone";
        String email = "rewriteEmail";
        Integer numberOfOrders = 5;
        instance.rewriteRegularCustomer(id,firstName,lastName,phone, email, numberOfOrders);
        System.out.println(instance.getRegularCustomerById(id));
    }

    @Test
    public void rewriteRegularCustomerFail() throws IOException {
        System.out.println("rewriteRegularCustomerFail");
        DataProviderCSV instance = new DataProviderCSV();
        long id = 10;
        String firstName = "rewriteFirstName";
        String lastName = "rewriteLastName";
        String phone = "rewritePhone";
        String email = "rewriteEmail";
        Integer numberOfOrders = 5;
        instance.rewriteRegularCustomer(id,firstName,lastName,phone, email, numberOfOrders);
        System.out.println(instance.getRegularCustomerById(id));
    }

    //Service service
    @Test
    public void insertOrderItemSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertOrderItemSuccess");
        List<OrderItem> listOrderItem = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Service service = new Service();
        OrderItem orderItem1 = createOrderItem(1, service, 100.1, 1);
        OrderItem orderItem2 = createOrderItem(2, service, 200.1, 2);
        OrderItem orderItem3 = createOrderItem(3, service, 300.1, 3);
        listOrderItem.add(orderItem1);
        listOrderItem.add(orderItem2);
        listOrderItem.add(orderItem3);
        instance.insertOrderItem(listOrderItem);
        assertEquals(orderItem1, instance.getOrderItemById(1));
        assertEquals(orderItem2, instance.getOrderItemById(2));
        assertEquals(orderItem3, instance.getOrderItemById(3));
    }

    @Test
    public void insertOrderItemFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertOrderItemFail");
        List<OrderItem> listOrderItem = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Service service = new Service();
        OrderItem orderItem1 = createOrderItem(1, service, 100.1, 1);
        OrderItem orderItem2 = createOrderItem(2, service, 200.1, 2);
        OrderItem orderItem3 = createOrderItem(3, service, 300.1, 3);
        listOrderItem.add(orderItem1);
        listOrderItem.add(orderItem2);
        listOrderItem.add(orderItem3);
        instance.insertOrderItem(listOrderItem);
        assertNull(instance.getOrderItemById(4));
    }

    @Test
    public void testGetByIdOrderItemSuccess() throws IOException {
        System.out.println("testGetByOrderItemSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getOrderItemById(2));
    }

    @Test
    public void testGetByIdOrderItemFail() throws IOException {
        System.out.println("testGetByOrderItemFail");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getOrderItemById(5));
    }

    @Test
    public void deleteOrderItemSuccess() throws IOException {
        System.out.println("deleteOrderItemSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteOrderItem(3);
    }

    @Test
    public void deleteOrderItemFail() throws IOException {
        System.out.println("deleteOrderItemFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteOrderItem(21);
    }

    @Test
    public void rewriteOrderItemSuccess() throws IOException {
        System.out.println("rewriteOrderItemSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        Service service = new Service();
        long number = 1;
        Double coast = 500.0;
        Integer quantity = 5;
        instance.rewriteOrderItem(number, service, coast, quantity);
        System.out.println(instance.getRegularCustomerById(number));
    }

    @Test
    public void rewriteOrderItemFail() throws IOException {
        System.out.println("rewriteOrderItemFail");
        DataProviderCSV instance = new DataProviderCSV();
        Service service = new Service();
        long number = 20;
        Double coast = 500.0;
        Integer quantity = 5;
        instance.rewriteOrderItem(number, service, coast, quantity);
        System.out.println(instance.getRegularCustomerById(number));
    }

    //OrderItem orderItem
    //Customer customer
    @Test
    public void insertOrderSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertOrderSuccess");
        List<Order> listOrder = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        OrderItem orderItem = new OrderItem();
        Customer customer = new Customer();
        //Order order1 = createOrder(1, "created1", orderItem, 100.0, Order.OrderStatus.CREATED, customer, "lastUpdate1", "completed1");
        //Order order2 = createOrder(2, "created2", orderItem, 400.0, Order.OrderStatus.COMPLETED, customer, "lastUpdate2", "completed2");
        //Order order3 = createOrder(3, "created3", orderItem, 900.0, Order.OrderStatus.CANCELED, customer, "lastUpdate3", "completed3");
        //listOrder.add(order1);
        //listOrder.add(order2);
        //listOrder.add(order3);
        instance.insertOrder(listOrder);
        //assertEquals(order1, instance.getOrderById(1));
        //assertEquals(order2, instance.getOrderById(2));
        //assertEquals(order3, instance.getOrderById(3));
    }

    @Test
    public void insertOrderFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertOrderFail");
        List<Order> listOrder = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        OrderItem orderItem = new OrderItem();
        Customer customer = new Customer();
        //Order order1 = createOrder(1, "created1", orderItem, 100.0, Order.OrderStatus.CREATED, customer, "lastUpdate1", "completed1");
        //Order order2 = createOrder(2, "created2", orderItem, 400.0, Order.OrderStatus.COMPLETED, customer, "lastUpdate2", "completed2");
        //Order order3 = createOrder(3, "created3", orderItem, 900.0, Order.OrderStatus.CANCELED, customer, "lastUpdate3", "completed3");
        //listOrder.add(order1);
        //listOrder.add(order2);
        //listOrder.add(order3);
        instance.insertOrder(listOrder);
        assertNull(instance.getOrderById(4));
    }

    @Test
    public void testGetByIdOrderSuccess() throws IOException {
        System.out.println("testGetByOrderSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getOrderById(2));
    }

    @Test
    public void testGetByIdOrderFail() throws IOException {
        System.out.println("testGetByOrderFail");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getOrderById(20));
    }

    @Test
    public void deleteOrderSuccess() throws IOException {
        System.out.println("deleteOrderSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteOrder(3);
    }

    @Test
    public void deleteOrderFail() throws IOException {
        System.out.println("deleteOrderFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteOrder(21);
    }

    @Test
    public void rewriteOrderSuccess() throws IOException {
        System.out.println("rewriteOrderSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        OrderItem orderItem = new OrderItem();
        Customer customer = new Customer();
        long id = 1;
        String created = "rewriteCreated";
        Double coast = 5000.0;
        Order.OrderStatus status = Order.OrderStatus.COMPLETED;
        String lastUpdated = "rewriteLastUpdated";
        String completed = "rewriteCompleted";
        instance.rewriteOrder(id, created, orderItem, coast, status, customer, lastUpdated,completed);
        System.out.println(instance.getRegularCustomerById(id));
    }

    @Test
    public void rewriteOrderFail() throws IOException {
        System.out.println("rewriteOrderFail");
        DataProviderCSV instance = new DataProviderCSV();
        OrderItem orderItem = new OrderItem();
        Customer customer = new Customer();
        long id = 20;
        String created = "rewriteCreated";
        Double coast = 5000.0;
        Order.OrderStatus status = Order.OrderStatus.COMPLETED;
        String lastUpdated = "rewriteLastUpdated";
        String completed = "rewriteCompleted";
        instance.rewriteOrder(id, created, orderItem, coast, status, customer, lastUpdated,completed);
        System.out.println(instance.getRegularCustomerById(id));
    }

    //List<Service> listService
    @Test
    void insertMasterSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertMasterSuccess");
        List<Master> listMaster = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        for(int i=1; i<=3; i++){
            List<Service> listService = new ArrayList<>();

        }
        List<Service> listService = new ArrayList<>();
        Master master1 = createMaster(1, "FirstName1", "LastName1", "Position1", listService, "Phone1", 10000.0);
        Master master2 = createMaster(2, "FirstName2", "LastName2", "Position2", listService, "Phone2", 20000.0);
        Master master3 = createMaster(3, "FirstName3", "LastName3", "Position3", listService, "Phone3", 30000.0);
        listMaster.add(master1);
        listMaster.add(master2);
        listMaster.add(master3);
        instance.insertMaster(listMaster);
        assertEquals(master1, instance.getMasterById(1));
        assertEquals(master2, instance.getMasterById(2));
        assertEquals(master3, instance.getMasterById(3));
    }

    @Test
    void insertMasterFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertMasterFail");
        List<Master> listMaster = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        List<Service> listService = new ArrayList<>();
        Master master1 = createMaster(1, "FirstName1", "LastName1", "Position1", listService, "Phone1", 10000.0);
        Master master2 = createMaster(2, "FirstName2", "LastName2", "Position2", listService, "Phone2", 20000.0);
        Master master3 = createMaster(3, "FirstName3", "LastName3", "Position3", listService, "Phone3", 30000.0);
        listMaster.add(master1);
        listMaster.add(master2);
        listMaster.add(master3);
        instance.insertMaster(listMaster);
        assertNull(instance.getMasterById(4));
    }

    @Test
    public void testGetByIdMasterSuccess() throws IOException {
        System.out.println("testGetByMasterSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getMasterById(2));
    }

    @Test
    public void testGetByIdMasterFail() throws IOException {
        System.out.println("testGetByMasterFail");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getMasterById(20));
    }

    @Test
    public void deleteOrderMaster() throws IOException {
        System.out.println("deleteOrderMaster");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteMaster(3);
    }

    @Test
    public void deleteMasterFail() throws IOException {
        System.out.println("deleteMasterFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteMaster(21);
    }

    @Test
    public void rewriteMasterSuccess() throws IOException {
        System.out.println("rewriteMasterSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        List<Service> listService = new ArrayList<>();
        long id = 1;
        String firstName = "rewriteFirstName";
        String lastName = "rewriteLastName";
        String position = "rewritePosition";
        String phone = "rewritePhone";
        Double salary = 50000.0;
        instance.rewriteMaster(id, firstName, lastName, position,listService,phone,salary);
        System.out.println(instance.getRegularCustomerById(id));
    }

    @Test
    public void rewriteMasterFail() throws IOException {
        System.out.println("rewriteMasterFail");
        DataProviderCSV instance = new DataProviderCSV();
        List<Service> listService = new ArrayList<>();
        long id = 1;
        String firstName = "rewriteFirstName";
        String lastName = "rewriteLastName";
        String position = "rewritePosition";
        String phone = "rewritePhone";
        Double salary = 50000.0;
        instance.rewriteMaster(id, firstName, lastName, position,listService,phone,salary);
        System.out.println(instance.getRegularCustomerById(id));
    }

    //List<Master> listMaster
    @Test
    void insertSalonSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertSalonSuccess");
        List<Salon> listSalon = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        List<Master> listMaster = new ArrayList<>();
        Salon salon1 = createSalon(1, "address1", listMaster);
        Salon salon2 = createSalon(2, "address1", listMaster);
        Salon salon3 = createSalon(3, "address1", listMaster);
        listSalon.add(salon1);
        listSalon.add(salon2);
        listSalon.add(salon3);
        instance.insertSalon(listSalon);
        assertEquals(salon1, instance.getSalonById(1));
        assertEquals(salon2, instance.getSalonById(2));
        assertEquals(salon3, instance.getSalonById(3));
    }

    @Test
    void insertSalonFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertSalonFail");
        List<Salon> listSalon = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        List<Master> listMaster = new ArrayList<>();
        Salon salon1 = createSalon(1, "address1", listMaster);
        Salon salon2 = createSalon(2, "address1", listMaster);
        Salon salon3 = createSalon(3, "address1", listMaster);
        listSalon.add(salon1);
        listSalon.add(salon2);
        listSalon.add(salon3);
        instance.insertSalon(listSalon);
        assertEquals(salon1, instance.getSalonById(1));
        assertEquals(salon2, instance.getSalonById(2));
        assertEquals(salon3, instance.getSalonById(3));
        assertNull(instance.getSalonById(4));
    }

    @Test
    public void testGetByIdSalonSuccess() throws IOException {
        System.out.println("testGetBySalonSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getSalonById(2));
    }

    @Test
    public void testGetByIdSalonFail() throws IOException {
        System.out.println("testGetBySalonFail");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getSalonById(5));
    }

    @Test
    public void deleteOrderSalon() throws IOException {
        System.out.println("deleteOrderSalon");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteSalon(3);
    }

    @Test
    public void deleteSalonFail() throws IOException {
        System.out.println("deleteSalonFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteSalon(21);
    }

    @Test
    public void rewriteSalonSuccess() throws IOException {
        System.out.println("rewriteSalonSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        List<Master> listMaster = new ArrayList<>();
        long id = 1;
        String address = "rewriteAddress";
        instance.rewriteSalon(id, address, listMaster);
        System.out.println(instance.getRegularCustomerById(id));
    }

    @Test
    public void rewriteSalonFail() throws IOException {
        System.out.println("rewriteSalonFail");
        DataProviderCSV instance = new DataProviderCSV();
        List<Master> listMaster = new ArrayList<>();
        long id = 20;
        String address = "rewriteAddress";
        instance.rewriteSalon(id, address, listMaster);
        System.out.println(instance.getRegularCustomerById(id));
    }

}