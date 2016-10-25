package com.peter.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

/**
 * Created by andreajacobsson on 2016-09-01.
 */
public class WelcomeViewController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ProgressBar progressBar;


    public WelcomeViewController() {

    }

    @FXML
    public void initialize() {
        String url = getClass().getResource("/images/startupbackgound1.jpg").toString();
        rootAnchorPane.setStyle("-fx-background-image: url(" + url + ")");
    }

    public void updateProgressBar(double update) {
        progressBar.setProgress(update);
    }

}
