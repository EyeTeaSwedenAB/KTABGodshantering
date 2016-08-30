package com.peter.controller.popupviewcontroller;

import com.peter.controller.Util;
import com.peter.controller.observ.InvoiceRecieverUpdateEvent;
import com.peter.controller.observ.ObserverForViewController;
import com.peter.controller.observ.UpdateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-28.
 */
public class DeleteInvoiceRecieverPopupController extends AbstractPopupViewController {

    @FXML
    private ListView<String> invoiceRecieverListView;

    @Override
    public void init() {

        updateInvoiceRecievers();
    }

    @FXML
    private void handleDeleteButtonClicked() {
        String selectedInvoiceReciever = invoiceRecieverListView.getSelectionModel().getSelectedItem();
        if (isValidChoice(selectedInvoiceReciever)) {

            try {

                List<String> newInvoiceRecievers = this.getMainController().deleteInvoiceReciever(selectedInvoiceReciever);
                UpdateEvent updateEvent = new InvoiceRecieverUpdateEvent(newInvoiceRecievers);

                for (ObserverForViewController observerForViewController : this.getObserverForViewControllers()) {
                    observerForViewController.update(updateEvent);
                }

                Util.showAlert("Följande Fakturamottagare togs bort", selectedInvoiceReciever.toUpperCase(), Alert.AlertType.CONFIRMATION);
                this.getStage().close();

            } catch (SQLException e) {
                e.printStackTrace();
                Util.showAlert("Ett fel uppstod", selectedInvoiceReciever + " har tidigare använte vid inmatning och kan därför inte tas bort.", Alert.AlertType.ERROR);
            }
        } else
            Util.showAlert("Felaktigt val", "Du måste välja en Fakturamottagare att ta bort", Alert.AlertType.WARNING);
    }

    @FXML
    private void handleCancelButtonClicked() {
        this.getStage().close();
    }

    private void updateInvoiceRecievers() {

        try {
            invoiceRecieverListView.getItems().clear();
            invoiceRecieverListView.getItems().addAll(getMainController().getAllInvoiceRecievers());

        } catch (SQLException e) {
            e.printStackTrace();
            Util.showAlert("Ett fel uppstod", "Kunde inte kommunicera med databasen just nu, försök igen senare", Alert.AlertType.ERROR);
        }
    }


    private boolean isValidChoice(String selectedInvoiceReciever) {
        return selectedInvoiceReciever != null;
    }


}
