package com.peter.startup;

import com.peter.controller.maincontroller.MainController;
import com.peter.controller.viewcontroller.ContainerView;
import com.peter.controller.viewcontroller.Input;
import com.peter.controller.viewcontroller.SummaryViewController;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MainApp extends Application {

    private ContainerView containerViewController;
    private Input inputViewController;
    private BorderPane inputView;
    private BorderPane containerView;
    private BorderPane summaryView;
    private SummaryViewController summaryViewController;


    public static void main(String[] args) throws Exception {

        LauncherImpl.launchApplication(MainApp.class, MyPreloader.class, args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        containerView = new BorderPane();
        FXMLLoader containerLoader = new FXMLLoader(getClass().getResource("/fxml/application/ContainerView.fxml"));
        containerLoader.setRoot(containerView);
        containerLoader.setController(containerViewController);
        containerLoader.load();
        containerViewController.init();


        inputView = new BorderPane();
        FXMLLoader inputLoader = new FXMLLoader(getClass().getResource("/fxml/application/InputView.fxml"));
        inputLoader.setRoot(inputView);
        inputLoader.setController(inputViewController);
        inputLoader.load();
        inputViewController.init();

        summaryView = new BorderPane();
        FXMLLoader summaryLoader = new FXMLLoader(getClass().getResource("/fxml/application/SummayView.fxml"));
        summaryLoader.setRoot(summaryView);
        summaryLoader.setController(summaryViewController);
        summaryLoader.load();
        summaryViewController.init();

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
        // business into theese before adding them to their views in "Start()" method

        notifyPreloader(new Preloader.ProgressNotification(0));

        MainController mainController = new MainController();
        mainController.setLoginInformation("jdbc:mysql://ktabtest.cyzgfcxn1ubh.eu-central-1.rds.amazonaws.com:3306/KTABGoodsTest", "pebo0602", "PetBob82");

        notifyPreloader(new Preloader.ProgressNotification(0.4));
        Thread.sleep(400);

        containerViewController = new ContainerView();
        containerViewController.setMainController(mainController);
        containerViewController.setMainApp(this);
        notifyPreloader(new Preloader.ProgressNotification(0.5));

        inputViewController = new Input();
        inputViewController.setMainController(mainController);
        inputViewController.setMainApp(this);
        inputViewController.loadInitialData();
        notifyPreloader(new Preloader.ProgressNotification(0.8));

        summaryViewController = new SummaryViewController();
        summaryViewController.setMainApp(this);
        summaryViewController.setMainController(mainController);


//        Thread.sleep(1000);
        containerViewController.addObserver(inputViewController);
        notifyPreloader(new Preloader.ProgressNotification(1));

        // WE ARE READY!
    }

    public void changeView(String view) {

        if (view.equalsIgnoreCase("inputView"))
//            if (containerView.getBottom() != inputView)
            containerView.setBottom(inputView);


        else if (view.equalsIgnoreCase("summaryView"))
//                if (containerView.getBottom() != summaryView)
            containerView.setBottom(summaryView);
    }
}
