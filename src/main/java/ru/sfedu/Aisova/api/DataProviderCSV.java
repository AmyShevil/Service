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
import ru.sfedu.Aisova.bean.*;
import ru.sfedu.Aisova.utils.ConfigurationUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class DataProviderCSV {

    private final String PATH = "csv_path";
    private final String FILE_EXTENSION = "csv";
    private static Logger log = LogManager.getLogger(DataProviderCSV.class);

    public void insertUser(List<User> listUser) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(PATH) + listUser.get(0).getClass().getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<User> beanToCsv = new StatefulBeanToCsvBuilder<User>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listUser);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
    }

    public User getUserById(long id) throws IOException {
        List<User> userList = select(User.class);
        try {
            User user = userList.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            return user;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    public void insertCustomer(List<Customer> listCustomer) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(PATH) + listCustomer.get(0).getClass().getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<Customer> beanToCsv = new StatefulBeanToCsvBuilder<Customer>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listCustomer);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
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

    public void insertNewCustomer(List<NewCustomer> listNewCustomer) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(PATH) + listNewCustomer.get(0).getClass().getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<NewCustomer> beanToCsv = new StatefulBeanToCsvBuilder<NewCustomer>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listNewCustomer);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
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

    public void insertRegularCustomer(List<RegularCustomer> listRegularCustomer) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(PATH) + listRegularCustomer.get(0).getClass().getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<RegularCustomer> beanToCsv = new StatefulBeanToCsvBuilder<RegularCustomer>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listRegularCustomer);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
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

    public void insertService(List<Service> listService) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(PATH) + listService.get(0).getClass().getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<Service> beanToCsv = new StatefulBeanToCsvBuilder<Service>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listService);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
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

    public void insertMaster(List<Master> listMaster) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(PATH) + listMaster.get(0).getClass().getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<Master> beanToCsv = new StatefulBeanToCsvBuilder<Master>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listMaster);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
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

    public void insertOrderItem(List<OrderItem> listOrderItem) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(PATH) + listOrderItem.get(0).getClass().getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<OrderItem> beanToCsv = new StatefulBeanToCsvBuilder<OrderItem>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listOrderItem);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
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

    public void insertOrder(List<Order> listOrder) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(PATH) + listOrder.get(0).getClass().getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<Order> beanToCsv = new StatefulBeanToCsvBuilder<Order>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listOrder);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
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

    public void insertSalon(List<Salon> listSalon) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(PATH) + listSalon.get(0).getClass().getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<Salon> beanToCsv = new StatefulBeanToCsvBuilder<Salon>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listSalon);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
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

    public <T> List<T> select(Class cl) throws IOException {
        FileReader fileReader = new FileReader(ConfigurationUtil.getConfigurationEntry(PATH) + cl.getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                .withType(cl)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<T> list = csvToBean.parse();
        return list;
    }
}
