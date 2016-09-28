package com.peter.controller.popupviewcontroller;

import com.peter.controller.Util;
import com.peter.controller.observ.InvoiceRecieverUpdateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-27.
 */
public class AddInvoiceRecieverPopupViewController extends AbstractPopupViewViewController {

    @FXML
    private TextField companyTextField;

    @FXML
    private TextField adressTextField;

    @FXML
    private TextField contactTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField emailTextfield;


    @Override
    public void init() {

    }


    @FXML
    private void handleAddButtonClicked() {

        String company = companyTextField.getText();
        String address = adressTextField.getText();
        String contact = contactTextField.getText();
        String phone = phoneTextField.getText();
        String email = emailTextfield.getText();

        if (isValidInput(company, address, contact, phone, email)) {
            try {
                List<String> newInvoiceRecevers = this.getMainController().addNewInvoiceReciever(company, address, contact, phone, email);

                this.notfyObservers(new InvoiceRecieverUpdateEvent(newInvoiceRecevers));
                this.getStage().close();

            } catch (SQLException e) {
                e.printStackTrace();
                Util.showAlert("Ett fel uppstod", "Kunde inte kommunicera med databasen just nu, försök igen senare", Alert.AlertType.ERROR);
            }
        } else {

            Util.showAlert("Felaktig inmatning", "Samtliga fält måste vara ifyllda", Alert.AlertType.WARNING);
        }


    }

    @FXML
    private void handleCancelButtonClicked() {
        this.getStage().close();
    }


    private boolean isValidInput(String company, String address, String contact, String phone, String email) {
//        return company.length() != 0 && address.length() != 0 && contact.length() != 0 && phone.length() != 0;
        return company.length() != 0;
    }

}
