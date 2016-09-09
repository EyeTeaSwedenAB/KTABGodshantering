package com.peter.controller.viewcontroller;

import com.peter.controller.Util;
import com.peter.dto.OrderDTO;
import com.peter.dto.OrderSummaryDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;

/**
 * Created by andreajacobsson on 2016-09-04.
 */
public class SummaryViewController extends AbstractViewController {

    @FXML
    private ComboBox<Month> monthCombobox;

    @FXML
    private ComboBox<Year> yearCombobox;

    @FXML
    private ListView<String> invoiceRecieverListView;

    @FXML
    private TableView<OrderDTO> ordersTableView;

    @FXML
    private TableColumn<OrderDTO, String> dateColumn;

    @FXML
    private TableColumn<OrderDTO, String> goodsCategoryColumn;

    @FXML
    private TableColumn<OrderDTO, String> destinationColumn;

    @FXML
    private TableColumn<OrderDTO, Integer> noOfUnitsColumn;

    @FXML
    private TableColumn<OrderDTO, Double> unitPriceColumn;

    @FXML
    private TableColumn<OrderDTO, Double> totalPriceColumn;

    @FXML
    private TableColumn<OrderDTO, String> commentsColumn;

    @FXML
    private TableColumn<OrderDTO, Integer> isInvoiceSentColumn;

    @FXML
    private Label infoLabel;

    @FXML
    private Label totalLabel;

    private Map<String, OrderSummaryDTO> summaryMap;

    @Override
    public void init() {

        initTimeComboboxes();
        initTableView();
        setUpListeners();
    }

    private void initTimeComboboxes() {

        yearCombobox.getItems().add(Year.of(2016));
        yearCombobox.getItems().add(Year.of(2017));
        yearCombobox.getItems().add(Year.of(2018));
        yearCombobox.getItems().add(Year.of(2019));
        yearCombobox.getItems().add(Year.of(2020));
        yearCombobox.getItems().add(Year.of(2021));
        yearCombobox.getItems().add(Year.of(2022));
        yearCombobox.getItems().add(Year.of(2023));
        yearCombobox.getSelectionModel().selectFirst();

        monthCombobox.getItems().addAll(Month.values());
        monthCombobox.getSelectionModel().selectFirst();
    }

    private void initTableView() {

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        goodsCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("goodsCategory"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        noOfUnitsColumn.setCellValueFactory(new PropertyValueFactory<>("noOfUnits"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        isInvoiceSentColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceSent"));

    }


    private void setUpListeners() {

        invoiceRecieverListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            infoLabel.setText("");
            OrderSummaryDTO orderSummaryDTO = summaryMap.get(newValue);
            List<OrderDTO> monthlyOrders = orderSummaryDTO.getMonthlyOrders();
            if (monthlyOrders != null) {
                ordersTableView.getItems().clear();
                ordersTableView.getItems().addAll(monthlyOrders);
                infoLabel.setText("Ordrar för -" + newValue + "- under -" + monthCombobox.getValue() + "-");

                totalLabel.setText("Totalt att fakturera: " + Double.toString(orderSummaryDTO.getMonthlyDueAmount()));
            }
        });


    }


    @FXML
    private void handleCollectButtonClicked() {

        ordersTableView.getItems().clear();
        invoiceRecieverListView.getItems().clear();

        Year year = yearCombobox.getValue();
        Month month = monthCombobox.getValue();
        int lastDayOfmonth = month.maxLength();

        LocalDate start = LocalDate.of(year.getValue(), month, 1);
        LocalDate end = LocalDate.of(year.getValue(), month, lastDayOfmonth);

        try {
            summaryMap = getMainController().getSummary(start, end);
            if (summaryMap.isEmpty())
                Util.showAlert("Meddelade", "Inga transaktioner finns för den angivna månaden\n" + " " +
                        yearCombobox.getSelectionModel().getSelectedItem() + " " +
                        monthCombobox.getSelectionModel().getSelectedItem(), Alert.AlertType.INFORMATION);

            else
                invoiceRecieverListView.getItems().addAll(summaryMap.keySet());

        } catch (SQLException e) {
            e.printStackTrace();
            Util.showAlert("Ett fel uppstod", "Kunde inte kommnicera med databasen just nu", Alert.AlertType.ERROR);
        }


    }


}
