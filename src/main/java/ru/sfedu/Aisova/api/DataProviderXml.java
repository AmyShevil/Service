package ru.sfedu.Aisova.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import static ru.sfedu.Aisova.Constants.*;
import ru.sfedu.Aisova.model.*;
import static ru.sfedu.Aisova.utils.ConfigurationUtil.getConfigurationEntry;
import ru.sfedu.Aisova.utils.WrapperXML;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Data provider xml.
 */
public class DataProviderXml implements DataProvider{

    private static Logger log = LogManager.getLogger(DataProviderXml.class);

    /**
     * Запись в файл xml
     *
     * @param tClass
     * @param object
     * @param rewrite
     * @param <T>
     * @return true или false
     * @throws Exception
     */
    public <T> boolean writeToXml(Class<?> tClass, List<T> object, boolean rewrite) throws Exception{
        try{
            List<T> objectList;
            if (!rewrite) {
                objectList = (List<T>) readFromXml(tClass);
                objectList.addAll(object);
            }
            else {
                objectList = new ArrayList<>(object);
            }

            (new File(this.getFilePath(tClass))).createNewFile();
            FileWriter writer = new FileWriter(this.getFilePath(tClass), false);
            Serializer serializer = new Persister();
            WrapperXML xml = new WrapperXML();
            xml.setList(objectList);
            serializer.write(xml, writer);
            log.info(WRITE_SUCCESS);
            return true;
        }catch(IndexOutOfBoundsException e){
            log.info(WRITE_ERROR);
            log.error(e);
            return false;
        }
    }

    /**
     * Чтение из файла xml
     *
     * @param cl
     * @param <T>
     * @return список
     */
    public <T> List<T> readFromXml(Class cl) {
        try {
            FileReader fileReader = new FileReader(this.getFilePath(cl));
            Serializer serializer = new Persister();
            WrapperXML xml = serializer.read(WrapperXML.class, fileReader);
            if (xml.getList() == null) xml.setList(new ArrayList<T>());
            log.info(READ_SUCCESS);
            return xml.getList();
        } catch (Exception e) {
            log.info(READ_ERROR);
            log.error(e);
            return new ArrayList<>();
        }
    }

    /**
     * Запись в csv
     * @param object 
     * @param <T>
     * @return true или false
     * @throws Exception
     */
    private <T> boolean writeToXml (T object) throws Exception {
        try {
            if (object == null) {
                log.info(STH_NULL);
                return false;
            }
            return writeToXml(object.getClass(), Collections.singletonList(object), false);
        }catch (NullPointerException e){
            log.error(e);
            return false;
        }
    }

    /**
     * Получить путь к файлу
     * @param cl 
     * @return путь
     * @throws IOException
     */
    private String getFilePath(Class cl) throws IOException {
        return getConfigurationEntry(PATH_XML)+ cl.getSimpleName().toLowerCase()+getConfigurationEntry(FILE_EXTENSION_XML);
    }

    /**
     * Получение списка услуг у мастера
     *
     * @param tClass
     * @param object
     * @param <T>
     * @return список услуг мастера
     * @throws IOException
     */
    private <T> List<Service> getServiceListInMaster(Class<T> tClass, T object) throws IOException {
        try {
            List<Service> objectServiceList;
            Master master = (Master) object;
            objectServiceList = master.getListService();
            List<Service> serviceList = readFromXml(Service.class);
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

    /**
     * Получение списка строк заказа с услугой
     *
     * @param tClass
     * @param object
     * @param <T>
     * @return список строк заказа с услугой
     * @throws IOException
     */
    private <T> List<OrderItem> getOrderItemList(Class<T> tClass, T object) throws IOException {
        try {
            List<OrderItem> objectOrderItemList;
            Order order = (Order) object;
            objectOrderItemList = order.getItem();
            List<OrderItem> orderItemList = readFromXml(OrderItem.class);
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

    /**
     * Получение списка мастеров с услугами в салоне
     *
     * @param tClass
     * @param object
     * @param <T>
     * @return список мастеров в салоне
     * @throws IOException
     */
    private <T> List<Master> getMasterList(Class<T> tClass, T object) throws IOException {
        try {
            List<Master> objectMasterList;
            Salon salon = (Salon) object;
            objectMasterList = salon.getListMaster();
            List<Master> masterList = readFromXml(Master.class);
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

    /**
     * Получение услуги в строке заказа
     *
     * @param tClass
     * @param object
     * @param <T>
     * @return услуга в строке заказа
     * @throws IOException
     */
    private <T> Service getServiceInOrderItem(Class<T> tClass, T object) throws IOException {
        try {
            Service serviceInOrderItem;
            OrderItem orderItem = (OrderItem) object;
            Service service = orderItem.getService();
            List<Service> serviceList = readFromXml(Service.class);
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

    /**
     * Генерация id услуги
     * @return id
     */
    private long getNextServiceId(){
        try {
            List<Service> objectList = readFromXml(Service.class);
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

    /**
     * Генерация id строки заказа
     * @return id
     */
    private long getNextOrderItemId(){
        try{
            List<OrderItem> objectList = readFromXml(OrderItem.class);
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

    /**
     * Генерация id нового пользователя
     * @return id
     */
    private long getNextNewCustomerId(){
        try {
            List<NewCustomer> objectList = readFromXml(NewCustomer.class);
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

    /**
     * Генерация id мастера
     * @return id
     */
    private long getNextMasterId(){
        try{
            List<Master> objectList = readFromXml(Master.class);
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

    /**
     * Генерация id постоянного пользователя
     * @return id
     */
    private long getNextRegularCustomerId(){
        try{
            List<RegularCustomer> objectList = readFromXml(RegularCustomer.class);
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

    /**
     * Генерация id заказа
     * @return id
     */
    private long getNextOrderId(){
        try{
            List<Order> objectList = readFromXml(Order.class);
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

    /**
     * Генерация id салона
     * @return id
     */
    private long getNextSalonId(){
        try{
            List<Salon> objectList = readFromXml(Salon.class);
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
            log.info(LOAD_CREATE_SERVICE);
            if (name == null || price == null || description == null){
                log.info(NULL_VALUE);
                log.info(SERVICE_NOT_CREATED);
                log.info(LOAD_CREATE_SERVICE_COMPLETE);
                return false;
            }else {
                Service service = new Service();
                service.setId(getNextServiceId());
                service.setName(name);
                service.setPrice(price);
                service.setDescription(description);
                log.info(SERVICE_CREATED);
                log.debug(service);
                log.info(LOAD_CREATE_SERVICE_COMPLETE);
                return writeToXml(service);
            }
        }catch (Exception e){
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editService(long id, String name, Double price, String description) {
        List<Service> listService = readFromXml(Service.class);
        try {
            log.info(LOAD_EDIT_SERVICE);
            if (getServiceById(id) == null){
                log.info(SERVICE_ID + id + NOT_FOUND);
                log.info(SERVICE_NOT_EDITED);
                log.info(LOAD_EDIT_SERVICE_COMPLETE);
                return false;
            }
            Service newService = new Service();
            newService.setId(id);
            newService.setName(name);
            newService.setPrice(price);
            newService.setDescription(description);
            listService.removeIf(service -> service.getId() == id);
            writeToXml(Service.class, listService, true);
            log.info(SERVICE_EDITED);
            log.debug(newService);
            writeToXml(newService);
            log.info(LOAD_EDIT_SERVICE_COMPLETE);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteService(long id) {
        try {
            log.info(LOAD_DELETE_SERVICE);
            if (getServiceById(id) == null){
                log.info(SERVICE_ID + id + NOT_FOUND);
                log.info(SERVICE_NOT_DELETED);
                log.info(LOAD_DELETE_SERVICE_COMPLETE);
                return false;
            }
            List<Service> serviceList = readFromXml(Service.class);
            serviceList.removeIf(service -> service.getId() == id);
            log.debug(serviceList);
            writeToXml(Service.class, serviceList, true);
            log.info(SERVICE_DELETED);
            log.info(LOAD_DELETE_SERVICE_COMPLETE);
            return true;
        }catch (Exception e){
            log.error(e);
            return false;
        }
    }

    @Override
    public Service getServiceById(long id) {
        List<Service> listService = readFromXml(Service.class);
        try {
            log.info(LOAD_GET_SERVICE);
            Service service = listService.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            log.info(SERVICE_RECEIVED);
            log.debug(service);
            log.info(LOAD_GET_SERVICE_COMPLETE);
            return service;
        }catch (NoSuchElementException | NullPointerException e){
            log.info(SERVICE_NOT_RECEIVED);
            log.error(e);
            log.info(LOAD_GET_SERVICE_COMPLETE);
            return null;
        }
    }

    @Override
    public boolean createNewCustomer(String firstName, String lastName, String phone, String email, Integer discount) {
        try {
            log.info(LOAD_CREATE_NEW_CUSTOMER);
            if (firstName == null || lastName == null || phone == null || email == null || discount == null){
                log.info(NULL_VALUE);
                log.info(NEW_CUSTOMER_NOT_CREATED);
                log.info(LOAD_CREATE_NEW_CUSTOMER_COMPLETE);
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
                log.info(LOAD_CREATE_NEW_CUSTOMER_COMPLETE);
                return writeToXml(customer);
            }
        }catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editNewCustomer(long id, String firstName, String lastName, String phone, String email, Integer discount) {
        List<NewCustomer> newCustomerList = readFromXml(NewCustomer.class);
        try {
            log.info(LOAD_EDIT_NEW_CUSTOMER);
            if (!getNewCustomerById(id).isPresent()){
                log.info(NEW_CUSTOMER_ID + id + NOT_FOUND);
                log.info(NEW_CUSTOMER_NOT_EDITED);
                log.info(LOAD_EDIT_NEW_CUSTOMER_COMPLETE);
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
            writeToXml(NewCustomer.class, newCustomerList, true);
            writeToXml(customer);
            log.info(NEW_CUSTOMER_EDITED);
            log.debug(customer);
            log.info(LOAD_EDIT_NEW_CUSTOMER_COMPLETE);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteNewCustomer(long id) {
        try{
            log.info(LOAD_DELETE_NEW_CUSTOMER);
            if(!getNewCustomerById(id).isPresent()){
                log.info(NEW_CUSTOMER_ID + id + NOT_FOUND);
                log.info(NEW_CUSTOMER_NOT_DELETED);
                log.info(LOAD_DELETE_NEW_CUSTOMER_COMPLETE);
                return false;
            }
            List<NewCustomer> newCustomerList = readFromXml(NewCustomer.class);
            newCustomerList.removeIf(customer -> customer.getId() == id);
            log.debug(newCustomerList);
            writeToXml(NewCustomer.class, newCustomerList, true);
            log.info(NEW_CUSTOMER_DELETED);
            log.info(LOAD_DELETE_NEW_CUSTOMER_COMPLETE);
            return true;
        }catch (Exception e){
            log.error(e);
            return false;
        }
    }

    @Override
    public Optional<NewCustomer> getNewCustomerById(long id) {
        List<NewCustomer> listNewCustomer = readFromXml(NewCustomer.class);
        try {
            log.info(LOAD_GET_NEW_CUSTOMER);
            NewCustomer newCustomer = listNewCustomer.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            log.info(NEW_CUSTOMER_RECEIVED);
            log.debug(newCustomer);
            log.info(LOAD_GET_NEW_CUSTOMER_COMPLETE);
            return Optional.of(newCustomer);
        }catch (NullPointerException | NoSuchElementException e){
            log.info(NEW_CUSTOMER_NOT_RECEIVED);
            log.error(e);
            log.info(LOAD_GET_NEW_CUSTOMER_COMPLETE);
            return Optional.empty();
        }
    }

    @Override
    public boolean createRegularCustomer(String firstName, String lastName, String phone, String email, Integer countOfOrder) {
        try{
            log.info(LOAD_CREATE_REGULAR_CUSTOMER);
            if (firstName == null || lastName == null || phone == null || email == null || countOfOrder == null){
                log.info(NULL_VALUE);
                log.info(REGULAR_CUSTOMER_NOT_CREATED);
                log.info(LOAD_CREATE_REGULAR_CUSTOMER_COMPLETE);
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
                log.info(LOAD_CREATE_REGULAR_CUSTOMER_COMPLETE);
                return writeToXml(customer);
            }
        }catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editRegularCustomer(long id, String firstName, String lastName, String phone, String email, Integer countOfOrder) {
        List<RegularCustomer> regularCustomerList = readFromXml(RegularCustomer.class);
        try {
            log.info(LOAD_EDIT_REGULAR_CUSTOMER);
            if (!getRegularCustomerById(id).isPresent()){
                log.info(REGULAR_CUSTOMER_ID + id + NOT_FOUND);
                log.info(REGULAR_CUSTOMER_NOT_EDITED);
                log.info(LOAD_EDIT_REGULAR_CUSTOMER_COMPLETE);
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
            writeToXml(RegularCustomer.class, regularCustomerList, true);
            writeToXml(customer);
            log.info(REGULAR_CUSTOMER_EDITED);
            log.info(LOAD_EDIT_REGULAR_CUSTOMER_COMPLETE);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteRegularCustomer(long id) {
        try{
            log.info(LOAD_DELETE_REGULAR_CUSTOMER);
            if (!getRegularCustomerById(id).isPresent()){
                log.info(REGULAR_CUSTOMER_ID + id + NOT_FOUND);
                log.info(REGULAR_CUSTOMER_NOT_DELETED);
                log.info(LOAD_DELETE_REGULAR_CUSTOMER_COMPLETE);
                return false;
            }
            List<RegularCustomer> regularCustomerList = readFromXml(RegularCustomer.class);
            regularCustomerList.removeIf(customer -> customer.getId() == id);
            log.debug(regularCustomerList);
            writeToXml(RegularCustomer.class, regularCustomerList, true);
            log.info(REGULAR_CUSTOMER_DELETED);
            log.info(LOAD_DELETE_REGULAR_CUSTOMER_COMPLETE);
            return true;
        }catch (Exception e){
            log.error(e);
            return false;
        }
    }

    @Override
    public Optional<RegularCustomer> getRegularCustomerById(long id) {
        List<RegularCustomer> listRegularCustomer = readFromXml(RegularCustomer.class);
        try {
            log.info(LOAD_GET_REGULAR_CUSTOMER);
            RegularCustomer regularCustomer = listRegularCustomer.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            log.info(REGULAR_CUSTOMER_RECEIVED);
            log.debug(regularCustomer);
            log.info(LOAD_GET_REGULAR_CUSTOMER_COMPLETE);
            return Optional.of(regularCustomer);
        }catch (NullPointerException | NoSuchElementException e){
            log.info(REGULAR_CUSTOMER_NOT_RECEIVED);
            log.error(e);
            log.info(LOAD_GET_REGULAR_CUSTOMER_COMPLETE);
            return Optional.empty();
        }
    }

    @Override
    public boolean createMaster(String firstName, String lastName, String position, String phone, Double salary, List<Service> listService, boolean needCreateMaster) {
        try{
            log.info(LOAD_CREATE_MASTER);
            if (firstName == null || lastName == null || position == null || phone == null || salary == null || listService == null || !needCreateMaster){
                log.info(NULL_VALUE);
                log.info(MASTER_NOT_CREATED);
                log.info(LOAD_CREATE_MASTER_COMPLETE);
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
                log.info(LOAD_CREATE_MASTER_COMPLETE);
                return writeToXml(master);
            }
        }catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editMaster(long id, String firstName, String lastName, String position, String phone, Double salary, List<Service> listService, boolean needEditMaster) {
        List<Master> masterList = readFromXml(Master.class);
        try {
            log.info(LOAD_EDIT_MASTER);
            if (getMasterById(id) == null || !needEditMaster){
                log.info(MASTER_ID + id + NOT_FOUND);
                log.info(MASTER_NOT_EDITED);
                log.info(LOAD_EDIT_MASTER_COMPLETE);
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
            writeToXml(Master.class, masterList, true);
            writeToXml(master);
            log.info(MASTER_EDITED);
            log.debug(master);
            log.info(LOAD_EDIT_MASTER_COMPLETE);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteMaster(long id, boolean needDeleteMaster) {
        try{
            log.info(LOAD_DELETE_MASTER);
            if (getMasterById(id) == null || !needDeleteMaster){
                log.info(MASTER_ID + id + NOT_FOUND);
                log.info(MASTER_NOT_DELETED);
                log.info(LOAD_DELETE_MASTER_COMPLETE);
                return false;
            }
            List<Master> masterList = readFromXml(Master.class);
            masterList.removeIf(master -> master.getId() == id);
            writeToXml(Master.class, masterList, true);
            log.info(MASTER_DELETED);
            log.info(LOAD_DELETE_MASTER_COMPLETE);
            return true;
        }catch (Exception e){
            log.error(e);
            return false;
        }
    }

    @Override
    public Master getMasterById(long id) {
        try {
            log.info(LOAD_GET_MASTER);
            List<Master> masterList = readFromXml(Master.class);
            Master master = masterList.stream()
                    .filter(task -> task.getId() == id)
                    .findAny()
                    .orElse(null);

            master.setListService(getServiceListInMaster(Master.class, master));
            log.info(MASTER_RECEIVED);
            log.debug(master);
            log.info(LOAD_GET_MASTER_COMPLETE);
            return master;
        } catch (IOException | NoSuchElementException | NullPointerException e) {
            log.info(MASTER_NOT_RECEIVED);
            log.error(e);
            log.info(LOAD_GET_MASTER_COMPLETE);
            return null;
        }
    }

    @Override
    public boolean createSalon(String address, List<Master> listMaster) {
        try{
            log.info(LOAD_CREATE_SALON);
            if (address == null || listMaster == null){
                log.info(NULL_VALUE);
                log.info(SALON_NOT_CREATED);
                log.info(LOAD_CREATE_SALON_COMPLETE);
                return false;
            }else {
                Salon salon = new Salon();
                salon.setId(getNextSalonId());
                salon.setAddress(address);
                salon.setListMaster(listMaster);
                log.info(SALON_CREATED);
                log.debug(salon);
                log.info(LOAD_CREATE_SALON_COMPLETE);
                return writeToXml(salon);
            }
        }catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editSalon(long id, String address, List<Master> listMaster) {
        List<Salon> salonList = readFromXml(Salon.class);
        try {
            log.info(LOAD_EDIT_SALON);
            if (getSalonById(id) == null){
                log.info(SALON_ID + id + NOT_FOUND);
                log.info(SALON_NOT_EDITED);
                log.info(LOAD_EDIT_SALON_COMPLETE);
                return false;
            }
            Salon salon = new Salon();
            salon.setId(id);
            salon.setAddress(address);
            salon.setListMaster(listMaster);
            salonList.removeIf(sal -> sal.getId() == id);
            writeToXml(Salon.class, salonList, true);
            writeToXml(salon);
            log.info(SALON_EDITED);
            log.debug(salon);
            log.info(LOAD_EDIT_SALON_COMPLETE);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteSalon(long id) {
        try {
            log.info(LOAD_DELETE_SALON);
            if (getSalonById(id) == null){
                log.info(SALON_ID + id + NOT_FOUND);
                log.info(SALON_NOT_DELETED);
                log.info(LOAD_DELETE_SALON_COMPLETE);
                return false;
            }
            List<Salon> salonList = readFromXml(Salon.class);
            salonList.removeIf(salon -> salon.getId() == id);
            writeToXml(Salon.class, salonList, true);
            log.info(SALON_DELETED);
            log.info(LOAD_DELETE_SALON_COMPLETE);
            return true;
        }catch (Exception e){
            log.error(e);
            return false;
        }
    }

    @Override
    public Salon getSalonById(long id) {
        try {
            log.info(LOAD_GET_SALON);
            List<Salon> listSalon = readFromXml(Salon.class);
            Salon salon = listSalon.stream()
                    .filter(task -> task.getId() == id)
                    .findAny()
                    .orElse(null);

            salon.setListMaster(getMasterList(Salon.class, salon));
            log.info(SALON_RECEIVED);
            log.debug(salon);
            log.info(LOAD_GET_SALON_COMPLETE);
            return salon;
        } catch (IOException | NoSuchElementException | NullPointerException e) {
            log.info(SALON_NOT_RECEIVED);
            log.error(e);
            log.info(LOAD_GET_SALON_COMPLETE);
            return null;
        }
    }

    @Override
    public boolean createOrderItem(Service service, Integer quantity) {
        try{
            log.info(LOAD_CREATE_ITEM);
            if (service == null || quantity == null){
                log.info(NULL_VALUE);
                log.info(ORDER_ITEM_NOT_CREATED);
                log.info(LOAD_CREATE_ITEM_COMPLETE);
                return false;
            }else {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(getNextOrderItemId());
                orderItem.setService(service);
                orderItem.setCost(service.getPrice());
                orderItem.setQuantity(quantity);
                log.info(ORDER_ITEM_CREATED);
                log.debug(orderItem);
                log.info(LOAD_CREATE_ITEM_COMPLETE);
                return writeToXml(orderItem);
            }
        }catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editOrderItem(long id, Service service, Double cost, Integer quantity) {
        List<OrderItem> orderItemList = readFromXml(OrderItem.class);
        try {
            log.info(LOAD_EDIT_ITEM);
            if (getOrderItemById(id) == null){
                log.info(ORDER_ITEM_ID + id + NOT_FOUND);
                log.info(ORDER_ITEM_NOT_EDITED);
                log.info(LOAD_EDIT_ITEM_COMPLETE);
                return false;
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setId(id);
            orderItem.setService(service);
            orderItem.setCost(cost);
            orderItem.setQuantity(quantity);
            orderItemList.removeIf(item -> item.getId() == id);
            writeToXml(OrderItem.class, orderItemList, true);
            writeToXml(orderItem);
            log.info(ORDER_ITEM_EDITED);
            log.debug(orderItem);
            log.info(LOAD_EDIT_ITEM_COMPLETE);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteOrderItem(long id) {
        try{
            log.info(LOAD_DELETE_ITEM);
            if (getOrderItemById(id) == null){
                log.info(ORDER_ITEM_ID + id + NOT_FOUND);
                log.info(ORDER_ITEM_NOT_DELETED);
                log.info(LOAD_DELETE_ITEM_COMPLETE);
                return false;
            }
            List<OrderItem> orderItemList = readFromXml(OrderItem.class);
            orderItemList.removeIf(item -> item.getId() == id);
            writeToXml(OrderItem.class, orderItemList, true);
            log.info(ORDER_ITEM_DELETED);
            log.info(LOAD_DELETE_ITEM_COMPLETE);
            return true;
        }catch (Exception e){
            log.error(e);
            return false;
        }
    }

    @Override
    public OrderItem getOrderItemById(long id) {
        try {
            log.info(LOAD_GET_ITEM);
            List<OrderItem> orderItemList = readFromXml(OrderItem.class);
            OrderItem orderItem = orderItemList.stream()
                    .filter(task -> task.getId() == id)
                    .findAny()
                    .orElse(null);

            orderItem.setService(getServiceInOrderItem(OrderItem.class, orderItem));
            log.info(ORDER_ITEM_RECEIVED);
            log.debug(orderItem);
            log.info(LOAD_GET_ITEM_COMPLETE);
            return orderItem;
        } catch (IOException | NoSuchElementException | NullPointerException e) {
            log.info(ORDER_ITEM_NOT_RECEIVED);
            log.error(e);
            log.info(LOAD_GET_ITEM_COMPLETE);
            return null;
        }
    }

    @Override
    public boolean createOrder(String created, List<OrderItem> item, String status, long customerId) {
        try{
            log.info(LOAD_CREATE_ORDER);
            if (created == null || item == null || status == null){
                log.info(NULL_VALUE);
                log.info(ORDER_NOT_CREATED);
                log.info(LOAD_CREATE_ORDER_COMPLETE);
                return false;
            }else {
                Order order = new Order();
                order.setCreated(created);
                order.setId(getNextOrderId());
                order.setItem(item);
                order.setStatus(status);
                order.setCustomerId(customerId);
                log.info(ORDER_CREATE);
                log.debug(order);
                log.info(LOAD_CREATE_ORDER_COMPLETE);
                return writeToXml(order);
            }
        }catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editOrder(long id, String created, List<OrderItem> item,  String status, long customerId) {
        List<Order> orderList = readFromXml(Order.class);
        try {
            log.info(LOAD_EDIT_ORDER);
            if (getOrderById(id) == null){
                log.info(ORDER_ID + id + NOT_FOUND);
                log.info(ORDER_NOT_EDITED);
                log.info(LOAD_EDIT_ORDER_COMPLETE);
                return false;
            }
            Order order = new Order();
            order.setCreated(created);
            order.setId(id);
            order.setItem(item);
            order.setStatus(status);
            order.setCustomerId(customerId);
            orderList.removeIf(ord -> ord.getId() == id);
            writeToXml(Order.class, orderList, true);
            writeToXml(order);
            log.info(ORDER_EDITED);
            log.debug(order);
            log.info(LOAD_EDIT_ORDER_COMPLETE);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteOrder(long id) {
        try{
            log.info(LOAD_DELETE_ORDER);
            if (getOrderById(id) == null){
                log.info(ORDER_ID + id + NOT_FOUND);
                log.info(ORDER_NOT_DELETED);
                log.info(LOAD_DELETE_ORDER_COMPLETE);
                return false;
            }
            List<Order> orderList = readFromXml(Order.class);
            orderList.removeIf(ord -> ord.getId() == id);
            writeToXml(Order.class, orderList, true);
            log.info(ORDER_DELETED);
            log.info(LOAD_DELETE_ORDER_COMPLETE);
            return true;
        }catch (Exception e){
            log.error(e);
            return false;
        }
    }

    @Override
    public Order getOrderById(long id) {
        try{
            log.info(LOAD_GET_ORDER);
            List<Order> orderList = readFromXml(Order.class);
            Order order = orderList.stream()
                    .filter(task -> task.getId() == id)
                    .findAny()
                    .orElse(null);

            order.setItem(getOrderItemList(Order.class, order));
            log.info(ORDER_RECEIVED);
            log.debug(order);
            log.info(LOAD_GET_ORDER_COMPLETE);
            return order;
        } catch (IOException | NoSuchElementException | NullPointerException e) {
            log.info(ORDER_NOT_RECEIVED);
            log.error(e);
            log.info(LOAD_GET_ORDER_COMPLETE);
            return null;
        }
    }

    @Override
    public Double calculateOrderValue(long orderId) {
        try{
            log.info(LOAD_CALCULATE);
            Order order = getOrderById(orderId);
            List<OrderItem> orderItemList = getOrderItemList(Order.class, order);
            List<Double> price = orderItemList.stream()
                    .map(value -> value.getCost()*value.getQuantity())
                    .collect(Collectors.toList());
            Double cost = price.stream().map(value -> value.doubleValue())
                    .filter(a -> a != null).mapToDouble(a -> a).sum();
            log.info(COST + orderId + EQL + cost);
            log.info(LOAD_CALCULATE_COMPLETE);
            return cost;
        } catch (NoSuchElementException | NullPointerException | IOException e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public List<Order> viewOrderHistory(long customerId) {
        try{
            log.info(LOAD_VIEW_ORDER);
            if (!getNewCustomerById(customerId).isPresent()){
                log.info( CUSTOMER_ID+ customerId + NOT_FOUND);
                log.info(LOAD_VIEW_ORDER_COMPLETE);
                return null;
            }
            List<Order> orderList = readFromXml(Order.class);
            orderList = orderList.stream()
                    .filter(user -> user.getCustomerId() == customerId)
                    .collect(Collectors.toList());
            Order order = orderList.stream()
                    .findAny().orElse(null);
            order.setItem(getOrderItemList(Order.class, order));
            log.info(LIST_CUSTOMER + customerId + COLON);
            log.debug(orderList);
            log.info(LOAD_VIEW_ORDER_COMPLETE);
            return orderList;
        }catch (NullPointerException | NoSuchElementException | IOException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public List<Order> getListOfCurrentOrders(long customerId, String status) {
        try{
            log.info(LOAD_GET_ORDER_LIST);
            if(status.equals(PROCESSING) && getNewCustomerById(customerId).isPresent()){
                List<Order> orderList = readFromXml(Order.class);
                orderList = orderList.stream()
                        .filter(user -> user.getCustomerId() == customerId && user.getStatus().equals(status))
                        .collect(Collectors.toList());
                Order order = orderList.stream()
                        .findAny().orElse(null);
                order.setItem(getOrderItemList(Order.class, order));
                log.info(CURRENT_ORDER + customerId + COLON);
                log.debug(orderList);
                log.info(LOAD_GET_ORDER_LIST_COMPLETE);
                return orderList;
            }else{
                log.info(NOT_CURRENT_ORDER);
                log.info(LOAD_GET_ORDER_LIST_COMPLETE);
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
            log.info(LOAD_CUSTOMER_REPORT);
            if (!getNewCustomerById(customerId).isPresent()){
                log.info( CUSTOMER_ID + customerId + NOT_FOUND);
                log.info(LOAD_CUSTOMER_REPORT_COMPLETE);
                return null;
            }
            List<Order> orderList = readFromXml(Order.class);
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
            log.info(LOAD_CUSTOMER_REPORT_COMPLETE);
            return report;
        }catch (NullPointerException | NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public List<Master> changeTheLisOfMaster(long salonId) {
        try{
            log.info(LOAD_CHANGE_MASTER);
            if (getSalonById(salonId) == null){
                log.info( SALON_ID + salonId + NOT_FOUND);
                log.info(LOAD_CHANGE_MASTER_COMPLETE);
                return null;
            }
            Salon salon = getSalonById(salonId);
            List<Master> masterList = getMasterList(Salon.class, salon);

            log.info(LIST_MASTER + salonId + COLON);
            log.debug(masterList);
            log.info(LOAD_CHANGE_MASTER_COMPLETE);
            return masterList;
        }catch (NullPointerException | NoSuchElementException | IOException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean assignService(List<Service> service, long masterId) {
        try{
            log.info(LOAD_ASSIGN_SERVICE);
            List<Master> masterList = readFromXml(Master.class);
            Master master = getMasterById(masterId);
            master.setListService(service);
            masterList.removeIf(user -> user.getId() == masterId);
            writeToXml(Master.class, masterList, true);
            writeToXml(master);
            log.info(ASSIGN_SUCCESS);
            log.debug(master);
            log.info(LOAD_ASSIGN_SERVICE_COMPLETE);
            return true;
        } catch (Exception e) {
            log.info(ASSIGN_FAIL);
            log.error(e);
            return false;
        }
    }

    @Override
    public StringBuffer createMasterReport(long masterId , boolean needMasterReport) {
        try{
            log.info(LOAD_MASTER_REPORT);
            if (getMasterById(masterId) == null || !needMasterReport){
                log.info( MASTER_ID + masterId + NOT_FOUND);
                log.info(LOAD_MASTER_REPORT_COMPLETE);
                return null;
            }
            List<Master> masterList = readFromXml(Master.class);
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
            log.info(LOAD_MASTER_REPORT_COMPLETE);
            return report;
        }catch (NullPointerException | NoSuchElementException | IOException e){
            log.error(e);
            return null;
        }
    }
}
