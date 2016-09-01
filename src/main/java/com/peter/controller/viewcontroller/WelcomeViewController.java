package com.peter.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

/**
 * Created by andreajacobsson on 2016-09-01.
 */
public class WelcomeViewController {


    @FXML
    private ProgressBar progressBar;


    public void updateProgressBar(double update){
        progressBar.setProgress(update);
    }

}
