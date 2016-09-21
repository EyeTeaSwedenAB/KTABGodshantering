package com.peter.model;

/**
 * Created by andreajacobsson on 2016-09-20.
 */
public enum SwedishMonth {

    JANUARI("Januari"),
    FEBRUARI("Februari"),
    MARS("Mars"),
    APRIL("April"),
    MAJ("Maj"),
    JUNI("Juni"),
    JULI("Juli"),
    AUGUSTI("Augusti"),
    SEPTEMBER("September"),
    OKTOBER("Oktober"),
    NOVEMBER("November"),
    DECEMBER("December");


    private final String name;

    SwedishMonth(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
