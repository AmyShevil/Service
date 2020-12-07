package ru.sfedu.Aisova.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.Constants;
import ru.sfedu.Aisova.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceConverter extends AbstractBeanField<Service> {
    private static final Logger log = LogManager.getLogger(ServiceConverter.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        if (s.isEmpty()) {
            return null;
        }
        String indexString = s.substring(1, s.length() - 1);
        Service service = new Service();

        if (!indexString.isEmpty()) {

            service.setId(Long.parseLong(indexString));

        }

        return service;

    }



    @Override
    protected String convertToWrite(Object value) {
        Service service = (Service) value;
        StringBuilder builder = new StringBuilder(Constants.PEOPLE_START_SYMBOL);
        if (service != null) {

            builder.append(service.getId());
            builder.append(Constants.SPLIT);

            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append(Constants.PEOPLE_END_SYMBOL);
        log.debug(builder.toString());
        return builder.toString();
    }


}
