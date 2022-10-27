package com.peter.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andreajacobsson on 2016-08-30.
 */
public class AutoCompleteComboBox extends ComboBox<String> {

    ObservableList<String> filteredResultList = FXCollections.observableArrayList();
    ObservableList<String> fullList = FXCollections.observableArrayList();

    public AutoCompleteComboBox() {

        this.setEditable(true);

        getEditor().textProperty().addListener((observable, oldValue, newValue) -> {


            filteredResultList.clear();

            if (newValue.equals("")) {
                filteredResultList.addAll(fullList);


            } else {

                String input = ".*" + newValue.toUpperCase() + ".*";
                Pattern pattern = Pattern.compile(input);

                for (String item : fullList) {
                    String uppcaseItem = item.toUpperCase();
                    Matcher matcher = pattern.matcher(uppcaseItem);

                    if (matcher.matches())
                        filteredResultList.add(item);
                }


            }

            getItems().clear();
            getItems().addAll(filteredResultList);
            this.show();

        });


    }

    public void addAll(Collection<? extends String> items) {
        fullList.addAll(items);


    }


}
