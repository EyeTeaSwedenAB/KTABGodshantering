package com.peter.startup;

import com.peter.controller.maincontroller.MainController;
import com.peter.controller.viewcontroller.ContainerViewViewController;
import com.peter.controller.viewcontroller.InputViewController;
import com.peter.controller.viewcontroller.WelcomeViewController;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MainApp extends Application {

    private ContainerViewViewController containerViewController;
    private InputViewController inputViewController;
    private WelcomeViewController welcomeViewController;


    public static void main(String[] args) throws Exception {

        LauncherImpl.launchApplication(MainApp.class, MyPreloader.class, args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        BorderPane containerView = new BorderPane();
        FXMLLoader containerLoader = new FXMLLoader(getClass().getResource("/fxml/application/ContainerView.fxml"));
        containerLoader.setRoot(containerView);
        containerLoader.setController(containerViewController);
        containerLoader.load();
        containerViewController.init();


        BorderPane inputView = new BorderPane();
        FXMLLoader inputLoader = new FXMLLoader(getClass().getResource("/fxml/application/InputView.fxml"));
        inputLoader.setRoot(inputView);
        inputLoader.setController(inputViewController);
        inputLoader.load();
        inputViewController.init();


        containerView.setBottom(inputView);
        stage.setScene(new Scene(containerView));
        stage.show();



    }

    @Override
    public void init() throws Exception {
        setUpViewControllers();


    }

    private void setUpViewControllers() throws SQLException, InterruptedException {
        // Create Fundamental view controllers for the main windows and load tha
        // data into theese before adding them to their views in "Start()" method

        notifyPreloader(new Preloader.ProgressNotification(0));

        MainController mainController = new MainController();
        mainController.setLoginInformation("jdbc:mysql://ktabtest.cyzgfcxn1ubh.eu-central-1.rds.amazonaws.com:3306/KTABGoodsTest", "pebo0602", "PetBob82");

        notifyPreloader(new Preloader.ProgressNotification(0.4));
        Thread.sleep(400);

        containerViewController = new ContainerViewViewController();
        containerViewController.setMainController(mainController);
        notifyPreloader(new Preloader.ProgressNotification(0.5));

        inputViewController = new InputViewController();
        inputViewController.setMainController(mainController);
        inputViewController.loadInitialData();
        notifyPreloader(new Preloader.ProgressNotification(0.8));

        Thread.sleep(1000);
        containerViewController.addObserver(inputViewController);
        notifyPreloader(new Preloader.ProgressNotification(1));
        // WE ARE READY!
    }
}
