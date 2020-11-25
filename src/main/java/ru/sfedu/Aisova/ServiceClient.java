package ru.sfedu.Aisova;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.api.DataProviderCSV;
import ru.sfedu.Aisova.bean.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.sfedu.Aisova.Constants.*;
import static ru.sfedu.Aisova.utils.ConfigurationUtil.getConfigurationEntry;

public class ServiceClient {

    private static Logger log = LogManager.getLogger(ServiceClient.class);

    public ServiceClient(){
        log.debug("LogClient: starting application.........");
    }

    public static void main(String[] args) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        new ServiceClient();
        logBasicSystemInfo();

        User user = new User();
        user.setId(1);
        user.setName("Amina");

        List<User> listUser = new ArrayList<>();
        listUser.add(user);

        DataProviderCSV providerCSV = new DataProviderCSV();
        providerCSV.insertUser(listUser);
    }

    public static void logBasicSystemInfo() throws IOException {

        log.trace("Trace_test");
        log.debug("Debug_test");
        log.info("Info_test");
        log.warn("Warn_test");
        log.error("Error_test");
        log.fatal("Fatal_test");
        log.info(getConfigurationEntry(ENV_CONST));
        log.info(String.format(TEXT_CONST, NUM_CONST_ONE));
    }
}
