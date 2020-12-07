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
    }

    public static void logBasicSystemInfo() throws IOException {
        log.info("Launching the application...");
        log.info(
                "Operating System: " + System.getProperty("os.name") + " "
                        + System.getProperty("os.version")
        );
        log.info("JRE: " + System.getProperty("java.version"));
        log.info("Java Launched From: " + System.getProperty("java.home"));
        log.info("Class Path: " + System.getProperty("java.class.path"));
        log.info("Library Path: " + System.getProperty("java.library.path"));
        log.info("User Home Directory: " + System.getProperty("user.home"));
        log.info("User Working Directory: " + System.getProperty("user.dir"));
        log.info("Test INFO logging.");
    }
}
