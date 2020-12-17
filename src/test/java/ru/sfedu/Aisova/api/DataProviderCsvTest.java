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
import java.util.stream.Collectors;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataProviderCsvTest {

    private static final Logger log = LogManager.getLogger(DataProviderCsvTest.class);
    private static final DataProvider dataProvider = new DataProviderCsv();
    private Object NullPointerException;

    private static <T> void deleteFile(Class<T> tClass) {
        try {
            log.debug(new File(ConfigurationUtil.getConfigurationEntry(Constants.PATH_CSV)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION_CSV)).delete());
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
    void createServiceSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.createService("service1", 1000.0, "description1"));
        Assertions.assertTrue(dataProvider.createService("service2", 2000.0, "description2"));
        Assertions.assertTrue(dataProvider.createService("service3", 3000.0, "description3"));
        Assertions.assertTrue(dataProvider.createService("service4", 4000.0, "description4"));
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void createServiceFail() throws Exception {
        Assertions.assertFalse(dataProvider.createService(null, 1000.0, "description1"));
        Assertions.assertFalse(dataProvider.createService("service2", null, "description2"));
        Assertions.assertFalse(dataProvider.createService("service3", 3000.0, null));
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void editServiceSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.editService(2, "rewriteName", 500.0, "rewriteDescription"));
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void editServiceFail() throws Exception {
        Assertions.assertFalse(dataProvider.editService(10, "rewriteName", 500.0, "rewriteDescription"));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void deleteServiceSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.deleteService(3));
        Assertions.assertNull(dataProvider.getServiceById(3));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void deleteServiceFail() throws Exception {
        Assertions.assertTrue(dataProvider.deleteService(10));
        Assertions.assertEquals(dataProvider.getServiceById(10), NullPointerException);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void getServiceByIdSuccess() throws Exception {
        log.debug(dataProvider.getServiceById(1));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void getServiceByIdFail() throws Exception {
        log.debug(dataProvider.getServiceById(10));
        Assertions.assertEquals(dataProvider.getServiceById(10), NullPointerException);
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void createNewCustomerSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.createNewCustomer("firstName1", "lastName1", "phone1", "email1", 10));
        Assertions.assertTrue(dataProvider.createNewCustomer( "firstName2", "lastName2", "phone2", "email2", 20));
        Assertions.assertTrue(dataProvider.createNewCustomer( "firstName3", "lastName3", "phone3", "email3", 30));
        Assertions.assertTrue(dataProvider.createNewCustomer( "firstName4", "lastName4", "phone4", "email4", 40));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void createNewCustomerFail() throws Exception {
        Assertions.assertFalse(dataProvider.createNewCustomer(null, "lastName1", "phone1", "email1", 10));
        Assertions.assertFalse(dataProvider.createNewCustomer( "firstName2", null, "phone2", "email2", 20));
        Assertions.assertFalse(dataProvider.createNewCustomer( "firstName3", "lastName3", null, "email3", 30));
        Assertions.assertFalse(dataProvider.createNewCustomer( "firstName4", "lastName4", "phone4", null, 40));
        Assertions.assertFalse(dataProvider.createNewCustomer( "firstName5", "lastName5", "phone5", "email5", null));
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void editNewCustomerSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.editNewCustomer(2,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 50));
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void editNewCustomerFail() throws Exception {
        Assertions.assertFalse(dataProvider.editNewCustomer(10,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 50));
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void deleteNewCustomerSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.deleteNewCustomer(3));
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void deleteNewCustomerFail() throws Exception {
        Assertions.assertTrue(dataProvider.deleteNewCustomer(10));
        Assertions.assertEquals(dataProvider.getNewCustomerById(10), Optional.empty());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void getNewCustomerByIdSuccess() throws Exception {
        log.debug(dataProvider.getNewCustomerById(1));
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void getNewCustomerByIdFail() throws Exception {
        log.debug(dataProvider.getNewCustomerById(10));
        Assertions.assertEquals(dataProvider.getNewCustomerById(10), Optional.empty());

    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void createRegularCustomerSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName1", "lastName1", "phone1", "email1", 1));
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName2", "lastName2", "phone2", "email2", 2));
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName3", "lastName3", "phone3", "email3", 3));
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName4", "lastName4", "phone4", "email4", 4));
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void createRegularCustomerFail() throws Exception {
        Assertions.assertFalse(dataProvider.createRegularCustomer(null, "lastName1", "phone1", "email1", 1));
        Assertions.assertFalse(dataProvider.createRegularCustomer( "firstName2", null, "phone2", "email2", 2));
        Assertions.assertFalse(dataProvider.createRegularCustomer( "firstName3", "lastName3", null, "email3", 3));
        Assertions.assertFalse(dataProvider.createRegularCustomer( "firstName4", "lastName4", "phone4", null, 4));
        Assertions.assertFalse(dataProvider.createRegularCustomer( "firstName5", "lastName5", "phone5", "email5", null));
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void editRegularCustomerSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.editRegularCustomer(2,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 5));
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void editRegularCustomerFail() throws Exception {
        Assertions.assertFalse(dataProvider.editRegularCustomer(10,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 5));
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    void deleteRegularCustomerSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.deleteRegularCustomer(3));
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    void deleteRegularCustomerFail() throws Exception {
        Assertions.assertTrue(dataProvider.deleteRegularCustomer(10));
        Assertions.assertEquals(dataProvider.getRegularCustomerById(10), Optional.empty());
    }

    @Test
    @org.junit.jupiter.api.Order(11)
    void getRegularCustomerByIdSuccess() throws Exception {
        log.debug(dataProvider.getRegularCustomerById(1));

    }
    @Test
    @org.junit.jupiter.api.Order(11)
    void getRegularCustomerByIdFail() throws Exception {
        log.debug(dataProvider.getRegularCustomerById(10));
        Assertions.assertEquals(dataProvider.getRegularCustomerById(10), Optional.empty());
    }

     @Test
     @org.junit.jupiter.api.Order(12)
     void createMasterSuccess() throws Exception {
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
    @org.junit.jupiter.api.Order(12)
    void createMasterFail() throws Exception {
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
     @org.junit.jupiter.api.Order(13)
     void editMasterSuccess() throws Exception {
         List<Service> listService = new ArrayList<>();
         listService.add(dataProvider.getServiceById(2));
         listService.add(dataProvider.getServiceById(1));
         Assertions.assertTrue(dataProvider.editMaster(2,"rewriteFirstName", "rewriteLastName", "rewritePosition", "rewritePhone", 50000.0, listService));
     }

    @Test
    @org.junit.jupiter.api.Order(13)
    void editMasterFail() throws Exception {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(2));
        listService.add(dataProvider.getServiceById(1));
        Assertions.assertFalse(dataProvider.editMaster(10,"rewriteFirstName", "rewriteLastName", "rewritePosition", "rewritePhone", 50000.0, listService));
    }

     @Test
     @org.junit.jupiter.api.Order(14)
     void deleteMasterSuccess() throws Exception {
         Assertions.assertTrue(dataProvider.deleteMaster(3));
         Assertions.assertNull(dataProvider.getMasterById(3));
     }

    @Test
    @org.junit.jupiter.api.Order(14)
    void deleteMasterFail() throws Exception {
        Assertions.assertTrue(dataProvider.deleteMaster(10));
        Assertions.assertEquals(dataProvider.getMasterById(10), NullPointerException);
    }

     @Test
     @org.junit.jupiter.api.Order(15)
     void getMasterByIdSuccess() throws Exception {
         log.debug(dataProvider.getMasterById(1));
     }

    @Test
    @org.junit.jupiter.api.Order(15)
    void getMasterByIdFail() throws Exception {
        log.debug(dataProvider.getMasterById(10));
        Assertions.assertEquals(dataProvider.getMasterById(10), NullPointerException);

    }

     @Test
     @org.junit.jupiter.api.Order(16)
     void createSalonSuccess() throws Exception {
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
    @org.junit.jupiter.api.Order(16)
    void createSalonFail() throws Exception {
        List<Master> listMaster1 = new ArrayList<>();
        listMaster1.add(dataProvider.getMasterById(0));
        listMaster1.add(dataProvider.getMasterById(1));
        listMaster1.add(dataProvider.getMasterById(2));

        Assertions.assertFalse(dataProvider.createSalon(null, listMaster1));
        Assertions.assertFalse(dataProvider.createSalon("Address2", null));
    }

     @Test
     @org.junit.jupiter.api.Order(17)
     void editSalonSuccess() throws Exception {
         List<Master> listMaster = new ArrayList<>();
         listMaster.add(dataProvider.getMasterById(2));
         listMaster.add(dataProvider.getMasterById(1));

         Assertions.assertTrue(dataProvider.editSalon(1,"rewriteAddress", listMaster));
     }

    @Test
    @org.junit.jupiter.api.Order(17)
    void editSalonFail() throws Exception {
        List<Master> listMaster = new ArrayList<>();
        listMaster.add(dataProvider.getMasterById(2));
        listMaster.add(dataProvider.getMasterById(1));

        Assertions.assertFalse(dataProvider.editSalon(10,"rewriteAddress", listMaster));
    }

     @Test
     @org.junit.jupiter.api.Order(18)
     void deleteSalonSuccess() throws Exception {
         Assertions.assertTrue(dataProvider.deleteSalon(2));
         Assertions.assertNull(dataProvider.getSalonById(3));
     }

    @Test
    @org.junit.jupiter.api.Order(18)
    void deleteSalonFail() throws Exception {
        Assertions.assertTrue(dataProvider.deleteSalon(10));
        Assertions.assertEquals(dataProvider.getSalonById(10), NullPointerException);
    }

     @Test
     @org.junit.jupiter.api.Order(19)
     void getSalonByIdSuccess() throws Exception {
         log.debug(dataProvider.getSalonById(0));
     }

    @Test
    @org.junit.jupiter.api.Order(19)
    void getSalonByIdFail() throws Exception {
        log.debug(dataProvider.getSalonById(10));
        Assertions.assertEquals(dataProvider.getSalonById(10), NullPointerException);

    }

    @Test
    @org.junit.jupiter.api.Order(20)
    void createOrderItemSuccess() throws Exception {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(0));
        listService.add(dataProvider.getServiceById(1));
        listService.add(dataProvider.getServiceById(2));
        Service service1 = listService.stream().filter(el->el.getId()==0).findFirst().get();
        Service service2 = listService.stream().filter(el->el.getId()==1).findFirst().get();
        Service service3 = listService.stream().filter(el->el.getId()==2).findFirst().get();
        Assertions.assertTrue(dataProvider.createOrderItem(service1,1));
        Assertions.assertTrue(dataProvider.createOrderItem(service2,2));
        Assertions.assertTrue(dataProvider.createOrderItem(service3,3));
        Assertions.assertTrue(dataProvider.createOrderItem(service2,4));
    }

    @Test
    @org.junit.jupiter.api.Order(20)
    void createOrderItemFail() throws Exception {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(0));
        listService.add(dataProvider.getServiceById(1));
        listService.add(dataProvider.getServiceById(2));
        Service service2 = listService.stream().filter(el->el.getId()==1).findFirst().get();
        Service service3 = listService.stream().filter(el->el.getId()==2).findFirst().get();
        Assertions.assertFalse(dataProvider.createOrderItem(null,1));
        Assertions.assertFalse(dataProvider.createOrderItem(service3,null));
    }

    @Test
    @org.junit.jupiter.api.Order(21)
    void editOrderItemSuccess() throws Exception {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(2));
        Service service = listService.stream().filter(el->el.getId()==2).findFirst().get();
        Assertions.assertTrue(dataProvider.editOrderItem(2, service, service.getPrice(), 5));
    }

    @Test
    @org.junit.jupiter.api.Order(21)
    void editOrderItemFail() throws Exception {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(2));
        Service service = listService.stream().filter(el->el.getId()==2).findFirst().get();
        Assertions.assertFalse(dataProvider.editOrderItem(10, service, service.getPrice(), 5));
    }

    @Test
    @org.junit.jupiter.api.Order(22)
    void deleteOrderItemSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.deleteOrderItem(3));
        Assertions.assertNull(dataProvider.getOrderItemById(3));
    }

    @Test
    @org.junit.jupiter.api.Order(22)
    void deleteOrderItemFail() throws Exception {
        Assertions.assertTrue(dataProvider.deleteOrderItem(10));
        Assertions.assertEquals(dataProvider.getOrderItemById(10), NullPointerException);
    }

    @Test
    @org.junit.jupiter.api.Order(23)
    void getOrderItemByIdSuccess() throws Exception {
        log.debug(dataProvider.getOrderItemById(1));
    }

    @Test
    @org.junit.jupiter.api.Order(23)
    void getOrderItemByIdFail() throws Exception {
        log.debug(dataProvider.getOrderItemById(10));
        Assertions.assertEquals(dataProvider.getOrderItemById(10), NullPointerException);

    }

    @Test
    @org.junit.jupiter.api.Order(24)
    void createOrderSuccess() throws Exception {
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

        Assertions.assertTrue(dataProvider.createOrder("01.12.2020", orderItemList1, 10000.0,"CREATED", 0, null, null));
        Assertions.assertTrue(dataProvider.createOrder("02.12.2020", orderItemList2, 10000.0,"PROCESSING", 1, "04.12.2020", null));
        Assertions.assertTrue(dataProvider.createOrder("03.12.2020", orderItemList3, 10000.0,"COMPLETED", 2, "05.12.2020", "10.12.2020"));
        Assertions.assertTrue(dataProvider.createOrder("04.12.2020", orderItemList4, 10000.0,"CANCELED", 3, null, "05.12.2020"));
    }

    @Test
    @org.junit.jupiter.api.Order(24)
    void createOrderFail() throws Exception {
        List<OrderItem> orderItemList1 = new ArrayList<>();
        orderItemList1.add(dataProvider.getOrderItemById(1));
        List<OrderItem> orderItemList4 = new ArrayList<>();
        orderItemList4.add(dataProvider.getOrderItemById(0));
        orderItemList4.add(dataProvider.getOrderItemById(2));

        Assertions.assertFalse(dataProvider.createOrder(null, orderItemList1, 10000.0,"CREATED", 0, null, null));
        Assertions.assertFalse(dataProvider.createOrder("02.12.2020", null, 10000.0,"PROCESSING", 1, "04.12.2020", null));
        Assertions.assertFalse(dataProvider.createOrder("04.12.2020", orderItemList4, 10000.0,null, 2, null, "05.12.2020"));
        Assertions.assertFalse(dataProvider.createOrder("04.12.2020", orderItemList4, null,"PROCESSING", 2, null, "05.12.2020"));
    }

    @Test
    @org.junit.jupiter.api.Order(25)
    void editOrderSuccess() throws Exception {
        List<OrderItem> orderItemList1 = new ArrayList<>();
        orderItemList1.add(dataProvider.getOrderItemById(1));
        List<OrderItem> orderItemList2 = new ArrayList<>();
        orderItemList2.add(dataProvider.getOrderItemById(0));
        orderItemList2.add(dataProvider.getOrderItemById(1));
        List<OrderItem> orderItemList3 = new ArrayList<>();
        orderItemList3.add(dataProvider.getOrderItemById(0));
        orderItemList3.add(dataProvider.getOrderItemById(1));
        orderItemList3.add(dataProvider.getOrderItemById(2));

        Assertions.assertTrue(dataProvider.editOrder(0,"01.12.2020", orderItemList1, 10000.0,"CREATED", 0, null, null));
        Assertions.assertTrue(dataProvider.editOrder(1,"02.12.2020", orderItemList2, 10000.0,"PROCESSING", 1, "04.12.2020", null));
        Assertions.assertTrue(dataProvider.editOrder(2,"03.12.2020", orderItemList3, 10000.0,"COMPLETED", 1, "05.12.2020", "10.12.2020"));

    }

    @Test
    @org.junit.jupiter.api.Order(25)
    void editOrderFail() throws Exception {
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(dataProvider.getOrderItemById(1));
        orderItemList.add(dataProvider.getOrderItemById(2));

        Assertions.assertFalse(dataProvider.editOrder(10,"01.12.2020", orderItemList,  10000.0,"COMPLETED", 0, "05.12.2020", "10.12.2020"));
    }

    @Test
    @org.junit.jupiter.api.Order(26)
    void deleteOrderSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.deleteOrder(3));
        Assertions.assertNull(dataProvider.getOrderById(3));
    }

    @Test
    @org.junit.jupiter.api.Order(26)
    void deleteOrderFail() throws Exception {
        Assertions.assertTrue(dataProvider.deleteOrder(10));
        Assertions.assertEquals(dataProvider.getOrderById(10), NullPointerException);
    }

    @Test
    @org.junit.jupiter.api.Order(27)
    void getOrderByIdSuccess() throws Exception {
        log.debug(dataProvider.getOrderById(1));
    }

    @Test
    @org.junit.jupiter.api.Order(27)
    void getOrderByIdFail() throws Exception {
        log.debug(dataProvider.getOrderById(10));
        Assertions.assertEquals(dataProvider.getOrderById(10), NullPointerException);

    }

    @Test
    @org.junit.jupiter.api.Order(28)
    void calculateOrderValueSuccess() throws Exception {
        Order order = dataProvider.getOrderById(1);
        Assertions.assertEquals(order.getCost(), dataProvider.calculateOrderValue(1));
    }

    @Test
    @org.junit.jupiter.api.Order(28)
    void calculateOrderValueFail() throws Exception {
        List<Order> orderList = new ArrayList<>();
        orderList.add(dataProvider.getOrderById(10));
        Assertions.assertFalse(orderList.isEmpty());
        Assertions.assertEquals(dataProvider.calculateOrderValue(10), NullPointerException);

    }

    @Test
    @org.junit.jupiter.api.Order(29)
    void assignServiceSuccess() throws Exception {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(0));
        listService.add(dataProvider.getServiceById(1));
        listService.add(dataProvider.getServiceById(2));
        Assertions.assertTrue(dataProvider.assignService(listService, 1));
    }

    @Test
    @org.junit.jupiter.api.Order(29)
    void assignServiceFail() throws Exception {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(0));
        listService.add(dataProvider.getServiceById(1));
        listService.add(dataProvider.getServiceById(2));
        Assertions.assertFalse(dataProvider.assignService(listService, 10));
    }

    @Test
    @org.junit.jupiter.api.Order(30)
    void viewOrderHistorySuccess() throws Exception {
        List<Order> orderList = new ArrayList<>();
        orderList.add(dataProvider.getOrderById(0));
        orderList.add(dataProvider.getOrderById(1));
        orderList.add(dataProvider.getOrderById(2));
        orderList = orderList.stream()
                .filter(user -> user.getCustomerId() == 1)
                .collect(Collectors.toList());
        log.debug( dataProvider.viewOrderHistory(1));
    }

    @Test
    @org.junit.jupiter.api.Order(30)
    void viewOrderHistoryFail() throws Exception {
        log.debug(dataProvider.viewOrderHistory(10));
    }

    @Test
    @org.junit.jupiter.api.Order(31)
    void getListOfCurrentOrdersSuccess() throws Exception {
        List<Order> orderList = new ArrayList<>();
        orderList.add(dataProvider.getOrderById(0));
        orderList.add(dataProvider.getOrderById(1));
        orderList.add(dataProvider.getOrderById(2));
        orderList = orderList.stream()
                .filter(user -> user.getCustomerId() == 1 && user.getStatus().equals("PROCESSING"))
                .collect(Collectors.toList());
        log.debug(dataProvider.getListOfCurrentOrders(1, "PROCESSING"));
    }

    @Test
    @org.junit.jupiter.api.Order(31)
    void getListOfCurrentOrdersFail() throws Exception {
        log.debug(dataProvider.getListOfCurrentOrders(1, "CREATED"));
        log.debug(dataProvider.getListOfCurrentOrders(10, "PROCESSING"));
    }

    @Test
    @org.junit.jupiter.api.Order(32)
    void changeTheLisOfMasterSuccess() throws Exception {
        Salon salon = dataProvider.getSalonById(1);
        List<Master> masterList = salon.getListMaster();
        Assertions.assertEquals(masterList, dataProvider.changeTheLisOfMaster(1));
    }

    @Test
    @org.junit.jupiter.api.Order(32)
    void changeTheLisOfMasterFail() throws Exception {
        log.debug(dataProvider.changeTheLisOfMaster(10));
        Assertions.assertEquals(dataProvider.getSalonById(10), NullPointerException);

    }

    @Test
    @org.junit.jupiter.api.Order(33)
    void createCustomerReportSuccess() throws Exception {
        log.debug(dataProvider.createCustomerReport(1));
    }

    @Test
    @org.junit.jupiter.api.Order(33)
    void createCustomerReportFail() throws Exception {
        log.debug(dataProvider.createCustomerReport(10));
        Assertions.assertEquals(dataProvider.getOrderById(10), NullPointerException);

    }

    @Test
    @org.junit.jupiter.api.Order(34)
    void createMasterReportSuccess() throws Exception {
        log.debug(dataProvider.createMasterReport(1));
    }

    @Test
    @org.junit.jupiter.api.Order(34)
    void createMasterReportFail() throws Exception {
        log.debug(dataProvider.createMasterReport(10));
        Assertions.assertEquals(dataProvider.getMasterById(10), NullPointerException);

    }

}