package pers.ricardo.boundary;


import pers.ricardo.control.*;
import pers.ricardo.entity.*;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
//@Tracked(ProcessTracker.Category.MANUFACTURE) It is also possible to track the entire class
public class CarManufacturer {

    @Inject
    CarFactory carFactory;

    /* !!!!!!!!!!No longer needed since we have an entity manager now!!!!!
    @Inject
    CarRepository carRepository;
    */

    @Inject
    Event<CarCreated> carCreatedEvent;

    @PersistenceContext(name = "prod")
    EntityManager entityManager;

    @Inject
    CarCache carCache;

    @TransactionAttribute(TransactionAttributeType.REQUIRED) // This is the default value, it creates a new transaction if it wasnt called in another transaction context
    //@Transactional(rollbackOn = CarStorateException.class, dontRollbackOn = ConstraintViolationException.class, value = Transactional.TxType.REQUIRED)
    //@Interceptors(ProcessTrackingInterceptor.class) if we do not wish to tightly couple interceptors we can use custom interceptor binding
    @Tracked(ProcessTracker.Category.MANUFACTURE) //This is way to track the method without it being tightly coupled.
    public Car manufactureCar(Specification specification) {
        Car car = carFactory.createCar(specification);
        entityManager.persist(car);
        carCache.cacheCar(car); //caches the car when it is created
        carCreatedEvent.fire(new CarCreated(car.getIdentifier())); // this is a synchronous event, the business logic hangs until the observer has finished
        return car;
    }

    /* !!!!!!!!!!No longer needed since we have an entity manager now!!!!!
    public Car manufactureCar(Specification specification) {
        Car car = carFactory.createCar(specification);
        carRepository.store(car);
        carCreatedEvent.fire(new CarCreated(car.getIdentifier())); // this is a synchronous event, the business logic hangs until the observer has finished
        return car;
    }
     */

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! THIS METHOD IS USING CACHED CARS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @SuppressWarnings("unchecked") //This is neeed to avoid needing to check the assignment
    public List<Car> retrieveCars() {
        List<Car> cachedCars = loadCachedCars();
        return cachedCars == null || cachedCars.isEmpty() ?  entityManager.createNamedQuery(Car.FIND_ALL).getResultList() : cachedCars;
    }
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! THIS METHOD IS USING THE CACHED CARS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    //This is specific for the endpoint load cars that accepts a filter in the urls like this: URL/?filter=EngineType
    @SuppressWarnings("unchecked") //This is neeed to avoid needing to check the assignment
    public List<Car> retrieveCars(EngineType engineType) {
        return entityManager.createNamedQuery(Car.FIND_ALL_BY_ENGINE)
                .setParameter("engineType",engineType)
                .getResultList();
    }

    public Car retrieveCar(String identifier){
        return entityManager.find(Car.class, identifier);
    }

    /* !!!!!!!!!!No longer needed since we have an entity manager now!!!!!
    public List<Car> retrieveCars() {
        return carRepository.loadCars();
    }

    //This is specific for the endpoint load cars that accepts a filter in the urls like this: URL/?filter=EngineType
    public List<Car> retrieveCars(EngineType engineType) {
        return carRepository.loadCars(engineType);
    }

    public Car retrieveCar(String identifier) {
        return carRepository.loadCars().stream()
                .filter(c -> c.getIdentifier().equals(identifier))
                .findFirst().get(); //Needs to be null checked otherwise it will throw exception
    }
     */

    private List<Car> loadCachedCars () {
        return carCache.retrieveCars();
    }
}
