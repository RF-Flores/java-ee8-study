package pers.ricardo.control;

import pers.ricardo.entity.Car;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton //it could be either this or @ApplicationScoped, we only want 1 instance in our application either way.
@ConcurrencyManagement(ConcurrencyManagementType.BEAN) //This usually means that beans lock is handled outside of the bean, with this annotation we are saying that the bean manages the concurrency itself.
public class CarCache {

    private final Map<String,Car> cache = new ConcurrentHashMap<>();

    @PersistenceContext(name = "prod")
    EntityManager entityManager;

    @PostConstruct //This is needed to fetch the data and put it in the cache itself after the bean is created.
    private void initCache() {
        loadCars();
    }

    public void loadCars() { //This can be invocked on a timer to refresh the
        List<Car> carsFromDB = entityManager.createNamedQuery(Car.FIND_ALL, Car.class).getResultList();
        carsFromDB.forEach(c -> cache.put(c.getIdentifier(),c));
        //carsFromDB.forEach(this::cacheCar); This funnels each car into the cacheCar method to store it in cache
    }

    public List<Car> retrieveCars() {
        return new LinkedList<>(cache.values());
    }

    //!!!!!!!!!!!!!! This is needed to make sure the car is cached when it is created and persisted !!!!!!!!!!!!
    public void cacheCar(Car car) {
        cache.put(car.getIdentifier(),car);
    }
}
