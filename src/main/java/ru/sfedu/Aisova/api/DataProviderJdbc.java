package ru.sfedu.Aisova.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.model.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.sfedu.Aisova.Constants.*;
import static ru.sfedu.Aisova.utils.ConfigurationUtil.getConfigurationEntry;

/**
 * The type Data provider jdbc.
 */
public class DataProviderJdbc implements DataProvider {

    private static final Logger log = LogManager.getLogger(DataProviderJdbc.class);
    private Connection connection;

    /**
     * Открыть соединение с базой данных
     */
    public DataProviderJdbc() {
        try {
            getConnection();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Соединение и возможность переключения платформы БД через конфигурационный файл
     * По умолчанию выбрана база данных Heroku
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public void getConnection() throws ClassNotFoundException, SQLException, IOException {
        try {
            if (getConfigurationEntry(DB_Heroku_USED).equals(TRUE)) {
                Class.forName(getConfigurationEntry(DB_Heroku_DRIVER));
                connection = DriverManager.getConnection(getConfigurationEntry(DB_Heroku_URL), getConfigurationEntry(DB_Heroku_USER), getConfigurationEntry(DB_Heroku_PASS));
                log.info(DB_SUCCESS + HEROKU);
            }else if(getConfigurationEntry(DB_PostgreSQL_USED).equals(TRUE)){
                Class.forName(getConfigurationEntry(DB_PostgreSQL_DRIVER));
                connection = DriverManager.getConnection(getConfigurationEntry(DB_PostgreSQL_URL), getConfigurationEntry(DB_PostgreSQL_USER), getConfigurationEntry(DB_PostgreSQL_PASS));
                log.info(DB_SUCCESS + POSTGRESQL);
            }else {
                log.info(NOT_SELECTED);
                System.exit(1);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.info(DB_UNABLE_CONNECT);
            log.fatal(e);
            System.exit(1);
        }

    }

    /**
     * Создание объекта для выполнения SQL и возврата полученных результатов
     * @return connection.createStatement()
     * @throws SQLException
     */
    private Statement createStatement() throws SQLException {
        if (!connection.isClosed()) {
            return connection.createStatement();
        } else {
            throw new SQLException();
        }
    }

    /**
     * Выполнить sql запрос
     *
     * @param sql значение sql запроса
     */
    public void execute(String sql) {
        try {
            log.debug(sql);
            Statement statement = createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            log.error(e);
        }
    }

    /**
     * Удалить все таблицы из базы данных
     */
    public void deleteAll() {
        try {
            log.info(LOAD_DROP);
            execute(DROP_TABLES);
            log.info(LOAD_DROP_COMPLETE);
        }catch (NullPointerException e) {
            log.error(e);
        }
    }

    /**
     * Создание таблиц в базе данных
     */
    public void createTable() {
        try {
            log.info(LOAD_CREATE);
            execute(CREATE_SERVICE);
            execute(CREATE_NEW_CUSTOMER);
            execute(CREATE_REGULAR_CUSTOMER);
            execute(CREATE_ORDER_ITEM);
            execute(CREATE_MASTER);
            execute(CREATE_LIST_SERVICE);
            execute(CREATE_SALON);
            execute(CREATE_LIST_MASTER);
            execute(CREATE_ORDER);
            execute(CREATE_LIST_ITEM);
            log.info(LOAD_CREATE_COMPLETE);
        }catch (NullPointerException e) {
            log.error(e);
        }
    }

    @Override
    public boolean createService(String name, Double price, String description)  {
        try {
            log.info(LOAD_CREATE_SERVICE);
            if (name == null || price == null || description == null){
                log.info(NULL_VALUE);
                log.info(SERVICE_NOT_CREATED);
                log.info(LOAD_CREATE_SERVICE_COMPLETE);
                return false;
            }else {
                Service service = new Service();
                service.setName(name);
                service.setPrice(price);
                service.setDescription(description);
                log.info(SERVICE_CREATED);
                log.debug(service);
                execute(String.format(DB_INSERT, service.getClass().getSimpleName().toLowerCase(), SERVICE_FIELDS,
                        String.format(SERVICE_INSERT_FORMAT,service.getName(), service.getPrice(), service.getDescription())));
                log.info(LOAD_CREATE_SERVICE_COMPLETE);
                return true;
            }
        }catch (NullPointerException e){
            log.error(e);
            return false;
        }
    }


    @Override
    public boolean editService(long id, String name, Double price, String description)  {
        try {
            log.info(LOAD_EDIT_SERVICE);
            if (getServiceById(id) == null){
                log.info(SERVICE_ID + id + NOT_FOUND);
                log.info(SERVICE_NOT_EDITED);
                log.info(LOAD_EDIT_SERVICE_COMPLETE);
                return false;
            }
            Service service = new Service();
            service.setId(id);
            service.setName(name);
            service.setPrice(price);
            service.setDescription(description);
            log.info(SERVICE_EDITED);
            log.debug(service);
            execute(String.format(DB_UPDATE_SERVICE, service.getName(), service.getPrice(), service.getDescription(), id));
            log.info(LOAD_EDIT_SERVICE_COMPLETE);
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException | NullPointerException e) {
            log.error(e);
            return false;
        }    }

    @Override
    public boolean deleteService(long id)  {
        try {
            log.info(LOAD_DELETE_SERVICE);
            if(getServiceById(id) == null){
                log.info(SERVICE_ID + id + NOT_FOUND);
                log.info(SERVICE_NOT_DELETED);
                log.info(LOAD_DELETE_SERVICE_COMPLETE);
                return false;
            }
            execute(String.format(DB_DELETE, Service.class.getSimpleName().toLowerCase(), id));
            log.info(SERVICE_DELETED);
            log.info(LOAD_DELETE_SERVICE_COMPLETE);
            return true;
        }catch (NullPointerException e){
            log.error(e);
            return false;
        }
    }

    @Override
    public Service getServiceById(long id) {
        try {
            log.info(LOAD_GET_SERVICE);
            log.debug(String.format(DB_SELECT, Service.class.getSimpleName().toLowerCase(), id));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT, Service.class.getSimpleName().toLowerCase(), id));
            if (set != null && set.next()) {
                Service service = new Service();
                service.setId(set.getLong(ID));
                service.setName(set.getString(SERVICE_NAME));
                service.setPrice(set.getDouble(SERVICE_PRICE));
                service.setDescription(set.getString(SERVICE_DESCRIPTION));
                log.info(SERVICE_RECEIVED);
                log.debug(service);
                statement.close();
                log.info(LOAD_GET_SERVICE_COMPLETE);
                return service;
            } else {
                log.error(ERROR_GET_ID);
                log.info(SERVICE_NOT_RECEIVED);
                statement.close();
                log.info(LOAD_GET_SERVICE_COMPLETE);
                return null;
            }
        } catch (SQLException | NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createNewCustomer(String firstName, String lastName, String phone, String email, Integer discount)  {
        try {
            log.info(LOAD_CREATE_NEW_CUSTOMER);
            if (firstName == null || lastName == null || phone == null || email == null || discount == null){
                log.info(NULL_VALUE);
                log.info(NEW_CUSTOMER_NOT_CREATED);
                log.info(LOAD_CREATE_NEW_CUSTOMER_COMPLETE);
                return false;
            }else {
                NewCustomer customer = new NewCustomer();
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPhone(phone);
                customer.setEmail(email);
                customer.setDiscount(discount);
                log.info(NEW_CUSTOMER_CREATED);
                log.debug(customer);
                execute(String.format(DB_INSERT, customer.getClass().getSimpleName().toLowerCase(), NEW_CUSTOMER_FIELDS,
                        String.format(CUSTOMER_INSERT_FORMAT,customer.getFirstName(), customer.getLastName(), customer.getPhone(), customer.getEmail(), customer.getDiscount())));
                log.info(LOAD_CREATE_NEW_CUSTOMER_COMPLETE);
                return true;
            }
        }catch (NullPointerException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editNewCustomer(long id, String firstName, String lastName, String phone, String email, Integer discount)  {
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
            log.info(NEW_CUSTOMER_EDITED);
            log.debug(customer);
            execute(String.format(DB_UPDATE_NEW_CUSTOMER, customer.getFirstName(), customer.getLastName(), customer.getPhone(), customer.getEmail(), customer.getDiscount(), id));
            log.info(LOAD_EDIT_NEW_CUSTOMER_COMPLETE);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteNewCustomer(long id)  {
        try{
            log.info(LOAD_DELETE_NEW_CUSTOMER);
            if(!getNewCustomerById(id).isPresent()){
                log.info(NEW_CUSTOMER_ID + id + NOT_FOUND);
                log.info(NEW_CUSTOMER_NOT_DELETED);
                log.info(LOAD_DELETE_NEW_CUSTOMER_COMPLETE);
                return false;
            }
            execute(String.format(DB_DELETE, NewCustomer.class.getSimpleName().toLowerCase(), id));
            log.info(NEW_CUSTOMER_DELETED);
            log.info(LOAD_DELETE_NEW_CUSTOMER_COMPLETE);
            return true;
        }catch (NullPointerException e){
            log.error(e);
            return false;
        }
    }

    @Override
    public Optional<NewCustomer> getNewCustomerById(long id)  {
        try {
            log.info(LOAD_GET_NEW_CUSTOMER);
            log.debug(String.format(DB_SELECT, NewCustomer.class.getSimpleName().toLowerCase(), id));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT, NewCustomer.class.getSimpleName().toLowerCase(), id));
            if (set != null && set.next()) {
                NewCustomer customer = new NewCustomer();
                customer.setId(set.getLong(ID));
                customer.setFirstName(set.getString(CUSTOMER_FIRST_NAME));
                customer.setLastName(set.getString(CUSTOMER_LAST_NAME));
                customer.setPhone(set.getString(CUSTOMER_PHONE));
                customer.setEmail(set.getString(CUSTOMER_EMAIL));
                customer.setDiscount(set.getInt(NEW_CUSTOMER_DISCOUNT));
                log.info(NEW_CUSTOMER_RECEIVED);
                log.debug(customer);
                statement.close();
                log.info(LOAD_GET_NEW_CUSTOMER_COMPLETE);
                return Optional.of(customer);
            } else {
                log.error(ERROR_GET_ID);
                log.info(NEW_CUSTOMER_NOT_RECEIVED);
                statement.close();
                log.info(LOAD_GET_NEW_CUSTOMER_COMPLETE);
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    @Override
    public boolean createRegularCustomer(String firstName, String lastName, String phone, String email, Integer countOfOrder)  {
        try {
            log.info(LOAD_CREATE_REGULAR_CUSTOMER);
            if (firstName == null || lastName == null || phone == null || email == null || countOfOrder == null){
                log.info(NULL_VALUE);
                log.info(REGULAR_CUSTOMER_NOT_CREATED);
                log.info(LOAD_CREATE_REGULAR_CUSTOMER_COMPLETE);
                return false;
            }else {
                RegularCustomer customer = new RegularCustomer();
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPhone(phone);
                customer.setEmail(email);
                customer.setNumberOfOrders(countOfOrder);
                log.info(REGULAR_CUSTOMER_CREATED);
                log.debug(customer);
                execute(String.format(DB_INSERT, customer.getClass().getSimpleName().toLowerCase(), REGULAR_CUSTOMER_FIELDS,
                        String.format(CUSTOMER_INSERT_FORMAT,customer.getFirstName(), customer.getLastName(), customer.getPhone(), customer.getEmail(), customer.getNumberOfOrders())));
                log.info(LOAD_CREATE_REGULAR_CUSTOMER_COMPLETE);
                return true;
            }
        }catch (NullPointerException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editRegularCustomer(long id, String firstName, String lastName, String phone, String email, Integer countOfOrder)  {
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
            log.info(REGULAR_CUSTOMER_EDITED);
            log.debug(customer);
            execute(String.format(DB_UPDATE_REGULAR_CUSTOMER, customer.getFirstName(), customer.getLastName(), customer.getPhone(), customer.getEmail(), customer.getNumberOfOrders(), id));
            log.info(LOAD_EDIT_REGULAR_CUSTOMER_COMPLETE);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteRegularCustomer(long id)  {
        try{
            log.info(LOAD_DELETE_REGULAR_CUSTOMER);
            if (!getRegularCustomerById(id).isPresent()){
                log.info(REGULAR_CUSTOMER_ID + id + NOT_FOUND);
                log.info(REGULAR_CUSTOMER_NOT_DELETED);
                log.info(LOAD_DELETE_REGULAR_CUSTOMER_COMPLETE);
                return false;
            }
            execute(String.format(DB_DELETE, RegularCustomer.class.getSimpleName().toLowerCase(), id));
            log.info(REGULAR_CUSTOMER_DELETED);
            log.info(LOAD_DELETE_REGULAR_CUSTOMER_COMPLETE);
            return true;
        }catch (NullPointerException e){
            log.error(e);
            return false;
        }
    }

    @Override
    public Optional<RegularCustomer> getRegularCustomerById(long id)  {
        try {
            log.info(LOAD_GET_REGULAR_CUSTOMER);
            log.debug(String.format(DB_SELECT, RegularCustomer.class.getSimpleName().toLowerCase(), id));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT, RegularCustomer.class.getSimpleName().toLowerCase(), id));
            if (set != null && set.next()) {
                RegularCustomer customer = new RegularCustomer();
                customer.setId(set.getLong(ID));
                customer.setFirstName(set.getString(CUSTOMER_FIRST_NAME));
                customer.setLastName(set.getString(CUSTOMER_LAST_NAME));
                customer.setPhone(set.getString(CUSTOMER_PHONE));
                customer.setEmail(set.getString(CUSTOMER_EMAIL));
                customer.setNumberOfOrders(set.getInt(REGULAR_CUSTOMER_NUMBER));
                log.info(REGULAR_CUSTOMER_RECEIVED);
                log.debug(customer);
                statement.close();
                log.info(LOAD_GET_REGULAR_CUSTOMER_COMPLETE);
                return Optional.of(customer);
            } else {
                log.error(ERROR_GET_ID);
                log.info(REGULAR_CUSTOMER_NOT_RECEIVED);
                statement.close();
                log.info(LOAD_GET_REGULAR_CUSTOMER_COMPLETE);
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error(e);
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
                master.setFirstName(firstName);
                master.setLastName(lastName);
                master.setPosition(position);
                master.setListService(listService);
                master.setPhone(phone);
                master.setSalary(salary);
                log.info(MASTER_CREATED);
                log.debug(master);
                execute(String.format(DB_INSERT, master.getClass().getSimpleName().toLowerCase(), MASTER_FIELDS,
                        String.format(MASTER_INSERT_FORMAT,master.getFirstName(), master.getLastName(), master.getPosition(), master.getPhone(), master.getSalary().toString())));
                log.info(LOAD_CREATE_MASTER_COMPLETE);
                return true;
            }
        }catch (NullPointerException e) {
            log.error(e);
            return false;
        }
    }

    public void createListService(long idMaster, long idService) {
        try{
            log.info(LOAD_CREATE_LIST_MASTER);
            if(getMasterById(idMaster) != null && getServiceById(idService) != null) {
                log.info(LIST_ADD + MASTER_LIST_SERVICE);
                execute(String.format(DB_INSERT, MASTER_LIST_SERVICE, LIST_SERVICE_FIELDS,
                        String.format(LIST_FORMAT, idMaster, idService)));
                log.info(LOAD_CREATE_LIST_SERVICE_COMPLETE);
            }else{
                log.info(LIST_NOT_ADD + MASTER_LIST_SERVICE);
                log.info(LOAD_CREATE_LIST_SERVICE_COMPLETE);
            }
        }catch (NullPointerException e){
            log.info(LIST_NOT_ADD + MASTER_LIST_SERVICE);
            log.error(e);
        }
    }

    @Override
    public boolean editMaster(long id, String firstName, String lastName, String position, String phone, Double salary, List<Service> listService, boolean needEditMaster) {
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
            log.info(MASTER_EDITED);
            log.debug(master);
            execute(String.format(DB_UPDATE_MASTER, master.getFirstName(), master.getLastName(), master.getPosition(), master.getPhone(), master.getSalary(), id));
            log.info(LOAD_EDIT_MASTER_COMPLETE);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.error(e);
            return false;
        }
    }

    public boolean editListService(long idMaster, long idService) {
        try {
            log.info(LOAD_EDIT_LIST_MASTER);
            if (getMasterById(idMaster) != null && getServiceById(idService) != null) {
                execute(String.format(DB_UPDATE_LIST_SERVICE, idService, idMaster));
                log.info(LIST_UPDATE + MASTER_LIST_SERVICE);
                log.info(LOAD_EDIT_LIST_SERVICE_COMPLETE);
                return true;
            }else {
                log.info(MASTER_ID + idMaster + NOT_FOUND);
                log.info(LIST_NOT_UPDATE + MASTER_LIST_SERVICE);
                log.info(LOAD_EDIT_LIST_SERVICE_COMPLETE);
                return false;
            }
        }catch (NullPointerException e){
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
            execute(String.format(DB_DELETE, Master.class.getSimpleName().toLowerCase(), id));
            log.info(MASTER_DELETED);
            log.info(LOAD_DELETE_MASTER_COMPLETE);
            return true;
        }catch (NullPointerException e){
            log.error(e);
            return false;
        }
    }

    public boolean deleteListService(long idMaster) {
        try{
            log.info(LOAD_DELETE_LIST_MASTER);
            if (getListServiceById(idMaster) == null || getMasterById(idMaster) == null){
                log.info(SERVICE_LIST + idMaster + NOT_FOUND);
                log.info(LIST_NOT_DELETE + MASTER_LIST_SERVICE);
                log.info(LOAD_DELETE_LIST_SERVICE_COMPLETE);
                return false;
            }
            execute(String.format(DB_DELETE_LIST_SERVICE, MASTER_LIST_SERVICE, idMaster));
            log.info(LIST_DELETE + MASTER_LIST_SERVICE);
            log.info(LOAD_DELETE_LIST_SERVICE_COMPLETE);
            return true;
        }catch (NullPointerException e){
            log.error(e);
            return false;
        }
    }

    @Override
    public Master getMasterById(long id) {
        try {
            log.info(LOAD_GET_MASTER);
            log.debug(String.format(DB_SELECT, Master.class.getSimpleName().toLowerCase(), id));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT, Master.class.getSimpleName().toLowerCase(), id));
            if (set != null && set.next()) {
                Master master = new Master();
                master.setId(set.getLong(ID));
                master.setFirstName(set.getString(MASTER_FIRST_NAME));
                master.setLastName(set.getString(MASTER_LAST_NAME));
                master.setPosition(set.getString(MASTER_POSITION));
                master.setPhone(set.getString(MASTER_PHONE));
                master.setSalary(set.getDouble(MASTER_SALARY));
                log.info(MASTER_RECEIVED);
                log.debug(master);
                statement.close();
                log.info(LOAD_GET_MASTER_COMPLETE);
                return master;
            } else {
                log.info(MASTER_NOT_RECEIVED);
                log.error(ERROR_GET_ID);
                statement.close();
                log.info(LOAD_GET_MASTER_COMPLETE);
                return null;
            }
        } catch (SQLException | NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public List<Long> getListServiceById(long idMaster) {
        try {
            log.info(LOAD_GET_LIST_SERVICE);
            log.debug(String.format(DB_SELECT_LIST_SERVICE, MASTER_LIST_SERVICE, idMaster));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT_LIST_SERVICE, MASTER_LIST_SERVICE, idMaster));
            if (set != null ) {
                List<Long> list = new ArrayList<>();
                while(set.next()) {
                    list.add(set.getLong(ID_SERVICE));
                }
                log.info(LIST_GET + MASTER_LIST_SERVICE);
                log.debug(list);
                statement.close();
                log.info(LOAD_GET_LIST_SERVICE_COMPLETE);
                return list;
            } else {
                log.error(ERROR_GET_ID);
                log.info(LIST_NOT_GET + MASTER_LIST_SERVICE);
                statement.close();
                log.info(LOAD_GET_LIST_SERVICE_COMPLETE);
                return null;
            }
        } catch (SQLException | NullPointerException e) {
            log.error(e);
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
                salon.setAddress(address);
                salon.setListMaster(listMaster);
                log.info(SALON_CREATED);
                log.debug(salon);
                execute(String.format(DB_INSERT, salon.getClass().getSimpleName().toLowerCase(), SALON_FIELDS,
                        String.format(SALON_INSERT_FORMAT,salon.getAddress())));
                log.info(LOAD_CREATE_SALON_COMPLETE);
                return true;
            }
        }catch (NullPointerException e) {
            log.error(e);
            return false;
        }
    }

    public void createListMaster(long idSalon, long idMaster) {
        try{
            log.info(LOAD_CREATE_LIST_MASTER);
            if(getMasterById(idMaster) != null && getServiceById(idSalon) != null) {
                log.info(LIST_ADD + SALON_LIST_MASTER);
                execute(String.format(DB_INSERT, SALON_LIST_MASTER, LIST_MASTER_FIELDS,
                        String.format(LIST_FORMAT,idSalon,idMaster)));
                log.info(LOAD_CREATE_LIST_MASTER_COMPLETE);
            }else{
                log.info(LIST_NOT_ADD + SALON_LIST_MASTER);
                log.info(LOAD_CREATE_LIST_MASTER_COMPLETE);
            }
        }catch (NullPointerException e){
            log.info(LIST_NOT_ADD + SALON_LIST_MASTER);
            log.error(e);
        }
    }

    @Override
    public boolean editSalon(long id, String address, List<Master> listMaster) {
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
            log.info(SALON_EDITED);
            log.debug(salon);
            execute(String.format(DB_UPDATE_SALON, salon.getAddress(), id));
            log.info(LOAD_EDIT_SALON_COMPLETE);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.error(e);
            return false;
        }
    }

    public boolean editListMaster(long idSalon, long idMaster) {
        try {
            log.info(LOAD_EDIT_LIST_MASTER);
            if (getMasterById(idSalon) != null && getMasterById(idMaster) != null) {
                execute(String.format(DB_UPDATE_LIST_MASTER, idMaster, idSalon));
                log.info(LIST_UPDATE + SALON_LIST_MASTER);
                log.info(LOAD_EDIT_LIST_MASTER_COMPLETE);
                return true;
            }else {
                log.info(SALON_ID + idSalon + NOT_FOUND);
                log.info(LIST_NOT_UPDATE + SALON_LIST_MASTER);
                log.info(LOAD_EDIT_LIST_MASTER_COMPLETE);
                return false;
            }
        }catch (NullPointerException e){
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
            execute(String.format(DB_DELETE, Salon.class.getSimpleName().toLowerCase(), id));
            log.info(SALON_DELETED);
            log.info(LOAD_DELETE_SALON_COMPLETE);
            return true;
        }catch (NullPointerException e){
            log.error(e);
            return false;
        }
    }

    public boolean deleteListMaster(long idSalon) {
        try{
            log.info(LOAD_DELETE_LIST_MASTER);
            if (getListServiceById(idSalon) == null || getSalonById(idSalon) == null){
                log.info(MASTER_LIST + idSalon + NOT_FOUND);
                log.info(LIST_NOT_DELETE + SALON_LIST_MASTER);
                log.info(LOAD_DELETE_LIST_MASTER_COMPLETE);
                return false;
            }
            execute(String.format(DB_DELETE_LIST_MASTER, SALON_LIST_MASTER, idSalon));
            log.info(LIST_DELETE + SALON_LIST_MASTER);
            log.info(LOAD_DELETE_LIST_MASTER_COMPLETE);
            return true;
        }catch (NullPointerException  e){
            log.error(e);
            return false;
        }
    }

    @Override
    public Salon getSalonById(long id) {
        try {
            log.info(LOAD_GET_SALON);
            log.debug(String.format(DB_SELECT, Salon.class.getSimpleName().toLowerCase(), id));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT, Salon.class.getSimpleName().toLowerCase(), id));
            if (set != null && set.next()) {
                Salon salon = new Salon();
                salon.setId(set.getLong(ID));
                salon.setAddress(set.getString(SALON_ADDRESS));
                log.info(SALON_RECEIVED);
                log.debug(salon);
                statement.close();
                log.info(LOAD_GET_SALON_COMPLETE);
                return salon;
            } else {
                log.info(SALON_NOT_RECEIVED);
                log.error(ERROR_GET_ID);
                statement.close();
                log.info(LOAD_GET_SALON_COMPLETE);
                return null;
            }
        } catch (NoSuchElementException | NullPointerException | SQLException e) {
            log.error(e);
            return null;
        }
    }

    public List<Long> getListMasterById(long idSalon) {
        try {
            log.info(LOAD_GET_LIST_MASTER);
            log.debug(String.format(DB_SELECT_LIST_MASTER, SALON_LIST_MASTER, idSalon));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT_LIST_MASTER, SALON_LIST_MASTER, idSalon));
            if (set != null ) {
                List<Long> list = new ArrayList<>();
                while(set.next()) {
                    list.add(set.getLong(ID_MASTER));
                }
                log.info(LIST_GET + SALON_LIST_MASTER);
                log.debug(list);
                statement.close();
                log.info(LOAD_GET_LIST_MASTER_COMPLETE);
                return list;
            } else {
                log.error(ERROR_GET_ID);
                log.info(LIST_NOT_GET + SALON_LIST_MASTER);
                statement.close();
                log.info(LOAD_GET_LIST_MASTER_COMPLETE);
                return null;
            }
        } catch (SQLException | NullPointerException e) {
            log.error(e);
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
                orderItem.setService(service);
                orderItem.setCost(service.getPrice());
                orderItem.setQuantity(quantity);
                log.info(ORDER_ITEM_CREATED);
                log.debug(orderItem);
                log.debug(orderItem.getService().getId());
                execute(String.format(DB_INSERT, orderItem.getClass().getSimpleName().toLowerCase(), ORDER_ITEM_FIELDS,
                        String.format(ORDER_ITEM_INSERT_FORMAT,orderItem.getService().getId(), orderItem.getCost(), orderItem.getQuantity())));
                log.info(LOAD_CREATE_ITEM_COMPLETE);
                return true;
            }
        }catch (NullPointerException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editOrderItem(long id, Service service, Double cost, Integer quantity) {
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
            log.info(ORDER_ITEM_EDITED);
            log.debug(orderItem);
            execute(String.format(DB_UPDATE_ORDER_ITEM, orderItem.getService().getId(), orderItem.getCost(), orderItem.getQuantity(), id));
            log.info(LOAD_EDIT_ITEM_COMPLETE);
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException | NullPointerException e) {
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
            execute(String.format(DB_DELETE, OrderItem.class.getSimpleName().toLowerCase(), id));
            log.info(ORDER_ITEM_DELETED);
            log.info(LOAD_DELETE_ITEM_COMPLETE);
            return true;
        }catch (NullPointerException e){
            log.error(e);
            return false;
        }
    }

    @Override
    public OrderItem getOrderItemById(long id) {
        try {
            log.info(LOAD_GET_ITEM);
            log.debug(String.format(DB_SELECT, OrderItem.class.getSimpleName().toLowerCase(), id));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT, OrderItem.class.getSimpleName().toLowerCase(), id));
            if (set != null && set.next()) {
                long idService = set.getLong(ORDER_ITEM_SERVICE);
                List<Service> listService = new ArrayList<>();
                listService.add(getServiceById(idService));
                Service service = listService.stream().filter(el->el.getId()==idService).findFirst().get();
                OrderItem orderItem = new OrderItem();
                orderItem.setId(set.getLong(ID));
                orderItem.setService(service);
                orderItem.setCost(set.getDouble(ORDER_ITEM_COST));
                orderItem.setQuantity(set.getInt(ORDER_ITEM_QUANTITY));
                log.info(ORDER_ITEM_RECEIVED);
                log.debug(orderItem);
                statement.close();
                log.info(LOAD_GET_ITEM_COMPLETE);
                return orderItem;
            } else {
                log.info(ORDER_ITEM_NOT_RECEIVED);
                log.error(ERROR_GET_ID);
                statement.close();
                log.info(LOAD_GET_ITEM_COMPLETE);
                return null;
            }
        } catch (NoSuchElementException | NullPointerException | SQLException e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean createOrder(String created, List<OrderItem> item, Double cost, String status, long customerId, String lastUpdated, String completed) {
        try{
            log.info(LOAD_CREATE_ORDER);
            if (created == null || item == null || cost == null || status == null){
                log.info(NULL_VALUE);
                log.info(ORDER_NOT_CREATED);
                log.info(LOAD_CREATE_ORDER_COMPLETE);
                return false;
            }else {
                Order order = new Order();
                order.setCreated(created);
                order.setItem(item);
                order.setCost(cost);
                order.setStatus(status);
                order.setCustomerId(customerId);
                order.setLastUpdated(lastUpdated);
                order.setCompleted(completed);
                log.info(ORDER_CREATE);
                log.debug(order);
                execute(String.format(DB_INSERT_ORDER, ORDER_FIELDS,
                        String.format(ORDER_INSERT_FORMAT,order.getCreated(), order.getCost(), order.getStatus(), order.getCustomerId(), order.getLastUpdated(), order.getCompleted())));
                log.info(LOAD_CREATE_ORDER_COMPLETE);
                return true;
            }
        }catch (NullPointerException e) {
            log.error(e);
            return false;
        }
    }

    public void createListItem(long idOrder, long idItem) {
        try{
            log.info(LOAD_CREATE_LIST_ITEM);
            if(getOrderById(idOrder) != null && getOrderById(idItem) != null) {
                log.info(LIST_ADD + ORDER_ITEM);
                execute(String.format(DB_INSERT, ORDER_ITEM, LIST_ORDER_ITEM_FIELDS,
                        String.format(LIST_FORMAT,idOrder,idItem)));
                log.info(LOAD_CREATE_LIST_ITEM_COMPLETE);
            }
        }catch (NullPointerException e){
            log.info(LIST_NOT_ADD + ORDER_ITEM);
            log.error(e);
            log.info(LOAD_CREATE_LIST_ITEM_COMPLETE);
        }
    }

    @Override
    public boolean editOrder(long id, String created, List<OrderItem> item, Double cost, String status, long customerId, String lastUpdated, String completed) {
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
            order.setCost(cost);
            order.setStatus(status);
            order.setCustomerId(customerId);
            order.setLastUpdated(lastUpdated);
            order.setCompleted(completed);
            log.info(ORDER_EDITED);
            log.debug(order);
            execute(String.format(DB_UPDATE_ORDER, order.getCreated(), order.getCost(), order.getStatus(), order.getCustomerId(), order.getLastUpdated(), order.getCompleted(), id));
            log.info(LOAD_EDIT_ORDER_COMPLETE);
            return true;
        } catch (NullPointerException | NoSuchElementException | IndexOutOfBoundsException e) {
            log.error(e);
            return false;
        }
    }

    public boolean editListItem(long idOrder, long idItem) {
        try {
            log.info(LOAD_EDIT_LIST_ITEM);
            if (getOrderById(idOrder) != null && getOrderItemById(idItem) != null) {
                execute(String.format(DB_UPDATE_LIST_ORDER_ITEM, idItem, idOrder));
                log.info(LIST_UPDATE + ORDER_ITEM);
                log.info(LOAD_EDIT_LIST_ITEM_COMPLETE);
                return true;
            }else {
                log.info(ORDER_ID + idOrder + NOT_FOUND);
                log.info(LIST_NOT_UPDATE + ORDER_ITEM);
                log.info(LOAD_EDIT_LIST_ITEM_COMPLETE);
                return false;
            }
        }catch (NullPointerException e){
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
            execute(String.format(DB_DELETE_ORDER, id));
            log.info(ORDER_DELETED);
            log.info(LOAD_DELETE_ORDER_COMPLETE);
            return true;
        }catch (NullPointerException e){
            log.error(e);
            return false;
        }
    }

    public boolean deleteListItem(long idOrder) {
        try{
            log.info(LOAD_DELETE_LIST_ITEM);
            if (getListItemById(idOrder) == null || getOrderById(idOrder) == null){
                log.info(ORDER_ITEM_LIST + idOrder + NOT_FOUND);
                log.info(LIST_NOT_DELETE + ORDER_ITEM);
                log.info(LOAD_DELETE_LIST_ITEM_COMPLETE);
                return false;
            }
            execute(String.format(DB_DELETE_LIST_ORDER_ITEM, ORDER_ITEM, idOrder));
            log.info(LIST_DELETE + ORDER_ITEM);
            log.info(LOAD_DELETE_LIST_ITEM_COMPLETE);
            return true;
        }catch (NullPointerException e){
            log.error(e);
            return false;
        }
    }

    @Override
    public Order getOrderById(long id) {
        try {
            log.info(LOAD_GET_ORDER);
            log.debug(String.format(DB_SELECT_ORDER, id));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT_ORDER, id));
            if (set != null && set.next()) {
                Order order = new Order();
                order.setId(set.getLong(ID));
                order.setCreated(set.getString(ORDER_CREATED));
                order.setCost(set.getDouble(ORDER_COST));
                order.setStatus(set.getString(ORDER_STATUS));
                order.setCustomerId(set.getLong(ORDER_CUSTOMER));
                order.setLastUpdated(set.getString(ORDER_UPDATE));
                order.setCompleted(set.getString(ORDER_COMPLETED));
                log.info(ORDER_RECEIVED);
                log.debug(order);
                statement.close();
                log.info(LOAD_GET_ORDER_COMPLETE);
                return order;
            } else {
                log.info(ORDER_NOT_RECEIVED);
                log.error(ERROR_GET_ID);
                statement.close();
                log.info(LOAD_GET_ORDER_COMPLETE);
                return null;
            }
        } catch (NoSuchElementException | NullPointerException | SQLException e) {
            log.error(e);
            return null;
        }
    }

    public List<Long> getListItemById(long idOrder) {
        try {
            log.info(LOAD_GET_LIST_ITEM);
            log.debug(String.format(DB_SELECT_LIST_ORDER_ITEM, ORDER_ITEM, idOrder));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT_LIST_ORDER_ITEM, ORDER_ITEM, idOrder));
            if (set != null ) {
                List<Long> list = new ArrayList<>();
                while(set.next()) {
                    list.add(set.getLong(ID_ITEM));
                }
                log.info(LIST_GET + ORDER_ITEM);
                log.debug(list);
                statement.close();
                log.info(LOAD_GET_LIST_ITEM_COMPLETE);
                return list;
            } else {
                log.error(ERROR_GET_ID);
                log.info(LIST_NOT_GET + SALON_LIST_MASTER);
                statement.close();
                log.info(LOAD_GET_LIST_ITEM_COMPLETE);
                return null;
            }
        } catch (SQLException | NullPointerException e) {
            log.error(e);
            return null;
        }
    }


    @Override
    public Double calculateOrderValue(long orderId) {
        try{
            log.info(LOAD_CALCULATE);
            List<Long> listItemById = getListItemById(orderId);
            List<OrderItem> orderItemList = new ArrayList<>();
            for (long i=listItemById.get(0); i<=listItemById.size(); i++){
                orderItemList.add(getOrderItemById(i));
            }
            List<Double> price = orderItemList.stream()
                    .map(value -> value.getCost()*value.getQuantity())
                    .collect(Collectors.toList());
            Double cost = price.stream().map(value -> value.doubleValue())
                    .filter(a -> a != null).mapToDouble(a -> a).sum();
            log.info(COST + orderId + EQL + cost);
            log.info(LOAD_CALCULATE_COMPLETE);
            return cost;
        } catch (NoSuchElementException | NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public List<Order> viewOrderHistory(long customerId) {
        try {
            log.info(LOAD_VIEW_ORDER);
            List<Order> orderList = new ArrayList<>();
            log.debug(String.format(DB_SELECT_ORDER_HISTORY, customerId));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT_ORDER_HISTORY, customerId));
            if (set != null) {
                while(set.next()) {
                    Order order = new Order();
                    order.setId(set.getLong(ID));
                    order.setCreated(set.getString(ORDER_CREATED));
                    order.setCost(set.getDouble(ORDER_COST));
                    order.setStatus(set.getString(ORDER_STATUS));
                    order.setCustomerId(set.getLong(ORDER_CUSTOMER));
                    order.setLastUpdated(set.getString(ORDER_UPDATE));
                    order.setCompleted(set.getString(ORDER_COMPLETED));
                    orderList.add(order);
                }
                log.info(ORDER_RECEIVED);
                log.debug(orderList);
                statement.close();
                log.info(LOAD_VIEW_ORDER_COMPLETE);
                return orderList;
            } else {
                log.error(ERROR_GET_ID);
                log.info(ORDER_NOT_RECEIVED);
                statement.close();
                log.info(LOAD_VIEW_ORDER_COMPLETE);
                return null;
            }
        }catch (NullPointerException | NoSuchElementException | SQLException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public List<Order> getListOfCurrentOrders(long customerId, String status) {
        try {
            log.info(LOAD_GET_ORDER_LIST);
            List<Order> orderList = new ArrayList<>();
            log.debug(String.format(DB_SELECT_ORDER_HISTORY, customerId));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT_ORDER_HISTORY, customerId));
            if (set != null && status.equals(PROCESSING)) {
                while(set.next()) {
                    Order order = new Order();
                    order.setId(set.getLong(ID));
                    order.setCreated(set.getString(ORDER_CREATED));
                    order.setCost(set.getDouble(ORDER_COST));
                    order.setStatus(set.getString(ORDER_STATUS));
                    order.setCustomerId(set.getLong(ORDER_CUSTOMER));
                    order.setLastUpdated(set.getString(ORDER_UPDATE));
                    order.setCompleted(set.getString(ORDER_COMPLETED));
                    orderList.add(order);
                }
                log.info(ORDER_RECEIVED);
                log.debug(orderList);
                statement.close();
                log.info(LOAD_GET_ORDER_LIST_COMPLETE);
                return orderList;
            } else {
                log.info(NOT_CURRENT_ORDER);
                log.info(ORDER_NOT_RECEIVED);
                statement.close();
                log.info(LOAD_GET_ORDER_LIST_COMPLETE);
                return null;
            }
        }catch (NullPointerException | NoSuchElementException | SQLException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public StringBuffer createCustomerReport(long customerId) {
        try {
            log.info(LOAD_CUSTOMER_REPORT);
            int count = 0;
            log.debug(String.format(String.format(DB_SELECT_ORDER_HISTORY, customerId)));
            Statement statement = createStatement();
            ResultSet set = statement.executeQuery(String.format(DB_SELECT_ORDER_HISTORY, customerId));
            if (set != null) {
                while(set.next()) {
                    count++;
                }
                log.debug(count);
                StringBuffer report = new StringBuffer();
                report.append(NUM_CUSTOMER)
                        .append(customerId)
                        .append(ORDER_EQL)
                        .append(count);
                statement.close();
                log.info(LOAD_CUSTOMER_REPORT_COMPLETE);
                return report;
            } else {
                log.error(ERROR_GET_ID);
                statement.close();
                log.info(LOAD_CUSTOMER_REPORT_COMPLETE);
                return null;
            }
        }catch (NullPointerException | NoSuchElementException | SQLException e){
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
            List<Long> listMasterById = getListMasterById(salonId);
            List<Master> masterList = new ArrayList<>();
            for (long i=listMasterById.get(0); i<=listMasterById.size(); i++){
                masterList.add(getMasterById(i));
            }
            log.info(LIST_MASTER + salonId + COLON);
            log.debug(masterList);
            log.info(LOAD_CHANGE_MASTER_COMPLETE);
            return masterList;
        }catch (NullPointerException | NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean assignService(List<Service> service, long masterId) {
        try{
            log.info(LOAD_ASSIGN_SERVICE);
            Master master = getMasterById(masterId);
            master.setListService(service);
            log.info(ASSIGN_SUCCESS);
            log.debug(master);
            log.info(LOAD_ASSIGN_SERVICE_COMPLETE);
            return true;
        } catch (NoSuchElementException | NullPointerException | IndexOutOfBoundsException e) {
            log.info(ASSIGN_FAIL);
            log.error(e);
            return false;
        }
    }

    public StringBuffer createMasterReport(long masterId , boolean needMasterReport) {
        try{
            log.info(LOAD_MASTER_REPORT);
            if (getMasterById(masterId) == null || !needMasterReport) {
                log.info(MASTER_ID + masterId + NOT_FOUND);
                log.info(LOAD_MASTER_REPORT_COMPLETE);
                return null;
            }
            List<Long> listServiceId = getListServiceById(masterId);
            List<Service> serviceList = new ArrayList<>();
            for (long i=listServiceId.get(0); i<=listServiceId.size(); i++){
                serviceList.add(getServiceById(i));
            }
            StringBuffer report = new StringBuffer();
            report.append(MASTER)
                    .append(masterId)
                    .append(PROVIDE_SERVICE)
                    .append(serviceList);
            log.info(LOAD_MASTER_REPORT_COMPLETE);
            return report;
        }catch (NullPointerException | NoSuchElementException e){
            log.error(e);
            return null;
        }
    }
}
