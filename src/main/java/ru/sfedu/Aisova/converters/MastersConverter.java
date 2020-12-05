package ru.sfedu.Aisova.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Aisova.Constants;
import ru.sfedu.Aisova.model.Master;
import ru.sfedu.Aisova.model.Service;

import java.util.ArrayList;
import java.util.List;

public class MastersConverter extends AbstractBeanField<Master> {
    private static final Logger log = LogManager.getLogger(Master.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        if (s.isEmpty()) {
            return null;
        }
        String indexString = s.substring(1, s.length() - 1);
        String[] unparsedIndexList = indexString.split(Constants.SPLIT);
        List<Master> indexList = new ArrayList<>();
        for (String strIndex : unparsedIndexList) {

            if (!strIndex.isEmpty()) {
                Master master = new Master();
                master.setId(Long.parseLong(strIndex));
                indexList.add(master);
            }
        }
        return indexList;
    }

    @Override
    protected String convertToWrite(Object value) {
        List<Master> list = (List<Master>) value;
        StringBuilder builder = new StringBuilder(Constants.LIST_START_SYMBOL);
        if (list.size() != 0) {
            for (Master modificationRecord : list) {
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
