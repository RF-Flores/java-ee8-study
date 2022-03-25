package pers.ricardo.entity;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class SeatBelt {

    @Enumerated(EnumType.STRING)
    private SeatBeltModel seatBeltModel;

    public SeatBelt() {
    }

    public SeatBelt(SeatBeltModel seatBeltModel) {
        this.seatBeltModel = seatBeltModel;
    }

    public SeatBeltModel getSeatBeltModel() {
        return seatBeltModel;
    }

    public void setSeatBeltModel(SeatBeltModel seatBeltModel) {
        this.seatBeltModel = seatBeltModel;
    }



    public void open() {

    }

    public void close() {

    }

}
