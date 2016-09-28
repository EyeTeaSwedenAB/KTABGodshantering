package com.peter.controller.popupviewcontroller;

import com.peter.controller.Util;
import com.peter.controller.observ.InvoiceRecieverUpdateEvent;
import com.peter.controller.observ.Observer;
import com.peter.controller.observ.UpdateEvent;
import com.peter.dto.ChangebleInvoiceRecieverAttrs;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andreajacobsson on 2016-09-28.
 */
public class EditInvoiceRecieverPopupViewController extends AbstractPopupViewViewController {


    @FXML
    private TextField adressTextField;

    @FXML
    private TextField contactTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField emailTextfield;

    @FXML
    private ListView<String> invoiceRecieverListView;

    boolean valueChanged = false;

    @Override
    public void init() {

        updateInvoiceRecivers();
        setUpListeners();
    }

    @FXML
    private void handleUpdateButtonClicked() {
        String newAddress = adressTextField.getText();
        String newContact = contactTextField.getText();
        String newPhone = phoneTextField.getText();
        String newEmail = emailTextfield.getText();

        if (validChange(newAddress, newContact, newPhone, newEmail)) {

            try {

                String selectedReciever = invoiceRecieverListView.getSelectionModel().getSelectedItem();
                ChangebleInvoiceRecieverAttrs attrs = new ChangebleInvoiceRecieverAttrs(newAddress, newContact, newPhone, newEmail);
                List<String> updatedRecievers = this.getMainController().updateInvoiceReciever(selectedReciever, attrs);
                notfyObservers(new InvoiceRecieverUpdateEvent(updatedRecievers));
                this.getStage().close();

            } catch (SQLException e) {
                e.printStackTrace();
                Util.showAlert("Ett fel uppstod", "Kunde inte kontakta databasen just nu", Alert.AlertType.ERROR);
            }

        } else {
            Util.showAlert("Meddelande", "Inga fält får vara tomma, En riktig E-postaddress måste användas", Alert.AlertType.INFORMATION);
        }

    }

    @FXML
    private void handleCancelButtonClicked() {
        getStage().close();
    }

    private void setUpListeners() {

        invoiceRecieverListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String invoiceReciver = newValue;
            ChangebleInvoiceRecieverAttrs invRecAttrs = getMainController().getChangebleInvoiceRecieverAttrs(invoiceReciver);

            adressTextField.setText(invRecAttrs.getAddress());
            contactTextField.setText(invRecAttrs.getContact());
            phoneTextField.setText(invRecAttrs.getPhone());
            emailTextfield.setText(invRecAttrs.getEmail());
            valueChanged = false;

        });

        adressTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            valueChanged = true;
        });

        contactTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            valueChanged = true;
        });

        phoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            valueChanged = true;
        });

        emailTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            valueChanged = true;
        });


    }

    private void updateInvoiceRecivers() {
        try {
            invoiceRecieverListView.getItems().clear();
            invoiceRecieverListView.getItems().addAll(getMainController().getAllInvoiceRecievers());

        } catch (SQLException e) {
            e.printStackTrace();
            Util.showAlert("Ett fel uppstod", "Kunde inte kommunicera med databasen just nu, försök igen senare", Alert.AlertType.ERROR);
        }
    }

    private boolean validChange(String adress, String contact, String phone, String email) {

        if (emptyTextFields(adress, contact, phone, email))
            return false;

        if (!isValidEmail(email))
            return false;

        return valueChanged;
    }

    private boolean emptyTextFields(String adress, String contact, String phone, String email) {
        return isEmptyString(adress) || isEmptyString(contact) || isEmptyString(phone) || isEmptyString(email);
    }

    private boolean isEmptyString(String s) {
        return s.equals("") || s == null;
    }

    private boolean isValidEmail(String email) {

        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
