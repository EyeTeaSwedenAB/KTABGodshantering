package com.peter.controller;

import com.peter.controller.maincontroller.MainController;

/**
 * Created by andreajacobsson on 2016-08-27.
 */
public interface InitializableControllee {

    void setMainController(MainController mainController);

    void init();

}
