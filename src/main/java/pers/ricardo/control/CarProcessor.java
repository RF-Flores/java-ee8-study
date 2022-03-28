package pers.ricardo.control;

import pers.ricardo.entity.Car;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.concurrent.Future;
import java.util.concurrent.locks.LockSupport;

@Stateless
public class CarProcessor {

    @Asynchronous
    public Future<String> processNewCar(Car car) { //can also be a void to be a fire and forget
        LockSupport.parkNanos(2_000_000_000L);
        String result = "proccessed: " + car;
        System.out.println(result);
        return  new AsyncResult<>(result);
    }
}
