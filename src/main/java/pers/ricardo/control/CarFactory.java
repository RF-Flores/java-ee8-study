package pers.ricardo.control;

import pers.ricardo.CarStorateException;
import pers.ricardo.entity.Specification;
import pers.ricardo.entity.Car;
import pers.ricardo.entity.Color;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Random;
import java.util.UUID;

public class CarFactory {

    @Inject
    @Diesel
    //@Named("diesel") --> not type safe since it can be annoted elsewhere create custom annotation for typesafety
    private Color defaultCarColor;

    //@Transactional(Transactional.TxType.REQUIRED) --> example on how to make the CDI bean create a transaction if one does not exist already
    //@Transactional(rollbackOn = CarStorateException.class) --> example on how to make the transaction rollback on a specific exception even for CDI beans
    public Car createCar(Specification specification) {
        //Forcing a exception
        if (new Random().nextBoolean()) {
            throw new CarCreationException("The production is currently stopped!");
        }
        Car car = new Car();
        car.setIdentifier(UUID.randomUUID().toString());
        car.setColor(specification.getColor() == null? defaultCarColor : specification.getColor());
        car.setEngineType(specification.getEngineType());
        return car;
    }
}