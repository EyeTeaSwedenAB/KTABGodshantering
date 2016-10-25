package com.peter.controller.viewcontroller;

import com.peter.controller.Util;
import com.peter.controller.observ.*;
import com.peter.dto.OrderDTO;
import com.peter.view.AutoCompleteComboBoxListener;
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
public class InputViewController extends AbstractViewController implements Observer {

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


    private TaskCreator taskCreator = new TaskCreator();
    private boolean lastRecordDeleted = true;

    private List<String> accounts = null;
    private List<String> invoiceRecieverDTOs = null;
    private List<String> goodsCategories = null;
    private List<OrderDTO> orderDTOs = null;

    private Task<Void> currentTask = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            return null;
        }
    };

    OrderDTO selectedRow = null;


    @Override
    public void init() {

        datePicker.setValue(LocalDate.now());
        noOfUnitsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3000));
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
                Util.showAlert("Lugn i stormen!", "Jag arbetar fortfarande med ditt senaste kommando", Alert.AlertType.INFORMATION);

        } else
            Util.showAlert("Felinmatning", "Samtliga fält måte innehålla värden! \n\n" +
                    "Fältet \"Á pris\" kan endast innehålla numeriska värden\n" +
                    "Fältet \"Antal\" måste vara större än 0", Alert.AlertType.ERROR);

    }

    @FXML
    private OrderDTO handleUndoButtonClicked(ActionEvent actionEvent) {

        OrderDTO removedOrder = null;
        if (!lastRecordDeleted) {
            ObservableList<OrderDTO> orderDTOs = tableView.getItems();
            removedOrder = orderDTOs.remove(orderDTOs.size() - 1);
            try {

                int rowsaffected = getMainController().deleteLastEntry();
                System.out.println("Rows affected = " + rowsaffected);
                lastRecordDeleted = true;

            } catch (SQLException e) {
                e.printStackTrace();
                showDefaultDatabaseErrorAlert();
            }
        } else {
            Util.showAlert("Meddelande", "Du kan endast radera den senaste inmatningen från database med denna knapp", Alert.AlertType.INFORMATION);
        }
        return removedOrder;

    }

    @FXML
    private void handleDeleteSelected() {

        if (selectedRow != null) {
            try {
                getMainController().deleOrder(selectedRow);
                tableView.getItems().clear();
                tableView.getItems().addAll(getMainController().getOrders(datePicker.getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
                showDefaultDatabaseErrorAlert();
            }
        } else
            Util.showAlert("Felaktigt val", "Du måste välja en rad arr ta bort först", Alert.AlertType.INFORMATION);
    }


    private boolean isValidInputFields() {

        if (invoiceRecieverCombobox.getValue() == null || accountsCombobox.getValue() == null || goodsCategoryComboBox.getValue() == null)
            return false;

        try {
            Double.parseDouble(unitPriceTextField.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return false;
        }
        if (noOfUnitsSpinner.getValue() <= 0)
            return false;

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


    public void loadInitialData() {
        if (getMainController() != null) {

            try {
                accounts = getMainController().getAllAccounts();
                goodsCategories = getMainController().getAllGoodsCategories();
                invoiceRecieverDTOs = getMainController().getAllInvoiceRecievers();
                orderDTOs = getMainController().getOrders(LocalDate.now());


            } catch (SQLException e) {
                e.printStackTrace();
                showDefaultDatabaseErrorAlert();
            }
        } else System.err.println("Maincontroller not set!");

    }


    private void populateComponents() {

        invoiceRecieverCombobox.getItems().addAll(invoiceRecieverDTOs);
        goodsCategoryComboBox.getItems().addAll(goodsCategories);
        accountsCombobox.getItems().addAll(accounts);
        tableView.getItems().addAll(orderDTOs);


    }


    private void setUpListeners() {


        new AutoCompleteComboBoxListener(invoiceRecieverCombobox);

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
            } else
                Util.showAlert("Lugn i stormen!", "Jag arbetar fortfarande med ditt senaste kommando", Alert.AlertType.INFORMATION);
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = newValue;

        });


    }

    private void showDefaultDatabaseErrorAlert() {
        Util.showAlert("Ett fel uppstod", "Det gick inte att få kontakt med databasen just nu, försök igen senare", Alert.AlertType.ERROR);
    }

    private void updatePriceLables() {
        double unitPrice = getMainController().getUnitPrice(goodsCategoryComboBox.getValue());
        int noOfUnits = noOfUnitsSpinner.getValue();

        unitPriceTextField.setText(Double.toString(unitPrice));
        totalPriceLabel.setText(Double.toString(unitPrice * noOfUnits));
    }


    @Override
    public void update(UpdateEvent event) {

        if (event instanceof InvoiceRecieverUpdateEvent) {
            invoiceRecieverCombobox.getItems().clear();
            invoiceRecieverCombobox.getItems().addAll((List<String>) event.getObject());

        } else if (event instanceof GoodsCategoryUpdateEvent) {
            goodsCategoryComboBox.getItems().clear();
            goodsCategoryComboBox.getItems().addAll((List<String>) event.getObject());

        } else if (event instanceof AccountUpdateEvent) {
            accountsCombobox.getItems().clear();
            accountsCombobox.getItems().addAll((List<String>) event.getObject());
        }

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


                    newOrderDTO = new OrderDTO(0, date, invoiceReciever, accountDTO, goodsCatDTO, noOfUnits, unitPrice, totalPrice, comment, "Ej mailad");

                    try {
                        getMainController().sendNewEntry(newOrderDTO);
                        tableView.getItems().clear();
                        tableView.getItems().addAll(getMainController().getOrders(datePicker.getValue()));


                    } catch (SQLException e) {
                        e.printStackTrace();
                        showDefaultDatabaseErrorAlert();
                    }
                    return null;
                }

            };


            task.setOnSucceeded(event -> {
                infoLabel.setText("");
                updatePriceLables();
                lastRecordDeleted = false;
                noOfUnitsSpinner.getValueFactory().setValue(0);
                invoiceRecieverCombobox.getSelectionModel().select(null);
                accountsCombobox.getSelectionModel().select(null);
                goodsCategoryComboBox.getSelectionModel().select(null);
                commentsTextField.setText("");
            });

            return task;

        }

        private Task<Void> getUpdateTableViewTask() {

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        tableView.getItems().clear();
                        tableView.getItems().addAll(getMainController().getOrders(datePicker.getValue()));

                    } catch (SQLException e) {
                        e.printStackTrace();
                        showDefaultDatabaseErrorAlert();
                    }
                    return null;
                }
            };

            task.setOnSucceeded(event -> {
                infoLabel.setText("");
            });

            return task;
        }

    }
}



