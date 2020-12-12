package ru.sfedu.Aisova.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.Constants;

import ru.sfedu.Aisova.model.*;
import ru.sfedu.Aisova.utils.ConfigurationUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Data provider csv.
 */
public class DataProviderCsv implements DataProvider{

    private static final Logger log = LogManager.getLogger(DataProviderCsv.class);

    private <T> boolean writeToCsv (Class<?> tClass, List<T> object, boolean overwrite) {
        List<T> fileObjectList;
        if (!overwrite) {
            fileObjectList = (List<T>) readFromCsv(tClass);
            fileObjectList.addAll(object);
        }
        else {
            fileObjectList = new ArrayList<>(object);
        }
        CSVWriter csvWriter;
        try {
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(Constants.PATH_CSV)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION_CSV));
            csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(fileObjectList);
            log.info("Write success");
            csvWriter.close();
            return true;
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e ) {
            log.info("Write error");
            log.error(e);
            return false;
        }
    }

    private <T> boolean writeToCsv (T object) {
        if (object == null) {
            log.info("Something is null");
            return false;
        }
        return writeToCsv(object.getClass(), Collections.singletonList(object), false);
    }

    private <T> List<T> readFromCsv (Class<T> tClass) {
        try {
            FileReader reader = new FileReader(ConfigurationUtil.getConfigurationEntry(Constants.PATH_CSV)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION_CSV));

            CSVReader csvReader = new CSVReader(reader);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                    .withType(tClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            log.info("Read success");
            return csvToBean.parse();
        } catch (IOException e) {
            log.info("Read error");
            log.error(e);
            return new ArrayList<>();
        }
    }

    private <T> List<Service> getServiceListInMaster(Class<T> tClass, T object) throws IOException {
        try {
            List<Service> objectServiceList;
            Master master = (Master) object;
            objectServiceList = master.getListService();
            List<Service> serviceList = readFromCsv(Service.class);
            List<Long> idServiceInMaster;

            idServiceInMaster = objectServiceList.stream().map(value -> value.getId()).collect(Collectors.toList());

            List<Service>serviceListInMaster;
            serviceListInMaster =serviceList.stream().filter(service -> idServiceInMaster.stream()
                    .anyMatch(serviceInMaster -> serviceInMaster.longValue() ==  service.getId())).collect(Collectors.toList());

            return serviceListInMaster;

        }catch(NullPointerException e){
            log.error(e);
            return new ArrayList<>();
        }
    }

    private <T> List<OrderItem> getOrderItemList(Class<T> tClass, T object) throws IOException {
        try {
            List<OrderItem> objectOrderItemList;
            Order order = (Order) object;
            objectOrderItemList = order.getItem();
            List<OrderItem> orderItemList = readFromCsv(OrderItem.class);
            List<OrderItem> orderItemListInOrder;
            List<Long> idOrderItemInOrder;
            List<OrderItem> orderItemWithService;

            idOrderItemInOrder = objectOrderItemList.stream().map(value -> value.getId()).collect(Collectors.toList());

            List<Long> finalListOrderItemIdInOrder = idOrderItemInOrder;
            orderItemListInOrder =orderItemList.stream().filter(service -> finalListOrderItemIdInOrder.stream()
                    .anyMatch(orderItemInOrder -> orderItemInOrder.longValue() ==  service.getId())).collect(Collectors.toList());

            orderItemWithService = orderItemListInOrder.stream().map(orderItem -> getOrderItemById(orderItem.getId())).collect(Collectors.toList());
            return orderItemWithService;

        }catch(NullPointerException e){
            log.error(e);
            return new ArrayList<>();
        }
    }

    private <T> List<Master> getMasterList(Class<T> tClass, T object) throws IOException {
        try {
            List<Master> objectMasterList;
            Salon salon = (Salon) object;
            objectMasterList = salon.getListMaster();
            List<Master> masterList = readFromCsv(Master.class);
            List<Master> masterListInSalon;
            List<Long> idMasterInSalon;
            List<Master> masterWithService;

            idMasterInSalon = objectMasterList.stream().map(value -> value.getId()).collect(Collectors.toList());

            List<Long> finalListMasterIdInSalon = idMasterInSalon;
            masterListInSalon =masterList.stream().filter(master -> finalListMasterIdInSalon.stream()
                    .anyMatch(masterInSalon -> masterInSalon.longValue() ==  master.getId())).collect(Collectors.toList());

            masterWithService = masterListInSalon.stream().map(master -> getMasterById(master.getId())).collect(Collectors.toList());
            return masterWithService;

        }catch(NullPointerException e){
            log.error(e);
            return new ArrayList<>();
        }
    }

    private <T> Service getServiceInOrderItem(Class<T> tClass, T object) throws IOException {
        try {
            Service serviceInOrderItem;
            OrderItem orderItem = (OrderItem) object;
            Service service = orderItem.getService();
            List<Service> serviceList = readFromCsv(Service.class);
            serviceInOrderItem = serviceList
                    .stream()
                    .filter(x -> x.getId() == service.getId())
                    .findAny()
                    .orElse(null);
            return serviceInOrderItem;
        }catch (NullPointerException e){
            log.error(e);
            return null;
        }
    }

    private <T> Customer getNewCustomerInOrder(Class<T> tClass, T object) throws IOException {
        try {
            NewCustomer newCustomerInOrder;
            Order order = (Order) object;
            Customer newCustomer = order.getCustomer();
            List<NewCustomer> newCustomerList = readFromCsv(NewCustomer.class);
            newCustomerInOrder = newCustomerList
                    .stream()
                    .filter(x -> x.getId() == newCustomer.getId())
                    .findAny()
                    .orElse(null);
            return newCustomerInOrder;
        }catch (NullPointerException e){
            log.error(e);
            return null;
        }
    }

    private long getNextServiceId(){
        List<Service> objectList = readFromCsv(Service.class);
        long maxId = -1;
        for(Service service : objectList){
            if(maxId < service.getId()){
                maxId = service.getId();
            }
        }
        return maxId+1;
    }

    private long getNextOrderItemId(){
        List<OrderItem> objectList = readFromCsv(OrderItem.class);
        long maxId = -1;
        for(OrderItem orderItem : objectList){
            if(maxId < orderItem.getId()){
                maxId = orderItem.getId();
            }
        }
        return maxId+1;
    }

    private long getNextNewCustomerId(){
        try {
            List<NewCustomer> objectList = readFromCsv(NewCustomer.class);
            long maxId = -1;
            for(NewCustomer newCustomer : objectList){
                if(maxId < newCustomer.getId()){
                    maxId = newCustomer.getId();
                }
            }
            return maxId+1;
        }catch (NullPointerException e){
            log.error(e);
            return 0;
        }
    }

    private long getNextMasterId(){
        List<Master> objectList = readFromCsv(Master.class);
        long maxId = -1;
        for(Master master : objectList){
            if(maxId < master.getId()){
                maxId = master.getId();
            }
        }
        return maxId+1;
    }

    private long getNextRegularCustomerId(){
        List<RegularCustomer> objectList = readFromCsv(RegularCustomer.class);
        long maxId = -1;
        for(RegularCustomer regularCustomer : objectList){
            if(maxId < regularCustomer.getId()){
                maxId = regularCustomer.getId();
            }
        }
        return maxId+1;
    }

    private long getNextOrderId(){
        List<Order> objectList = readFromCsv(Order.class);
        long maxId = -1;
        for(Order order : objectList){
            if(maxId < order.getId()){
                maxId = order.getId();
            }
        }
        return maxId+1;
    }

    private long getNextSalonId(){
        List<Salon> objectList = readFromCsv(Salon.class);
        long maxId = -1;
        for(Salon salon : objectList){
            if(maxId < salon.getId()){
                maxId = salon.getId();
            }
        }
        return maxId+1;
    }

    @Override
    public boolean createService(String name, Double price, String description) {
        try {
            if (name == null || price == null || description == null){
                log.info(Constants.NULL_VALUE);
                return false;
            }else {
                Service service = new Service();
                service.setId(getNextServiceId());
                service.setName(name);
                service.setPrice(price);
                service.setDescription(description);
                log.info(Constants.SERVICE_CREATED);
                log.debug(service);
                return writeToCsv(service);
            }
        }catch (NullPointerException e){
            log.info(Constants.SERVICE_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editService(long id, String name, Double price, String description) {
        List<Service> listService = readFromCsv(Service.class);
        try {
            if (getServiceById(id) == null){
                log.info(Constants.SERVICE_ID + id + Constants.NOT_FOUND);
                return false;
            }
            Service newService = new Service();
            newService.setId(id);
            newService.setName(name);
            newService.setPrice(price);
            newService.setDescription(description);
            listService.removeIf(service -> service.getId() == id);
            writeToCsv(Service.class, listService, true);
            log.info(Constants.SERVICE_EDITED);
            log.debug(newService);
            writeToCsv(newService);
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException | NullPointerException e) {
            log.info(Constants.SERVICE_NOT_EDITED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteService(long id) {
        try {
            List<Service> serviceList = readFromCsv(Service.class);
            serviceList.removeIf(service -> service.getId() == id);
            log.debug(serviceList);
            writeToCsv(Service.class, serviceList, true);
            log.info(Constants.SERVICE_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.SERVICE_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public Service getServiceById(long id) {
        List<Service> listService = readFromCsv(Service.class);
        try {
            Service service = listService.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            log.info(Constants.SERVICE_RECEIVED);
            log.debug(service);
            return service;
        }catch (NoSuchElementException | NullPointerException e){
            log.info(Constants.SERVICE_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createNewCustomer(String firstName, String lastName, String phone, String email, Integer discount) {
        try {
            if (firstName == null || lastName == null || phone == null || email == null || discount == null){
                log.info(Constants.NULL_VALUE);
                return false;
            }else {
                NewCustomer customer = new NewCustomer();
                customer.setId(getNextNewCustomerId());
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPhone(phone);
                customer.setEmail(email);
                customer.setDiscount(discount);
                log.info(Constants.NEW_CUSTOMER_CREATED);
                log.debug(customer);
                return writeToCsv(customer);
            }
        }catch (NullPointerException e) {
            log.info(Constants.NEW_CUSTOMER_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editNewCustomer(long id, String firstName, String lastName, String phone, String email, Integer discount) {
        List<NewCustomer> newCustomerList = readFromCsv(NewCustomer.class);
        try {
            if (getNewCustomerById(id) == null){
                log.info(Constants.NEW_CUSTOMER_ID + id + Constants.NOT_FOUND);
                return false;
            }
            NewCustomer customer = new NewCustomer();
            customer.setId(id);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setPhone(phone);
            customer.setEmail(email);
            customer.setDiscount(discount);
            newCustomerList.removeIf(user -> user.getId() == id);
            writeToCsv(NewCustomer.class, newCustomerList, true);
            writeToCsv(customer);
            log.info(Constants.NEW_CUSTOMER_EDITED);
            log.debug(customer);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.info(Constants.NEW_CUSTOMER_NOT_EDITED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteNewCustomer(long id) {
        try{
            List<NewCustomer> newCustomerList = readFromCsv(NewCustomer.class);
            newCustomerList.removeIf(customer -> customer.getId() == id);
            log.debug(newCustomerList);
            writeToCsv(NewCustomer.class, newCustomerList, true);
            log.info(Constants.NEW_CUSTOMER_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.NEW_CUSTOMER_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public NewCustomer getNewCustomerById(long id) {
        List<NewCustomer> listNewCustomer = readFromCsv(NewCustomer.class);
        try {
            NewCustomer newCustomer = listNewCustomer.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            log.info(Constants.NEW_CUSTOMER_RECEIVED);
            log.debug(newCustomer);
            return newCustomer;
        }catch (NullPointerException | NoSuchElementException e){
            log.info(Constants.NEW_CUSTOMER_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createRegularCustomer(String firstName, String lastName, String phone, String email, Integer countOfOrder) {
        try{
            if (firstName == null || lastName == null || phone == null || email == null || countOfOrder == null){
                log.info(Constants.NULL_VALUE);
                return false;
            }else {
                RegularCustomer customer = new RegularCustomer();
                customer.setId(getNextRegularCustomerId());
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPhone(phone);
                customer.setEmail(email);
                customer.setNumberOfOrders(countOfOrder);
                log.info(Constants.REGULAR_CUSTOMER_CREATED);
                log.debug(customer);
                return writeToCsv(customer);
            }
        }catch (NullPointerException e) {
            log.info(Constants.REGULAR_CUSTOMER_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editRegularCustomer(long id, String firstName, String lastName, String phone, String email, Integer countOfOrder) {
        List<RegularCustomer> regularCustomerList = readFromCsv(RegularCustomer.class);
        try {
            if (getRegularCustomerById(id) == null){
                log.info(Constants.REGULAR_CUSTOMER_ID + id + Constants.NOT_FOUND);
                return false;
            }
            RegularCustomer customer = new RegularCustomer();
            customer.setId(id);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setPhone(phone);
            customer.setEmail(email);
            customer.setNumberOfOrders(countOfOrder);
            regularCustomerList.removeIf(user -> user.getId() == id);
            writeToCsv(RegularCustomer.class, regularCustomerList, true);
            writeToCsv(customer);
            log.info(Constants.REGULAR_CUSTOMER_EDITED);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.info(Constants.REGULAR_CUSTOMER_NOT_EDITED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteRegularCustomer(long id) {
        try{
            List<RegularCustomer> regularCustomerList = readFromCsv(RegularCustomer.class);
            regularCustomerList.removeIf(customer -> customer.getId() == id);
            log.debug(regularCustomerList);
            writeToCsv(RegularCustomer.class, regularCustomerList, true);
            log.info(Constants.REGULAR_CUSTOMER_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.REGULAR_CUSTOMER_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public RegularCustomer getRegularCustomerById(long id) {
        List<RegularCustomer> listRegularCustomer = readFromCsv(RegularCustomer.class);
        try {
            RegularCustomer regularCustomer = listRegularCustomer.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            log.info(Constants.REGULAR_CUSTOMER_RECEIVED);
            log.debug(regularCustomer);
            return regularCustomer;
        }catch (NullPointerException | NoSuchElementException e){
            log.info(Constants.REGULAR_CUSTOMER_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createMaster(String firstName, String lastName, String position, String phone, Double salary, List<Service> listService) {
        try{
            if (firstName == null || lastName == null || position == null || phone == null || salary == null || listService == null){
                log.info(Constants.NULL_VALUE);
                return false;
            }else {
                Master master = new Master();
                master.setId(getNextMasterId());
                master.setFirstName(firstName);
                master.setLastName(lastName);
                master.setPosition(position);
                master.setListService(listService);
                master.setPhone(phone);
                master.setSalary(salary);
                log.info(Constants.MASTER_CREATED);
                log.debug(master);
                return writeToCsv(master);
            }
        }catch (NullPointerException e) {
            log.info(Constants.MASTER_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editMaster(long id, String firstName, String lastName, String position, String phone, Double salary, List<Service> listService) {
        List<Master> masterList = readFromCsv(Master.class);
        try {
            if (getMasterById(id) == null){
                log.info(Constants.MASTER_ID + id + Constants.NOT_FOUND);
                return false;
            }
            Master master = new Master();
            master.setId(id);
            master.setFirstName(firstName);
            master.setLastName(lastName);
            master.setPosition(position);
            master.setPhone(phone);
            master.setSalary(salary);
            master.setListService(listService);
            masterList.removeIf(user -> user.getId() == id);
            writeToCsv(Master.class, masterList, true);
            writeToCsv(master);
            log.info(Constants.MASTER_EDITED);
            log.debug(master);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.info(Constants.MASTER_NOT_EDITED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteMaster(long id) {
        try{
            List<Master> masterList = readFromCsv(Master.class);
            masterList.removeIf(master -> master.getId() == id);
            log.debug(masterList);
            writeToCsv(Master.class, masterList, true);
            log.info(Constants.MASTER_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.MASTER_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public Master getMasterById(long id) {
        try {
            List<Master> masterList = readFromCsv(Master.class);
            Master master = masterList.stream()
                    .filter(task -> task.getId() == id)
                    .findAny()
                    .orElse(null);

            master.setListService(getServiceListInMaster(Master.class, master));
            log.info(Constants.MASTER_RECEIVED);
            log.debug(master);
            return master;
        } catch (IOException | NoSuchElementException | NullPointerException e) {
            log.info(Constants.MASTER_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createSalon(String address, List<Master> listMaster) {
        try{
            if (address == null || listMaster == null){
                log.info(Constants.NULL_VALUE);
                return false;
            }else {
                Salon salon = new Salon();
                salon.setId(getNextSalonId());
                salon.setAddress(address);
                salon.setListMaster(listMaster);
                log.info(Constants.SALON_CREATED);
                log.debug(salon);
                return writeToCsv(salon);
            }
        }catch (NullPointerException e) {
            log.info(Constants.SALON_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editSalon(long id, String address, List<Master> listMaster) {
        List<Salon> salonList = readFromCsv(Salon.class);
        try {
            if (getSalonById(id) == null){
                log.info(Constants.SALON_ID + id + Constants.NOT_FOUND);
                return false;
            }
            Salon salon = new Salon();
            salon.setId(id);
            salon.setAddress(address);
            salon.setListMaster(listMaster);
            salonList.removeIf(sal -> sal.getId() == id);
            writeToCsv(Salon.class, salonList, true);
            writeToCsv(salon);
            log.info(Constants.SALON_EDITED);
            log.debug(salon);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.info(Constants.SALON_NOT_EDITED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteSalon(long id) {
        try {
            List<Salon> salonList = readFromCsv(Salon.class);
            salonList.removeIf(salon -> salon.getId() == id);
            writeToCsv(Salon.class, salonList, true);
            log.info(Constants.SALON_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.SALON_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public Salon getSalonById(long id) {
        try {
            List<Salon> listSalon = readFromCsv(Salon.class);
            Salon salon = listSalon.stream()
                    .filter(task -> task.getId() == id)
                    .findAny()
                    .orElse(null);

            salon.setListMaster(getMasterList(Salon.class, salon));
            log.info(Constants.SALON_RECEIVED);
            log.debug(salon);
            return salon;
        } catch (IOException | NoSuchElementException | NullPointerException e) {
            log.info(Constants.SALON_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createOrderItem(Service service, Double cost, Integer quantity) {
        try{
            if (service == null || cost == null || quantity == null){
                log.info(Constants.NULL_VALUE);
                return false;
            }else {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(getNextOrderItemId());
                orderItem.setService(service);
                orderItem.setCost(cost);
                orderItem.setQuantity(quantity);
                log.info(Constants.ORDER_ITEM_CREATED);
                log.debug(orderItem);
                return writeToCsv(orderItem);
            }
        }catch (NullPointerException e) {
            log.info(Constants.ORDER_ITEM_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editOrderItem(long id, Service service, Double cost, Integer quantity) {
        List<OrderItem> orderItemList = readFromCsv(OrderItem.class);
        try {
            if (getOrderItemById(id) == null){
                log.info(Constants.ORDER_ITEM_ID + id + Constants.NOT_FOUND);
                return false;
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setId(id);
            orderItem.setService(service);
            orderItem.setCost(cost);
            orderItem.setQuantity(quantity);
            orderItemList.removeIf(item -> item.getId() == id);
            writeToCsv(OrderItem.class, orderItemList, true);
            writeToCsv(orderItem);
            log.info(Constants.ORDER_ITEM_EDITED);
            log.debug(orderItem);
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException | NullPointerException e) {
            log.info(Constants.ORDER_ITEM_NOT_EDITED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteOrderItem(long id) {
        try{
            List<OrderItem> orderItemList = readFromCsv(OrderItem.class);
            orderItemList.removeIf(item -> item.getId() == id);
            writeToCsv(OrderItem.class, orderItemList, true);
            log.info(Constants.ORDER_ITEM_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.ORDER_ITEM_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public OrderItem getOrderItemById(long id) {
        try {
            List<OrderItem> orderItemList = readFromCsv(OrderItem.class);
            OrderItem orderItem = orderItemList.stream()
                    .filter(task -> task.getId() == id)
                    .findAny()
                    .orElse(null);

            orderItem.setService(getServiceInOrderItem(OrderItem.class, orderItem));
            log.info(Constants.ORDER_ITEM_RECEIVED);
            log.debug(orderItem);
            return orderItem;
        } catch (IOException | NoSuchElementException | NullPointerException e) {
            log.info(Constants.ORDER_ITEM_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createOrder(String created, List<OrderItem> item, Double cost, String status, Customer customer, String lastUpdated, String completed) {
        try{
            if (created == null || item == null || cost == null || status == null || customer == null){
                log.info(Constants.NULL_VALUE);
                return false;
            }else {
                Order order = new Order();
                order.setCreated(created);
                order.setId(getNextOrderId());
                order.setItem(item);
                order.setCost(cost);
                order.setStatus(status);
                order.setCustomer(customer);
                order.setLastUpdated(lastUpdated);
                order.setCompleted(completed);
                log.info(Constants.ORDER_CREATED);
                log.debug(order);
                return writeToCsv(order);
            }
        }catch (NullPointerException e) {
            log.info(Constants.ORDER_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editOrder(long id, String created, List<OrderItem> item, Double cost, String status, Customer customer, String lastUpdated, String completed) {
        List<Order> orderList = readFromCsv(Order.class);
        try {
            if (getOrderById(id) == null){
                log.info(Constants.ORDER_ID + id + Constants.NOT_FOUND);
                return false;
            }
            Order order = new Order();
            order.setCreated(created);
            order.setId(id);
            order.setItem(item);
            order.setCost(cost);
            order.setStatus(status);
            order.setCustomer(customer);
            order.setLastUpdated(lastUpdated);
            order.setCompleted(completed);
            orderList.removeIf(ord -> ord.getId() == id);
            writeToCsv(Order.class, orderList, true);
            writeToCsv(order);
            log.info(Constants.ORDER_EDITED);
            log.debug(order);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.info(Constants.ORDER_NOT_EDITED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteOrder(long id) {
        try{
            List<Order> orderList = readFromCsv(Order.class);
            orderList.removeIf(ord -> ord.getId() == id);
            writeToCsv(Order.class, orderList, true);
            log.info(Constants.ORDER_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.ORDER_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public Order getOrderById(long id) {
        try{
            List<Order> orderList = readFromCsv(Order.class);
            Order order = orderList.stream()
                    .filter(task -> task.getId() == id)
                    .findAny()
                    .orElse(null);

            order.setItem(getOrderItemList(Order.class, order));
            order.setCustomer(getNewCustomerInOrder(Order.class, order));
            log.info(Constants.ORDER_RECEIVED);
            log.debug(order);
            return order;
        } catch (IOException | NoSuchElementException | NullPointerException e) {
            log.info(Constants.ORDER_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public Double calculateOrderValue(long orderId) {
        return null;
    }

    @Override
    public List<Order> viewOrderHistory(Order order) {
        try{
            List<Order> orderList = readFromCsv(Order.class);
            order.setItem(getOrderItemList(Order.class, order));
            order.setCustomer(getNewCustomerInOrder(Order.class, order));
            log.info("Список заказов: " );
            log.debug(orderList);
            return orderList;
        }catch (NullPointerException | IOException | NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public List<Order> getListOfCurrentOrders(Order order, String status) {
        try{
            if(status.equals("PROCESSING")){
                List<Order> orderList = readFromCsv(Order.class);
                order.setItem(getOrderItemList(Order.class, order));
                order.setCustomer(getNewCustomerInOrder(Order.class, order));
                log.info("Список текущих заказов: " );
                log.debug(orderList);
                return orderList;
            }else {
                return null;
            }
        }catch (NullPointerException | IOException | NoSuchElementException e){
            log.error(e);
            return null;
        }
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
