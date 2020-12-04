package ru.sfedu.Aisova;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.api.DataProviderCSV;
import ru.sfedu.Aisova.model.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.sfedu.Aisova.Constants.*;
import static ru.sfedu.Aisova.model.Order.OrderStatus.CREATED;
import static ru.sfedu.Aisova.utils.ConfigurationUtil.getConfigurationEntry;

public class ServiceClient {

    private static Logger log = LogManager.getLogger(ServiceClient.class);

    public ServiceClient(){
        log.debug("LogClient: starting application.........");
    }

    public static void main(String[] args) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        new ServiceClient();
        logBasicSystemInfo();

        DataProviderCSV providerCSV = new DataProviderCSV();

        Service service = new Service();
        service.setId(1);
        service.setName("name1");
        service.setPrice(1.0);
        service.setDescription("description1");

        List<Service> listService = new ArrayList<>();
        listService.add(service);

        providerCSV.insertService(listService);

/*
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("firstName1");
        customer.setLastName("lastName1");
        customer.setPhone("phone1");
        customer.setEmail("email1");

        List<Customer> listCustomer = new ArrayList<>();
        listCustomer.add(customer);

        providerCSV.insertCustomer(listCustomer);

 */

        NewCustomer newCustomer = new NewCustomer();
        newCustomer.setId(2);
        newCustomer.setFirstName("firstName2");
        newCustomer.setLastName("lastName2");
        newCustomer.setPhone("phone2");
        newCustomer.setEmail("email2");
        newCustomer.setDiscount(10);

        List<NewCustomer> listNewCustomer = new ArrayList<>();
        listNewCustomer.add(newCustomer);

        providerCSV.insertNewCustomer(listNewCustomer);

        RegularCustomer regularCustomer = new RegularCustomer();
        regularCustomer.setId(3);
        regularCustomer.setFirstName("firstName3");
        regularCustomer.setLastName("lastName3");
        regularCustomer.setPhone("phone3");
        regularCustomer.setEmail("email3");
        regularCustomer.setNumberOfOrders(1);

        List<RegularCustomer> listRegularCustomer = new ArrayList<>();
        listRegularCustomer.add(regularCustomer);

        providerCSV.insertRegularCustomer(listRegularCustomer);


        /*
        Master master = new Master();
        master.setId(1);
        master.setFirstName("firstName1");
        master.setLastName("lastName1");
        master.setPosition("position1");
        master.setServiceList(listService);
        master.setPhone("phone1");
        master.setSalary(1.0);

        List<Master> listMaster = new ArrayList<>();
        listMaster.add(master);

        providerCSV.insertMaster(listMaster);

         */

        Salon salon = new Salon();
        salon.setId(1);
        salon.setAddress("address1");
        //salon.setListMaster(listMaster);

        List<Salon> listSalon = new ArrayList<>();
        listSalon.add(salon);

        providerCSV.insertSalon(listSalon);

        OrderItem orderItem = new OrderItem();
        orderItem.setNumber(10);
        orderItem.setService(service);
        orderItem.setCost(1.0);
        orderItem.setQuantity(5);

        List<OrderItem> listOrderItem = new ArrayList<>();
        listOrderItem.add(orderItem);

        providerCSV.insertOrderItem(listOrderItem);

        Order order = new Order();
        order.setId(1);
        //order.setCreated("01.11.2020");
        order.setItem(orderItem);
        order.setCost(1.0);
        order.setStatus(CREATED);
        //order.setCustomer(customer);
        order.setCompleted("20.11.2020");

        List<Order> listOrder = new ArrayList<>();
        listOrder.add(order);

        providerCSV.insertOrder(listOrder);
    }

    public static void logBasicSystemInfo() throws IOException {

        log.trace("Trace_test");
        log.debug("Debug_test");
        log.info("Info_test");
        log.warn("Warn_test");
        log.error("Error_test");
        log.fatal("Fatal_test");
        log.info(getConfigurationEntry(ENV_CONST));
        log.info(String.format(TEXT_CONST, NUM_CONST_ONE));
    }
}
