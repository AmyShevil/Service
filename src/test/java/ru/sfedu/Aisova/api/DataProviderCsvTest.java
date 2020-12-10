package ru.sfedu.Aisova.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.sfedu.Aisova.Constants;
import ru.sfedu.Aisova.enums.OrderStatus;
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
    @org.junit.jupiter.api.Order(1)
    void editServiceSuccess() {
        Assertions.assertTrue(dataProvider.editService(2, "rewriteName", 5.0, "rewriteDescription"));
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void editServiceFail() {
        Assertions.assertFalse(dataProvider.editService(10, "rewriteName", 5.0, "rewriteDescription"));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void deleteServiceSuccess() {
        Assertions.assertTrue(dataProvider.deleteService(3));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void deleteServiceFail() {
        Assertions.assertTrue(dataProvider.deleteService(10));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void getServiceByIdSuccess() {
        log.debug(dataProvider.getServiceById(1));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void getServiceByIdFail() {
        log.debug(dataProvider.getServiceById(10));
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void createNewCustomerSuccess() {
        Assertions.assertTrue(dataProvider.createNewCustomer("firstName1", "lastName1", "phone1", "email1", 10));
        Assertions.assertTrue(dataProvider.createNewCustomer( "firstName2", "lastName2", "phone2", "email2", 20));
        Assertions.assertTrue(dataProvider.createNewCustomer( "firstName3", "lastName3", "phone3", "email3", 30));
        Assertions.assertTrue(dataProvider.createNewCustomer( "firstName4", "lastName4", "phone4", "email4", 40));
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
    @org.junit.jupiter.api.Order(1)
    void editNewCustomerSuccess() {
        Assertions.assertTrue(dataProvider.editNewCustomer(2,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 50));
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void editNewCustomerFail() {
        Assertions.assertFalse(dataProvider.editNewCustomer(10,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 50));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void deleteNewCustomerSuccess() {
        Assertions.assertTrue(dataProvider.deleteNewCustomer(3));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void deleteNewCustomerFail() {
        Assertions.assertTrue(dataProvider.deleteNewCustomer(10));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testGetNewCustomerByIdSuccess() {
        log.debug(dataProvider.getNewCustomerById(1));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testGetNewCustomerByIdFail() {
        log.debug(dataProvider.getNewCustomerById(10));
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void createRegularCustomerSuccess() {
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName1", "lastName1", "phone1", "email1", 1));
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName2", "lastName2", "phone2", "email2", 2));
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName3", "lastName3", "phone3", "email3", 3));
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName4", "lastName4", "phone4", "email4", 4));
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
    @org.junit.jupiter.api.Order(1)
    void editRegularCustomerSuccess() {
        Assertions.assertTrue(dataProvider.editRegularCustomer(2,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 5));
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void editRegularCustomerFail() {
        Assertions.assertFalse(dataProvider.editRegularCustomer(10,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 5));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void deleteRegularCustomerSuccess() {
        Assertions.assertTrue(dataProvider.deleteRegularCustomer(3));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void deleteRegularCustomerFail() {
        Assertions.assertTrue(dataProvider.deleteRegularCustomer(10));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void getRegularCustomerByIdSuccess() {
        log.debug(dataProvider.getRegularCustomerById(1));

    }
    @Test
    @org.junit.jupiter.api.Order(3)
    void getRegularCustomerByIdFail() {
        log.debug(dataProvider.getRegularCustomerById(10));
    }

     @Test
     @org.junit.jupiter.api.Order(4)
     void createMasterSuccess() {
         List<Service> listService1 = new ArrayList<>();
         listService1.add(dataProvider.getServiceById(0));
         listService1.add(dataProvider.getServiceById(1));
         List<Service> listService2 = new ArrayList<>();
         listService2.add(dataProvider.getServiceById(1));
         listService2.add(dataProvider.getServiceById(2));
         List<Service> listService3 = new ArrayList<>();
         listService3.add(dataProvider.getServiceById(0));
         listService3.add(dataProvider.getServiceById(1));
         listService3.add(dataProvider.getServiceById(2));

         Assertions.assertTrue(dataProvider.createMaster("FirstName1", "LastName1", "Position1", "Phone1", 10000.0, listService1));
         Assertions.assertTrue(dataProvider.createMaster("FirstName2", "LastName2", "Position2", "Phone2", 20000.0, listService2));
         Assertions.assertTrue(dataProvider.createMaster("FirstName3", "LastName3", "Position3", "Phone3", 30000.0, listService3));
         Assertions.assertTrue(dataProvider.createMaster("FirstName4", "LastName4", "Position4", "Phone4", 40000.0, listService3));
     }

    @Test
    @org.junit.jupiter.api.Order(4)
    void createMasterFail() {
        List<Service> listService1 = new ArrayList<>();
        listService1.add(dataProvider.getServiceById(0));
        listService1.add(dataProvider.getServiceById(1));
        List<Service> listService2 = new ArrayList<>();
        listService2.add(dataProvider.getServiceById(1));
        listService2.add(dataProvider.getServiceById(2));
        List<Service> listService3 = new ArrayList<>();
        listService3.add(dataProvider.getServiceById(0));
        listService3.add(dataProvider.getServiceById(1));
        listService3.add(dataProvider.getServiceById(2));

        Assertions.assertFalse(dataProvider.createMaster(null, "LastName1", "Position1", "Phone1", 10000.0, listService1));
        Assertions.assertFalse(dataProvider.createMaster("FirstName2", null, "Position2", "Phone2", 20000.0, listService2));
        Assertions.assertFalse(dataProvider.createMaster("FirstName3", "LastName3", null, "Phone3", 30000.0, listService3));
        Assertions.assertFalse(dataProvider.createMaster("FirstName4", "LastName4", "Position4", null, 10000.0, listService1));
        Assertions.assertFalse(dataProvider.createMaster("FirstName5", "LastName5", "Position5", "Phone5", null, listService3));
        Assertions.assertFalse(dataProvider.createMaster("FirstName6", "LastName6", "Position6", "Phone6", 60000.0, null));
    }

     @Test
     @org.junit.jupiter.api.Order(5)
     void editMasterSuccess() {
         List<Service> listService = new ArrayList<>();
         listService.add(dataProvider.getServiceById(2));
         listService.add(dataProvider.getServiceById(1));
         Assertions.assertTrue(dataProvider.editMaster(2,"rewriteFirstName", "rewriteLastName", "rewritePosition", "rewritePhone", 50000.0, listService));
     }

    @Test
    @org.junit.jupiter.api.Order(5)
    void editMasterFail() {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(2));
        listService.add(dataProvider.getServiceById(1));
        Assertions.assertFalse(dataProvider.editMaster(10,"rewriteFirstName", "rewriteLastName", "rewritePosition", "rewritePhone", 50000.0, listService));
    }

     @Test
     @org.junit.jupiter.api.Order(6)
     void deleteMasterSuccess() {
         Assertions.assertTrue(dataProvider.deleteMaster(3));
     }

    @Test
    @org.junit.jupiter.api.Order(6)
    void deleteMasterFail() {
        Assertions.assertTrue(dataProvider.deleteMaster(10));
    }

     @Test
     @org.junit.jupiter.api.Order(7)
     void getMasterByIdSuccess() {
         log.debug(dataProvider.getMasterById(1));
     }

    @Test
    @org.junit.jupiter.api.Order(7)
    void getMasterByIdFail() {
        log.debug(dataProvider.getMasterById(10));
    }

     @Test
     @org.junit.jupiter.api.Order(8)
     void createSalonSuccess() {
         List<Master> listMaster1 = new ArrayList<>();
         listMaster1.add(dataProvider.getMasterById(0));
         listMaster1.add(dataProvider.getMasterById(1));
         listMaster1.add(dataProvider.getMasterById(2));
         List<Master> listMaster2 = new ArrayList<>();
         listMaster2.add(dataProvider.getMasterById(0));
         listMaster2.add(dataProvider.getMasterById(1));
         List<Master> listMaster3 = new ArrayList<>();
         listMaster3.add(dataProvider.getMasterById(1));
         listMaster3.add(dataProvider.getMasterById(2));

         Assertions.assertTrue(dataProvider.createSalon("Address1", listMaster1));
         Assertions.assertTrue(dataProvider.createSalon("Address2", listMaster2));
         Assertions.assertTrue(dataProvider.createSalon("Address3", listMaster3));
     }

    @Test
    @org.junit.jupiter.api.Order(8)
    void createSalonFail() {
        List<Master> listMaster1 = new ArrayList<>();
        listMaster1.add(dataProvider.getMasterById(0));
        listMaster1.add(dataProvider.getMasterById(1));
        listMaster1.add(dataProvider.getMasterById(2));

        Assertions.assertFalse(dataProvider.createSalon(null, listMaster1));
        Assertions.assertFalse(dataProvider.createSalon("Address2", null));
    }

     @Test
     @org.junit.jupiter.api.Order(9)
     void editSalonSuccess() {
         List<Master> listMaster = new ArrayList<>();
         listMaster.add(dataProvider.getMasterById(2));
         listMaster.add(dataProvider.getMasterById(1));

         Assertions.assertTrue(dataProvider.editSalon(1,"rewriteAddress", listMaster));
     }

    @Test
    @org.junit.jupiter.api.Order(9)
    void editSalonFail() {
        List<Master> listMaster = new ArrayList<>();
        listMaster.add(dataProvider.getMasterById(2));
        listMaster.add(dataProvider.getMasterById(1));

        Assertions.assertFalse(dataProvider.editSalon(10,"rewriteAddress", listMaster));
    }

     @Test
     @org.junit.jupiter.api.Order(10)
     void deleteSalonSuccess() {
         Assertions.assertTrue(dataProvider.deleteSalon(2));
     }

    @Test
    @org.junit.jupiter.api.Order(10)
    void deleteSalonFail() {
        Assertions.assertTrue(dataProvider.deleteSalon(10));
    }

     @Test
     @org.junit.jupiter.api.Order(11)
     void getSalonByIdSuccess() {
         log.debug(dataProvider.getSalonById(0));
     }

    @Test
    @org.junit.jupiter.api.Order(11)
    void getSalonByIdFail() {
        log.debug(dataProvider.getSalonById(10));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
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
        Assertions.assertTrue(dataProvider.createOrderItem(service2, 4444.0, 4));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
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

    @Test
    @org.junit.jupiter.api.Order(5)
    void editOrderItemSuccess() {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(2));
        Service service = listService.stream().filter(el->el.getId()==2).findFirst().get();
        Assertions.assertTrue(dataProvider.editOrderItem(2, service, 500.0, 5));
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void editOrderItem() {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(2));
        Service service = listService.stream().filter(el->el.getId()==2).findFirst().get();
        Assertions.assertFalse(dataProvider.editOrderItem(10, service, 500.0, 5));
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void deleteOrderItemSuccess() {
        Assertions.assertTrue(dataProvider.deleteOrderItem(3));
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void deleteOrderItemFail() {
        Assertions.assertTrue(dataProvider.deleteOrderItem(10));
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void getOrderItemByIdSuccess() {
        log.debug(dataProvider.getOrderItemById(1));
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void getOrderItemByIdFail() {
        log.debug(dataProvider.getOrderItemById(10));
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void createOrderSuccess() {
        List<NewCustomer> newCustomerList = new ArrayList<>();
        newCustomerList.add(dataProvider.getNewCustomerById(0));
        newCustomerList.add(dataProvider.getNewCustomerById(1));
        newCustomerList.add(dataProvider.getNewCustomerById(2));
        NewCustomer customer1 = newCustomerList.stream().filter(el->el.getId()==0).findFirst().get();
        NewCustomer customer2 = newCustomerList.stream().filter(el->el.getId()==1).findFirst().get();
        NewCustomer customer3 = newCustomerList.stream().filter(el->el.getId()==2).findFirst().get();
        NewCustomer customer4 = newCustomerList.stream().filter(el->el.getId()==1).findFirst().get();

        List<OrderItem> orderItemList1 = new ArrayList<>();
        orderItemList1.add(dataProvider.getOrderItemById(1));
        List<OrderItem> orderItemList2 = new ArrayList<>();
        orderItemList2.add(dataProvider.getOrderItemById(0));
        orderItemList2.add(dataProvider.getOrderItemById(1));
        List<OrderItem> orderItemList3 = new ArrayList<>();
        orderItemList3.add(dataProvider.getOrderItemById(0));
        orderItemList3.add(dataProvider.getOrderItemById(1));
        orderItemList3.add(dataProvider.getOrderItemById(2));
        List<OrderItem> orderItemList4 = new ArrayList<>();
        orderItemList4.add(dataProvider.getOrderItemById(0));
        orderItemList4.add(dataProvider.getOrderItemById(2));

        Assertions.assertTrue(dataProvider.createOrder("01.12.2020", orderItemList1, 10000.0, "CREATED", customer1, null, null));
        Assertions.assertTrue(dataProvider.createOrder("02.12.2020", orderItemList2, 20000.0, "PROCESSING", customer2, "04.12.2020", null));
        Assertions.assertTrue(dataProvider.createOrder("03.12.2020", orderItemList3, 30000.0, "COMPLETED", customer3, "05.12.2020", "10.12.2020"));
        Assertions.assertTrue(dataProvider.createOrder("04.12.2020", orderItemList4, 40000.0, "CANCELED", customer4, null, "05.12.2020"));
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void createOrderFail() {
        List<NewCustomer> newCustomerList = new ArrayList<>();
        newCustomerList.add(dataProvider.getNewCustomerById(0));
        newCustomerList.add(dataProvider.getNewCustomerById(1));
        newCustomerList.add(dataProvider.getNewCustomerById(2));
        NewCustomer customer1 = newCustomerList.stream().filter(el->el.getId()==0).findFirst().get();
        NewCustomer customer2 = newCustomerList.stream().filter(el->el.getId()==1).findFirst().get();
        NewCustomer customer3 = newCustomerList.stream().filter(el->el.getId()==2).findFirst().get();
        NewCustomer customer4 = newCustomerList.stream().filter(el->el.getId()==1).findFirst().get();

        List<OrderItem> orderItemList1 = new ArrayList<>();
        orderItemList1.add(dataProvider.getOrderItemById(1));
        List<OrderItem> orderItemList3 = new ArrayList<>();
        orderItemList3.add(dataProvider.getOrderItemById(0));
        orderItemList3.add(dataProvider.getOrderItemById(1));
        orderItemList3.add(dataProvider.getOrderItemById(2));
        List<OrderItem> orderItemList4 = new ArrayList<>();
        orderItemList4.add(dataProvider.getOrderItemById(0));
        orderItemList4.add(dataProvider.getOrderItemById(2));

        Assertions.assertFalse(dataProvider.createOrder(null, orderItemList1, 10000.0, "CREATED", customer1, null, null));
        Assertions.assertFalse(dataProvider.createOrder("02.12.2020", null, 20000.0, "PROCESSING", customer2, "04.12.2020", null));
        Assertions.assertFalse(dataProvider.createOrder("03.12.2020", orderItemList3, null, "COMPLETED", customer3, "05.12.2020", "10.12.2020"));
        Assertions.assertFalse(dataProvider.createOrder("04.12.2020", orderItemList4, 40000.0, null, customer4, null, "05.12.2020"));
        Assertions.assertFalse(dataProvider.createOrder("04.12.2020", orderItemList4, 40000.0, "COMPLETED", null, "04.12.2020", "05.12.2020"));
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void editOrderSuccess() {
        List<NewCustomer> newCustomerList = new ArrayList<>();
        newCustomerList.add(dataProvider.getNewCustomerById(1));
        NewCustomer customer = newCustomerList.stream().filter(el->el.getId()==1).findFirst().get();

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(dataProvider.getOrderItemById(1));
        orderItemList.add(dataProvider.getOrderItemById(2));

        Assertions.assertTrue(dataProvider.editOrder(2,"01.12.2020", orderItemList, 50000.0, "COMPLETED", customer, "05.12.2020", "10.12.2020"));
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void editOrderFail() {
        List<NewCustomer> newCustomerList = new ArrayList<>();
        newCustomerList.add(dataProvider.getNewCustomerById(1));
        NewCustomer customer = newCustomerList.stream().filter(el->el.getId()==1).findFirst().get();

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(dataProvider.getOrderItemById(1));
        orderItemList.add(dataProvider.getOrderItemById(2));

        Assertions.assertFalse(dataProvider.editOrder(10,"01.12.2020", orderItemList, 50000.0, "COMPLETED", customer, "05.12.2020", "10.12.2020"));
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    void deleteOrderSuccess() {
        Assertions.assertTrue(dataProvider.deleteOrder(3));
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    void deleteOrderFail() {
        Assertions.assertTrue(dataProvider.deleteOrder(10));
    }

    @Test
    @org.junit.jupiter.api.Order(11)
    void getOrderByIdSuccess() {
        log.debug(dataProvider.getOrderById(1));
    }

    @Test
    @org.junit.jupiter.api.Order(11)
    void getOrderByIdFail() {
        log.debug(dataProvider.getOrderById(10));
    }
/*
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