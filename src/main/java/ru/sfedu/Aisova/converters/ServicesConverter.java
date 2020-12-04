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

public class ServicesConverter extends AbstractBeanField<Service> {
    private static final Logger log = LogManager.getLogger(Service.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        if (s.isEmpty()) {
            return null;
        }
        String indexString = s.substring(1, s.length() - 1);
        String[] unparsedIndexList = indexString.split(Constants.ARRAY_SPLIT);
        List<Service> indexServiceList = new ArrayList<>();
        for (String strIndex : unparsedIndexList) {

            if (!strIndex.isEmpty()) {
                Service service = new Service();
                service.setId(Long.parseLong(strIndex));
                indexServiceList.add(service);
            }
        }
        return indexServiceList;
    }

    @Override
    protected String convertToWrite(Object value) {
        List<Service> serviceList = (List<Service>) value;
        StringBuilder builder = new StringBuilder(Constants.ARRAY_START_SYMBOL);
        if (serviceList.size() != 0) {
            for (Service modificationRecord : serviceList) {
                builder.append(modificationRecord.getId());
                builder.append(Constants.ARRAY_SPLIT);
            }
            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append(Constants.ARRAY_END_SYMBOL);
        log.debug(builder.toString());
        return builder.toString();
    }
}
