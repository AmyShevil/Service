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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class DataProviderCSV {

    private final String PATH = "csv_path";
    private final String FILE_EXTENSION = "csv";
    private static Logger log = LogManager.getLogger(DataProviderCSV.class);

    public <T> void insertClass(List<T> listClass) {
        try{
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(PATH)
                    + listClass.get(0).getClass().getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listClass);
            csvWriter.close();
        }catch (IndexOutOfBoundsException | IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e){
            log.error(e);
        }
    }

    public <T> List<T> select(Class<T> tClass) {
        try {
            FileReader fileReader = new FileReader(ConfigurationUtil.getConfigurationEntry(PATH)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVReader csvReader = new CSVReader(fileReader);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                    .withType(tClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        }catch (IOException e){
            log.error(e);
            return new ArrayList<>();
        }
    }
/*
    public <T> void deleteClass(Class<T> tClass, long id) throws IOException {
        List<T> listClass = select(tClass);
        try {
            Optional<T> opt = listClass.stream()
                    .filter(el -> el.getId() == id)
                    .findFirst().get();

            listClass.remove(opt);
            insertNewCustomer(listClass);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error(e);
        }
    }

 */



/*
    public void insertCustomer(List<Customer> listCustomer) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        this.insertClass(listCustomer);
    }

    public Customer getCustomerById(long id) throws IOException {
        List<Customer> listCustomer = select(Customer.class);
        try {
            Customer customer = listCustomer.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            return customer;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    public void deleteCustomer(long id) throws IOException {
        List<Customer> listCustomer = select(Customer.class);
        try {
            Customer customer = listCustomer.stream()
                    .filter(el -> el.getId() == id)
                    .findFirst().get();

            listCustomer.remove(customer);
            insertCustomer(listCustomer);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error(e);
        }
    }

    public void rewriteCustomer(long id, String firstName, String lastName, String phone, String email) throws IOException {
        List<Customer> listCustomer = select(Customer.class);
        try {
            Customer newCustomer = new Customer();
            newCustomer.setId(id);
            newCustomer.setFirstName(firstName);
            newCustomer.setLastName(lastName);
            newCustomer.setPhone(phone);
            newCustomer.setEmail(email);
            int newId = Integer.parseInt(String.valueOf(id))-1;
            listCustomer.set(newId,newCustomer);
            insertCustomer(listCustomer);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error(e);
        }
    }

 */

    public void insertNewCustomer(List<NewCustomer> listNewCustomer) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        this.insertClass(listNewCustomer);
    }

    public NewCustomer getNewCustomerById(long id) throws IOException {
        List<NewCustomer> listNewCustomer = select(NewCustomer.class);
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

    public void deleteNewCustomer(long id) throws IOException {
        List<NewCustomer> listNewCustomer = select(NewCustomer.class);
        try {
            NewCustomer newCustomer = listNewCustomer.stream()
                    .filter(el -> el.getId() == id)
                    .findFirst().get();

            listNewCustomer.remove(newCustomer);
            insertNewCustomer(listNewCustomer);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error(e);
        }
    }

    public void rewriteNewCustomer(long id, String firstName, String lastName, String phone, String email, Integer discount) throws IOException {
        List<NewCustomer> listNewCustomer = select(NewCustomer.class);
        try {
            NewCustomer newNewCustomer = new NewCustomer();
            newNewCustomer.setId(id);
            newNewCustomer.setFirstName(firstName);
            newNewCustomer.setLastName(lastName);
            newNewCustomer.setPhone(phone);
            newNewCustomer.setEmail(email);
            newNewCustomer.setDiscount(discount);
            int newId = Integer.parseInt(String.valueOf(id))-1;
            listNewCustomer.set(newId,newNewCustomer);
            insertNewCustomer(listNewCustomer);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IndexOutOfBoundsException e) {
            log.error(e);
        }
    }

    public void insertRegularCustomer(List<RegularCustomer> listRegularCustomer) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        this.insertClass(listRegularCustomer);
    }

    public RegularCustomer getRegularCustomerById(long id) throws IOException {
        List<RegularCustomer> listRegularCustomer = select(RegularCustomer.class);
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

    public void deleteRegularCustomer(long id) throws IOException {
        List<RegularCustomer> listRegularCustomer = select(RegularCustomer.class);
        try {
            RegularCustomer regularCustomer = listRegularCustomer.stream()
                    .filter(el -> el.getId() == id)
                    .findFirst().get();

            listRegularCustomer.remove(regularCustomer);
            insertRegularCustomer(listRegularCustomer);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error(e);
        }
    }

    public void rewriteRegularCustomer(long id, String firstName, String lastName, String phone, String email, Integer numberOfOrders) throws IOException {
        List<RegularCustomer> listRegularCustomer = select(RegularCustomer.class);
        try {
            RegularCustomer newRegularCustomer = new RegularCustomer();
            newRegularCustomer.setId(id);
            newRegularCustomer.setFirstName(firstName);
            newRegularCustomer.setLastName(lastName);
            newRegularCustomer.setPhone(phone);
            newRegularCustomer.setEmail(email);
            newRegularCustomer.setNumberOfOrders(numberOfOrders);
            int newId = Integer.parseInt(String.valueOf(id))-1;
            listRegularCustomer.set(newId,newRegularCustomer);
            insertRegularCustomer(listRegularCustomer);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IndexOutOfBoundsException e) {
            log.error(e);
        }
    }

    public void insertService(List<Service> listService) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        this.insertClass(listService);
    }

    public Service getServiceById(long id) throws IOException {
        List<Service> listService = select(Service.class);
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

    public void deleteService(long id) throws IOException {
        List<Service> listService = select(Service.class);
        try {
            Service service = listService.stream()
                    .filter(el -> el.getId() == id)
                    .findFirst().get();

            listService.remove(service);
            insertService(listService);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error(e);
        }
    }

    public void rewriteService(long id, String name, Double price, String description) throws IOException {
        List<Service> listService = select(Service.class);
        try {
            Service newService = new Service();
            newService.setId(id);
            newService.setName(name);
            newService.setPrice(price);
            newService.setDescription(description);
            int newId = Integer.parseInt(String.valueOf(id))-1;
            listService.set(newId,newService);
            insertService(listService);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IndexOutOfBoundsException e) {
            log.error(e);
        }
    }

    public void insertMaster(List<Master> listMaster) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        this.insertClass(listMaster);
    }

    public Master getMasterById(long id) throws IOException {
        List<Master> listMaster = select(Master.class);
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

    public void deleteMaster(long id) throws IOException {
        List<Master> listMaster = select(Master.class);
        try {
            Master master = listMaster.stream()
                    .filter(el -> el.getId() == id)
                    .findFirst().get();

            listMaster.remove(master);
            insertMaster(listMaster);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error(e);
        }
    }

    public void rewriteMaster(long id, String firstName, String lastName, String position, List<Service> listService, String phone, Double salary) throws IOException {
        List<Master> listMaster = select(Master.class);
        try {
            Master newMaster = new Master();
            newMaster.setId(id);
            newMaster.setFirstName(firstName);
            newMaster.setLastName(lastName);
            newMaster.setPosition(position);
            newMaster.setServiceList(listService);
            newMaster.setPhone(phone);
            newMaster.setSalary(salary);
            int newId = Integer.parseInt(String.valueOf(id))-1;
            listMaster.set(newId,newMaster);
            insertMaster(listMaster);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IndexOutOfBoundsException e) {
            log.error(e);
        }
    }

    public void insertOrderItem(List<OrderItem> listOrderItem) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        this.insertClass(listOrderItem);
    }

    public OrderItem getOrderItemById(long id) throws IOException {
        List<OrderItem> listOrderItem = select(OrderItem.class);
        try {
            OrderItem orderItem = listOrderItem.stream()
                    .filter(el->el.getNumber()==id)
                    .findFirst().get();
            return orderItem;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    public void deleteOrderItem(long id) throws IOException {
        List<OrderItem> listOrderItem = select(OrderItem.class);
        try {
            OrderItem orderItem = listOrderItem.stream()
                    .filter(el -> el.getNumber() == id)
                    .findFirst().get();

            listOrderItem.remove(orderItem);
            insertOrderItem(listOrderItem);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error(e);
        }
    }

    public void rewriteOrderItem(long number, Service service, Double cost, Integer quantity) throws IOException {
        List<OrderItem> listOrderItem = select(OrderItem.class);
        try {
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setNumber(number);
            newOrderItem.setService(service);
            newOrderItem.setCost(cost);
            newOrderItem.setQuantity(quantity);
            int newId = Integer.parseInt(String.valueOf(number))-1;
            listOrderItem.set(newId,newOrderItem);
            insertOrderItem(listOrderItem);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IndexOutOfBoundsException e) {
            log.error(e);
        }
    }

    public void insertOrder(List<Order> listOrder) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        this.insertClass(listOrder);
    }

    public Order getOrderById(long id) throws IOException {
        List<Order> listOrder = select(Order.class);
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

    public void deleteOrder(long id) throws IOException {
        List<Order> listOrder = select(Order.class);
        try {
            Order order = listOrder.stream()
                    .filter(el -> el.getId() == id)
                    .findFirst().get();

            listOrder.remove(order);
            insertOrder(listOrder);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error(e);
        }
    }

    public void rewriteOrder(long id, String created, OrderItem item, Double cost, Order.OrderStatus status, Customer customer, String lastUpdated, String completed) throws IOException {
        List<Order> listOrder = select(Order.class);
        try {
            Order newOrder = new Order();
            newOrder.setId(id);
            newOrder.setCreated(created);
            newOrder.setItem(item);
            newOrder.setCost(cost);
            newOrder.setStatus(status);
            newOrder.setCustomer(customer);
            newOrder.setLastUpdated(lastUpdated);
            newOrder.setCompleted(completed);
            int newId = Integer.parseInt(String.valueOf(id))-1;
            listOrder.set(newId,newOrder);
            insertOrder(listOrder);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IndexOutOfBoundsException e) {
            log.error(e);
        }
    }

    public void insertSalon(List<Salon> listSalon) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        this.insertClass(listSalon);
    }

    public Salon getSalonById(long id) throws IOException {
        List<Salon> listSalon = select(Salon.class);
        try {
            Salon salon = listSalon.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            return salon;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    public void deleteSalon(long id) throws IOException {
        List<Salon> listSalon = select(Salon.class);
        try {
            Salon salon = listSalon.stream()
                    .filter(el -> el.getId() == id)
                    .findFirst().get();

            listSalon.remove(salon);
            insertSalon(listSalon);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error(e);
        }
    }

    public void rewriteSalon(long id, String address, List<Master> listMaster) throws IOException {
        List<Salon> listSalon = select(Salon.class);
        try {
            Salon newSalon = new Salon();
            newSalon.setId(id);
            newSalon.setAddress(address);
            newSalon.setListMaster(listMaster);
            int newId = Integer.parseInt(String.valueOf(id))-1;
            listSalon.set(newId,newSalon);
            insertSalon(listSalon);
        } catch (NoSuchElementException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IndexOutOfBoundsException e) {
            log.error(e);
        }
    }
}
