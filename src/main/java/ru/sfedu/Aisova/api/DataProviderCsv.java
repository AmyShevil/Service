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
import static ru.sfedu.Aisova.Constants.*;
import ru.sfedu.Aisova.model.*;
import static ru.sfedu.Aisova.utils.ConfigurationUtil.getConfigurationEntry;

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

    /**
     * Запись в файл csv
     *
     * @param tClass
     * @param object
     * @param rewrite
     * @param <T>
     * @return true или false
     */
    private <T> boolean writeToCsv (Class<?> tClass, List<T> object, boolean rewrite) {
        try {
            List<T> objectList;
            if (!rewrite) {
                objectList = (List<T>) readFromCsv(tClass);
                objectList.addAll(object);
            }
            else {
                objectList = new ArrayList<>(object);
            }
            CSVWriter csvWriter;
            FileWriter writer = new FileWriter(getConfigurationEntry(PATH_CSV)
                    + tClass.getSimpleName().toLowerCase()
                    + getConfigurationEntry(FILE_EXTENSION_CSV));
            csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).withApplyQuotesToAll(false).build();
            beanToCsv.write(objectList);
            log.info(WRITE_SUCCESS);
            csvWriter.close();
            return true;
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e ) {
            log.info(WRITE_ERROR);
            log.error(e);
            return false;
        }
    }

    /**
     * Чтение из файла csv
     *
     * @param tClass
     * @param <T>
     * @return список
     */
    private <T> List<T> readFromCsv (Class<T> tClass) {
        try {
            FileReader reader = new FileReader(getConfigurationEntry(PATH_CSV)
                    + tClass.getSimpleName().toLowerCase()
                    + getConfigurationEntry(FILE_EXTENSION_CSV));

            CSVReader csvReader = new CSVReader(reader);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                    .withType(tClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            log.info(READ_SUCCESS);
            return csvToBean.parse();
        } catch (IOException e) {
            log.info(READ_ERROR);
            log.error(e);
            return new ArrayList<>();
        }
    }

    private <T> boolean writeToCsv (T object) {
        try {
            if (object == null) {
                log.info(STH_NULL);
                return false;
            }
            return writeToCsv(object.getClass(), Collections.singletonList(object), false);
        }catch (NullPointerException e){
            log.error(e);
            return false;
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

    private long getNextServiceId(){
        try {
            List<Service> objectList = readFromCsv(Service.class);
            long maxId = -1;
            for (Service service : objectList) {
                if (maxId < service.getId()) {
                    maxId = service.getId();
                }
            }
            return maxId + 1;
        }catch (NullPointerException e){
            log.error(e);
            return Long.parseLong(null);
        }
    }

    private long getNextOrderItemId(){
        try{
            List<OrderItem> objectList = readFromCsv(OrderItem.class);
            long maxId = -1;
            for(OrderItem orderItem : objectList){
                if(maxId < orderItem.getId()){
                    maxId = orderItem.getId();
                }
            }
            return maxId+1;
        }catch (NullPointerException e){
            log.error(e);
            return Long.parseLong(null);
        }
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
        try{
            List<Master> objectList = readFromCsv(Master.class);
            long maxId = -1;
            for(Master master : objectList){
                if(maxId < master.getId()){
                    maxId = master.getId();
                }
            }
            return maxId+1;
        }catch (NullPointerException e){
            log.error(e);
            return Long.parseLong(null);
        }
    }

    private long getNextRegularCustomerId(){
        try{
            List<RegularCustomer> objectList = readFromCsv(RegularCustomer.class);
            long maxId = -1;
            for(RegularCustomer regularCustomer : objectList){
                if(maxId < regularCustomer.getId()){
                    maxId = regularCustomer.getId();
                }
            }
            return maxId+1;
        }catch (NullPointerException e){
            log.error(e);
            return Long.parseLong(null);
        }
    }

    private long getNextOrderId(){
        try{
            List<Order> objectList = readFromCsv(Order.class);
            long maxId = -1;
            for(Order order : objectList){
                if(maxId < order.getId()){
                    maxId = order.getId();
                }
            }
            return maxId+1;
        }catch (NullPointerException e){
            log.error(e);
            return Long.parseLong(null);
        }
    }

    private long getNextSalonId(){
        try{
            List<Salon> objectList = readFromCsv(Salon.class);
            long maxId = -1;
            for(Salon salon : objectList){
                if(maxId < salon.getId()){
                    maxId = salon.getId();
                }
            }
            return maxId+1;
        }catch (NullPointerException e){
            log.error(e);
            return Long.parseLong(null);
        }
    }

    @Override
    public boolean createService(String name, Double price, String description) {
        try {
            if (name == null || price == null || description == null){
                log.info(NULL_VALUE);
                return false;
            }else {
                Service service = new Service();
                service.setId(getNextServiceId());
                service.setName(name);
                service.setPrice(price);
                service.setDescription(description);
                log.info(SERVICE_CREATED);
                log.debug(service);
                return writeToCsv(service);
            }
        }catch (NullPointerException e){
            log.info(SERVICE_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editService(long id, String name, Double price, String description) {
        List<Service> listService = readFromCsv(Service.class);
        try {
            if (getServiceById(id) == null){
                log.info(SERVICE_ID + id + NOT_FOUND);
                return false;
            }
            Service newService = new Service();
            newService.setId(id);
            newService.setName(name);
            newService.setPrice(price);
            newService.setDescription(description);
            listService.removeIf(service -> service.getId() == id);
            writeToCsv(Service.class, listService, true);
            log.info(SERVICE_EDITED);
            log.debug(newService);
            writeToCsv(newService);
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException | NullPointerException e) {
            log.info(SERVICE_NOT_EDITED);
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
            log.info(SERVICE_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(SERVICE_NOT_DELETED);
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
            log.info(SERVICE_RECEIVED);
            log.debug(service);
            return service;
        }catch (NoSuchElementException | NullPointerException e){
            log.info(SERVICE_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createNewCustomer(String firstName, String lastName, String phone, String email, Integer discount) {
        try {
            if (firstName == null || lastName == null || phone == null || email == null || discount == null){
                log.info(NULL_VALUE);
                return false;
            }else {
                NewCustomer customer = new NewCustomer();
                customer.setId(getNextNewCustomerId());
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPhone(phone);
                customer.setEmail(email);
                customer.setDiscount(discount);
                log.info(NEW_CUSTOMER_CREATED);
                log.debug(customer);
                return writeToCsv(customer);
            }
        }catch (NullPointerException e) {
            log.info(NEW_CUSTOMER_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editNewCustomer(long id, String firstName, String lastName, String phone, String email, Integer discount) {
        List<NewCustomer> newCustomerList = readFromCsv(NewCustomer.class);
        try {
            if (!getNewCustomerById(id).isPresent()){
                log.info(NEW_CUSTOMER_ID + id + NOT_FOUND);
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
            log.info(NEW_CUSTOMER_EDITED);
            log.debug(customer);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.info(NEW_CUSTOMER_NOT_EDITED);
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
            log.info(NEW_CUSTOMER_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(NEW_CUSTOMER_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public Optional<NewCustomer> getNewCustomerById(long id) {
        List<NewCustomer> listNewCustomer = readFromCsv(NewCustomer.class);
        try {
            NewCustomer newCustomer = listNewCustomer.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            log.info(NEW_CUSTOMER_RECEIVED);
            log.debug(newCustomer);
            return Optional.of(newCustomer);
        }catch (NullPointerException | NoSuchElementException e){
            log.info(NEW_CUSTOMER_NOT_RECEIVED);
            log.error(e);
            return Optional.empty();
        }
    }

    @Override
    public boolean createRegularCustomer(String firstName, String lastName, String phone, String email, Integer countOfOrder) {
        try{
            if (firstName == null || lastName == null || phone == null || email == null || countOfOrder == null){
                log.info(NULL_VALUE);
                return false;
            }else {
                RegularCustomer customer = new RegularCustomer();
                customer.setId(getNextRegularCustomerId());
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPhone(phone);
                customer.setEmail(email);
                customer.setNumberOfOrders(countOfOrder);
                log.info(REGULAR_CUSTOMER_CREATED);
                log.debug(customer);
                return writeToCsv(customer);
            }
        }catch (NullPointerException e) {
            log.info(REGULAR_CUSTOMER_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editRegularCustomer(long id, String firstName, String lastName, String phone, String email, Integer countOfOrder) {
        List<RegularCustomer> regularCustomerList = readFromCsv(RegularCustomer.class);
        try {
            if (!getRegularCustomerById(id).isPresent()){
                log.info(REGULAR_CUSTOMER_ID + id + NOT_FOUND);
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
            log.info(REGULAR_CUSTOMER_EDITED);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.info(REGULAR_CUSTOMER_NOT_EDITED);
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
            log.info(REGULAR_CUSTOMER_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(REGULAR_CUSTOMER_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public Optional<RegularCustomer> getRegularCustomerById(long id) {
        List<RegularCustomer> listRegularCustomer = readFromCsv(RegularCustomer.class);
        try {
            RegularCustomer regularCustomer = listRegularCustomer.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            log.info(REGULAR_CUSTOMER_RECEIVED);
            log.debug(regularCustomer);
            return Optional.of(regularCustomer);
        }catch (NullPointerException | NoSuchElementException e){
            log.info(REGULAR_CUSTOMER_NOT_RECEIVED);
            log.error(e);
            return Optional.empty();
        }
    }

    @Override
    public boolean createMaster(String firstName, String lastName, String position, String phone, Double salary, List<Service> listService, boolean needCreateMaster) {
        try{
            if (firstName == null || lastName == null || position == null || phone == null || salary == null || listService == null || !needCreateMaster){
                log.info(NULL_VALUE);
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
                log.info(MASTER_CREATED);
                log.debug(master);
                return writeToCsv(master);
            }
        }catch (NullPointerException e) {
            log.info(MASTER_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editMaster(long id, String firstName, String lastName, String position, String phone, Double salary, List<Service> listService, boolean needEditMaster) {
        List<Master> masterList = readFromCsv(Master.class);
        try {
            if (getMasterById(id) == null || !needEditMaster){
                log.info(MASTER_ID + id + NOT_FOUND);
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
            log.info(MASTER_EDITED);
            log.debug(master);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.info(MASTER_NOT_EDITED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteMaster(long id, boolean needDeleteMaster) {
        try{
            if (getMasterById(id) == null || !needDeleteMaster){
                log.info(MASTER_ID + id + NOT_FOUND);
                return false;
            }
            List<Master> masterList = readFromCsv(Master.class);
            masterList.removeIf(master -> master.getId() == id);
            log.debug(masterList);
            writeToCsv(Master.class, masterList, true);
            log.info(MASTER_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(MASTER_NOT_DELETED);
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
            log.info(MASTER_RECEIVED);
            log.debug(master);
            return master;
        } catch (IOException | NoSuchElementException | NullPointerException e) {
            log.info(MASTER_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createSalon(String address, List<Master> listMaster) {
        try{
            if (address == null || listMaster == null){
                log.info(NULL_VALUE);
                return false;
            }else {
                Salon salon = new Salon();
                salon.setId(getNextSalonId());
                salon.setAddress(address);
                salon.setListMaster(listMaster);
                log.info(SALON_CREATED);
                log.debug(salon);
                return writeToCsv(salon);
            }
        }catch (NullPointerException e) {
            log.info(SALON_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editSalon(long id, String address, List<Master> listMaster) {
        List<Salon> salonList = readFromCsv(Salon.class);
        try {
            if (getSalonById(id) == null){
                log.info(SALON_ID + id + NOT_FOUND);
                return false;
            }
            Salon salon = new Salon();
            salon.setId(id);
            salon.setAddress(address);
            salon.setListMaster(listMaster);
            salonList.removeIf(sal -> sal.getId() == id);
            writeToCsv(Salon.class, salonList, true);
            writeToCsv(salon);
            log.info(SALON_EDITED);
            log.debug(salon);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.info(SALON_NOT_EDITED);
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
            log.info(SALON_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(SALON_NOT_DELETED);
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
            log.info(SALON_RECEIVED);
            log.debug(salon);
            return salon;
        } catch (IOException | NoSuchElementException | NullPointerException e) {
            log.info(SALON_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createOrderItem(Service service, Integer quantity) {
        try{
            if (service == null || quantity == null){
                log.info(NULL_VALUE);
                return false;
            }else {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(getNextOrderItemId());
                orderItem.setService(service);
                orderItem.setCost(service.getPrice());
                orderItem.setQuantity(quantity);
                log.info(ORDER_ITEM_CREATED);
                log.debug(orderItem);
                return writeToCsv(orderItem);
            }
        }catch (NullPointerException e) {
            log.info(ORDER_ITEM_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editOrderItem(long id, Service service, Double cost, Integer quantity) {
        List<OrderItem> orderItemList = readFromCsv(OrderItem.class);
        try {
            if (getOrderItemById(id) == null){
                log.info(ORDER_ITEM_ID + id + NOT_FOUND);
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
            log.info(ORDER_ITEM_EDITED);
            log.debug(orderItem);
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException | NullPointerException e) {
            log.info(ORDER_ITEM_NOT_EDITED);
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
            log.info(ORDER_ITEM_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(ORDER_ITEM_NOT_DELETED);
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
            log.info(ORDER_ITEM_RECEIVED);
            log.debug(orderItem);
            return orderItem;
        } catch (IOException | NoSuchElementException | NullPointerException e) {
            log.info(ORDER_ITEM_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createOrder(String created, List<OrderItem> item, Double cost, String status, long customerId, String lastUpdated, String completed) {
        try{
            if (created == null || item == null || cost == null || status == null){
                log.info(NULL_VALUE);
                return false;
            }else {
                Order order = new Order();
                order.setCreated(created);
                order.setId(getNextOrderId());
                order.setItem(item);
                order.setCost(cost);
                order.setStatus(status);
                order.setCustomerId(customerId);
                order.setLastUpdated(lastUpdated);
                order.setCompleted(completed);
                log.info(ORDER_CREATE);
                log.debug(order);
                return writeToCsv(order);
            }
        }catch (NullPointerException e) {
            log.info(ORDER_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editOrder(long id, String created, List<OrderItem> item, Double cost, String status, long customerId, String lastUpdated, String completed) {
        List<Order> orderList = readFromCsv(Order.class);
        try {
            if (getOrderById(id) == null){
                log.info(ORDER_ID + id + NOT_FOUND);
                return false;
            }
            Order order = new Order();
            order.setCreated(created);
            order.setId(id);
            order.setItem(item);
            order.setCost(calculateOrderValue(id));
            order.setStatus(status);
            order.setCustomerId(customerId);
            order.setLastUpdated(lastUpdated);
            order.setCompleted(completed);
            orderList.removeIf(ord -> ord.getId() == id);
            writeToCsv(Order.class, orderList, true);
            writeToCsv(order);
            log.info(ORDER_EDITED);
            log.debug(order);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.info(ORDER_NOT_EDITED);
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
            log.info(ORDER_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(ORDER_NOT_DELETED);
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
            log.info(ORDER_RECEIVED);
            log.debug(order);
            return order;
        } catch (IOException | NoSuchElementException | NullPointerException e) {
            log.info(ORDER_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public Double calculateOrderValue(long orderId) {
        try{
            Order order = getOrderById(orderId);
            List<OrderItem> orderItemList = getOrderItemList(Order.class, order);
            List<Double> price = orderItemList.stream()
                    .map(value -> value.getCost()*value.getQuantity())
                    .collect(Collectors.toList());
            Double cost = price.stream().map(value -> value.doubleValue())
                    .filter(a -> a != null).mapToDouble(a -> a).sum();
            log.info(COST + orderId + EQL + cost);
            return cost;
        } catch (NoSuchElementException | NullPointerException | IOException e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public List<Order> viewOrderHistory(long customerId) {
        try{
            List<Order> orderList = readFromCsv(Order.class);
            orderList = orderList.stream()
                    .filter(user -> user.getCustomerId() == customerId)
                    .collect(Collectors.toList());
            log.debug(orderList);
            Order order = orderList.stream()
                    .findAny().orElse(null);
            order.setItem(getOrderItemList(Order.class, order));
            log.info(LIST_CUSTOMER + customerId + COLON);
            log.debug(orderList);
            return orderList;
        }catch (NullPointerException | NoSuchElementException | IOException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public List<Order> getListOfCurrentOrders(long customerId, String status) {
        try{
            if(status.equals(PROCESSING)){
                List<Order> orderList = readFromCsv(Order.class);
                orderList = orderList.stream()
                        .filter(user -> user.getCustomerId() == customerId && user.getStatus().equals(status))
                        .collect(Collectors.toList());
                Order order = orderList.stream()
                        .findAny().orElse(null);
                order.setItem(getOrderItemList(Order.class, order));
                log.info(CURRENT_ORDER + customerId + COLON);
                log.debug(orderList);
                return orderList;
            }else{
                log.info(NOT_CURRENT_ORDER);
                return null;
            }

        }catch (NullPointerException | NoSuchElementException | IOException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public StringBuffer createCustomerReport(long customerId) {
        try{
            List<Order> orderList = readFromCsv(Order.class);
            orderList = orderList.stream()
                    .filter(user -> user.getCustomerId() == customerId)
                    .collect(Collectors.toList());
            int count = 0;
            for(int i=0;i<orderList.size();i++){
                count++;
            }
            log.debug(count);
            StringBuffer report = new StringBuffer();
            report.append(NUM_CUSTOMER)
                    .append(customerId)
                    .append(ORDER_EQL)
                    .append(count);
            return report;
        }catch (NullPointerException | NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public List<Master> changeTheLisOfMaster(long salonId) {
        try{
            if (getSalonById(salonId) == null){
                log.info( SALON_ID + salonId + NOT_FOUND);
                return null;
            }
            Salon salon = getSalonById(salonId);
            List<Master> masterList = getMasterList(Salon.class, salon);

            log.info(LIST_MASTER + salonId + COLON);
            log.debug(masterList);
            return masterList;
        }catch (NullPointerException | NoSuchElementException | IOException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean assignService(List<Service> service, long masterId) {
        try{
            List<Master> masterList = readFromCsv(Master.class);
            Master master = getMasterById(masterId);
            master.setListService(service);
            masterList.removeIf(user -> user.getId() == masterId);
            writeToCsv(Master.class, masterList, true);
            writeToCsv(master);
            log.info(ASSIGN_SUCCESS);
            log.debug(master);
            return true;
        } catch (NoSuchElementException | NullPointerException | IndexOutOfBoundsException e) {
            log.info(ASSIGN_FAIL);
            log.error(e);
            return false;
        }
    }

    @Override
    public StringBuffer createMasterReport(long masterId , boolean needMasterReport) {
        try{
            if (getMasterById(masterId) == null || !needMasterReport){
                log.info( MASTER_ID + masterId + NOT_FOUND);
                return null;
            }
            List<Master> masterList = readFromCsv(Master.class);
            Master master = masterList.stream()
                    .filter(task -> task.getId() == masterId)
                    .findAny()
                    .orElse(null);
            master.setListService(getServiceListInMaster(Master.class, master));

            StringBuffer report = new StringBuffer();
            report.append(MASTER)
                    .append(masterId)
                    .append(PROVIDE_SERVICE)
                    .append(master.getListService());

            return report;
        }catch (NullPointerException | NoSuchElementException | IOException e){
            log.error(e);
            return null;
        }
    }
}
