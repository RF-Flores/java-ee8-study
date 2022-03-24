package pers.ricardo.control;

import pers.ricardo.entity.Specification;
import pers.ricardo.entity.Car;
import pers.ricardo.entity.Color;

import javax.inject.Inject;
import java.util.UUID;

public class CarFactory {

    @Inject
    @Diesel
    //@Named("diesel") --> not type safe since it can be annoted elsewhere create custom annotation for typesafety
    private Color defaultCarColor;

    public Car createCar(Specification specification) {
        Car car = new Car();
        car.setIdentifier(UUID.randomUUID().toString());
        car.setColor(specification.getColor() == null? defaultCarColor : specification.getColor());
        car.setEngineType(specification.getEngineType());
        return car;
    }
}