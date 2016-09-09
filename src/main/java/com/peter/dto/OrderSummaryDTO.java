package com.peter.dto;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-09-07.
 */
public class OrderSummaryDTO {

    private final List<OrderDTO> monthlyOrders = new ArrayList<>();
    private Month month;

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

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
