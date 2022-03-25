package pers.ricardo.entity;

import pers.ricardo.entity.Color;
import pers.ricardo.entity.EngineType;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class Specification {

    @NotNull
    private Color color;

    @JsonbProperty("engine")
    @NotNull
    @EnvironmentalFriendly
    private EngineType engineType;

    public Specification() {
    }

    public Specification(Color color, EngineType engineType) {
        this.color = color;
        this.engineType = engineType;
    }

    public Color getColor() {
        return color;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }
}
