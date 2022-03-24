package pers.ricardo.control;

import pers.ricardo.entity.CarCreated;

import javax.enterprise.event.Observes;

public class CarCreationListener {

    public void onCarCreation(@Observes CarCreated carCreated) {
        System.out.println("New car created, ID : " + carCreated.getCarIdentifier());
    }
}
