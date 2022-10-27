package com.peter.controller.popupviewcontroller;

import com.peter.controller.Util;
import com.peter.controller.observ.GoodsCategoryUpdateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by KungPeter on 2017-04-30.
 */
public class EditGoodsCategoryPopupViewController extends AbstractPopupViewViewController {


    @FXML
    public ListView<String> goodsCategoryListView;

    @FXML
    public TextField unitpriceTextfield;




    @Override
    public void init() {

        updateGoodsCategories();
        setUpListneners();

    }

    private void setUpListneners() {
        goodsCategoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selectedCategory = newValue;
            double unitPrice = getMainController().getUnitPrice(selectedCategory);
            unitpriceTextfield.setText(Double.toString(unitPrice));
        });

    }


    @FXML
    public void handleUpdateButtonClicked(ActionEvent actionEvent) {

        String input = unitpriceTextfield.getText();
        if (validChange(input)) {

            try {
                double newValue = Double.parseDouble(input);
                String selectedCategory = goodsCategoryListView.getSelectionModel().getSelectedItem();
                List<String> newGoodsCategories = getMainController().updateGoodsCategory(selectedCategory, newValue);
                notfyObservers(new GoodsCategoryUpdateEvent(newGoodsCategories));
                getStage().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            Util.showAlert("Meddende", "Enhetspriset måste vara ett nummer \n" +
                    "Enhetspriset får inte vara mindre än 0", Alert.AlertType.ERROR);
        }
    }

    private boolean validChange(String input) {

        double newValue;
        try {
            newValue = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return newValue >= 0.0;
    }

    @FXML
    public void handleCancelButtonClicked(ActionEvent actionEvent) {
        getStage().close();
    }


    private void updateGoodsCategories() {

        try {
            goodsCategoryListView.getItems().clear();
            goodsCategoryListView.getItems().addAll(getMainController().getAllGoodsCategories());
        } catch (SQLException e) {
            e.printStackTrace();
            Util.showAlert("Ett fel uppstod", "Kunde inte kommunicera med databasen just nu, försök igen senare", Alert.AlertType.ERROR);
        }
    }
}
