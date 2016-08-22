package com.peter.startup;

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

        FXMLLoader loader = new FXMLLoader();
        BorderPane mainWindow = loader.load(getClass().getResource("/fxml/MainWindow.fxml"));
        BorderPane inputWindow = loader.load(getClass().getResource("/fxml/InputWindow.fxml"));
        mainWindow.setCenter(inputWindow);

        stage.setScene(new Scene(mainWindow));
        stage.show();
        int x = 2;


    }
}
