package com.peter.controller.viewcontroller;

import com.peter.controller.InitializableControllee;
import com.peter.controller.maincontroller.MainController;

/**
 * Created by andreajacobsson on 2016-08-29.
 */
public abstract class AbstractViewController implements InitializableControllee {

    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public MainController getMainController() {
        return mainController;
    }

    @Override
    public abstract void init();
}
