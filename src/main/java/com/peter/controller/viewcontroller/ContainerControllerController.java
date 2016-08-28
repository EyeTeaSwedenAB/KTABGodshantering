package com.peter.controller.viewcontroller;

import com.peter.controller.InitializableControllee;
import com.peter.controller.maincontroller.MainController;
import com.peter.controller.popupviewcontroller.AbstractPopupwindowController;
import com.peter.observ.Oberver;
import com.peter.observ.ObservableController;
import com.peter.observ.UpdateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-22.
 */
public class ContainerControllerController implements InitializableControllee, Oberver, ObservableController {
    MainController mainController;
    @FXML
    private MenuItem addInvoiceRecieverMenuItem;
    @FXML
    private MenuItem addGoodsCategoryMenuItem;
    @FXML
    private MenuItem addAccountMenuItem;


    private List<Oberver> obervers = new ArrayList<>();

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void init() {

    }


    @FXML
    private void handeAddInvoiceReciever() {
        try {
            showPopup("/fxml/AddInvoiceRecieverPopup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddGoodsCategory() {
        try {
            showPopup("/fxml/AddGoodsCategoryPopup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleAddAccount() {
        try {
            showPopup("/fxml/AddAccountPopup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showPopup(String path) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent window = loader.load();
        AbstractPopupwindowController popupView = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(window));


        popupView.setMainController(this.mainController);
        popupView.addObserver(this);
        popupView.setStage(stage);
        stage.show();

    }


    @Override
    public void update(UpdateEvent event) {
        for (Oberver oberver : obervers) {
            oberver.update(event);
        }

    }

    @Override
    public void addObserver(Oberver oberver) {
        obervers.add(oberver);

    }
}
