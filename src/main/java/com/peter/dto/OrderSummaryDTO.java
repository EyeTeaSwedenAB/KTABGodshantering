package com.peter.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-09-07.
 */
public class OrderSummaryDTO {

    private final List<OrderDTO> monthlyOrders = new ArrayList<>();


    public double getMonthlyDueAmount() {
        double dueAmount = 0;
        for (OrderDTO dto : monthlyOrders)
            dueAmount += dto.getTotalPrice();

        return dueAmount;
    }

    public List<OrderDTO> getMonthlyOrders() {
        return monthlyOrders;
    }

}
