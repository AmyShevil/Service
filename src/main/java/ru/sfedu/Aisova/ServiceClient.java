package ru.sfedu.Aisova;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.api.DataProvider;
import ru.sfedu.Aisova.api.DataProviderCsv;
import ru.sfedu.Aisova.api.DataProviderXml;
import ru.sfedu.Aisova.model.Order;
import ru.sfedu.Aisova.model.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static ru.sfedu.Aisova.Constants.*;

public class ServiceClient {

    private static Logger log = LogManager.getLogger(ServiceClient.class);

    public ServiceClient(){
        log.debug(START_APP);
    }

    public static DataProviderCsv getDataProviderCsv(){
        return new DataProviderCsv();
    }

    public static DataProviderXml getDataProviderXml(){
        return new DataProviderXml();
    }

    public static DataProvider getSource(List<String> command){
        if (command.size() == 0) return null;
        switch (command.remove(0)){
            case FILE_EXTENSION_CSV:
                return getDataProviderCsv();
            case FILE_EXTENSION_XML:
                return getDataProviderXml();
            default:
                return null;
        }
    }

    public static String getFile(List<String> command){
        if (command.size() == 0) return null;
        String file = command.remove(0);
        switch (file){
            case SERVICE_COMMAND:
            case NEW_CUSTOMER_COMMAND:
            case REGULAR_CUSTOMER_COMMAND:
            case MASTER_COMMAND:
            case SALON_COMMAND:
            case ORDER_ITEM_COMMAND:
            case ORDER_COMMAND:
                return file;
            default:
                return null;
        }
    }

    public static String getAction(List<String> command){
        if (command.size() == 0) return null;
        String action = command.remove(0);
        switch (action){
            case CREATE_COMMAND:
            case DELETE_COMMAND:
            case GET_BY_ID_COMMAND:
            case GET_ORDER_HISTORY_COMMAND:
            case CALCULATE_COMMAND:
                return action;
            default:
                return null;
        }
    }

    public static Long getId(List<String> command) {
        if (command.size() == 0) return null;
        try{
            return Long.valueOf(command.remove(0));
        } catch (Exception e) {
            return null;
        }

    }

    public static void getById(DataProvider dataProvider, String file, long id) throws Exception {
        Object object;
        switch (file){
            case SERVICE_COMMAND:
                object = dataProvider.getServiceById(id);
                break;
            case NEW_CUSTOMER_COMMAND:
                object = dataProvider.getNewCustomerById(id);
                break;
            case REGULAR_CUSTOMER_COMMAND:
                object = dataProvider.getRegularCustomerById(id);
                break;
            case MASTER_COMMAND:
                object = dataProvider.getMasterById(id);
                break;
            case SALON_COMMAND:
                object = dataProvider.getSalonById(id);
                break;
            case ORDER_ITEM_COMMAND:
                object = dataProvider.getOrderItemById(id);
                break;
            case ORDER_COMMAND:
                object = dataProvider.getOrderById(id);
                break;
            default:
                object = null;
        }
        if (object != null) log.info(object.toString());
    }

    public static void delete(DataProvider dataProvider, String file, long id) throws Exception {
        switch (file){
            case SERVICE_COMMAND:
                dataProvider.deleteService(id);
                break;
            case NEW_CUSTOMER_COMMAND:
                dataProvider.deleteNewCustomer(id);
                break;
            case REGULAR_CUSTOMER_COMMAND:
                dataProvider.deleteRegularCustomer(id);
                break;
            case MASTER_COMMAND:
                dataProvider.deleteMaster(id, true);
                break;
            case SALON_COMMAND:
                dataProvider.deleteSalon(id);
                break;
            case ORDER_ITEM_COMMAND:
                dataProvider.deleteOrderItem(id);
                break;
            case ORDER_COMMAND:
                dataProvider.deleteOrder(id);
                break;
        }
    }

    public static void getOrderHistory(DataProvider dataProvider, String file, long id) throws Exception {
        List<Order> orderList = new ArrayList<>();
        if (ORDER_COMMAND.equals(file)) {
            orderList = dataProvider.viewOrderHistory(id);
        } else {
            orderList = null;
        }
        if (orderList != null) log.info(orderList.toString());
    }

    public static void getCalculateOrder(DataProvider dataProvider, String file, long id) throws Exception {
        Double cost;
        if (ORDER_COMMAND.equals(file)) {
            cost = dataProvider.calculateOrderValue(id);
        } else {
            cost = null;
        }
        if (cost != null) log.info(cost.toString());
    }

    public static void write(DataProvider dataProvider, String table, List<String> arguments) throws Exception {
        switch (table){
            case SERVICE_COMMAND:
                dataProvider.createService(arguments.get(0),
                        Double.parseDouble(arguments.get(1)),
                        arguments.get(2));
                break;
            case NEW_CUSTOMER_COMMAND:
                dataProvider.createNewCustomer(arguments.get(0),
                        arguments.get(1),
                        arguments.get(2),
                        arguments.get(3),
                        Integer.parseInt(arguments.get(0)));
                break;
            case REGULAR_CUSTOMER_COMMAND:
                dataProvider.createRegularCustomer(arguments.get(0),
                        arguments.get(1),
                        arguments.get(2),
                        arguments.get(3),
                        Integer.parseInt(arguments.get(0)));
                break;
            case MASTER_COMMAND:
                //dataProvider.createMaster();
                break;
            case SALON_COMMAND:
                //dataProvider.createSalon();
                break;
            case ORDER_ITEM_COMMAND:
                //dataProvider.createOrderItem();
                break;
            case ORDER_COMMAND:
                //dataProvider.createOrder();
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        new ServiceClient();
        logBasicSystemInfo();
        log.info(LINE);

        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(Constants.PROPERTIES));
            properties.forEach((o, o2) -> System.setProperty((String) o, (String) o2));
        } catch (IOException e) {
            log.info(READ_FAIL);
            log.fatal(e);
            System.exit(1);
        }


        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        DataProvider dataProvider = getSource(arguments);
        if (dataProvider == null) {
            log.error(SOURCE_FAIL + FILE_EXTENSION_CSV + COMMA + FILE_EXTENSION_XML);
        }

        String file = getFile(arguments);
        if (file == null) {
            log.error(FILE_FAIL
                    + SERVICE_COMMAND + COMMA
                    + NEW_CUSTOMER_COMMAND + COMMA
                    + REGULAR_CUSTOMER_COMMAND + COMMA
                    + MASTER_COMMAND + COMMA
                    + SALON_COMMAND+ COMMA
                    + ORDER_ITEM_COMMAND+ COMMA
                    + ORDER_COMMAND);
        }

        String action = getAction(arguments);
        if (action == null) {
            log.error(ACTION_FAIL
                    + CREATE_COMMAND + COMMA
                    + DELETE_COMMAND + COMMA
                    + GET_BY_ID_COMMAND + COMMA
                    + GET_ORDER_HISTORY_COMMAND + COMMA
                    + CALCULATE_COMMAND);
            return;
        }

        if (action.equals(DELETE_COMMAND) || action.equals(GET_BY_ID_COMMAND) || action.equals(GET_ORDER_HISTORY_COMMAND) || action.equals(CALCULATE_COMMAND)){
            Long id = getId(arguments);
            if (id == null){
                log.error(BAD_ID);
                return;
            }
            switch (action) {
                case GET_BY_ID_COMMAND:
                    getById(dataProvider, file, id);
                    break;
                case DELETE_COMMAND:
                    delete(dataProvider, file, id);
                    break;
                case GET_ORDER_HISTORY_COMMAND:
                    getOrderHistory(dataProvider, file, id);
                    break;
                case CALCULATE_COMMAND:
                    getCalculateOrder(dataProvider, file, id);
                    break;
            }
        }

        if (action.equals(CREATE_COMMAND))
            write(dataProvider, file, arguments);

        log.info(LINE);
    }

    public static void logBasicSystemInfo() {
        log.info(LAUNCH_APP);
        log.info(OPERATING_SYS + System.getProperty(OS_NAME) + PROB + System.getProperty(OS_VERSION));
        log.info(JRE + System.getProperty(JAVA_VERSION));
        log.info(JAVA_LAUNCH + System.getProperty(JAVA_HOME));
        log.info(CLASS_PATH + System.getProperty(JAVA_CLASS));
        log.info(LIBRARY_PATH + System.getProperty(JAVA_LIBRARY));
        log.info(USER_HOME_DIRECTORY + System.getProperty(USER_HOME));
        log.info(USER_WORKING_DIRECTORY + System.getProperty(USER_DIR));
        log.info(TEST_INFO);

    }
}
