package com.peter.startup;

import com.peter.controller.maincontroller.MainController;
import com.peter.controller.viewcontroller.ContainerControllerController;
import com.peter.controller.viewcontroller.InputViewController;
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

        MainController mainController = new MainController();
        mainController.setLoginInformation("jdbc:mysql://ktabtest.cyzgfcxn1ubh.eu-central-1.rds.amazonaws.com:3306/KTABGoodsTest", "pebo0602", "PetBob82");

        FXMLLoader containerViewLoader = new FXMLLoader(getClass().getResource("/fxml/ContainerView.fxml"));
        BorderPane containerView = containerViewLoader.load();
        ContainerControllerController containerViewController = containerViewLoader.getController();
        containerViewController.setMainController(mainController);
        containerViewController.init();

        FXMLLoader inputViewLoader = new FXMLLoader(getClass().getResource("/fxml/InputView.fxml"));
        BorderPane inputView = inputViewLoader.load();
        InputViewController inputViewController = inputViewLoader.getController();
        inputViewController.setMainController(mainController);
        inputViewController.init();

        containerViewController.addObserver(inputViewController);

        containerView.setBottom(inputView);
        stage.setScene(new Scene(containerView));
        stage.show();


    }
}
