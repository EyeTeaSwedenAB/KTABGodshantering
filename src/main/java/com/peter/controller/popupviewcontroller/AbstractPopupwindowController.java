package com.peter.controller.popupviewcontroller;

import com.peter.controller.maincontroller.MainController;
import com.peter.observ.Oberver;
import com.peter.observ.ObservableController;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-28.
 */
public abstract class AbstractPopupwindowController implements ObservableController {


    private MainController mainController;
    private List<Oberver> obervers = new ArrayList<>();
    private Stage stage;

    public MainController getMainController() {
        return mainController;
    }

    public List<Oberver> getObervers() {
        return obervers;
    }

    public void setObervers(List<Oberver> obervers) {
        this.obervers = obervers;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void addObserver(Oberver oberver) {
        obervers.add(oberver);
    }
}
