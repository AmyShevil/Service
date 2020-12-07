package ru.sfedu.Aisova.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ru.sfedu.Aisova.TestBase;
import ru.sfedu.Aisova.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataProviderCSVTest extends TestBase {

    private static Logger log = LogManager.getLogger(DataProviderCSVTest.class);

    private static List<Service> getListService(){
        List<Service> listService = new ArrayList<>();
        Service service1 = createService(1, "service1", 1000.0, "description1");
        Service service2 = createService(2, "service2", 2000.0, "description2");
        Service service3 = createService(3, "service3", 3000.0, "description3");
        listService.add(service1);
        listService.add(service2);
        listService.add(service3);
        return listService;
    }

    private static List<Master> getListMaster(){
        List<Master> listMaster = new ArrayList<>();
        List<Service> listService = new ArrayList<>();
        listService.addAll(getListService());
/*
        List<Service> listServiceForMaster = new ArrayList<>();
        listServiceForMaster.add(listService.get(0));
        listServiceForMaster.add(listService.get(1));
 */
        Master master1 = createMaster(1, "FirstName1", "LastName1", "Position1", listService, "Phone1", 10000.0);
        Master master2 = createMaster(2, "FirstName2", "LastName2", "Position2", listService, "Phone2", 20000.0);
        Master master3 = createMaster(3, "FirstName3", "LastName3", "Position3", listService, "Phone3", 30000.0);
        listMaster.add(master1);
        listMaster.add(master2);
        listMaster.add(master3);
        return listMaster;
    }

    private static List<OrderItem> getListOrderItem(){
        List<OrderItem> listOrderItem = new ArrayList<>();
        Service service1 = createService(1, "service1", 1000.0, "description1");
        Service service2 = createService(2, "service2", 2000.0, "description2");
        Service service3 = createService(3, "service3", 3000.0, "description3");

        OrderItem orderItem1 = createOrderItem(1, service1, 1000.0, 1);
        OrderItem orderItem2 = createOrderItem(2, service2, 2000.0, 1);
        OrderItem orderItem3 = createOrderItem(3, service3, 3000.0, 2);

        listOrderItem.add(orderItem1);
        listOrderItem.add(orderItem2);
        listOrderItem.add(orderItem3);
        return listOrderItem;
    }

    @org.junit.jupiter.api.Order(0)
    @Test
    public void insertServiceSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertServiceSuccess");
        List<Service> listService = new ArrayList<>();
        listService.addAll(getListService());
        DataProviderCSV instance = new DataProviderCSV();
        instance.insertService(listService);
        assertEquals(listService.get(0), instance.getServiceById(1));
    }

    @org.junit.jupiter.api.Order(0)
    @Test
    public void insertServiceFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertServiceFail");
        List<Service> listService = new ArrayList<>();
        listService.addAll(getListService());
        DataProviderCSV instance = new DataProviderCSV();
        instance.insertService(listService);
        assertNull(instance.getServiceById(4));
    }

    @org.junit.jupiter.api.Order(1)
    @Test
    public void testGetByIdServiceSuccess() throws IOException {
        System.out.println("testGetByIdServiceSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getServiceById(2));
    }

    @org.junit.jupiter.api.Order(1)
    @Test
    public void testGetByIdServiceFail() throws IOException {
        System.out.println("testGetByIdServiceFail");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getServiceById(5));
    }

    @org.junit.jupiter.api.Order(2)
    @Test
    public void deleteServiceSuccess() throws IOException {
        System.out.println("deleteServiceSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteService(3);
        assertNull(instance.getServiceById(3));
    }

    @org.junit.jupiter.api.Order(2)
    @Test
    public void deleteServiceFail() throws IOException {
        System.out.println("deleteServiceFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteService(21);
        System.out.println(instance.getServiceById(21));
    }

    @org.junit.jupiter.api.Order(3)
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

    @org.junit.jupiter.api.Order(3)
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

    @org.junit.jupiter.api.Order(4)
    @Test
    void insertMasterSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertMasterSuccess");
        List<Master> listMaster = new ArrayList<>();
        listMaster.addAll(getListMaster());
        log.debug(listMaster);
        DataProviderCSV instance = new DataProviderCSV();
        instance.insertMaster(listMaster);
        log.debug(listMaster.get(1));
        log.debug(instance.getMaster(2).get());
        assertEquals(listMaster.get(1), instance.getMaster(2).get());
    }

    @org.junit.jupiter.api.Order(4)
    @Test
    void insertMasterFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertMasterFail");
        List<Master> listMaster = new ArrayList<>();
        listMaster.addAll(getListMaster());
        DataProviderCSV instance = new DataProviderCSV();
        instance.insertMaster(listMaster);
        assertEquals(Optional.empty(), instance.getMaster(15));
    }

    @org.junit.jupiter.api.Order(5)
    @Test
    public void testGetByIdMasterSuccess() throws IOException {
        System.out.println("testGetByMasterSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getMaster(2).get());
    }

    @org.junit.jupiter.api.Order(5)
    @Test
    public void testGetByIdMasterFail() throws IOException {
        System.out.println("testGetByMasterFail");
        DataProviderCSV instance = new DataProviderCSV();
        assertEquals(Optional.empty(), instance.getMaster(15));
    }

    @org.junit.jupiter.api.Order(6)
    @Test
    public void deleteMasterSuccess() throws IOException {
        System.out.println("deleteMasterSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteMaster(3);
        assertEquals(Optional.empty(), instance.getMaster(3));
    }

    @org.junit.jupiter.api.Order(6)
    @Test
    public void deleteMasterFail() throws IOException {
        System.out.println("deleteMasterFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteMaster(21);
    }

    @org.junit.jupiter.api.Order(7)
    @Test
    public void rewriteMasterSuccess() throws IOException {
        System.out.println("rewriteMasterSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        List<Service> listService = new ArrayList<>();
        Service newService1 = new Service();
        long newId = Integer.parseInt(String.valueOf(2));
        String name = "rewriteServiceName1";
        Double price = 999.0;
        String description = "rewriteDescription1";
        newService1.setId(newId);
        newService1.setName(name);
        newService1.setPrice(price);
        newService1.setDescription(description);
        listService.add(newService1);
        Service newService2 = new Service(newId+1, "rewriteServiceName2", price+1000, "rewriteDescription2");
        listService.add(newService2);
        long id = 1;
        String firstName = "rewriteFirstName";
        String lastName = "rewriteLastName";
        String position = "rewritePosition";
        String phone = "rewritePhone";
        Double salary = 50000.0;
        instance.rewriteMaster(id, firstName, lastName, position,listService,phone,salary);
        System.out.println(instance.getMaster(newId).get());
    }

    @org.junit.jupiter.api.Order(7)
    @Test
    public void rewriteMasterFail() throws IOException {
        System.out.println("rewriteMasterFail");
        DataProviderCSV instance = new DataProviderCSV();
        List<Service> listService = new ArrayList<>();
        Service newService1 = new Service();
        long newId = Integer.parseInt(String.valueOf(1));
        String name = "rewriteServiceName1";
        Double price = 999.0;
        String description = "rewriteDescription1";
        newService1.setId(newId);
        newService1.setName(name);
        newService1.setPrice(price);
        newService1.setDescription(description);
        listService.add(newService1);
        Service newService2 = new Service(newId+1, "rewriteServiceName2", price+1000, "rewriteDescription2");
        listService.add(newService2);
        long id = 1;
        String firstName = "rewriteFirstName";
        String lastName = "rewriteLastName";
        String position = "rewritePosition";
        String phone = "rewritePhone";
        Double salary = 50000.0;
        instance.rewriteMaster(id, firstName, lastName, position,listService,phone,salary);
    }

    @org.junit.jupiter.api.Order(8)
    @Test
    void insertSalonSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertSalonSuccess");
        List<Salon> listSalon = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        List<Master> listMaster = new ArrayList<>();
        listMaster.addAll(getListMaster());
        Salon salon1 = createSalon(1, "address1", listMaster);
        Salon salon2 = createSalon(2, "address2", listMaster);
        Salon salon3 = createSalon(3, "address3", listMaster);
        listSalon.add(salon1);
        listSalon.add(salon2);
        listSalon.add(salon3);
        instance.insertSalon(listSalon);
        assertEquals(listSalon.get(1), instance.getSalon(2).get());

    }

    @org.junit.jupiter.api.Order(8)
    @Test
    void insertSalonFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertSalonFail");
        List<Salon> listSalon = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        List<Master> listMaster = new ArrayList<>();
        listMaster.addAll(getListMaster());
        Salon salon1 = createSalon(1, "address1", listMaster);
        Salon salon2 = createSalon(2, "address1", listMaster);
        Salon salon3 = createSalon(3, "address1", listMaster);
        listSalon.add(salon1);
        listSalon.add(salon2);
        listSalon.add(salon3);
        instance.insertSalon(listSalon);
        assertEquals(Optional.empty(), instance.getSalon(15));
    }

    @org.junit.jupiter.api.Order(9)
    @Test
    public void testGetByIdSalonSuccess() throws IOException {
        System.out.println("testGetBySalonSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getSalon(2).get());
    }

    @org.junit.jupiter.api.Order(9)
    @Test
    public void testGetByIdSalonFail() throws IOException {
        System.out.println("testGetBySalonFail");
        DataProviderCSV instance = new DataProviderCSV();
        assertEquals(Optional.empty(), instance.getSalon(15));
    }

    @org.junit.jupiter.api.Order(10)
    @Test
    public void deleteSalonSuccess() throws IOException {
        System.out.println("deleteSalonSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteSalon(3);
        assertEquals(Optional.empty(), instance.getSalon(3));
    }

    @org.junit.jupiter.api.Order(10)
    @Test
    public void deleteSalonFail() throws IOException {
        System.out.println("deleteSalonFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteSalon(21);
    }

    @org.junit.jupiter.api.Order(11)
    @Test
    public void rewriteSalonSuccess() throws IOException {
        System.out.println("rewriteSalonSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        long id = 1;
        String address = "rewriteAddress";
        List<Master> listMaster = new ArrayList<>();
        listMaster.addAll(getListMaster());
        instance.rewriteSalon(id, address, listMaster);
        System.out.println(instance.getSalon(id).get());
    }

    @org.junit.jupiter.api.Order(11)
    @Test
    public void rewriteSalonFail() throws IOException {
        System.out.println("rewriteSalonFail");
        DataProviderCSV instance = new DataProviderCSV();
        long id = 20;
        String address = "rewriteAddress";
        List<Master> listMaster = new ArrayList<>();
        listMaster.addAll(getListMaster());
        instance.rewriteSalon(id, address, listMaster);
        instance.rewriteSalon(id, address, listMaster);
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

    @org.junit.jupiter.api.Order(12)
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

    @org.junit.jupiter.api.Order(12)
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

    @org.junit.jupiter.api.Order(13)
    @Test
    public void testGetByIdNewCustomerSuccess() throws IOException {
        System.out.println("testGetByIdNewCustomerSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getNewCustomerById(2));
    }

    @org.junit.jupiter.api.Order(13)
    @Test
    public void testGetByIdNewCustomerFail() throws IOException {
        System.out.println("testGetByIdNewCustomerFail");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getNewCustomerById(5));
    }

    @org.junit.jupiter.api.Order(14)
    @Test
    public void deleteNewCustomerSuccess() throws IOException {
        System.out.println("deleteNewCustomerSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteNewCustomer(3);
    }

    @org.junit.jupiter.api.Order(14)
    @Test
    public void deleteNewCustomerFail() throws IOException {
        System.out.println("deleteNewCustomerFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteNewCustomer(21);
    }

    @org.junit.jupiter.api.Order(15)
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

    @org.junit.jupiter.api.Order(15)
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

    @org.junit.jupiter.api.Order(16)
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

    @org.junit.jupiter.api.Order(16)
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

    @org.junit.jupiter.api.Order(17)
    @Test
    public void testGetByIdRegularCustomerSuccess() throws IOException {
        System.out.println("testGetByRegularNewCustomerSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getRegularCustomerById(2));
    }

    @org.junit.jupiter.api.Order(17)
    @Test
    public void testGetByIdRegularCustomerFail() throws IOException {
        System.out.println("testGetByRegularNewCustomerFail");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getRegularCustomerById(5));
    }

    @org.junit.jupiter.api.Order(18)
    @Test
    public void deleteRegularCustomerSuccess() throws IOException {
        System.out.println("deleteRegularCustomerSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteRegularCustomer(3);
    }

    @org.junit.jupiter.api.Order(18)
    @Test
    public void deleteRegularCustomerFail() throws IOException {
        System.out.println("deleteRegularCustomerFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteRegularCustomer(21);
    }

    @org.junit.jupiter.api.Order(19)
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

    @org.junit.jupiter.api.Order(19)
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

    @org.junit.jupiter.api.Order(20)
    @Test
    public void insertOrderItemSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertOrderItemSuccess");
        List<OrderItem> listOrderItem = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        listOrderItem.addAll(getListOrderItem());
        instance.insertOrderItem(listOrderItem);
        System.out.println(instance.getOrderItem(2));
        System.out.println(listOrderItem.get(1));
        assertEquals(listOrderItem.get(1), instance.getOrderItem(2).get());
    }

    @org.junit.jupiter.api.Order(20)
    @Test
    public void insertOrderItemFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertOrderItemFail");
        List<OrderItem> listOrderItem = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        listOrderItem.addAll(getListOrderItem());
        instance.insertOrderItem(listOrderItem);
        assertEquals(Optional.empty(), instance.getOrderItem(5));
    }

    @org.junit.jupiter.api.Order(21)
    @Test
    public void testGetByIdOrderItemSuccess() throws IOException {
        System.out.println("testGetByOrderItemSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getOrderItem(2).get());
    }

    @org.junit.jupiter.api.Order(21)
    @Test
    public void testGetByIdOrderItemFail() throws IOException {
        System.out.println("testGetByOrderItemFail");
        DataProviderCSV instance = new DataProviderCSV();
        assertEquals(Optional.empty(), instance.getOrderItem(15));
    }

    @org.junit.jupiter.api.Order(22)
    @Test
    public void deleteOrderItemSuccess() throws IOException {
        System.out.println("deleteOrderItemSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteOrderItem(3);
        assertEquals(Optional.empty(), instance.getOrderItem(3));
    }

    @org.junit.jupiter.api.Order(22)
    @Test
    public void deleteOrderItemFail() throws IOException {
        System.out.println("deleteOrderItemFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteOrderItem(21);
    }

    @org.junit.jupiter.api.Order(23)
    @Test
    public void rewriteOrderItemSuccess() throws IOException {
        System.out.println("rewriteOrderItemSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        Service service = createService(1, "rewriteServiceName", 999.0, "rewriteDescription");
        long number = 1;
        Double coast = 500.0;
        Integer quantity = 5;
        instance.rewriteOrderItem(number, service, coast, quantity);
        System.out.println(instance.getOrderItem(number).get());
    }

    @org.junit.jupiter.api.Order(23)
    @Test
    public void rewriteOrderItemFail() throws IOException {
        System.out.println("rewriteOrderItemFail");
        DataProviderCSV instance = new DataProviderCSV();
        Service service = new Service();
        long number = 20;
        Double coast = 500.0;
        Integer quantity = 5;
        instance.rewriteOrderItem(number, service, coast, quantity);
    }

    @org.junit.jupiter.api.Order(24)
    @Test
    public void insertOrderSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertOrderSuccess");
        List<Order> listOrder = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        List<OrderItem> orderItem = new ArrayList<>();
        orderItem.addAll(getListOrderItem());
        Customer customer1 = createNewCustomer(1, "firstName1", "lastName1", "phone1", "email1", 10);
        Customer customer2 = createNewCustomer(2, "firstName2", "lastName2", "phone2", "email2", 20);
        Customer customer3 = createNewCustomer(3, "firstName3", "lastName3", "phone3", "email3", 30);

        Order order1 = createOrder(1, "created1", orderItem, 100.0, Order.OrderStatus.CREATED, customer1, "lastUpdate1", "completed1");
        Order order2 = createOrder(2, "created2", orderItem, 400.0, Order.OrderStatus.COMPLETED, customer2, "lastUpdate2", "completed2");
        Order order3 = createOrder(3, "created3", orderItem, 900.0, Order.OrderStatus.CANCELED, customer3, "lastUpdate3", "completed3");
        listOrder.add(order1);
        listOrder.add(order2);
        listOrder.add(order3);
        instance.insertOrder(listOrder);
        System.out.println(instance.getOrder(1).get());
        assertEquals(listOrder.get(0), instance.getOrder(1).get());
    }

    @org.junit.jupiter.api.Order(24)
    @Test
    public void insertOrderFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertOrderFail");
        List<Order> listOrder = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        List<OrderItem> orderItem = new ArrayList<>();
        orderItem.addAll(getListOrderItem());
        Customer customer1 = createNewCustomer(1, "firstName1", "lastName1", "phone1", "email1", 10);
        Customer customer2 = createNewCustomer(2, "firstName2", "lastName2", "phone2", "email2", 20);
        Customer customer3 = createNewCustomer(3, "firstName3", "lastName3", "phone3", "email3", 30);

        Order order1 = createOrder(1, "created1", orderItem, 100.0, Order.OrderStatus.CREATED, customer1, "lastUpdate1", "completed1");
        Order order2 = createOrder(2, "created2", orderItem, 400.0, Order.OrderStatus.COMPLETED, customer2, "lastUpdate2", "completed2");
        Order order3 = createOrder(3, "created3", orderItem, 900.0, Order.OrderStatus.CANCELED, customer3, "lastUpdate3", "completed3");
        listOrder.add(order1);
        listOrder.add(order2);
        listOrder.add(order3);
        instance.insertOrder(listOrder);
        assertEquals(Optional.empty(), instance.getOrder(10));
    }

    @org.junit.jupiter.api.Order(25)
    @Test
    public void testGetByIdOrderSuccess() throws IOException {
        System.out.println("testGetByOrderSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getOrder(2).get());
    }

    @org.junit.jupiter.api.Order(25)
    @Test
    public void testGetByIdOrderFail() throws IOException {
        System.out.println("testGetByOrderFail");
        DataProviderCSV instance = new DataProviderCSV();
        assertEquals(Optional.empty(), instance.getOrder(5));
    }

    @org.junit.jupiter.api.Order(26)
    @Test
    public void deleteOrderSuccess() throws IOException {
        System.out.println("deleteOrderSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteOrder(3);
        assertEquals(Optional.empty(), instance.getOrder(3));
    }

    @org.junit.jupiter.api.Order(26)
    @Test
    public void deleteOrderFail() throws IOException {
        System.out.println("deleteOrderFail");
        DataProviderCSV instance = new DataProviderCSV();
        instance.deleteOrder(21);
    }

    @org.junit.jupiter.api.Order(27)
    @Test
    public void rewriteOrderSuccess() throws IOException {
        System.out.println("rewriteOrderSuccess");
        DataProviderCSV instance = new DataProviderCSV();
        List<OrderItem> orderItem = new ArrayList<>();
        orderItem.addAll(getListOrderItem());
        Customer customer = createNewCustomer(1, "rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 90);
        long id = 1;
        String created = "rewriteCreated";
        Double coast = 5000.0;
        Order.OrderStatus status = Order.OrderStatus.PROCESSING;
        String lastUpdated = "rewriteLastUpdated";
        String completed = "rewriteCompleted";
        instance.rewriteOrder(id, created, orderItem, coast, status, customer, lastUpdated,completed);
        System.out.println(instance.getOrder(id).get());
    }

    @org.junit.jupiter.api.Order(27)
    @Test
    public void rewriteOrderFail() throws IOException {
        System.out.println("rewriteOrderFail");
        DataProviderCSV instance = new DataProviderCSV();
        List<OrderItem> orderItem = new ArrayList<>();
        orderItem.addAll(getListOrderItem());
        Customer customer = createNewCustomer(1, "rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 90);
        long id = 1;
        String created = "rewriteCreated";
        Double coast = 5000.0;
        Order.OrderStatus status = Order.OrderStatus.PROCESSING;
        String lastUpdated = "rewriteLastUpdated";
        String completed = "rewriteCompleted";
        instance.rewriteOrder(id, created, orderItem, coast, status, customer, lastUpdated,completed);
    }

}