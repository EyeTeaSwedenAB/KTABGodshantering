package com.peter.observ;

import com.peter.controller.maincontroller.MainController;

/**
 * Created by andreajacobsson on 2016-08-27.
 */
public interface ObservableController {

    void setMainController(MainController mainController);

    void addObserver(Oberver oberver);


}
