package com.peter.controller.viewcontroller;

import com.peter.controller.popupviewcontroller.AbstractPopupViewController;
import com.peter.controller.observ.ObserverForViewController;
import com.peter.controller.observ.ObservableController;
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
public class ContainerControllerController extends AbstractViewController implements ObserverForViewController, ObservableController {

    private List<ObserverForViewController> observerForViewControllers = new ArrayList<>();


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

    @FXML
    private void handleDeleteInvoiceReciever(){

        try {
            showPopup("/fxml/DeleteInvoiceRecieverPopup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteGoodsCategory(){
        try {
            showPopup("/fxml/DeleteGoodsCategoryPopup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteAccount(){
        try {
            showPopup("/fxml/DeleteAccountPopup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showPopup(String path) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent window = loader.load();
        AbstractPopupViewController popupView = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(window));


        popupView.setMainController(this.getMainController());
        popupView.addObserver(this);
        popupView.setStage(stage);
        popupView.init();
        stage.show();

    }


    @Override
    public void update(UpdateEvent event) {
        for (ObserverForViewController observerForViewController : observerForViewControllers) {
            observerForViewController.update(event);
        }

    }

    @Override
    public void addObserver(ObserverForViewController observerForViewController) {
        observerForViewControllers.add(observerForViewController);

    }
}
