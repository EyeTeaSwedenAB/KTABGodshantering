package com.peter.controller.popupviewcontroller;

import com.peter.controller.Util;
import com.peter.controller.observ.AccountUpdateEvent;
import com.peter.controller.observ.Observer;
import com.peter.controller.observ.UpdateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-28.
 */
public class AddAccountPopupViewController extends AbstractPopupViewViewController {

    @FXML
    private TextField accountTextField;

    @FXML
    private Button sendButton;


    @Override
    public void init() {

    }

    @FXML
    private void handleSendButtonClicked() {

        String account = accountTextField.getText();
        if (isValidInput(account)) {

            try {
                List<String> newAccounts = this.getMainController().addAccount(account);

                this.notfyObservers(new AccountUpdateEvent(newAccounts));
                this.getStage().close();

            } catch (SQLException e) {
                e.printStackTrace();
                Util.showAlert("Ett fel interäffade.", "Kunde inte kommunicera mde databasen just nu, försök igen senare", Alert.AlertType.ERROR);

            }
        } else
            Util.showAlert("Felaktig inmatning", "Fältet destnation måste vara ifyllt.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void handleCancelButtonClicked(){
        this.getStage().close();
    }

    private boolean isValidInput(String account) {
        return account.length() != 0;
    }
}
