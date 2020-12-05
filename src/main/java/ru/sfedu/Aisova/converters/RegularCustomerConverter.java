package ru.sfedu.Aisova.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.Constants;
import ru.sfedu.Aisova.model.RegularCustomer;

public class RegularCustomerConverter extends AbstractBeanField<RegularCustomer> {
    private static final Logger log = LogManager.getLogger(RegularCustomer.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        if (s.isEmpty()) {
            return null;
        }
        String indexString = s.substring(1, s.length() - 1);
        RegularCustomer regularCustomer = new RegularCustomer();

        if (!indexString.isEmpty()) {

            regularCustomer.setId(Long.parseLong(indexString));

        }

        return regularCustomer;

    }



    @Override
    protected String convertToWrite(Object value) {
        RegularCustomer regularCustomer = (RegularCustomer) value;
        StringBuilder builder = new StringBuilder(Constants.PEOPLE_START_SYMBOL);
        if (regularCustomer != null) {

            builder.append(regularCustomer.getId());
            builder.append(Constants.SPLIT);

            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append(Constants.PEOPLE_END_SYMBOL);
        log.debug(builder.toString());
        return builder.toString();
    }


}
