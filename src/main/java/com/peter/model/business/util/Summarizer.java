package com.peter.model.business.util;

import com.peter.dto.OrderDTO;
import com.peter.dto.OrderSummaryDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andreajacobsson on 2016-09-04.
 */
public class Summarizer {


    public Map<String, OrderSummaryDTO> summarize(List<OrderDTO> orderDTOs) {
        Map<String, OrderSummaryDTO> summaryMap = new HashMap<>();


        for (OrderDTO dto : orderDTOs) {
            if (!summaryMap.containsKey(dto.getInvoiceReciever())){

                OrderSummaryDTO orderSummaryDTO = new OrderSummaryDTO();
                orderSummaryDTO.getMonthlyOrders().add(dto);
                summaryMap.put(dto.getInvoiceReciever(), orderSummaryDTO);
            }
            else {
                summaryMap.get(dto.getInvoiceReciever()).getMonthlyOrders().add(dto);
            }
        }

        return summaryMap;
    }

}
