package com.peter.controller.popupviewcontroller;

import com.peter.controller.InitializableControllee;
import com.peter.controller.maincontroller.MainController;
import com.peter.controller.observ.ObservableViewController;
import com.peter.controller.observ.Observer;
import com.peter.controller.observ.UpdateEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-28.
 */
public abstract class AbstractPopupViewViewController implements ObservableViewController, InitializableControllee {


    private MainController mainController;
    private List<Observer> observers = new ArrayList<>();
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

    public List<Observer> getObservers() {
        return observers;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notfyObservers(UpdateEvent event){
        for (Observer observer : observers)
            observer.update(event);
    }
}
