package ru.sfedu.Aisova.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sfedu.Aisova.TestBase;
import ru.sfedu.Aisova.model.Master;
import ru.sfedu.Aisova.model.Service;
import ru.sfedu.Aisova.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderJdbcTest extends TestBase {

    private static final Logger log = LogManager.getLogger(DataProviderJdbcTest.class);


    @Test
    void insertS() throws SQLException, IOException, ClassNotFoundException {
        DataProviderJdbc instance = new DataProviderJdbc();
        User user = createUser(1,"name");
        instance.insert(user);
        User user1 = instance.getUserById(1);
        log.debug(user.getName());
        log.debug(instance.getUserById(1).toString());
        assertEquals(user.getId(), user1.getId());
        assertEquals(user.getName(), user1.getName());
    }

    @Test
    void insertF() throws SQLException, IOException, ClassNotFoundException {
        DataProviderJdbc instance = new DataProviderJdbc();
        User user = createUser(1,"name");
        instance.insert(user);
        assertNull(instance.getUserById(10));
    }

    @Test
    void getById() {
    }

    @Test
    void insertService() {
        DataProviderJdbc instance = new DataProviderJdbc();
        Service service = createService(1, "service1", 999.0, "description1");
        instance.insertService(service);
        assertEquals(service, instance.getServiceById(1));
    }

    @Test
    void insertMaster() {
        DataProviderJdbc instance = new DataProviderJdbc();
        Master master = createMaster(1, "FirstName1", "LastName1", "Position1", "Phone1", 10000.0);

        instance.insertMaster(master);
        assertEquals(master, instance.getMasterById(1));
    }

    @Test
    void insertListService() {
        DataProviderJdbc instance = new DataProviderJdbc();
        instance.insertListService(1,1);
        instance.insertListService(2,1);
        List<Long> list = new ArrayList<>();
        list.add((long) 1);
        list.add((long) 2);
        log.debug(list);
        log.debug(instance.getListServiceById(1));
        assertEquals(list, instance.getListServiceById(1));
    }
}