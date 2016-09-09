package com.peter.controller.viewcontroller;

import com.peter.controller.InitializableControllee;
import com.peter.controller.maincontroller.MainController;
import com.peter.startup.MainApp;

/**
 * Created by andreajacobsson on 2016-08-29.
 */
public abstract class AbstractViewController implements InitializableControllee {

    private MainController mainController;
    private MainApp mainApp;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public MainController getMainController() {
        return mainController;
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public abstract void init();
}
