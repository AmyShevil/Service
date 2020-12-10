package ru.sfedu.Aisova.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.Constants;
import ru.sfedu.Aisova.model.NewCustomer;
import ru.sfedu.Aisova.model.Service;

public class NewCustomerConverter extends AbstractBeanField<NewCustomer> {
    private static final Logger log = LogManager.getLogger(NewCustomerConverter.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        if (s.isEmpty()) {
            return null;
        }
        String indexString = s.substring(1, s.length() - 1);
        NewCustomer newCustomer = new NewCustomer();

        if (!indexString.isEmpty()) {
            newCustomer.setId(Long.parseLong(indexString));
        }
        return newCustomer;
    }


    @Override
    protected String convertToWrite(Object value) {
        NewCustomer newCustomer = (NewCustomer) value;
        StringBuilder builder = new StringBuilder(Constants.PEOPLE_START_SYMBOL);
        if (newCustomer != null) {

            builder.append(newCustomer.getId());
            builder.append(Constants.SPLIT);

            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append(Constants.PEOPLE_END_SYMBOL);
        log.debug(builder.toString());
        return builder.toString();
    }


}
