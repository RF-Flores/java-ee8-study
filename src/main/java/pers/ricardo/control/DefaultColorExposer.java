package pers.ricardo.control;


import pers.ricardo.entity.Color;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class DefaultColorExposer {

//    @Produces
//    private Color defaultColor;

    @Produces
    @Diesel
    //@Named("diesel") --> not type safe since it can also be named elsewhere use diesel custom annotation for type safety
    public Color exposeDefaultColor() {
        return Color.RED;
    }
}
