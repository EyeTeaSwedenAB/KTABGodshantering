package com.peter.view;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * Created by andreajacobsson on 2016-09-27.
 */
public class ModalPane extends StackPane {

    public ModalPane() {
        setPrefSize(1200, 700);

        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setMaxSize(200, 200);

        Label label = new Label("Skickar E-post, stäng inte av programmet");
        label.setFont(Font.font("Helvetica", 60));
        getStyleClass().add("modal-pane");
        getStylesheets().add(getClass().getResource("/css/Main.css").toString());
        getChildren().addAll(indicator, label);

    }
}
