package com.peter.controller.viewcontroller;

import com.peter.controller.popupviewcontroller.AbstractPopupViewViewController;
import com.peter.controller.observ.ViewControllerObserver;
import com.peter.controller.observ.ObservableViewController;
import com.peter.controller.observ.UpdateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-22.
 */
public class ContainerViewViewController extends AbstractViewController implements ViewControllerObserver, ObservableViewController {

    private List<ViewControllerObserver> viewControllerObservers = new ArrayList<>();


    @Override
    public void init() {

    }


    @FXML
    private void handeAddInvoiceReciever() {
        try {
            showPopup("/fxml/popup/AddInvoiceRecieverPopup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddGoodsCategory() {
        try {
            showPopup("/fxml/popup/AddGoodsCategoryPopup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleAddAccount() {
        try {
            showPopup("/fxml/popup/AddAccountPopup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteInvoiceReciever(){

        try {
            showPopup("/fxml/popup/DeleteInvoiceRecieverPopup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteGoodsCategory(){
        try {
            showPopup("/fxml/popup/DeleteGoodsCategoryPopup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteAccount(){
        try {
            showPopup("/fxml/popup/DeleteAccountPopup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showPopup(String path) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent window = loader.load();
        AbstractPopupViewViewController popupViewController = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(window));


        popupViewController.setMainController(this.getMainController());
        popupViewController.addObserver(this);
        popupViewController.setStage(stage);
        popupViewController.init();
        stage.show();

    }


    @Override
    public void update(UpdateEvent event) {
        for (ViewControllerObserver viewControllerObserver : viewControllerObservers) {
            viewControllerObserver.update(event);
        }

    }

    @Override
    public void addObserver(ViewControllerObserver viewControllerObserver) {
        viewControllerObservers.add(viewControllerObserver);

    }
}
