package pers.ricardo.control;

import pers.ricardo.CarStorateException;
import pers.ricardo.entity.Specification;
import pers.ricardo.entity.Car;
import pers.ricardo.entity.Color;

import javax.annotation.PostConstruct;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CarFactory {

    @Inject
    @Diesel
    //@Named("diesel") --> not type safe since it can be annoted elsewhere create custom annotation for typesafety
    private Color defaultCarColor;

    @Inject
    @Config("identfier.prefix")
    String identifierPrefix; //We can specify the default by using an exposer class and then we would need a qualifier if we had several string contexts

    @Inject
    ManagedScheduledExecutorService mses; //This is needed if we wish to create a sceduled job for a CDI bean

    //@Transactional(Transactional.TxType.REQUIRED) --> example on how to make the CDI bean create a transaction if one does not exist already
    //@Transactional(rollbackOn = CarStorateException.class) --> example on how to make the transaction rollback on a specific exception even for CDI beans
    public Car createCar(Specification specification) {
        //Forcing a exception
        if (new Random().nextBoolean()) {
            throw new CarCreationException("The production is currently stopped!");
        }
        Car car = new Car();
        car.setIdentifier(identifierPrefix + "-" + UUID.randomUUID().toString());
        car.setColor(specification.getColor() == null? defaultCarColor : specification.getColor());
        car.setEngineType(specification.getEngineType());
        return car;
    }

    @PostConstruct //This method is called after this object is instanced.
    public void activateTimer() {
        mses.scheduleAtFixedRate(this::doSomething, 5 , 5, TimeUnit.MINUTES); //This schedules doSomething that will start 5 minutes after the object has been instanced and will do it every 5 minutes
    }

    public void doSomething() {
        System.out.println("------I was scheduled to print this-------");
    }
}