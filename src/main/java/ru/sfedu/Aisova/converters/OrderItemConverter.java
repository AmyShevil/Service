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

public class OrderItemConverter extends AbstractBeanField<OrderItem> {
    private static final Logger log = LogManager.getLogger(OrderItem.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        if (s.isEmpty()) {
            return null;
        }
        String indexString = s.substring(1, s.length() - 1);
        String[] unparsedIndexList = indexString.split(Constants.SPLIT);
        List<OrderItem> indexOrderItemList = new ArrayList<>();
        for (String strIndex : unparsedIndexList) {

            if (!strIndex.isEmpty()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setNumber(Long.parseLong(strIndex));
                indexOrderItemList.add(orderItem);
            }
        }
        return indexOrderItemList;
    }

    @Override
    protected String convertToWrite(Object value) {
        List<OrderItem> orderItemList = (List<OrderItem>) value;
        StringBuilder builder = new StringBuilder(Constants.LIST_START_SYMBOL);
        if (orderItemList.size() != 0) {
            for (OrderItem modificationRecord : orderItemList) {
                builder.append(modificationRecord.getNumber());
                builder.append(Constants.SPLIT);
            }
            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append(Constants.LIST_END_SYMBOL);
        log.debug(builder.toString());
        return builder.toString();
    }
}
