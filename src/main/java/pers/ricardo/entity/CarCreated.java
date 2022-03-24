package pers.ricardo.entity;

public class CarCreated {
    private final String carIdentifier;

    public CarCreated(String carIdentifier) {
        this.carIdentifier = carIdentifier;
    }

    public String getCarIdentifier() {
        return carIdentifier;
    }
}
