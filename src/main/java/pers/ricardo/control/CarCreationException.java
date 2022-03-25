package pers.ricardo.control;

import javax.ejb.ApplicationException;

@ApplicationException
public class CarCreationException extends RuntimeException {
    public CarCreationException(String s) {
        super(s);
    }
}
