package com.peter.startup;

import com.peter.controller.maincontroller.MainController;
import com.peter.controller.viewcontroller.InputViewController;
import com.sun.glass.ui.MenuItem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        BorderPane mainView = mainViewLoader.load();

        FXMLLoader inputViewLoader = new FXMLLoader(getClass().getResource("/fxml/InputView.fxml"));
        BorderPane inputView = inputViewLoader.load();
        InputViewController ivc = inputViewLoader.getController();

        MainController mainController = new MainController();
        mainController.setLoginInformation("jdbc:mysql://ktabtest.cyzgfcxn1ubh.eu-central-1.rds.amazonaws.com:3306/KTABGoodsTest", "pebo0602", "PetBob82");

        ivc.setMainController(mainController);
        ivc.init();



        mainView.setBottom(inputView);
        stage.setScene(new Scene(mainView));
        stage.show();

    }
}
