package com.peter.Util;

import com.peter.model.SwedishMonth;

import java.time.Month;

/**
 * Created by andreajacobsson on 2016-09-20.
 */
public class SweMonthConverter {

    public static SwedishMonth convert(Month month) {

        if (month == Month.JANUARY)
            return SwedishMonth.JANUARI;

        else if (month == Month.FEBRUARY)
            return SwedishMonth.FEBRUARI;

        else if (month == Month.MARCH)
            return SwedishMonth.MARS;

        else if (month == Month.APRIL)
            return SwedishMonth.APRIL;

        else if (month == Month.MAY)
            return SwedishMonth.MAJ;

        else if (month == Month.JUNE)
            return SwedishMonth.JUNI;

        else if (month == Month.JULY)
            return SwedishMonth.JULI;

        else if (month == Month.AUGUST)
            return SwedishMonth.AUGUSTI;

        else if (month == Month.SEPTEMBER)
            return SwedishMonth.SEPTEMBER;

        else if (month == Month.OCTOBER)
            return SwedishMonth.OKTOBER;

        else if (month == Month.NOVEMBER)
            return SwedishMonth.NOVEMBER;

        else if (month == Month.DECEMBER)
            return SwedishMonth.DECEMBER;

        else
            return null;
    }
}
