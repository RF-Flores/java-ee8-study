package pers.ricardo.control;

import pers.ricardo.entity.CarCreated;

import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;

public class CarCreationListener {

    public void onCarCreation(@ObservesAsync CarCreated carCreated) {
        System.out.println("New car created, ID : " + carCreated.getCarIdentifier());
    }

}
