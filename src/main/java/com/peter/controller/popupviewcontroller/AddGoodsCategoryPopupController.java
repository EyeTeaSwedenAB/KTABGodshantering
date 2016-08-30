package com.peter.controller.popupviewcontroller;

import com.peter.controller.Util;
import com.peter.controller.observ.ObserverForViewController;
import com.peter.controller.observ.GoodsCategoryUpdateEvent;
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
public class AddGoodsCategoryPopupController extends AbstractPopupViewController {


    @FXML
    private TextField goodsCategoryTextField;

    @FXML
    private TextField unitPriceTextField;

    @FXML
    private Button sendButton;


    @Override
    public void init() {
    }


    @FXML
    private void handleSendButtonClicked() {
        String goodsCategory = goodsCategoryTextField.getText();
        String unitPrice = unitPriceTextField.getText();

        if (isValidInput(goodsCategory, unitPrice)) {

            try {
                double price = Double.parseDouble(unitPrice);
                List<String> newGoodsCategories = newGoodsCategories = this.getMainController().addGoodsCategory(goodsCategory, price);

                for (ObserverForViewController observerForViewController : this.getObserverForViewControllers()) {
                    UpdateEvent<List<String>> updateEvent = new GoodsCategoryUpdateEvent(newGoodsCategories);
                    observerForViewController.update(updateEvent);
                }

                this.getStage().close();

            } catch (SQLException e) {
                e.printStackTrace();
                Util.showAlert("Ett fel uppstod", "Kunde inte kommunicera med databasen just nu, försök igen senare.", Alert.AlertType.ERROR);
            }

        } else
            Util.showAlert("Felaktig inmating", "Samtliga fält måste vara ifyllda, enhetpris måste vara ett siffervärde", Alert.AlertType.WARNING);
    }

    @FXML
    private void handleCancelButtonClicked() {
        this.getStage().close();
    }

    private boolean isValidInput(String goodsCategory, String unitPrice) {

        try {
            Double.parseDouble(unitPrice);

        } catch (NumberFormatException e) {
            return false;
        }

        return goodsCategory.length() != 0;
    }


}
