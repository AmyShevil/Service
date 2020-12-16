package ru.sfedu.Aisova.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.Constants;
import ru.sfedu.Aisova.model.Service;
import ru.sfedu.Aisova.model.User;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Locale;

import static ru.sfedu.Aisova.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderJdbc {

    private static final Logger log = LogManager.getLogger(DataProviderJdbc.class);
    private static final String DB_INSERT = "INSERT INTO \"%s\" (%s) VALUES (%s)";
    private static final String DB_SELECT = "SELECT * FROM \"%s\" WHERE id=%d";
    public static final String ID ="id";
    public static final String SERVICE_NAME ="name";
    public static final String SERVICE_PRICE ="price";
    public static final String SERVICE_DESCRIPTION ="description";
    private static final String SERVICE_FIELDS=SERVICE_NAME+","+SERVICE_PRICE+","+SERVICE_DESCRIPTION;
    private static final String SERVICE_INSERT_FORMAT="'%s','%s','%s'";

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
            statement.close();
            return set;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
            return null;
        }
    }

    public void insert(User user) {
        this.execute(String.format(DB_INSERT, user.getClass().getSimpleName().toLowerCase(), Constants.USER_FIELDS, "'"+user.getName()+"'"));
    }

    public User getUserById(long id) throws SQLException {
        ResultSet set = getResultSet(String.format(DB_SELECT, User.class.getSimpleName().toLowerCase(), id));
        if (set != null && set.next()) {
            User user = new User();
            user.setId(set.getLong(ID));
            user.setName(set.getString(Constants.NAME));
            log.debug(user);
            return user;
        } else {
            log.error("ERROR GET BY ID");
            return null;
        }
    }

    public void insertService(Service service) {
        this.execute(String.format(
                Constants.INSERT_SERVICE,
                service.getClass().getSimpleName().toLowerCase(),
                SERVICE_FIELDS,
                String.format(
                        SERVICE_INSERT_FORMAT,service.getName(), service.getPrice(), service.getDescription()
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
}
