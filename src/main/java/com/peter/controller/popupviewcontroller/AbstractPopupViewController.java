package com.peter.controller.popupviewcontroller;

import com.peter.controller.InitializableControllee;
import com.peter.controller.maincontroller.MainController;
import com.peter.observ.Oberver;
import com.peter.observ.ObservableController;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-28.
 */
public abstract class AbstractPopupViewController implements ObservableController, InitializableControllee {


    private MainController mainController;
    private List<Oberver> obervers = new ArrayList<>();
    private Stage stage;

    @Override
    public abstract void init();

    public MainController getMainController() {
        return mainController;
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public List<Oberver> getObervers() {
        return obervers;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void addObserver(Oberver oberver) {
        obervers.add(oberver);
    }
}
