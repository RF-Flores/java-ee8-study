package pers.ricardo.boundary;


import pers.ricardo.control.CarFactory;
import pers.ricardo.control.CarRepository;
import pers.ricardo.entity.*;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Stateless
public class CarManufacture {

    @Inject
    CarFactory carFactory;

    @Inject
    CarRepository carRepository;

    @Inject
    Event<CarCreated> carCreatedEvent;


    public Car manufactureCar(Specification specification) {
        Car car = carFactory.createCar(specification);
        carRepository.store(car);
        carCreatedEvent.fire(new CarCreated(car.getIdentifier())); // this is a synchronous event, the business logic hangs until the observer has finished
        return car;
    }


    public List<Car> retrieveCars() {
        return carRepository.loadCars();
    }

    //This is specific for the endpoint load cars that accepts a filter in the urls like this: URL/?filter=EngineType
    public List<Car> retrieveCars(EngineType engineType) {
        return carRepository.loadCars(engineType);
    }

    public Car retrieveCar(String identifier) {
        return carRepository.loadCars().stream()
                .filter(c -> c.getIdentifier().equals(identifier))
                .findFirst().get(); //Needs to be null checked otherwise it will throw exception
    }
}
