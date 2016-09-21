package com.peter.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-09-07.
 */
public class OrderSummaryDTO {

    private final List<OrderDTO> monthlyOrders = new ArrayList<>();
    private String invoiceReciver;

    public double getMonthlyDueAmount() {
        double dueAmount = 0;
        for (OrderDTO dto : monthlyOrders)
            dueAmount += dto.getTotalPrice();

        return dueAmount;
    }

    public List<OrderDTO> getMonthlyOrders() {
        return monthlyOrders;
    }

    public String getInvoiceReciver() {

        if (monthlyOrders.size() != 0)
            return monthlyOrders.get(0).getInvoiceReciever();
        else
            return null;
    }

    public LocalDate getEndingDate() {

        if (monthlyOrders.size() != 0) {

            LocalDate endingDate = LocalDate.parse(monthlyOrders.get(monthlyOrders.size() - 1).getDate());
            return endingDate;
        } else return null;

    }

}
