package ru.sfedu.Aisova.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.sfedu.Aisova.Constants;
import ru.sfedu.Aisova.model.*;
import ru.sfedu.Aisova.model.Order;
import ru.sfedu.Aisova.utils.ConfigurationUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.Aisova.TestBase.createService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataProviderCsvTest {

    private static final Logger log = LogManager.getLogger(DataProviderCsvTest.class);
    private static final DataProvider dataProvider = new DataProviderCsv();

    private static <T> void deleteFile(Class<T> tClass) {
        try {
            log.debug(new File(ConfigurationUtil.getConfigurationEntry(Constants.PATH)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION)).delete());
        } catch (IOException e) {
            log.error(e);
        }
    }
    private static void deleteAll() {
        List<Class> classList = new ArrayList<>();
        classList.add(Master.class);
        classList.add(NewCustomer.class);
        classList.add(Order.class);
        classList.add(OrderItem.class);
        classList.add(RegularCustomer.class);
        classList.add(Salon.class);
        classList.add(Service.class);
        classList.forEach(DataProviderCsvTest::deleteFile);
    }

    @BeforeAll
    static void init(){
        deleteAll();
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void createServiceSuccess() {
        Assertions.assertTrue(dataProvider.createService("service1", 1000.0, "description1"));
        Assertions.assertTrue(dataProvider.createService("service2", 2000.0, "description2"));
        Assertions.assertTrue(dataProvider.createService("service3", 3000.0, "description3"));
        Assertions.assertTrue(dataProvider.createService("service4", 4000.0, "description4"));

    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void createServiceFail() {
        Assertions.assertFalse(dataProvider.createService(null, 1000.0, "description1"));
        Assertions.assertFalse(dataProvider.createService("service2", null, "description2"));
        Assertions.assertFalse(dataProvider.createService("service3", 3000.0, null));
    }

    @Test
    void editServiceSuccess() {
        Assertions.assertTrue(dataProvider.editService(2, "rewriteName", 5.0, "rewriteDescription"));
    }

    @Test
    void editServiceFail() {
        Assertions.assertFalse(dataProvider.editService(10, "rewriteName", 5.0, "rewriteDescription"));
    }

    @Test
    void deleteServiceSuccess() {
        Assertions.assertTrue(dataProvider.deleteService(3));
    }

    @Test
    void deleteServiceFail() {
        Assertions.assertTrue(dataProvider.deleteService(10));
    }

    @Test
    void getServiceByIdSuccess() {
        log.debug(dataProvider.getServiceById(1));
    }

    @Test
    void getServiceByIdFail() {
        log.debug(dataProvider.getServiceById(10));
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void createNewCustomerSuccess() {
        Assertions.assertTrue(dataProvider.createNewCustomer("firstName1", "lastName1", "phone1", "email1", 10));
        Assertions.assertTrue(dataProvider.createNewCustomer( "firstName2", "lastName2", "phone2", "email2", 20));
        Assertions.assertTrue(dataProvider.createNewCustomer( "firstName3", "lastName3", "phone3", "email3", 30));
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void createNewCustomerFail() {
        Assertions.assertFalse(dataProvider.createNewCustomer(null, "lastName1", "phone1", "email1", 10));
        Assertions.assertFalse(dataProvider.createNewCustomer( "firstName2", null, "phone2", "email2", 20));
        Assertions.assertFalse(dataProvider.createNewCustomer( "firstName3", "lastName3", null, "email3", 30));
        Assertions.assertFalse(dataProvider.createNewCustomer( "firstName4", "lastName4", "phone4", null, 40));
        Assertions.assertFalse(dataProvider.createNewCustomer( "firstName5", "lastName5", "phone5", "email5", null));
    }

    @Test
    void editNewCustomerSuccess() {
        Assertions.assertTrue(dataProvider.editNewCustomer(1,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 50));
    }

    @Test
    void editNewCustomerFail() {
        Assertions.assertFalse(dataProvider.editNewCustomer(10,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 50));
    }

    @Test
    void deleteNewCustomerSuccess() {
        Assertions.assertTrue(dataProvider.deleteNewCustomer(2));
    }

    @Test
    void deleteNewCustomerFail() {
        Assertions.assertTrue(dataProvider.deleteNewCustomer(10));
    }

    @Test
    void testGetNewCustomerByIdSuccess() {
        log.debug(dataProvider.getNewCustomerById(1));
    }

    @Test
    void testGetNewCustomerByIdFail() {
        log.debug(dataProvider.getNewCustomerById(10));
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void createRegularCustomerSuccess() {
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName1", "lastName1", "phone1", "email1", 1));
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName2", "lastName2", "phone2", "email2", 2));
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName3", "lastName3", "phone3", "email3", 3));
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void createRegularCustomerFail() {
        Assertions.assertFalse(dataProvider.createRegularCustomer(null, "lastName1", "phone1", "email1", 1));
        Assertions.assertFalse(dataProvider.createRegularCustomer( "firstName2", null, "phone2", "email2", 2));
        Assertions.assertFalse(dataProvider.createRegularCustomer( "firstName3", "lastName3", null, "email3", 3));
        Assertions.assertFalse(dataProvider.createRegularCustomer( "firstName4", "lastName4", "phone4", null, 4));
        Assertions.assertFalse(dataProvider.createRegularCustomer( "firstName5", "lastName5", "phone5", "email5", null));
    }

    @Test
    void editRegularCustomerSuccess() {
        Assertions.assertTrue(dataProvider.editRegularCustomer(1,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 5));
    }

    @Test
    void editRegularCustomerFail() {
        Assertions.assertFalse(dataProvider.editRegularCustomer(10,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 5));
    }

    @Test
    void deleteRegularCustomerSuccess() {
        Assertions.assertTrue(dataProvider.deleteRegularCustomer(2));
    }

    @Test
    void deleteRegularCustomerFail() {
        Assertions.assertTrue(dataProvider.deleteRegularCustomer(10));
    }

    @Test
    void testGetRegularCustomerByIdSuccess() {
        log.debug(dataProvider.getRegularCustomerById(1));

    }
    @Test
    void testGetRegularCustomerByIdFail() {
        log.debug(dataProvider.getRegularCustomerById(10));
    }
    /*
     @Test
     void testCreateMaster() {
         List<Service> listService = new ArrayList<>();
         listService.add(dataProvider.getServiceById(0));
         listService.add(dataProvider.getServiceById(1));

         Assertions.assertTrue(dataProvider.createMaster("FirstName1", "LastName1", "Position1", "Phone1", 10000.0, listService));
         Assertions.assertTrue(dataProvider.createMaster("FirstName2", "LastName2", "Position2", "Phone2", 20000.0, listService));
         Assertions.assertTrue(dataProvider.createMaster("FirstName3", "LastName3", "Position3", "Phone3", 30000.0, listService));

     }

         @Test
         void editMaster() {
         }

         @Test
         void deleteMaster() {
         }

         @Test
         void getMasterById() {
         }

         @Test
         void createSalon() {
         }

         @Test
         void editSalon() {
         }

         @Test
         void deleteSalon() {
         }

         @Test
         void getSalonById() {
         }
     */
    @Test
    @org.junit.jupiter.api.Order(0)
    void createOrderItemSuccess() {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(0));
        listService.add(dataProvider.getServiceById(1));
        listService.add(dataProvider.getServiceById(2));
        Service service1 = listService.stream().filter(el->el.getId()==0).findFirst().get();
        Service service2 = listService.stream().filter(el->el.getId()==1).findFirst().get();
        Service service3 = listService.stream().filter(el->el.getId()==2).findFirst().get();
        Assertions.assertTrue(dataProvider.createOrderItem(service1, 1111.0, 1));
        Assertions.assertTrue(dataProvider.createOrderItem(service2, 2222.0, 2));
        Assertions.assertTrue(dataProvider.createOrderItem(service3, 3333.0, 3));
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void createOrderItemFail() {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(0));
        listService.add(dataProvider.getServiceById(1));
        listService.add(dataProvider.getServiceById(2));
        Service service2 = listService.stream().filter(el->el.getId()==1).findFirst().get();
        Service service3 = listService.stream().filter(el->el.getId()==2).findFirst().get();
        Assertions.assertFalse(dataProvider.createOrderItem(null, 1111.0, 1));
        Assertions.assertFalse(dataProvider.createOrderItem(service2, null, 2));
        Assertions.assertFalse(dataProvider.createOrderItem(service3, 3333.0, null));
    }
/*
    @Test
    void editOrderItem() {
    }

    @Test
    void deleteOrderItem() {
    }

    @Test
    void getOrderItemById() {
    }

    @Test
    void createOrder() {
    }

    @Test
    void editOrder() {
    }

    @Test
    void deleteOrder() {
    }

    @Test
    void getOrderById() {
    }

    @Test
    void calculateOrderValue() {
    }

    @Test
    void viewOrderHistory() {
    }

    @Test
    void getListOfCurrentOrders() {
    }

    @Test
    void createCustomerReport() {
    }

    @Test
    void changeTheLisOfMaster() {
    }

    @Test
    void calculateSalaryOfTheMaster() {
    }

    @Test
    void assignService() {
    }

    @Test
    void createMasterProgressReport() {
    }

 */
}