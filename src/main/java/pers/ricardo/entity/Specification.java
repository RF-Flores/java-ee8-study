package pers.ricardo.entity;

import pers.ricardo.entity.Color;
import pers.ricardo.entity.EngineType;

import javax.validation.constraints.NotNull;

public class Specification {

    @NotNull
    private final Color color;

    @NotNull
    private final EngineType engineType;

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


}
