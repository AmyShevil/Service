package ru.sfedu.Aisova.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.Aisova.TestBase;
import ru.sfedu.Aisova.bean.Customer;
import ru.sfedu.Aisova.bean.Master;
import ru.sfedu.Aisova.bean.Service;
import ru.sfedu.Aisova.bean.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void insertUserSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertUserSuccess");
        List<User> listUser = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        User user1 = createUser(1, "Name1");
        User user2 = createUser(2, "Name2");
        User user3 = createUser(3, "Name3");
        listUser.add(user1);
        listUser.add(user2);
        listUser.add(user3);
        instance.insertUser(listUser);
        assertEquals(user1, instance.getUserById(1));
    }

    @Test
    void insertUserFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertUserFail");
        List<User> listUser = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        User user1 = createUser(1, "Name1");
        User user2 = createUser(2, "Name2");
        User user3 = createUser(3, "Name3");
        listUser.add(user1);
        listUser.add(user2);
        listUser.add(user3);
        instance.insertUser(listUser);
        assertNull(instance.getUserById(4));
    }

    @Test
    public void testGetByIdUser() throws IOException {
        System.out.println("testGetByIdUser");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getUserById(2));
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

    /*
    @Test
    void insertMasterSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertMasterSuccess");
        List<Master> listMaster = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        Master master1 = createMaster(1, "FirstName1", "LastName1", "Position1", "listService", "Phone1", 1.0);
        Master master2 = createMaster(2, "FirstName2", "LastName2", "Position2", "listService", "Phone2", 2.0);
        Master master3 = createMaster(3, "FirstName3", "LastName3", "Position3", "listService", "Phone3", 3.0);
        listMaster.add(master1);
        listMaster.add(master2);
        listMaster.add(master3);
        instance.insertMaster(listMaster);
        assertEquals(master1, instance.getMasterById(1));
    }
     */

}