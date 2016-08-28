package com.peter.controller;

import javafx.scene.control.Alert;

/**
 * Created by andreajacobsson on 2016-08-28.
 */
public class Util {

    public static void showAlert(String header, String message, Alert.AlertType alertType) {

        Alert alert = new Alert(alertType, message);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

}
