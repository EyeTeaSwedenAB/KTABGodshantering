package com.peter.startup;

import com.peter.controller.viewcontroller.WelcomeViewController;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;

/**
 * Created by andreajacobsson on 2016-08-31.
 */
public class MyPreloader extends Preloader {
    private Stage prelodaderStage;
    private WelcomeViewController controller;
    private Parent window;

    @Override
    public void init() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/application/WelcomeView.fxml"));
        window = loader.load();
        controller = loader.getController();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        prelodaderStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        prelodaderStage.setScene(new Scene(window));
        prelodaderStage.show();

    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {

        if (info.getType() == StateChangeNotification.Type.BEFORE_START)
            prelodaderStage.close();

    }


    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof ProgressNotification){
            controller.updateProgressBar(((ProgressNotification) info).getProgress());
        }
    }

}
