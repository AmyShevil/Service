package ru.sfedu.Aisova.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.Aisova.TestBase;
import ru.sfedu.Aisova.model.Service;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderJdbcTest extends TestBase {

    @Test
    void insert() throws SQLException, IOException, ClassNotFoundException {
        DataProviderJdbc instance = new DataProviderJdbc();
        Service service = createService(0, "service1", 999.0, "description1");
        instance.insert(service);
        assertEquals(service, instance.getById(0));
    }

    @Test
    void getById() {
    }
}