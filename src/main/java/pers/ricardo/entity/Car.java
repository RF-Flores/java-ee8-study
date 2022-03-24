package pers.ricardo.entity;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

public class Car {

    @JsonbTransient
    private String identifier;
    private Color color;
    @JsonbProperty("engine")
    private EngineType engineType;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String indentifier) {
        this.identifier = indentifier;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "indentifier='" + identifier + '\'' +
                ", color=" + color +
                ", engineType=" + engineType +
                '}';
    }
}
