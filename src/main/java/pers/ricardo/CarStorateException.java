package pers.ricardo;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class CarStorateException extends Exception {

    public CarStorateException(String message) {
        super(message);
    }
}
