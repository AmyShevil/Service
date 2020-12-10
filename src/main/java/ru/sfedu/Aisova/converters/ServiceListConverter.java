package ru.sfedu.Aisova.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.Constants;
import ru.sfedu.Aisova.model.OrderItem;
import ru.sfedu.Aisova.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceListConverter extends AbstractBeanField<Service> {
    private static final Logger log = LogManager.getLogger(ServiceListConverter.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        if (s.isEmpty()) {
            return null;
        }
        String indexString = s.substring(1, s.length() - 1);
        String[] unparsedIndexList = indexString.split(Constants.SPLIT);
        List<Service> listService = new ArrayList<>();
        for (String strIndex : unparsedIndexList) {

            if (!strIndex.isEmpty()) {
                Service service = new Service();
                service.setId(Long.parseLong(strIndex));
                listService.add(service);
            }
        }
        return listService;
    }

    @Override
    protected String convertToWrite(Object value) {
        List<Service> listService = (List<Service>) value;
        StringBuilder builder = new StringBuilder(Constants.LIST_START_SYMBOL);
        if (listService.size() != 0) {
            for (Service modificationRecord : listService) {
                builder.append(modificationRecord.getId());
                builder.append(Constants.SPLIT);
            }
            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append(Constants.LIST_END_SYMBOL);
        log.debug(builder.toString());
        return builder.toString();
    }
}
