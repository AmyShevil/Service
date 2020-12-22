package ru.sfedu.Aisova.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.sfedu.Aisova.TestBase;
import ru.sfedu.Aisova.model.*;
import ru.sfedu.Aisova.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.sfedu.Aisova.Constants.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DataProviderJdbcTest extends TestBase {

    private static final Logger log = LogManager.getLogger(DataProviderJdbcTest.class);
    private static final DataProviderJdbc dataProvider = new DataProviderJdbc();
    private Object NullPointerException;

    private static void deleteAll() {
        dataProvider.execute(DROP_TABLES);
    }

    private static void createTable() {
        dataProvider.execute(CREATE_SERVICE);
        dataProvider.execute(CREATE_NEW_CUSTOMER);
        dataProvider.execute(CREATE_REGULAR_CUSTOMER);
        dataProvider.execute(CREATE_ORDER_ITEM);
        dataProvider.execute(CREATE_MASTER);
        dataProvider.execute(CREATE_LIST_SERVICE);
        dataProvider.execute(CREATE_SALON);
        dataProvider.execute(CREATE_LIST_MASTER);
        dataProvider.execute(CREATE_ORDER);
        dataProvider.execute(CREATE_LIST_ITEM);
    }

    @BeforeAll
    static void init() {
        deleteAll();
        createTable();
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
        Assertions.assertTrue(dataProvider.editService(3, "rewriteName", 500.0, "rewriteDescription"));
        
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void editServiceFail() {
        Assertions.assertFalse(dataProvider.editService(10, "rewriteName", 500.0, "rewriteDescription"));
        
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void deleteServiceSuccess() {
        Assertions.assertTrue(dataProvider.deleteService(4));
        Assertions.assertNull(dataProvider.getServiceById(4));
        
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void deleteServiceFail() {
        Assertions.assertTrue(dataProvider.deleteService(10));
        Assertions.assertEquals(dataProvider.getServiceById(10), NullPointerException);
        
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void getServiceByIdSuccess() {
        log.debug(dataProvider.getServiceById(1));
        Assertions.assertNotNull(dataProvider.getServiceById(1));
        
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void getServiceByIdFail() {
        log.debug(dataProvider.getServiceById(10));
        Assertions.assertEquals(dataProvider.getServiceById(10), NullPointerException);
        
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void createNewCustomerSuccess() {
        Assertions.assertTrue(dataProvider.createNewCustomer("firstName1", "lastName1", "phone1", "email1", 10));
        Assertions.assertTrue(dataProvider.createNewCustomer( "firstName2", "lastName2", "phone2", "email2", 20));
        Assertions.assertTrue(dataProvider.createNewCustomer( "firstName3", "lastName3", "phone3", "email3", 30));
        Assertions.assertTrue(dataProvider.createNewCustomer( "firstName4", "lastName4", "phone4", "email4", 40));
        
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void createNewCustomerFail() {
        Assertions.assertFalse(dataProvider.createNewCustomer(null, "lastName1", "phone1", "email1", 10));
        Assertions.assertFalse(dataProvider.createNewCustomer( "firstName2", null, "phone2", "email2", 20));
        Assertions.assertFalse(dataProvider.createNewCustomer( "firstName3", "lastName3", null, "email3", 30));
        Assertions.assertFalse(dataProvider.createNewCustomer( "firstName4", "lastName4", "phone4", null, 40));
        Assertions.assertFalse(dataProvider.createNewCustomer( "firstName5", "lastName5", "phone5", "email5", null));
        
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void editNewCustomerSuccess() {
        Assertions.assertTrue(dataProvider.editNewCustomer(3,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 50));
        
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void editNewCustomerFail() {
        Assertions.assertFalse(dataProvider.editNewCustomer(10,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 50));
        
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void deleteNewCustomerSuccess() {
        Assertions.assertTrue(dataProvider.deleteNewCustomer(4));
        
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void deleteNewCustomerFail() {
        Assertions.assertTrue(dataProvider.deleteNewCustomer(10));
        Assertions.assertEquals(dataProvider.getNewCustomerById(10), Optional.empty());
        
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void getNewCustomerByIdSuccess() {
        log.debug(dataProvider.getNewCustomerById(1));
        Assertions.assertNotNull(dataProvider.getNewCustomerById(1));
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void getNewCustomerByIdFail() {
        log.debug(dataProvider.getNewCustomerById(10));
        Assertions.assertEquals(dataProvider.getNewCustomerById(10), Optional.empty());
        
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void createRegularCustomerSuccess() {
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName1", "lastName1", "phone1", "email1", 1));
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName2", "lastName2", "phone2", "email2", 2));
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName3", "lastName3", "phone3", "email3", 3));
        Assertions.assertTrue(dataProvider.createRegularCustomer( "firstName4", "lastName4", "phone4", "email4", 4));
        
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void createRegularCustomerFail() {
        Assertions.assertFalse(dataProvider.createRegularCustomer(null, "lastName1", "phone1", "email1", 1));
        Assertions.assertFalse(dataProvider.createRegularCustomer( "firstName2", null, "phone2", "email2", 2));
        Assertions.assertFalse(dataProvider.createRegularCustomer( "firstName3", "lastName3", null, "email3", 3));
        Assertions.assertFalse(dataProvider.createRegularCustomer( "firstName4", "lastName4", "phone4", null, 4));
        Assertions.assertFalse(dataProvider.createRegularCustomer( "firstName5", "lastName5", "phone5", "email5", null));
        
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void editRegularCustomerSuccess() {
        Assertions.assertTrue(dataProvider.editRegularCustomer(3,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 5));
        
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void editRegularCustomerFail() {
        Assertions.assertFalse(dataProvider.editRegularCustomer(10,"rewriteFirstName", "rewriteLastName", "rewritePhone", "rewriteEmail", 5));
        
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    void deleteRegularCustomerSuccess() {
        Assertions.assertTrue(dataProvider.deleteRegularCustomer(4));
        
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    void deleteRegularCustomerFail() {
        Assertions.assertTrue(dataProvider.deleteRegularCustomer(10));
        Assertions.assertEquals(dataProvider.getRegularCustomerById(10), Optional.empty());
        
    }

    @Test
    @org.junit.jupiter.api.Order(11)
    void getRegularCustomerByIdSuccess() {
        log.debug(dataProvider.getRegularCustomerById(1));
        Assertions.assertNotNull(dataProvider.getRegularCustomerById(1));

    }

    @Test
    @org.junit.jupiter.api.Order(11)
    void getRegularCustomerByIdFail() {
        log.debug(dataProvider.getRegularCustomerById(10));
        Assertions.assertEquals(dataProvider.getRegularCustomerById(10), Optional.empty());
        
    }

    @Test
    @org.junit.jupiter.api.Order(12)
    void createMasterSuccess() {
        List<Service> listService1 = new ArrayList<>();

        Assertions.assertTrue(dataProvider.createMaster("FirstName1", "LastName1", "Position1", "Phone1", 10000.0, listService1, true));
        Assertions.assertTrue(dataProvider.createMaster("FirstName2", "LastName2", "Position2", "Phone2", 20000.0, listService1, true));
        Assertions.assertTrue(dataProvider.createMaster("FirstName3", "LastName3", "Position3", "Phone3", 30000.0, listService1, true));
        Assertions.assertTrue(dataProvider.createMaster("FirstName4", "LastName4", "Position4", "Phone4", 40000.0, listService1, true));

        dataProvider.createListService(1,1);
        dataProvider.createListService(1,2);
        dataProvider.createListService(1,3);
        dataProvider.createListService(2,1);
        dataProvider.createListService(2,2);
        dataProvider.createListService(3,3);
        dataProvider.createListService(4,2);
        dataProvider.createListService(4,3);

        List<Long> list1 = new ArrayList<>();
        list1.add((long) 1);
        list1.add((long) 2);
        list1.add((long) 3);
        Assertions.assertEquals(list1, dataProvider.getListServiceById(1));
        List<Long> list2 = new ArrayList<>();
        list2.add((long) 1);
        list2.add((long) 2);
        Assertions.assertEquals(list2, dataProvider.getListServiceById(2));
        List<Long> list3 = new ArrayList<>();
        list3.add((long) 3);
        Assertions.assertEquals(list3, dataProvider.getListServiceById(3));
        List<Long> list4 = new ArrayList<>();
        list4.add((long) 2);
        list4.add((long) 3);
        Assertions.assertEquals(list4, dataProvider.getListServiceById(4));
        
    }

    @Test
    @org.junit.jupiter.api.Order(12)
    void createMasterFail() {
        List<Service> listService1 = new ArrayList<>();

        Assertions.assertFalse(dataProvider.createMaster(null, "LastName1", "Position1", "Phone1", 10000.0, listService1, true));
        Assertions.assertFalse(dataProvider.createMaster("FirstName2", null, "Position2", "Phone2", 20000.0, listService1, false));
        Assertions.assertFalse(dataProvider.createMaster("FirstName3", "LastName3", null, "Phone3", 30000.0, listService1, true));
        Assertions.assertFalse(dataProvider.createMaster("FirstName4", "LastName4", "Position4", null, 10000.0, listService1, false));
        Assertions.assertFalse(dataProvider.createMaster("FirstName5", "LastName5", "Position5", "Phone5", null, listService1, true));
        Assertions.assertFalse(dataProvider.createMaster("FirstName6", "LastName6", "Position6", "Phone6", 60000.0, null, false));

        dataProvider.createListService(1,10);
        dataProvider.createListService(10,2);
        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(list, dataProvider.getListServiceById(10));
        Assertions.assertEquals(NullPointerException, dataProvider.getServiceById(10));

    }

    @Test
    @org.junit.jupiter.api.Order(13)
    void editMasterSuccess() {
        List<Service> listService = new ArrayList<>();
        Assertions.assertTrue(dataProvider.editMaster(3,"rewriteFirstName", "rewriteLastName", "rewritePosition", "rewritePhone", 50000.0, listService, true));
        Assertions.assertTrue(dataProvider.editListService(3,1));
        List<Long> list = new ArrayList<>();
        list.add((long) 1);
        Assertions.assertEquals(list, dataProvider.getListServiceById(3));
        
    }

    @Test
    @org.junit.jupiter.api.Order(13)
    void editMasterFail() {
        List<Service> listService = new ArrayList<>();

        Assertions.assertFalse(dataProvider.editMaster(10,"rewriteFirstName", "rewriteLastName", "rewritePosition", "rewritePhone", 50000.0, listService, false));
        dataProvider.editListService(1,10);
        dataProvider.editListService(10,2);
        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(list, dataProvider.getListServiceById(10));
        Assertions.assertEquals(NullPointerException, dataProvider.getServiceById(10));
    }

    @Test
    @org.junit.jupiter.api.Order(14)
    void deleteMasterSuccess() {
        Assertions.assertTrue(dataProvider.deleteListService(4));
        Assertions.assertTrue(dataProvider.deleteMaster(4, true));
        Assertions.assertNull(dataProvider.getMasterById(4));
        
    }

    @Test
    @org.junit.jupiter.api.Order(14)
    void deleteMasterFail() {
        Assertions.assertFalse(dataProvider.deleteMaster(10, false));
        Assertions.assertEquals(dataProvider.getMasterById(10), NullPointerException);
        Assertions.assertTrue(dataProvider.deleteListService(10));
        
    }

    @Test
    @org.junit.jupiter.api.Order(15)
    void getMasterByIdSuccess() {
        log.debug(dataProvider.getMasterById(1));
        Assertions.assertNotNull(dataProvider.getMasterById(1));
        List<Long> list1 = new ArrayList<>();
        list1.add((long) 1);
        list1.add((long) 2);
        list1.add((long) 3);
        Assertions.assertEquals(list1, dataProvider.getListServiceById(1));
        
    }

    @Test
    @org.junit.jupiter.api.Order(15)
    void getMasterByIdFail() {
        log.debug(dataProvider.getMasterById(10));
        Assertions.assertEquals(dataProvider.getMasterById(10), NullPointerException);
        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(list, dataProvider.getListServiceById(10));

    }

    @Test
    @org.junit.jupiter.api.Order(16)
    void createSalonSuccess() {
        List<Master> listMaster1 = new ArrayList<>();

        Assertions.assertTrue(dataProvider.createSalon("Address1", listMaster1));
        Assertions.assertTrue(dataProvider.createSalon("Address2", listMaster1));
        Assertions.assertTrue(dataProvider.createSalon("Address3", listMaster1));

        dataProvider.createListMaster(1,1);
        dataProvider.createListMaster(1,2);
        dataProvider.createListMaster(1,3);
        dataProvider.createListMaster(2,2);
        dataProvider.createListMaster(3,1);
        dataProvider.createListMaster(3,3);

        List<Long> list1 = new ArrayList<>();
        list1.add((long) 1);
        list1.add((long) 2);
        list1.add((long) 3);
        Assertions.assertEquals(list1, dataProvider.getListMasterById(1));
        List<Long> list2 = new ArrayList<>();
        list2.add((long) 2);
        Assertions.assertEquals(list2, dataProvider.getListMasterById(2));
        List<Long> list3 = new ArrayList<>();
        list3.add((long) 1);
        list3.add((long) 3);
        Assertions.assertEquals(list3, dataProvider.getListMasterById(3));
        
    }

    @Test
    @org.junit.jupiter.api.Order(16)
    void createSalonFail() {
        List<Master> listMaster1 = new ArrayList<>();

        Assertions.assertFalse(dataProvider.createSalon(null, listMaster1));
        Assertions.assertFalse(dataProvider.createSalon("Address2", null));

        dataProvider.createListMaster(1,10);
        dataProvider.createListMaster(10,2);
        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(list, dataProvider.getListMasterById(10));
        Assertions.assertEquals(NullPointerException, dataProvider.getMasterById(10));
    }

    @Test
    @org.junit.jupiter.api.Order(17)
    void editSalonSuccess() {
        List<Master> listMaster = new ArrayList<>();

        Assertions.assertTrue(dataProvider.editSalon(2,"rewriteAddress", listMaster));
        Assertions.assertTrue(dataProvider.editListMaster(2,1));
        List<Long> list = new ArrayList<>();
        list.add((long) 1);
        Assertions.assertEquals(list, dataProvider.getListMasterById(2));
        
    }

    @Test
    @org.junit.jupiter.api.Order(17)
    void editSalonFail() {
        List<Master> listMaster = new ArrayList<>();

        Assertions.assertFalse(dataProvider.editSalon(10,"rewriteAddress", listMaster));

        dataProvider.editListMaster(1,10);
        dataProvider.editListMaster(10,2);
        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(list, dataProvider.getListMasterById(10));
        Assertions.assertEquals(NullPointerException, dataProvider.getMasterById(10));
        
    }

    @Test
    @org.junit.jupiter.api.Order(18)
    void deleteSalonSuccess() {
        Assertions.assertTrue(dataProvider.deleteListMaster(3));
        Assertions.assertTrue(dataProvider.deleteSalon(3));
        Assertions.assertNull(dataProvider.getSalonById(3));
        
    }

    @Test
    @org.junit.jupiter.api.Order(18)
    void deleteSalonFail() {
        Assertions.assertTrue(dataProvider.deleteListMaster(10));
        Assertions.assertFalse(dataProvider.deleteSalon(10));
        Assertions.assertEquals(dataProvider.getSalonById(10), NullPointerException);
        
    }

    @Test
    @org.junit.jupiter.api.Order(19)
    void getSalonByIdSuccess() {
        log.debug(dataProvider.getSalonById(1));
        Assertions.assertNotNull(dataProvider.getSalonById(1));
        List<Long> list1 = new ArrayList<>();
        list1.add((long) 1);
        list1.add((long) 2);
        list1.add((long) 3);
        Assertions.assertEquals(list1, dataProvider.getListMasterById(1));
        
    }

    @Test
    @org.junit.jupiter.api.Order(19)
    void getSalonByIdFail() {
        log.debug(dataProvider.getSalonById(10));
        Assertions.assertEquals(dataProvider.getSalonById(10), NullPointerException);

        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(list, dataProvider.getListMasterById(10));
    }

    @Test
    @org.junit.jupiter.api.Order(20)
    void createOrderItemSuccess() {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(1));
        listService.add(dataProvider.getServiceById(2));
        listService.add(dataProvider.getServiceById(3));
        Service service1 = listService.stream().filter(el->el.getId()==1).findFirst().get();
        Service service2 = listService.stream().filter(el->el.getId()==2).findFirst().get();
        Service service3 = listService.stream().filter(el->el.getId()==3).findFirst().get();
        Assertions.assertTrue(dataProvider.createOrderItem(service1,1));
        Assertions.assertTrue(dataProvider.createOrderItem(service2,2));
        Assertions.assertTrue(dataProvider.createOrderItem(service3,3));
        Assertions.assertTrue(dataProvider.createOrderItem(service2,4));
        
    }

    @Test
    @org.junit.jupiter.api.Order(20)
    void createOrderItemFail() {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(3));
        Service service3 = listService.stream().filter(el->el.getId()==3).findFirst().get();
        Assertions.assertFalse(dataProvider.createOrderItem(null,1));
        Assertions.assertFalse(dataProvider.createOrderItem(service3,null));
        
    }

    @Test
    @org.junit.jupiter.api.Order(21)
    void editOrderItemSuccess() {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(2));
        Service service = listService.stream().filter(el->el.getId()==2).findFirst().get();
        Assertions.assertTrue(dataProvider.editOrderItem(3, service, service.getPrice(), 5));
        
    }

    @Test
    @org.junit.jupiter.api.Order(21)
    void editOrderItemFail() {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(2));
        Service service = listService.stream().filter(el->el.getId()==2).findFirst().get();
        Assertions.assertFalse(dataProvider.editOrderItem(10, service, service.getPrice(), 5));
        
    }

    @Test
    @org.junit.jupiter.api.Order(22)
    void deleteOrderItemSuccess() {
        Assertions.assertTrue(dataProvider.deleteOrderItem(4));
        Assertions.assertNull(dataProvider.getOrderItemById(4));
        
    }

    @Test
    @org.junit.jupiter.api.Order(22)
    void deleteOrderItemFail() {
        Assertions.assertTrue(dataProvider.deleteOrderItem(10));
        Assertions.assertEquals(dataProvider.getOrderItemById(10), NullPointerException);
        
    }

    @Test
    @org.junit.jupiter.api.Order(23)
    void getOrderItemByIdSuccess() {
        log.debug(dataProvider.getOrderItemById(1));
        Assertions.assertNotNull(dataProvider.getOrderItemById(1));

    }

    @Test
    @org.junit.jupiter.api.Order(23)
    void getOrderItemByIdFail() {
        log.debug(dataProvider.getOrderItemById(10));
        Assertions.assertEquals(dataProvider.getOrderItemById(10), NullPointerException);
        
    }

    @Test
    @org.junit.jupiter.api.Order(24)
    void createOrderSuccess() {
        List<OrderItem> orderItemList1 = new ArrayList<>();

        Assertions.assertTrue(dataProvider.createOrder("01.12.2020", orderItemList1, 10000.0,"CREATED", 1, null, null));
        Assertions.assertTrue(dataProvider.createOrder("02.12.2020", orderItemList1, 20000.0,"COMPLETED", 3, "04.12.2020", null));
        Assertions.assertTrue(dataProvider.createOrder("03.12.2020", orderItemList1, 30000.0,"PROCESSING", 2, "05.12.2020", "10.12.2020"));
        Assertions.assertTrue(dataProvider.createOrder("04.12.2020", orderItemList1, 40000.0,"CANCELED", 2, null, "05.12.2020"));

        dataProvider.createListItem(1,1);
        dataProvider.createListItem(1,2);
        dataProvider.createListItem(1,3);
        dataProvider.createListItem(2,1);
        dataProvider.createListItem(2,2);
        dataProvider.createListItem(3,3);
        dataProvider.createListItem(4,2);
        dataProvider.createListItem(4,3);

        List<Long> list1 = new ArrayList<>();
        list1.add((long) 1);
        list1.add((long) 2);
        list1.add((long) 3);
        Assertions.assertEquals(list1, dataProvider.getListItemById(1));
        List<Long> list2 = new ArrayList<>();
        list2.add((long) 1);
        list2.add((long) 2);
        Assertions.assertEquals(list2, dataProvider.getListItemById(2));
        List<Long> list3 = new ArrayList<>();
        list3.add((long) 3);
        Assertions.assertEquals(list3, dataProvider.getListItemById(3));
        List<Long> list4 = new ArrayList<>();
        list4.add((long) 2);
        list4.add((long) 3);
        Assertions.assertEquals(list4, dataProvider.getListItemById(4));
    }

    @Test
    @org.junit.jupiter.api.Order(24)
    void createOrderFail() {
        List<OrderItem> orderItemList1 = new ArrayList<>();

        Assertions.assertFalse(dataProvider.createOrder(null, orderItemList1, 10000.0,"CREATED", 1, null, null));
        Assertions.assertFalse(dataProvider.createOrder("02.12.2020", null, 10000.0,"PROCESSING", 3, "04.12.2020", null));
        Assertions.assertFalse(dataProvider.createOrder("04.12.2020", orderItemList1, 10000.0,null, 2, null, "05.12.2020"));
        Assertions.assertFalse(dataProvider.createOrder("04.12.2020", orderItemList1, null,"PROCESSING", 2, null, "05.12.2020"));

        dataProvider.createListItem(1,10);
        dataProvider.createListItem(10,2);
        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(list, dataProvider.getListItemById(10));
        Assertions.assertEquals(NullPointerException, dataProvider.getOrderItemById(10));
    }

    @Test
    @org.junit.jupiter.api.Order(25)
    void editOrderSuccess() {
        List<OrderItem> orderItemList3 = new ArrayList<>();
        Assertions.assertTrue(dataProvider.editOrder(3,"03.12.2020", orderItemList3, 3000.0,"PROCESSING", 1, "11.12.2020", "12.12.2020"));

        Assertions.assertTrue(dataProvider.editListItem(3,1));
        List<Long> list = new ArrayList<>();
        list.add((long) 1);
        Assertions.assertEquals(list, dataProvider.getListItemById(3));
    }

    @Test
    @org.junit.jupiter.api.Order(25)
    void editOrderFail() {
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(dataProvider.getOrderItemById(1));
        orderItemList.add(dataProvider.getOrderItemById(2));
        Assertions.assertFalse(dataProvider.editOrder(10,"01.12.2020", orderItemList,  10000.0,"COMPLETED", 3, "05.12.2020", "10.12.2020"));
        dataProvider.editListItem(1,10);
        dataProvider.editListItem(10,2);
        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(list, dataProvider.getListItemById(10));
        Assertions.assertEquals(NullPointerException, dataProvider.getOrderItemById(10));
    }

    @Test
    @org.junit.jupiter.api.Order(26)
    void deleteOrderSuccess() {
        Assertions.assertTrue(dataProvider.deleteListItem(4));
        Assertions.assertTrue(dataProvider.deleteOrder(4));
        Assertions.assertNull(dataProvider.getOrderById(4));
    }

    @Test
    @org.junit.jupiter.api.Order(26)
    void deleteOrderFail() {
        Assertions.assertTrue(dataProvider.deleteListItem(10));
        Assertions.assertTrue(dataProvider.deleteOrder(10));
        Assertions.assertEquals(dataProvider.getOrderById(10), NullPointerException);
    }

    @Test
    @org.junit.jupiter.api.Order(27)
    void getOrderByIdSuccess() {
        log.debug(dataProvider.getOrderById(1));
        Assertions.assertNotNull(dataProvider.getOrderById(1));
        List<Long> list1 = new ArrayList<>();
        list1.add((long) 1);
        list1.add((long) 2);
        list1.add((long) 3);
        Assertions.assertEquals(list1, dataProvider.getListItemById(1));
    }

    @Test
    @org.junit.jupiter.api.Order(27)
    void getOrderByIdFail() {
        log.debug(dataProvider.getOrderById(10));
        Assertions.assertEquals(dataProvider.getOrderById(10), NullPointerException);

        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(list, dataProvider.getListItemById(10));
    }

    @Test
    @org.junit.jupiter.api.Order(28)
    void calculateOrderValueSuccess() {
        Order order = dataProvider.getOrderById(1);
        log.debug(dataProvider.calculateOrderValue(1));

    }

    @Test
    @org.junit.jupiter.api.Order(28)
    void calculateOrderValueFail() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(dataProvider.getOrderById(10));
        Assertions.assertFalse(orderList.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(29)
    void assignServiceSuccess() {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(1));
        listService.add(dataProvider.getServiceById(2));
        listService.add(dataProvider.getServiceById(3));
        Assertions.assertTrue(dataProvider.assignService(listService, 1));
    }

    @Test
    @org.junit.jupiter.api.Order(29)
    void assignServiceFail() {
        List<Service> listService = new ArrayList<>();
        listService.add(dataProvider.getServiceById(0));
        listService.add(dataProvider.getServiceById(1));
        listService.add(dataProvider.getServiceById(2));
        Assertions.assertFalse(dataProvider.assignService(listService, 10));
    }

    @Test
    @org.junit.jupiter.api.Order(30)
    void viewOrderHistorySuccess() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(dataProvider.getOrderById(1));
        orderList.add(dataProvider.getOrderById(2));
        orderList.add(dataProvider.getOrderById(3));

        log.debug( dataProvider.viewOrderHistory(1));
    }

    @Test
    @org.junit.jupiter.api.Order(30)
    void viewOrderHistoryFail() {
        log.debug(dataProvider.viewOrderHistory(10));
    }

    @Test
    @org.junit.jupiter.api.Order(31)
    void getListOfCurrentOrdersSuccess() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(dataProvider.getOrderById(0));
        orderList.add(dataProvider.getOrderById(1));
        orderList.add(dataProvider.getOrderById(2));

        log.debug(dataProvider.getListOfCurrentOrders(1, "PROCESSING"));
    }

    @Test
    @org.junit.jupiter.api.Order(31)
    void getListOfCurrentOrdersFail() {
        log.debug(dataProvider.getListOfCurrentOrders(1, "CREATED"));
        log.debug(dataProvider.getListOfCurrentOrders(10, "PROCESSING"));
    }

    @Test
    @org.junit.jupiter.api.Order(32)
    void changeTheLisOfMasterSuccess() {
        Salon salon = dataProvider.getSalonById(1);
        List<Master> masterList = salon.getListMaster();
        log.debug(dataProvider.changeTheLisOfMaster(1));
    }

    @Test
    @org.junit.jupiter.api.Order(32)
    void changeTheLisOfMasterFail() {
        log.debug(dataProvider.changeTheLisOfMaster(10));
        log.debug(dataProvider.changeTheLisOfMaster(10));

    }

    @Test
    @org.junit.jupiter.api.Order(33)
    void createCustomerReportSuccess() {
        log.debug(dataProvider.createCustomerReport(1));
    }

    @Test
    @org.junit.jupiter.api.Order(33)
    void createCustomerReportFail() {
        log.debug(dataProvider.createCustomerReport(10));
        Assertions.assertEquals(dataProvider.getOrderById(10), NullPointerException);

    }

    @Test
    @org.junit.jupiter.api.Order(34)
    void createMasterReportSuccess() {
        Assertions.assertNotNull(dataProvider.createMasterReport(1,true));
        log.debug(dataProvider.createMasterReport(1, true));
    }

    @Test
    @org.junit.jupiter.api.Order(34)
    void createMasterReportFail() {
        log.debug(dataProvider.createMasterReport(10, false));
        Assertions.assertEquals(dataProvider.getMasterById(10), NullPointerException);
    }

}