package ru.sfedu.Aisova.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.Aisova.Constants;
import ru.sfedu.Aisova.model.*;
import ru.sfedu.Aisova.utils.ConfigurationUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.Aisova.TestBase.createService;

public class DataProviderCsvTest {

    private static final Logger log = LogManager.getLogger(DataProviderCsvTest.class);
    private static final DataProvider dataProvider = new DataProviderCsv();

    private static <T> void deleteFile(Class<T> tClass) {
        try {
            log.debug(new File(ConfigurationUtil.getConfigurationEntry(Constants.PATH)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION)).delete());
        } catch (IOException e) {
            log.error(e);
        }
    }
    private static void deleteAll() {
        List<Class> classList = new ArrayList<>();
        classList.add(Master.class);
        classList.add(NewCustomer.class);
        classList.add(Order.class);
        classList.add(OrderItem.class);
        classList.add(RegularCustomer.class);
        classList.add(Salon.class);
        classList.add(Service.class);
        classList.forEach(DataProviderCsvTest::deleteFile);
    }

    @BeforeAll
    static void init(){
        deleteAll();
    }

    @Test
    void createServiceSuccess() throws IOException {

        Assertions.assertTrue(dataProvider.createService("service1", 1000.0, "description1"));
        Assertions.assertTrue(dataProvider.createService("service2", 2000.0, "description2"));
        Assertions.assertTrue(dataProvider.createService("service3", 3000.0, "description3"));

    }

    @Test
    void createServiceFail() throws IOException {

        Assertions.assertTrue(dataProvider.createService(null, 1000.0, "description1"));
        Assertions.assertTrue(dataProvider.createService("service2", null, "description2"));
        Assertions.assertTrue(dataProvider.createService("service3", 3000.0, null));

    }


}