package ru.sfedu.Aisova.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.bean.User;
import ru.sfedu.Aisova.utils.ConfigurationUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class DataProviderCSV {

    private final String PATH = "csv_path";
    private final String FILE_EXTENSION = "csv";
    private static Logger log = LogManager.getLogger(DataProviderCSV.class);

    public void insertUser(List<User> listUser) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(PATH) + listUser.get(0).getClass().getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<User> beanToCsv = new StatefulBeanToCsvBuilder<User>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(listUser);
            csvWriter.close();
        }catch (IndexOutOfBoundsException e){
            log.error(e);
        }
    }

    public User getUserById(long id) throws IOException {
        List<User> userList = select(User.class);
        try {
            User user = userList.stream()
                    .filter(el->el.getId()==id)
                    .findFirst().get();
            return user;
        }catch (NoSuchElementException e){
            log.error(e);
            return null;
        }
    }

    public <T> List<T> select(Class cl) throws IOException {
        FileReader fileReader = new FileReader(ConfigurationUtil.getConfigurationEntry(PATH) + cl.getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION));
        CSVReader csvReader = new CSVReader(fileReader);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                .withType(cl)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<T> list = csvToBean.parse();
        return list;
    }
}
