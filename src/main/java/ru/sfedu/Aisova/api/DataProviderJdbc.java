package ru.sfedu.Aisova.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.Constants;
import ru.sfedu.Aisova.model.Service;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Locale;

import static ru.sfedu.Aisova.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderJdbc {

    private static final Logger log = LogManager.getLogger(DataProviderJdbc.class);
    private static final String DB_INSERT ="INSERT INTO %s(%s) VALUES(%s)";
    private static final String DB_SELECT ="SELECT * FROM %s WHERE id=%d";

    private Connection connection() throws ClassNotFoundException, SQLException, IOException {

        Class.forName(getConfigurationEntry(Constants.JDBC_DRIVER));
        return DriverManager.getConnection(
                getConfigurationEntry(Constants.DB_URL),
                getConfigurationEntry(Constants.DB_USER),
                getConfigurationEntry(Constants.DB_PASS)
        );
    }

    public void execute(String sql){
        try {
            log.info(sql);
            //подготавливаем к тому что будем выполнять запрос sql
            PreparedStatement statement = connection().prepareStatement(sql);
            //выполняем запрос sql
            statement.executeUpdate();
            connection().close();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
    }

    private ResultSet getResultSet(String sql) throws SQLException, IOException, ClassNotFoundException {
        log.info(sql);
        PreparedStatement statement = connection().prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        statement.close();
        return set;
    }

    public void insert(Service service){
        this.execute(String.format(DB_INSERT, service.getClass().getSimpleName().toLowerCase(),service.getId(),service.getName(),service.getPrice(),service.getDescription()));

    }

    public Service getById(long id) throws SQLException, IOException, ClassNotFoundException {
        ResultSet set = getResultSet(String.format(DB_SELECT, Service.class.getSimpleName().toLowerCase(),id));
        if(set!=null && set.next()){
            Service service = new Service();
            service.setId(set.getLong(Constants.ID));
            service.setName(set.getString(Constants.NAME));
            service.setPrice(set.getDouble(Constants.PRICE));
            service.setDescription(set.getString(Constants.DESCRIPTION));
            return service;
        }else {
            log.error("ERROR GET BY ID");
            return null;
        }
    }

}
