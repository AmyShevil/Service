package ru.sfedu.Aisova.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.Constants;
import ru.sfedu.Aisova.model.Master;
import ru.sfedu.Aisova.model.Service;
import ru.sfedu.Aisova.model.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ru.sfedu.Aisova.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderJdbc {

    private static final Logger log = LogManager.getLogger(DataProviderJdbc.class);
    private static final String DB_INSERT = "INSERT INTO \"%s\" (%s) VALUES (%s)";
    private static final String DB_SELECT = "SELECT * FROM public.\"%s\" WHERE id=%d";
    public static final String ID ="id";
    public static final String SERVICE_NAME ="name";
    public static final String SERVICE_PRICE ="price";
    public static final String SERVICE_DESCRIPTION ="description";
    private static final String SERVICE_FIELDS=SERVICE_NAME+","+SERVICE_PRICE+","+SERVICE_DESCRIPTION;
    private static final String SERVICE_INSERT_FORMAT="'%s','%s','%s'";
    public static final String MASTER_FIRST_NAME ="firstName";
    public static final String MASTER_LAST_NAME ="lastName";
    public static final String MASTER_POSITION ="position";
    public static final String MASTER_PHONE ="phone";
    public static final String MASTER_SALARY ="salary";
    public static final String MASTER_LIST_SERVICE ="listService";
    private static final String MASTER_FIELDS=MASTER_FIRST_NAME+","+MASTER_LAST_NAME+","+MASTER_POSITION+","+MASTER_PHONE+","+MASTER_SALARY;
    private static final String MASTER_INSERT_FORMAT="'%s','%s','%s','%s','%s'";
    public static final String ID_SERVICE ="id_service";
    public static final String ID_MASTER ="id_master";
    private static final String LIST_SERVICE_FIELDS=ID_SERVICE+","+ID_MASTER;
    private static final String LIST_SERVICE_INSERT_FORMAT="'%s','%s'";


    private Connection connection;

    private Connection getConnection() throws ClassNotFoundException, SQLException, IOException {

        Class.forName(getConfigurationEntry(Constants.JDBC_DRIVER));
        return DriverManager.getConnection(
                getConfigurationEntry(Constants.DB_URL),
                getConfigurationEntry(Constants.DB_USER),
                getConfigurationEntry(Constants.DB_PASS)
        );
    }

    public void execute(String sql) {
        try {
            log.info(sql);
            //подготавливаем к тому что будем выполнять запрос sql
            PreparedStatement statement = getConnection().prepareStatement(sql);
            //выполняем запрос sql
            statement.executeUpdate();
            getConnection().close();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
    }

    private ResultSet getResultSet(String sql) {
        try {
            log.info(sql);
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            getConnection().close();
            log.debug("ResultSet = "+set);
            return set;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
            return null;
        }
    }

    public void insert(User user) {
        this.execute(String.format(DB_INSERT, user.getClass().getSimpleName().toLowerCase(), Constants.USER_FIELDS, "'"+user.getName()+"'"));
    }

    public User getUserById(long id) throws SQLException, IOException, ClassNotFoundException {
       // PreparedStatement statement = getConnection().prepareStatement(DB_SELECT);
        ResultSet set = getResultSet(String.format(DB_SELECT, User.class.getSimpleName().toLowerCase(), id));
        int col = set.getMetaData().getColumnCount();
        log.debug(col);
        log.debug(set);
        if (set != null && set.next()) {
            log.debug(set.getString(1));
            log.debug(set.getString(2));
            User user = new User();
            user.setId(set.getLong(ID));
            user.setName(set.getString(Constants.NAME));
            return user;
        } else {
            log.error("ERROR GET BY ID");
            return null;
        }
    }

    public void insertService(Service service) {
        this.execute(String.format(
                DB_INSERT,
                service.getClass().getSimpleName().toLowerCase(),
                SERVICE_FIELDS,
                String.format(
                        SERVICE_INSERT_FORMAT,service.getName(), service.getPrice().toString(), service.getDescription()
                )
        ));
    }

    public Service getServiceById(long id) {
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT, Service.class.getSimpleName().toLowerCase(), id));
            if (set != null && set.next()) {
                Service service = new Service();
                service.setId(set.getLong(ID));
                service.setName(set.getString(SERVICE_NAME));
                service.setPrice(set.getDouble(SERVICE_PRICE));
                service.setDescription(set.getString(SERVICE_DESCRIPTION));
                return service;
            } else {
                log.error("ERROR GET BY ID");
                return null;
            }
        } catch (SQLException e) {
            log.error(e);
            return null;
        }
    }

    public void insertMaster(Master master) {
        this.execute(String.format(
                DB_INSERT,
                master.getClass().getSimpleName().toLowerCase(),
                MASTER_FIELDS,
                String.format(
                        MASTER_INSERT_FORMAT,master.getFirstName(), master.getLastName(), master.getPosition(), master.getPhone(), master.getSalary().toString()
                )
        ));
    }

    public Master getMasterById(long id) {
        try {
            ResultSet set = getResultSet(String.format(DB_SELECT, Master.class.getSimpleName().toLowerCase(), id));
            if (set != null && set.next()) {
                Master master = new Master();
                master.setId(set.getLong(ID));
                master.setFirstName(set.getString(MASTER_FIRST_NAME));
                master.setLastName(set.getString(MASTER_LAST_NAME));
                master.setPosition(set.getString(MASTER_POSITION));
                master.setPhone(set.getString(MASTER_PHONE));
                master.setSalary(set.getDouble(MASTER_SALARY));
               // master.setListService(set.get(MASTER_LIST_SERVICE));

                return master;
            } else {
                log.error("ERROR GET BY ID");
                return null;
            }
        } catch (SQLException e) {
            log.error(e);
            return null;
        }
    }

    public void insertListService(long idService, long idMaster) {
        this.execute(String.format(
                DB_INSERT,
                "listService",
                LIST_SERVICE_FIELDS,
                String.format(
                        LIST_SERVICE_INSERT_FORMAT,idService,idMaster
                )
        ));
    }

    public List<Long> getListServiceById(long id) {
        try {
            ResultSet set = getResultSet(String.format("SELECT DISTINCT id_service FROM public.\"%s\" WHERE id_master=%d ORDER BY id_service", "listService", id));
            if (set != null ) {
                List<Long> list = new ArrayList<>();
                while(set.next()) {
                    list.add(set.getLong("id_service"));
                    //service.setId(set.getLong(ID));
                }
                return list;
            } else {
                log.error("ERROR GET BY ID");
                return null;
            }
        } catch (SQLException e) {
            log.error(e);
            return null;
        }
    }
}
