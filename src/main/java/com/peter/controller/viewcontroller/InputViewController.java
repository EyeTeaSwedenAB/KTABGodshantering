package com.peter.controller.viewcontroller;

import com.peter.controller.maincontroller.MainController;
import com.peter.dtos.AccountDTO;
import com.peter.dtos.GoodsCategoryDTO;
import com.peter.dtos.InvoiceRecieverDTO;
import com.peter.dtos.TransformedOrderDataDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-22.
 */
public class InputViewController {


    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<InvoiceRecieverDTO> invoiceRecieverCombobox;

    @FXML
    private ComboBox<AccountDTO> accountsCombobox;

    @FXML
    private ComboBox<GoodsCategoryDTO> goodsCategoryComboBox;

    @FXML
    private Spinner<Integer> noOfUnitsSpinner;

    @FXML
    private TextField unitPriceTextField;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private TableView<TransformedOrderDataDTO> tableView;

    @FXML
    private TableColumn<TransformedOrderDataDTO, Date> dateColumn;

    @FXML
    private TableColumn<TransformedOrderDataDTO, InvoiceRecieverDTO> invoiceRecieverColumn;

    @FXML
    private TableColumn<TransformedOrderDataDTO, AccountDTO> destinationColumn;

    @FXML
    private TableColumn<TransformedOrderDataDTO, GoodsCategoryDTO> goodsCategoryColumn;

    @FXML
    private TableColumn<TransformedOrderDataDTO, Integer> noOfUnitsColumn;

    @FXML
    private TableColumn<TransformedOrderDataDTO, Double> unitPriceColumn;

    @FXML
    private TableColumn<TransformedOrderDataDTO, Double> totalPriceColumn;


    private MainController mainController;


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
        initTableView();
        populateComponents();
        setUpListeners();
        updatePriceLables();
    }


    // PRIVATE DOMAIN
    //////////////////////////////////////////////////////////////////////////////////

    @FXML
    private TransformedOrderDataDTO handleSendButtonClicked(ActionEvent actionEvent) {

        TransformedOrderDataDTO orderEntryDTO = null;

        if (validateInputFields()) {

            LocalDate date = datePicker.getValue();
            InvoiceRecieverDTO invoiceRecieverDTO = invoiceRecieverCombobox.getValue();
            AccountDTO accountDTO = accountsCombobox.getValue();
            GoodsCategoryDTO goodsCatDTO = goodsCategoryComboBox.getValue();
            int noOfUnits = noOfUnitsSpinner.getValue();
            double unitPrice = Double.parseDouble(unitPriceTextField.getText());
            double totalPrice = noOfUnits * unitPrice;


            orderEntryDTO = new TransformedOrderDataDTO(date, invoiceRecieverDTO, accountDTO, goodsCatDTO, noOfUnits, unitPrice, totalPrice);

            try {
                mainController.sendNewEntry(orderEntryDTO);
                tableView.getItems().add(orderEntryDTO);
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Ett fel uppstod", "Det gick inte att skicka inmatningen till databasen just nu. \n " +
                        "försök igen senare", Alert.AlertType.ERROR);
            }

            // TODO: 2016-08-24 send orderEntry to database
        } else
            showAlert("Felinmatning", "Fältet \"Á pris\" kan endast innehålla numeriska värden", Alert.AlertType.ERROR);

        return orderEntryDTO;
    }

    private boolean validateInputFields() {
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


    private void populateComponents() {

        if (mainController != null) {
            List<InvoiceRecieverDTO> invoiceRecieverDTOs = null;
            List<GoodsCategoryDTO> goodsCategoryDTOs = null;
            List<AccountDTO> accountDTOs = null;
            List<TransformedOrderDataDTO> transformedOrderDataDTOs = null;

            try {
                invoiceRecieverDTOs = mainController.getAllInvoiceRecievers();
                goodsCategoryDTOs = mainController.getAllGoodsCategories();
                accountDTOs = mainController.getAllAccounts();
                transformedOrderDataDTOs = mainController.getEntries(5);


            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Ett fel uppstod", "Det gick inte att få kontakt med databasen just nu, försök igen senare", Alert.AlertType.ERROR);
            }

            invoiceRecieverCombobox.getItems().addAll(invoiceRecieverDTOs);
            invoiceRecieverCombobox.getSelectionModel().selectFirst();

            goodsCategoryComboBox.getItems().addAll(goodsCategoryDTOs);
            goodsCategoryComboBox.getSelectionModel().selectFirst();

            accountsCombobox.getItems().addAll(accountDTOs);
            accountsCombobox.getSelectionModel().selectFirst();

            tableView.getItems().addAll(transformedOrderDataDTOs);

        } else System.err.println("Maincontroller not set!");
    }


    private void initTableView() {

        dateColumn.setCellValueFactory(new PropertyValueFactory<TransformedOrderDataDTO, Date>("date"));
        invoiceRecieverColumn.setCellValueFactory(new PropertyValueFactory<TransformedOrderDataDTO, InvoiceRecieverDTO>("invoiceReciever"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<TransformedOrderDataDTO, AccountDTO>("destination"));
        goodsCategoryColumn.setCellValueFactory(new PropertyValueFactory<TransformedOrderDataDTO, GoodsCategoryDTO>("goodsCategory"));
        noOfUnitsColumn.setCellValueFactory(new PropertyValueFactory<TransformedOrderDataDTO, Integer>("noOfUnits"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<TransformedOrderDataDTO, Double>("unitPrice"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<TransformedOrderDataDTO, Double>("totalPrice"));


    }

    private void setUpListeners() {

        goodsCategoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updatePriceLables();
        });

        noOfUnitsSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            updatePriceLables();
        });


    }

    private void updatePriceLables() {
        double unitPrice = goodsCategoryComboBox.getValue().UNIT_PRICE;
        int noOfUnits = noOfUnitsSpinner.getValue();

        unitPriceTextField.setText(Double.toString(unitPrice));
        totalPriceLabel.setText(Double.toString(unitPrice * noOfUnits));
    }

    private void showAlert(String header, String message, Alert.AlertType alertType) {

        Alert alert = new Alert(alertType, message);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

}
