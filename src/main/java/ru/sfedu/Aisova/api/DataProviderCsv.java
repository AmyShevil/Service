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

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DataProviderCsv implements DataProvider{

    private static final Logger log = LogManager.getLogger(DataProviderCsv.class);

    private <T> boolean writeToCsv (Class<?> tClass, List<T> object, boolean overwrite) {
        List<T> fileObjectList;
        if (overwrite) {
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
        return writeToCsv(object.getClass(), Collections.singletonList(object), true);
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

    @Override
    public boolean createService(String name, Double price, String description) {
        if (name == null || price == null || description == null) {
            log.error("something is null");
            return false;
        }
        Service service = new Service();
        service.setId(getNextServiceId());
        service.setName(name);
        service.setPrice(price);
        service.setDescription(description);
        return  writeToCsv(service);
    }

    @Override
    public boolean editService(long id, Service editedService) {

        return false;
    }

    @Override
    public boolean deleteService(long id) {
        return false;
    }

    @Override
    public Service getServiceById(long id) {
        return null;
    }

    @Override
    public boolean createNewCustomer(String firstName, String lastName, String phone, String email, Integer discount) {
        return false;
    }

    @Override
    public boolean editNewCustomer(long id, NewCustomer editCustomer) {
        return false;
    }

    @Override
    public boolean deleteNewCustomer(long id) {
        return false;
    }

    @Override
    public NewCustomer getNewCustomerById(long id) {
        return null;
    }

    @Override
    public boolean createRegularCustomer(String firstName, String lastName, String phone, String email, Integer countOfOrder) {
        return false;
    }

    @Override
    public boolean editRegularCustomer(long id, RegularCustomer editCustomer) {
        return false;
    }

    @Override
    public boolean deleteRegularCustomer(long id) {
        return false;
    }

    @Override
    public RegularCustomer getRegularCustomerById(long id) {
        return null;
    }

    @Override
    public boolean createMaster(String firstName, String lastName, String position, List<Service> listService, String phone, Double salary) {
        return false;
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
        return null;
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
    public boolean createOrderItem(long number, Service service, Double cost, Integer quantity) {
        return false;
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
        return null;
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
        return null;
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
