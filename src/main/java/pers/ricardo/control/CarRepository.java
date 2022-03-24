package pers.ricardo.control;

import pers.ricardo.entity.Car;
import pers.ricardo.entity.Color;
import pers.ricardo.entity.EngineType;

import java.util.Arrays;
import java.util.List;

public class CarRepository {


    public void store(Car car) {

    }

    public List<Car> loadCars() {
        return Arrays.asList(
                createCar("X123A234", Color.RED, EngineType.DIESEL),
                createCar("X234B345", Color.BLACK, EngineType.ELECTRIC),
                createCar("X345C456", Color.GREY, EngineType.PETROL)
        );
    }

    private static Car createCar(String identifier, Color color, EngineType engineType) {
        Car car = new Car();
        car.setIdentifier(identifier);
        car.setColor(color);
        car.setEngineType(engineType);
        return car;
    }
}
