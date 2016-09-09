package com.peter.controller.popupviewcontroller;

import com.peter.controller.Util;
import com.peter.controller.observ.GoodsCategoryUpdateEvent;
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
public class DeleteGoodsCategoryPopupViewController extends AbstractPopupViewViewController {

    @FXML
    private ListView<String> goodsCategoryListView;

    @Override
    public void init() {

        updateListView();
    }

    @FXML
    private void handleDeleteButtonClicked() {

        String selectedGoodsCategory = goodsCategoryListView.getSelectionModel().getSelectedItem();
        System.out.println(selectedGoodsCategory);

        if (isValidChoice(selectedGoodsCategory)) {

            try {
                List<String> newGoodsCategories = getMainController().deleteGoodsCategory(selectedGoodsCategory);

                UpdateEvent<List<String>> updateEvent = new GoodsCategoryUpdateEvent(newGoodsCategories);
                for (Observer observer : getObservers())
                    observer.update(updateEvent);

                Util.showAlert("Följande godskategori togs bort", selectedGoodsCategory.toUpperCase(), Alert.AlertType.CONFIRMATION);
                this.getStage().close();


            } catch (SQLException e) {
                e.printStackTrace();
                Util.showAlert("Ett fel uppstod", selectedGoodsCategory + " har tidigare använte vid inmatning och kan därför inte tas bort.", Alert.AlertType.ERROR);
            }

        } else
            Util.showAlert("Felaktigt val", "Du måste välja en Fakturamottagare att ta bort", Alert.AlertType.WARNING);

    }

    @FXML
    private void handleCancelButtonClicked() {
        this.getStage().close();
    }


    private void updateListView() {

        try {
            goodsCategoryListView.getItems().clear();
            goodsCategoryListView.getItems().addAll(getMainController().getAllGoodsCategories());

        } catch (SQLException e) {
            e.printStackTrace();
            Util.showAlert("Ett fel uppstod", "Kunde inte kommunicera med databasen just nu", Alert.AlertType.ERROR);
        }

    }


    private boolean isValidChoice(String selectedGoodsCategory) {
        return selectedGoodsCategory != null;
    }


}
