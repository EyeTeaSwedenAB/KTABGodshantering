package com.peter.controller.viewcontroller;

import com.peter.controller.Util;
import com.peter.dto.OrderDTO;
import com.peter.dto.OrderSummaryDTO;
import com.peter.exceptions.NonValidDirectoryException;
import com.peter.exceptions.WrongFilenameFormatException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private BorderPane rootBorderpane;

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

    private Map<String, OrderSummaryDTO> currentSummaryMap;
    private ContextMenu contextMenu;

    @Override
    public void init() {

        initTimeComboboxes();
        initTableView();
        buildContextMenu();
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

            OrderSummaryDTO orderSummaryDTO = currentSummaryMap.get(newValue);
            List<OrderDTO> monthlyOrders = orderSummaryDTO.getMonthlyOrders();

            ordersTableView.getItems().clear();
            ordersTableView.getItems().addAll(monthlyOrders);
            infoLabel.setText("Ordrar för: " + newValue + ", " + monthCombobox.getValue());

            totalLabel.setText("Totalt att fakturera: " + Double.toString(orderSummaryDTO.getMonthlyDueAmount()) + " kr");

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
            currentSummaryMap = getMainController().getSummary(start, end);
            if (currentSummaryMap.isEmpty())
                Util.showAlert("Meddelade", "Inga transaktioner finns för den angivna månaden\n" + " " +
                        yearCombobox.getSelectionModel().getSelectedItem() + " " +
                        monthCombobox.getSelectionModel().getSelectedItem(), Alert.AlertType.INFORMATION);

            else
                invoiceRecieverListView.getItems().addAll(currentSummaryMap.keySet());

        } catch (SQLException e) {
            e.printStackTrace();
            Util.showAlert("Ett fel uppstod", "Kunde inte kommnicera med databasen just nu", Alert.AlertType.ERROR);
        }


    }

    @FXML
    private void handleExportButtonClicked() {

        if (dataIsCollected()) {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(null);

            if (file != null)
                try {
                    getMainController().printToExcel(currentSummaryMap, file);
                } catch (IOException e) {
                    e.printStackTrace();
                    Util.showAlert("Ett fel uppstod", "Kunde inte läsa filen", Alert.AlertType.ERROR);
                }
        } else
            Util.showAlert("Ett fel uppstod", "Du har inte hämtat någon data att exportera", Alert.AlertType.ERROR);
    }


    @FXML
    private File handleGeneratePDFButtonClicked() {

        if (dataIsCollected()) {

            try {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File directory = directoryChooser.showDialog(null);
                if (directory != null)
                    getMainController().generatePDFs(directory, currentSummaryMap);

                return directory;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Util.showAlert("Ett fel uppstod", "Kunde inte hitta mappen", Alert.AlertType.ERROR);

            } catch (NonValidDirectoryException e) {
                e.printStackTrace();

            }

        } else {
            Util.showAlert("Ett fel uppstod", "Du har inte hämtat någon data att exportera", Alert.AlertType.ERROR);
        }

        return null;
    }

    @FXML
    private void handleGeneratePDFsAndMailAllButtonClicked() {

        File chosenDirectory = handleGeneratePDFButtonClicked();

        if (chosenDirectory != null){

            try {
                getMainController().mailPDFs(chosenDirectory.listFiles());
            } catch (WrongFilenameFormatException e) {

                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println(e.getWrongFile().toString());
            }
        }
        else{

            // What to do if cancel button clicked
        }
    }


    @FXML
    private void handleListViewClicked(MouseEvent mouseEvent) {
        if (notEmptyListView()) {

            if (isSecondaryButton(mouseEvent)) {

                contextMenu.show(rootBorderpane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            } else
                contextMenu.hide();

        } else
            Util.showAlert("Meddelande", "Du måste hämta data innan du kan exportera till PDF.", Alert.AlertType.INFORMATION);
    }



    private void buildContextMenu() {
        contextMenu = new ContextMenu();
        contextMenu.getItems().add(createSinglePDFMenuItem());

    }

    private MenuItem createSinglePDFMenuItem() {
        MenuItem singlePDFMenuItem = new MenuItem("Skapa PDF");


        singlePDFMenuItem.setOnAction(actionEvent -> {

            DirectoryChooser directoryChooser = new DirectoryChooser();
            File directory = directoryChooser.showDialog(null);
            if (directory != null) {

                String invoiceReciever = invoiceRecieverListView.getSelectionModel().getSelectedItem();

                if (invoiceReciever != null) {

                    try {
                        getMainController().generatePDF(directory, currentSummaryMap.get(invoiceReciever));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Util.showAlert("Ett fel uppstod", "Kunde inte hitta mappen", Alert.AlertType.ERROR);
                    } catch (NonValidDirectoryException e) {
                        e.printStackTrace();
                        Util.showAlert("Ett fel uppstod", "Du har inte valt en giltig mapp att spara dina filer i", Alert.AlertType.ERROR);
                    }

                }

            }
        });

        return singlePDFMenuItem;
    }


    private boolean isSecondaryButton(MouseEvent mouseEvent) {
        return mouseEvent.getButton() == MouseButton.SECONDARY;
    }

    private boolean notEmptyListView() {
        return invoiceRecieverListView.getItems().size() > 0;
    }

    private boolean dataIsCollected() {
        return currentSummaryMap != null;
    }
}
