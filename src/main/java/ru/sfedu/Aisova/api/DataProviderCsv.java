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
import org.apache.maven.artifact.repository.metadata.Metadata;
import ru.sfedu.Aisova.Constants;

import ru.sfedu.Aisova.enums.OrderStatus;
import ru.sfedu.Aisova.model.*;
import ru.sfedu.Aisova.utils.ConfigurationUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataProviderCsv implements DataProvider{

    private static final Logger log = LogManager.getLogger(DataProviderCsv.class);

    private <T> boolean writeToCsv (Class<?> tClass, List<T> object, boolean overwrite) {
        log.info(object);
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
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(Constants.PATH)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION));
            csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(fileObjectList);
            csvWriter.close();
            return true;
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e ) {
            log.error(e);
            return false;
        }
    }

    private <T> boolean writeToCsv (T object) {
        if (object == null) {
            log.error("something is null");
            return false;
        }
        return writeToCsv(object.getClass(), Collections.singletonList(object), false);
    }

    private <T> List<T> readFromCsv (Class<T> tClass) {
        try {
            FileReader reader = new FileReader(ConfigurationUtil.getConfigurationEntry(Constants.PATH)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION));

            CSVReader csvReader = new CSVReader(reader);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                    .withType(tClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (IOException e) {
            log.error(e);
            return new ArrayList<>();
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

    @Override
    public boolean createService(String name, Double price, String description) {
        try {
            /*
            String str ="";
            Service service = new Service();
            service.setId(getNextServiceId());
            service.setName(str.concat(name));
            service.setPrice(price);
            service.setDescription(str.concat(description));
             */
            if (name == null || price == null || description == null){
                log.info("Строка не добавлена, так как одно из полей null");
                return false;
            }else {
                Service service = new Service();
                service.setId(getNextServiceId());
                service.setName(name);
                service.setPrice(price);
                service.setDescription(description);
                return writeToCsv(service);
            }
        }catch (NullPointerException e){
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editService(long id, String name, Double price, String description) {
        List<Service> listService = readFromCsv(Service.class);
        try {
            if (getServiceById(id) == null){
                log.info("Такого id нет в списке");
                return false;
            }
            Service newService = new Service();
            newService.setId(id);
            newService.setName(name);
            newService.setPrice(price);
            newService.setDescription(description);
            listService.removeIf(service -> service.getId() == id);
            writeToCsv(Service.class, listService, true);
            writeToCsv(newService);
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteService(long id) {
        List<Service> serviceList = readFromCsv(Service.class);
        serviceList.removeIf(service -> service.getId() == id);
        writeToCsv(Service.class, serviceList, true);
        return true;
    }

    @Override
    public Service getServiceById(long id) {
        List<Service> listService = readFromCsv(Service.class);
        try {
            Service service = listService.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            return service;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createNewCustomer(String firstName, String lastName, String phone, String email, Integer discount) {
        try {
            if (firstName == null || lastName == null || phone == null || email == null || discount == null){
                log.info("Строка не добавлена, так как одно из полей null");
                return false;
            }else {
                NewCustomer customer = new NewCustomer();
                customer.setId(getNextNewCustomerId());
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPhone(phone);
                customer.setEmail(email);
                customer.setDiscount(discount);
                return writeToCsv(customer);
            }
        }catch (NullPointerException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editNewCustomer(long id, String firstName, String lastName, String phone, String email, Integer discount) {
        List<NewCustomer> newCustomerList = readFromCsv(NewCustomer.class);
        try {
            if (getNewCustomerById(id) == null){
                log.info("Такого id нет в списке");
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
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteNewCustomer(long id) {
        List<NewCustomer> newCustomerList = readFromCsv(NewCustomer.class);
        newCustomerList.removeIf(customer -> customer.getId() == id);
        writeToCsv(NewCustomer.class, newCustomerList, true);
        return true;
    }

    @Override
    public NewCustomer getNewCustomerById(long id) {
        List<NewCustomer> listNewCustomer = readFromCsv(NewCustomer.class);
        try {
            NewCustomer newCustomer = listNewCustomer.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            return newCustomer;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createRegularCustomer(String firstName, String lastName, String phone, String email, Integer countOfOrder) {
        try{
            if (firstName == null || lastName == null || phone == null || email == null || countOfOrder == null){
                log.info("Строка не добавлена, так как одно из полей null");
                return false;
            }else {
                RegularCustomer customer = new RegularCustomer();
                customer.setId(getNextRegularCustomerId());
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPhone(phone);
                customer.setEmail(email);
                customer.setNumberOfOrders(countOfOrder);
                return writeToCsv(customer);
            }
        }catch (NullPointerException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editRegularCustomer(long id, String firstName, String lastName, String phone, String email, Integer countOfOrder) {
        List<RegularCustomer> regularCustomerList = readFromCsv(RegularCustomer.class);
        try {
            if (getRegularCustomerById(id) == null){
                log.info("Такого id нет в списке");
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
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteRegularCustomer(long id) {
        List<RegularCustomer> regularCustomerList = readFromCsv(RegularCustomer.class);
        regularCustomerList.removeIf(customer -> customer.getId() == id);
        writeToCsv(RegularCustomer.class, regularCustomerList, true);
        return true;
    }

    @Override
    public RegularCustomer getRegularCustomerById(long id) {
        List<RegularCustomer> listRegularCustomer = readFromCsv(RegularCustomer.class);
        try {
            RegularCustomer regularCustomer = listRegularCustomer.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            return regularCustomer;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createMaster(String firstName, String lastName, String position, String phone, Double salary, List<Service> serviceList) {
        try{
            if (firstName == null || lastName == null || position == null || phone == null || salary == null || serviceList == null){
                log.info("Строка не добавлена, так как одно из полей null");
                return false;
            }else {
                Master master = new Master();
                master.setId(getNextMasterId());
                master.setFirstName(firstName);
                master.setLastName(lastName);
                master.setPosition(position);
                master.setServiceList(serviceList);
                master.setPhone(phone);
                master.setSalary(salary);
                log.info(master);
                return writeToCsv(master);
            }
        }catch (NullPointerException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editMaster(long id, Master editedMaster) {
        return false;
    }

    @Override
    public boolean deleteMaster(long id) {
        return false;
    }

    @Override
    public Master getMasterById(long id) {
        List<Master> listMaster = readFromCsv(Master.class);
        try {
            Master master = listMaster.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            return master;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createSalon(String address, List<Master> listMaster) {
        return false;
    }

    @Override
    public boolean editSalon(long id, Salon editedSalon) {
        return false;
    }

    @Override
    public boolean deleteSalon(long id) {
        return false;
    }

    @Override
    public Salon getSalonById(long id) {
        return null;
    }

    @Override
    public boolean createOrderItem(Service service, Double cost, Integer quantity) {
        try{
            if (service == null || cost == null || quantity == null){
                log.info("Строка не добавлена, так как одно из полей null");
                return false;
            }else {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(getNextOrderItemId());
                orderItem.setService(service);
                orderItem.setCost(cost);
                orderItem.setQuantity(quantity);
                return writeToCsv(orderItem);
            }
        }catch (NullPointerException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editOrderItem(long id, OrderItem editedOrderItem) {
        return false;
    }

    @Override
    public boolean deleteOrderItem(long id) {
        return false;
    }

    @Override
    public OrderItem getOrderItemById(long id) {
        List<OrderItem> listOrderItem = readFromCsv(OrderItem.class);
        try {
            OrderItem orderItem = listOrderItem.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            return orderItem;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createOrder(long id, String created, List<OrderItem> item, Double cost, OrderStatus status, Customer customer, String lastUpdated, String completed) {
        return false;
    }

    @Override
    public boolean editOrder(long id, Order editedOrder) {
        return false;
    }

    @Override
    public boolean deleteOrder(long id) {
        return false;
    }

    @Override
    public Order getOrderById(long id) {
        List<Order> listOrder = readFromCsv(Order.class);
        try {
            Order order = listOrder.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            return order;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public Double calculateOrderValue() {
        return null;
    }

    @Override
    public List<Order> viewOrderHistory() {
        return null;
    }

    @Override
    public List<Order> getListOfCurrentOrders() {
        return null;
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
