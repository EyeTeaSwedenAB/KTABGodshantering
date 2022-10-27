package com.peter.controller.popupviewcontroller;

import com.peter.controller.Util;
import com.peter.controller.observ.AccountUpdateEvent;
import com.peter.controller.observ.UpdateEvent;
import com.peter.controller.observ.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-29.
 */
public class DeleteAccountPopupViewController extends AbstractPopupViewViewController {


    @FXML
    private ListView<String> accountListView;

    @Override
    public void init() {

        updateListView();

    }

    @FXML
    private void handleDeleteButtonClicked() {

        String selectedAccount = accountListView.getSelectionModel().getSelectedItem();

        if (isValidChlice(selectedAccount)) {

            try {
                List<String> newAccounts = getMainController().deleteAccount(selectedAccount);

                this.notfyObservers(new AccountUpdateEvent(newAccounts));

                Util.showAlert("Följande godskategori togs bort", selectedAccount.toUpperCase(), Alert.AlertType.CONFIRMATION);

                this.getStage().close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void handleCancelButtonClicked() {

        this.getStage().close();
    }


    private void updateListView() {


        try {
            accountListView.getItems().clear();
            accountListView.getItems().addAll(getMainController().getAllAccounts());
        } catch (SQLException e) {
            e.printStackTrace();
            Util.showAlert("Ett fel uppstod.", "Kunde inte kommunicera med databasen just nu", Alert.AlertType.ERROR);
        }
    }

    private boolean isValidChlice(String selectedAccount) {
        return selectedAccount != null;
    }


}
