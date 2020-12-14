package ru.sfedu.Aisova.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.Aisova.Constants;
import ru.sfedu.Aisova.model.*;
import ru.sfedu.Aisova.utils.ConfigurationUtil;
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

    public <T> boolean writeToXml(Class<?> tClass, List<T> object, boolean overwrite) throws Exception{
        List<T> fileObjectList;
        if (!overwrite) {
            fileObjectList = (List<T>) readFromXml(tClass);
            fileObjectList.addAll(object);
        }
        else {
            fileObjectList = new ArrayList<>(object);
        }
        try{
            //Проверяем, создан ли файл? Если нет, то создаём.
            //createNewFile возвращает истину, если путь к абстрактному файлу не существует и создается новый файл.
            // Он возвращает false, если имя файла уже существует.
            (new File(this.getFilePath(tClass))).createNewFile();
            //Подключаемся к потоку записи файла
            FileWriter writer = new FileWriter(this.getFilePath(tClass), false);
            //Определяем сериалайзер
            Serializer serializer = new Persister();

            //Определяем контейнер, в котором будут находиться все объекты
            WrapperXML xml = new WrapperXML();
            //Записываем список объектов в котнейнер
            xml.setList(fileObjectList);

            //Записываем в файл
            serializer.write(xml, writer);
            return true;
        }catch(IndexOutOfBoundsException e){
            log.error(e);
            return false;
        }
    }

    private <T> boolean writeToXml (T object) throws Exception {
        if (object == null) {
            log.info("Something is null");
            return false;
        }
        return writeToXml(object.getClass(), Collections.singletonList(object), false);
    }

    public <T> List<T> readFromXml(Class cl) throws IOException, Exception{
        try {
            //Подключаемся к считывающему потоку из файла
            FileReader fileReader = new FileReader(this.getFilePath(cl));
            //Определяем сериалайзер
            Serializer serializer = new Persister();
            //Определяем контейнер и записываем в него объекты
            WrapperXML xml = serializer.read(WrapperXML.class, fileReader);
            //Если список null, то делаем его пустым списком
            if (xml.getList() == null) xml.setList(new ArrayList<T>());
            //Возвращаем список объектов
            log.info("Read success");
            return xml.getList();
        } catch (IOException e) {
            log.info("Read error");
            log.error(e);
            return new ArrayList<>();
        }
    }

    /**
     * Получаем путь к файлу
     * @param cl
     * @return
     * @throws IOException
     */
    private String getFilePath(Class cl) throws IOException{
        return ConfigurationUtil.getConfigurationEntry(Constants.PATH_XML)+cl.getSimpleName().toString().toLowerCase()+ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION_XML);
    }

    private <T> List<Service> getServiceListInMaster(Class<T> tClass, T object) throws Exception {
        try {
            List<Service> objectServiceList;
            Master master = (Master) object;
            objectServiceList = master.getListService();
            List<Service> serviceList = readFromXml(Service.class);
            List<Long> idServiceInMaster;

            idServiceInMaster = objectServiceList.stream()
                    .map(value -> value.getId())
                    .collect(Collectors.toList());

            List<Service>serviceListInMaster;
            serviceListInMaster =serviceList.stream()
                    .filter(service -> idServiceInMaster
                            .stream()
                            .anyMatch(serviceInMaster -> serviceInMaster.longValue() ==  service.getId()))
                    .collect(Collectors.toList());

            return serviceListInMaster;

        }catch(NullPointerException e){
            log.error(e);
            return new ArrayList<>();
        }
    }

    private <T> List<OrderItem> getOrderItemList(Class<T> tClass, T object) throws Exception {
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

            List<OrderItem> list = new ArrayList<>();
            for (OrderItem orderItem : orderItemListInOrder) {
                OrderItem orderItemById = getOrderItemById(orderItem.getId());
                list.add(orderItemById);
            }
            orderItemWithService = list;

            return orderItemWithService;

        }catch(NullPointerException e){
            log.error(e);
            return new ArrayList<>();
        }
    }

    private <T> List<Master> getMasterList(Class<T> tClass, T object) throws Exception {
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

            List<Master> list = new ArrayList<>();
            for (Master master : masterListInSalon) {
                Master masterById = getMasterById(master.getId());
                list.add(masterById);
            }
            masterWithService = list;
            return masterWithService;

        }catch(NullPointerException e){
            log.error(e);
            return new ArrayList<>();
        }
    }

    private <T> Service getServiceInOrderItem(Class<T> tClass, T object) throws Exception {
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

    private long getNextServiceId() throws Exception {
        List<Service> objectList = readFromXml(Service.class);
        long maxId = -1;
        for(Service service : objectList){
            if(maxId < service.getId()){
                maxId = service.getId();
            }
        }
        return maxId+1;
    }

    private long getNextOrderItemId() throws Exception {
        List<OrderItem> objectList = readFromXml(OrderItem.class);
        long maxId = -1;
        for(OrderItem orderItem : objectList){
            if(maxId < orderItem.getId()){
                maxId = orderItem.getId();
            }
        }
        return maxId+1;
    }

    private long getNextNewCustomerId() throws Exception {
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

    private long getNextMasterId() throws Exception {
        List<Master> objectList = readFromXml(Master.class);
        long maxId = -1;
        for(Master master : objectList){
            if(maxId < master.getId()){
                maxId = master.getId();
            }
        }
        return maxId+1;
    }

    private long getNextRegularCustomerId() throws Exception {
        List<RegularCustomer> objectList = readFromXml(RegularCustomer.class);
        long maxId = -1;
        for(RegularCustomer regularCustomer : objectList){
            if(maxId < regularCustomer.getId()){
                maxId = regularCustomer.getId();
            }
        }
        return maxId+1;
    }

    private long getNextOrderId() throws Exception {
        List<Order> objectList = readFromXml(Order.class);
        long maxId = -1;
        for(Order order : objectList){
            if(maxId < order.getId()){
                maxId = order.getId();
            }
        }
        return maxId+1;
    }

    private long getNextSalonId() throws Exception {
        List<Salon> objectList = readFromXml(Salon.class);
        long maxId = -1;
        for(Salon salon : objectList){
            if(maxId < salon.getId()){
                maxId = salon.getId();
            }
        }
        return maxId+1;
    }

    @Override
    public boolean createService(String name, Double price, String description) throws Exception {
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
                return writeToXml(service);
            }
        }catch (NullPointerException e){
            log.info(Constants.SERVICE_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editService(long id, String name, Double price, String description) throws Exception {
        List<Service> listService = readFromXml(Service.class);
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
            writeToXml(Service.class, listService, true);
            log.info(Constants.SERVICE_EDITED);
            log.debug(newService);
            writeToXml(newService);
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException | NullPointerException e) {
            log.info(Constants.SERVICE_NOT_EDITED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteService(long id) throws Exception {
        try {
            List<Service> serviceList = readFromXml(Service.class);
            serviceList.removeIf(service -> service.getId() == id);
            log.debug(serviceList);
            writeToXml(Service.class, serviceList, true);
            log.info(Constants.SERVICE_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.SERVICE_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public Service getServiceById(long id) throws Exception {
        List<Service> list = this.readFromXml(Service.class);
        try{
            Service service =list.stream()
                    .filter(el->el.getId()==id)
                    .limit(1)
                    .findFirst().get();
            log.info(Constants.SERVICE_RECEIVED);
            log.debug(service);
            return service;
        }catch(NoSuchElementException e){
            log.info(Constants.SERVICE_NOT_RECEIVED);
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createNewCustomer(String firstName, String lastName, String phone, String email, Integer discount) throws Exception {
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
                return writeToXml(customer);
            }
        }catch (NullPointerException e) {
            log.info(Constants.NEW_CUSTOMER_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editNewCustomer(long id, String firstName, String lastName, String phone, String email, Integer discount) throws Exception {
        List<NewCustomer> newCustomerList = readFromXml(NewCustomer.class);
        try {
            if (!getNewCustomerById(id).isPresent()){
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
            writeToXml(NewCustomer.class, newCustomerList, true);
            writeToXml(customer);
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
    public boolean deleteNewCustomer(long id) throws Exception {
        try{
            List<NewCustomer> newCustomerList = readFromXml(NewCustomer.class);
            newCustomerList.removeIf(customer -> customer.getId() == id);
            log.debug(newCustomerList);
            writeToXml(NewCustomer.class, newCustomerList, true);
            log.info(Constants.NEW_CUSTOMER_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.NEW_CUSTOMER_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public Optional<NewCustomer> getNewCustomerById(long id) throws Exception {
        List<NewCustomer> listNewCustomer = readFromXml(NewCustomer.class);
        try {
            NewCustomer newCustomer = listNewCustomer.stream()
                    .filter(el->el.getId()==id)
                    .limit(1)
                    .findFirst().get();
            log.info(Constants.NEW_CUSTOMER_RECEIVED);
            log.debug(newCustomer);
            return Optional.of(newCustomer);
        }catch (NullPointerException | NoSuchElementException e){
            log.info(Constants.NEW_CUSTOMER_NOT_RECEIVED);
            log.error(e);
            return Optional.empty();
        }
    }

    @Override
    public boolean createRegularCustomer(String firstName, String lastName, String phone, String email, Integer countOfOrder) throws Exception {
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
                return writeToXml(customer);
            }
        }catch (NullPointerException e) {
            log.info(Constants.REGULAR_CUSTOMER_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editRegularCustomer(long id, String firstName, String lastName, String phone, String email, Integer countOfOrder) throws Exception {
        List<RegularCustomer> regularCustomerList = readFromXml(RegularCustomer.class);
        try {
            if (!getRegularCustomerById(id).isPresent()){
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
            writeToXml(RegularCustomer.class, regularCustomerList, true);
            writeToXml(customer);
            log.info(Constants.REGULAR_CUSTOMER_EDITED);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.info(Constants.REGULAR_CUSTOMER_NOT_EDITED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteRegularCustomer(long id) throws Exception {
        try{
            List<RegularCustomer> regularCustomerList = readFromXml(RegularCustomer.class);
            regularCustomerList.removeIf(customer -> customer.getId() == id);
            log.debug(regularCustomerList);
            writeToXml(RegularCustomer.class, regularCustomerList, true);
            log.info(Constants.REGULAR_CUSTOMER_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.REGULAR_CUSTOMER_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public Optional<RegularCustomer> getRegularCustomerById(long id) throws Exception {
        List<RegularCustomer> listRegularCustomer = readFromXml(RegularCustomer.class);
        try {
            RegularCustomer regularCustomer = listRegularCustomer.stream()
                    .filter(el->el.getId()==id)
                    .limit(1)
                    .findFirst().get();
            log.info(Constants.REGULAR_CUSTOMER_RECEIVED);
            log.debug(regularCustomer);
            return Optional.of(regularCustomer);
        }catch (NullPointerException | NoSuchElementException e){
            log.info(Constants.REGULAR_CUSTOMER_NOT_RECEIVED);
            log.error(e);
            return Optional.empty();
        }
    }

    @Override
    public boolean createMaster(String firstName, String lastName, String position, String phone, Double salary, List<Service> listService) throws Exception {
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
                return writeToXml(master);
            }
        }catch (NullPointerException e) {
            log.info(Constants.MASTER_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editMaster(long id, String firstName, String lastName, String position, String phone, Double salary, List<Service> listService) throws Exception {
        List<Master> masterList = readFromXml(Master.class);
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
            writeToXml(Master.class, masterList, true);
            writeToXml(master);
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
    public boolean deleteMaster(long id) throws Exception {
        try{
            List<Master> masterList = readFromXml(Master.class);
            masterList.removeIf(master -> master.getId() == id);
            log.debug(masterList);
            writeToXml(Master.class, masterList, true);
            log.info(Constants.MASTER_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.MASTER_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public Master getMasterById(long id) throws Exception {
        try {
            List<Master> masterList = readFromXml(Master.class);
            Master master = masterList.stream()
                    .filter(el->el.getId()==id)
                    .limit(1)
                    .findFirst().get();
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
    public boolean createSalon(String address, List<Master> listMaster) throws Exception {
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
                return writeToXml(salon);
            }
        }catch (NullPointerException e) {
            log.info(Constants.SALON_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editSalon(long id, String address, List<Master> listMaster) throws Exception {
        List<Salon> salonList = readFromXml(Salon.class);
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
            writeToXml(Salon.class, salonList, true);
            writeToXml(salon);
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
    public boolean deleteSalon(long id) throws Exception {
        try {
            List<Salon> salonList = readFromXml(Salon.class);
            salonList.removeIf(salon -> salon.getId() == id);
            writeToXml(Salon.class, salonList, true);
            log.info(Constants.SALON_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.SALON_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public Salon getSalonById(long id) throws Exception {
        try {
            List<Salon> listSalon = readFromXml(Salon.class);
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
    public boolean createOrderItem(Service service, Integer quantity) throws Exception {
        try{
            if (service == null || quantity == null){
                log.info(Constants.NULL_VALUE);
                return false;
            }else {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(getNextOrderItemId());
                orderItem.setService(service);
                orderItem.setCost(service.getPrice());
                orderItem.setQuantity(quantity);
                log.info(Constants.ORDER_ITEM_CREATED);
                log.debug(orderItem);
                return writeToXml(orderItem);
            }
        }catch (NullPointerException e) {
            log.info(Constants.ORDER_ITEM_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editOrderItem(long id, Service service, Double cost, Integer quantity) throws Exception {
        List<OrderItem> orderItemList = readFromXml(OrderItem.class);
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
            writeToXml(OrderItem.class, orderItemList, true);
            writeToXml(orderItem);
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
    public boolean deleteOrderItem(long id) throws Exception {
        try{
            List<OrderItem> orderItemList = readFromXml(OrderItem.class);
            orderItemList.removeIf(item -> item.getId() == id);
            writeToXml(OrderItem.class, orderItemList, true);
            log.info(Constants.ORDER_ITEM_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.ORDER_ITEM_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public OrderItem getOrderItemById(long id) throws Exception {
        try {
            List<OrderItem> orderItemList = readFromXml(OrderItem.class);
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
    public boolean createOrder(String created, List<OrderItem> item, Double cost, String status, long customerId, String lastUpdated, String completed) throws Exception {
        try{
            if (created == null || item == null || cost == null || status == null){
                log.info(Constants.NULL_VALUE);
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
                log.info(Constants.ORDER_CREATED);
                log.debug(order);
                return writeToXml(order);
            }
        }catch (NullPointerException e) {
            log.info(Constants.ORDER_NOT_CREATED);
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editOrder(long id, String created, List<OrderItem> item, Double cost, String status, long customerId, String lastUpdated, String completed) throws Exception {
        List<Order> orderList = readFromXml(Order.class);
        try {
            if (getOrderById(id) == null){
                log.info(Constants.ORDER_ID + id + Constants.NOT_FOUND);
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
            writeToXml(Order.class, orderList, true);
            writeToXml(order);
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
    public boolean deleteOrder(long id) throws Exception {
        try{
            List<Order> orderList = readFromXml(Order.class);
            orderList.removeIf(ord -> ord.getId() == id);
            writeToXml(Order.class, orderList, true);
            log.info(Constants.ORDER_DELETED);
            return true;
        }catch (NullPointerException e){
            log.info(Constants.ORDER_NOT_DELETED);
            log.error(e);
            return false;
        }
    }

    @Override
    public Order getOrderById(long id) throws Exception {
        try{
            List<Order> orderList = readFromXml(Order.class);
            Order order = orderList.stream()
                    .filter(task -> task.getId() == id)
                    .findAny()
                    .orElse(null);

            order.setItem(getOrderItemList(Order.class, order));
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
    public Double calculateOrderValue(long orderId) throws Exception {
        try{
            Order order = getOrderById(orderId);
            List<OrderItem> orderItemList = getOrderItemList(Order.class, order);
            List<Double> price = orderItemList.stream()
                    .map(value -> value.getCost()*value.getQuantity())
                    .collect(Collectors.toList());
            Double cost = price.stream().map(value -> value.doubleValue())
                    .filter(a -> a != null).mapToDouble(a -> a).sum();
            log.info(Constants.ORDER_COST + orderId + Constants.EQL + cost);
            return cost;
        } catch (NoSuchElementException | NullPointerException | IOException e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public List<Order> viewOrderHistory(long customerId) throws Exception {
        try{
            if (getOrderById(customerId) == null){
                log.info( Constants.CUSTOMER_ID+ customerId + Constants.NOT_FOUND);
                return null;
            }
            List<Order> orderList = readFromXml(Order.class);
            orderList = orderList.stream()
                    .filter(user -> user.getCustomerId() == customerId)
                    .collect(Collectors.toList());
            log.debug(orderList);
            Order order = orderList.stream()
                    .findAny().orElse(null);
            order.setItem(getOrderItemList(Order.class, order));
            log.info(Constants.LIST_CUSTOMER + customerId + Constants.COLON);
            log.debug(orderList);
            return orderList;
        }catch (NullPointerException | NoSuchElementException | IOException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public List<Order> getListOfCurrentOrders(long customerId, String status) throws Exception {
        try{
            if(status.equals(Constants.PROCESSING) && getOrderById(customerId) != null){
                List<Order> orderList = readFromXml(Order.class);
                orderList = orderList.stream()
                        .filter(user -> user.getCustomerId() == customerId && user.getStatus().equals(status))
                        .collect(Collectors.toList());
                Order order = orderList.stream()
                        .findAny().orElse(null);
                order.setItem(getOrderItemList(Order.class, order));
                log.info(Constants.CURRENT_ORDER + customerId + Constants.COLON);
                log.debug(orderList);
                return orderList;
            }else{
                log.info( Constants.CUSTOMER_ID+ customerId + Constants.NOT_FOUND);
                log.info(Constants.NOT_CURRENT_ORDER);
                return null;
            }

        }catch (NullPointerException | NoSuchElementException | IOException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public StringBuffer createCustomerReport(long customerId) throws Exception {
        try{
            if (getOrderById(customerId) == null){
                log.info( Constants.CUSTOMER_ID + customerId + Constants.NOT_FOUND);
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
            report.append(Constants.NUM_CUSTOMER)
                    .append(customerId)
                    .append(Constants.ORDER_EQL)
                    .append(count);
            return report;
        }catch (NullPointerException | NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public List<Master> changeTheLisOfMaster(long salonId) throws Exception {
        try{
            if (getOrderById(salonId) == null){
                log.info( Constants.SALON_ID + salonId + Constants.NOT_FOUND);
                return null;
            }
            Salon salon = getSalonById(salonId);
            List<Master> masterList = getMasterList(Salon.class, salon);

            log.info(Constants.LIST_MASTER + salonId + Constants.COLON);
            log.debug(masterList);
            return masterList;
        }catch (NullPointerException | NoSuchElementException | IOException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean assignService(List<Service> service, long masterId) throws Exception {
        try{
            List<Master> masterList = readFromXml(Master.class);
            Master master = getMasterById(masterId);
            master.setListService(service);
            masterList.removeIf(user -> user.getId() == masterId);
            writeToXml(Master.class, masterList, true);
            writeToXml(master);
            log.info(Constants.ASSIGN_SUCCESS);
            log.debug(master);
            return true;
        } catch (NoSuchElementException | NullPointerException | IndexOutOfBoundsException e) {
            log.info(Constants.ASSIGN_FAIL);
            log.error(e);
            return false;
        }
    }

    @Override
    public StringBuffer createMasterReport(long masterId) throws Exception {
        try {
            if (getMasterById(masterId) == null) {
                log.info(Constants.MASTER_ID + masterId + Constants.NOT_FOUND);
                return null;
            }
            List<Master> masterList = readFromXml(Master.class);
            Master master = masterList.stream()
                    .filter(task -> task.getId() == masterId)
                    .findAny()
                    .orElse(null);
            master.setListService(getServiceListInMaster(Master.class, master));

            StringBuffer report = new StringBuffer();
            report.append(Constants.MASTER)
                    .append(masterId)
                    .append(Constants.PROVIDE_SERVICE)
                    .append(master.getListService());

            return report;
        } catch (NullPointerException | NoSuchElementException | IOException e) {
            log.error(e);
            return null;
        }
    }
}
