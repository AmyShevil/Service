package ru.sfedu.Aisova.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.Aisova.TestBase;
import ru.sfedu.Aisova.model.Service;
import ru.sfedu.Aisova.model.User;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderJdbcTest extends TestBase {

    @Test
    void insertS() throws SQLException {
        DataProviderJdbc instance = new DataProviderJdbc();
        User user = createUser(1,"name");
        instance.insert(user);
        assertEquals(user, instance.getUserById(0));
    }

    @Test
    void insertF() throws SQLException {
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
        Service service = createService(0, "service1", 999.0, "description1");
        instance.insertService(service);
        assertEquals(service, instance.getServiceById(0));
    }
}