package pers.ricardo.entity;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
@NamedQueries({
        @NamedQuery(name = Car.FIND_ALL, query = "select c from Car c"),
        @NamedQuery(name = Car.FIND_ALL_BY_ENGINE, query = "select c from Car c where c.engineType = :engineType")
})
public class Car {

    public static final String FIND_ALL = "Car.findAll";
    public static final String FIND_ALL_BY_ENGINE = "Car.findall_by_engine";

    @Id
    @JsonbTransient
    private String identifier;

    @Enumerated(EnumType.STRING)
    private Color color;

    @JsonbProperty("engine")
    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    @OneToMany(cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "car", nullable = false)
    private Set<Seat> seats = new HashSet<>();

    public Car() {
    }

    public Car(String identifier, Color color, EngineType engineType) {
        this.identifier = identifier;
        this.color = color;
        this.engineType = engineType;
    }

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

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
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
