package com.peter.controller.viewcontroller;

import com.peter.controller.maincontroller.MainController;
import com.peter.dto.OrderDTO;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-22.
 */
public class InputViewController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> invoiceRecieverCombobox;

    @FXML
    private ComboBox<String> accountsCombobox;

    @FXML
    private ComboBox<String> goodsCategoryComboBox;

    @FXML
    private Spinner<Integer> noOfUnitsSpinner;

    @FXML
    private TextField unitPriceTextField;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private TextField commentsTextField;

    @FXML
    private TableView<OrderDTO> tableView;

    @FXML
    private TableColumn<OrderDTO, String> dateColumn;

    @FXML
    private TableColumn<OrderDTO, String> invoiceRecieverColumn;

    @FXML
    private TableColumn<OrderDTO, String> destinationColumn;

    @FXML
    private TableColumn<OrderDTO, String> goodsCategoryColumn;

    @FXML
    private TableColumn<OrderDTO, Integer> noOfUnitsColumn;

    @FXML
    private TableColumn<OrderDTO, Double> unitPriceColumn;

    @FXML
    private TableColumn<OrderDTO, Double> totalPriceColumn;

    @FXML
    private TableColumn<OrderDTO, String> commentsColumn;

    @FXML
    private Button sendButton;

    @FXML
    private Label infoLabel;


    private MainController mainController;
    private TaskCreator taskCreator = new TaskCreator();
    private boolean lastRecordDeleted = true;
    private Task<Void> currentTask = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            return null;
        }
    };


    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void init() {

        datePicker.setValue(LocalDate.now());
        noOfUnitsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 300));
        noOfUnitsSpinner.setEditable(true);
        unitPriceTextField.setEditable(false);
        sendButton.setDefaultButton(true);
        initTableView();
        populateComponents();
        setUpListeners();
        updatePriceLables();
    }

    //////////////////////////////////////////////////////////////////////////////////
    // PRIVATE DOMAIN


    @FXML
    private void handleSendButtonClicked(ActionEvent actionEvent) {

        OrderDTO newOrderDTO = null;

        if (isValidInputFields()) {
            if (!currentTask.isRunning()) {

                currentTask = taskCreator.getSendNewEntryAndUpdateTask();
                infoLabel.setText("KOMMUNICERAR MED DATABAS");
                Thread thread = new Thread(currentTask);
                thread.start();

            } else
                showAlert("Lugn i stormen!", "Jag arbetar fortfarande med ditt senaste kommando", Alert.AlertType.INFORMATION);

        } else
            showAlert("Felinmatning", "Fältet \"Á pris\" kan endast innehålla numeriska värden", Alert.AlertType.ERROR);

    }

    @FXML
    private OrderDTO handleUndoButtonClicked(ActionEvent actionEvent) {

        OrderDTO removedOrder = null;
        if (!lastRecordDeleted) {
            ObservableList<OrderDTO> orderDTOs = tableView.getItems();
            removedOrder = orderDTOs.remove(orderDTOs.size() - 1);
            try {

                int rowsaffected = mainController.deleteLastEntry();
                System.out.println("Rows affected = " + rowsaffected);
                lastRecordDeleted = true;

            } catch (SQLException e) {
                e.printStackTrace();
                showDefaultDatabaseErrorAlert();
            }
        } else {
            showAlert("Meddelande", "Du kan endast radera den senaste inmatningen från database med denna knapp", Alert.AlertType.INFORMATION);
        }
        return removedOrder;

    }


    private boolean isValidInputFields() {
        try {
            Double.parseDouble(unitPriceTextField.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private void initTableView() {

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        invoiceRecieverColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceReciever"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        goodsCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("goodsCategory"));
        noOfUnitsColumn.setCellValueFactory(new PropertyValueFactory<>("noOfUnits"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

    }


    private void populateComponents() {

        if (mainController != null) {
            List<String> accounts = null;
            List<String> invoiceRecieverDTOs = null;
            List<String> goodsCategories = null;
            List<OrderDTO> orderDTOs = null;

            try {
                accounts = mainController.getAllAccounts();
                goodsCategories = mainController.getAllGoodsCategories();
                invoiceRecieverDTOs = mainController.getAllInvoiceRecievers();
                orderDTOs = mainController.getOrders(datePicker.getValue());


            } catch (SQLException e) {
                e.printStackTrace();
                showDefaultDatabaseErrorAlert();
            }

            invoiceRecieverCombobox.getItems().addAll(invoiceRecieverDTOs);
            invoiceRecieverCombobox.getSelectionModel().selectFirst();

            goodsCategoryComboBox.getItems().addAll(goodsCategories);
            goodsCategoryComboBox.getSelectionModel().selectFirst();

            accountsCombobox.getItems().addAll(accounts);
            accountsCombobox.getSelectionModel().selectFirst();

            tableView.getItems().addAll(orderDTOs);

        } else System.err.println("Maincontroller not set!");
    }


    private void setUpListeners() {

        goodsCategoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updatePriceLables();
        });

        noOfUnitsSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            updatePriceLables();
        });

        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (!currentTask.isRunning()) {

                currentTask = taskCreator.getUpdateTableViewTask();
                infoLabel.setText("KOMMUNICERAR MED DATABAS");
                Thread thread = new Thread(currentTask);
                thread.start();
            }
            else showAlert("Lugn i stormen!", "Jag arbetar fortfarande med ditt senaste kommando", Alert.AlertType.INFORMATION);
        });


    }

    private void showDefaultDatabaseErrorAlert() {
        showAlert("Ett fel uppstod", "Det gick inte att få kontakt med databasen just nu, försök igen senare", Alert.AlertType.ERROR);
    }

    private void updatePriceLables() {
        double unitPrice = mainController.getUnitPrice(goodsCategoryComboBox.getValue());
        int noOfUnits = noOfUnitsSpinner.getValue();

        unitPriceTextField.setText(Double.toString(unitPrice));
        totalPriceLabel.setText(Double.toString(unitPrice * noOfUnits));
    }

    private void showAlert(String header, String message, Alert.AlertType alertType) {

        Alert alert = new Alert(alertType, message);
        alert.setHeaderText(header);
        alert.showAndWait();
    }


    private class TaskCreator {


        private Task<Void> getSendNewEntryAndUpdateTask() {

            Task task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {

                    OrderDTO newOrderDTO;


                    String date = datePicker.getValue().toString();
                    String invoiceReciever = invoiceRecieverCombobox.getValue();
                    String accountDTO = accountsCombobox.getValue();
                    String goodsCatDTO = goodsCategoryComboBox.getValue();
                    int noOfUnits = noOfUnitsSpinner.getValue();
                    double unitPrice = Double.parseDouble(unitPriceTextField.getText());
                    double totalPrice = noOfUnits * unitPrice;
                    String comment = commentsTextField.getText();


                    newOrderDTO = new OrderDTO(0, date, invoiceReciever, accountDTO, goodsCatDTO, noOfUnits, unitPrice, totalPrice, comment);

                    try {
                        mainController.sendNewEntry(newOrderDTO);
                        tableView.getItems().clear();
                        tableView.getItems().addAll(mainController.getOrders(datePicker.getValue()));


                        for (OrderDTO o : tableView.getItems())
                            System.out.println(o.getId());

                        lastRecordDeleted = false;


                    } catch (SQLException e) {
                        e.printStackTrace();
                        showDefaultDatabaseErrorAlert();
                    }
                    return null;
                }

            };


            task.setOnSucceeded(event -> {
                infoLabel.setText("");
                System.out.println("sendNewEntryAndUpdateTask closed");

            });

            return task;

        }


        private Task<Void> getUpdateTableViewTask() {

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        tableView.getItems().clear();
                        tableView.getItems().addAll(mainController.getOrders(datePicker.getValue()));

                    } catch (SQLException e) {
                        e.printStackTrace();
                        showDefaultDatabaseErrorAlert();
                    }
                    return null;
                }
            };

            task.setOnSucceeded(event -> {
                infoLabel.setText("");
                System.out.println("updateTableTask closed");
            });

            return task;
        }
    }


}




