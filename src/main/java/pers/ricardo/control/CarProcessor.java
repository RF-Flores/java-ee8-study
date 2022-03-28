package pers.ricardo.control;

import pers.ricardo.entity.Car;

import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Stream;

@Stateless
public class CarProcessor {

    @Resource
    ManagedExecutorService mes;

    @Asynchronous
    public Future<String> processNewCarAsync(Car car) { //can also be a void to be a fire and forget
        LockSupport.parkNanos(2_000_000_000L);
        String result = "proccessed: " + car;
        System.out.println(result);
        return  new AsyncResult<>(result);
    }

    public void processNewCar(Car car) { //This is still a synchronous method
        LockSupport.parkNanos(2_000_000_000L);
        String result = "proccessed: " + car;

        //Stream.of().parallel() is we had a stream we would need to use a jdk managed thread pool and not a application managed thread pool
        //CompletableFuture.supplyAsync(() -> null, mes).then....; This is an alternative that by default uses the jdk managed thread pool

        System.out.println(result);
    }
}
