package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarRepositoryTest {

    @InjectMocks
    CarRepository carRepository;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Toyota Camry");
        car.setCarColor("Silver");
        car.setCarQuantity(10);
    }

    @Test
    void testCreateAndFind() {
        carRepository.create(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car.getCarId(), savedCar.getCarId());
        assertEquals("Toyota Camry", savedCar.getCarName());
        assertEquals("Silver", savedCar.getCarColor());
        assertEquals(10, savedCar.getCarQuantity());
    }

    @Test
    void testCreateWithNullId() {
        Car carWithoutId = new Car();
        carWithoutId.setCarName("Honda Civic");
        carWithoutId.setCarColor("Blue");
        carWithoutId.setCarQuantity(5);

        Car createdCar = carRepository.create(carWithoutId);

        assertNotNull(createdCar.getCarId());
        assertEquals("Honda Civic", createdCar.getCarName());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneCar() {
        carRepository.create(car);

        Car car2 = new Car();
        car2.setCarId(UUID.randomUUID().toString());
        car2.setCarName("Honda Accord");
        car2.setCarColor("Black");
        car2.setCarQuantity(8);
        carRepository.create(car2);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car.getCarId(), savedCar.getCarId());
        savedCar = carIterator.next();
        assertEquals(car2.getCarId(), savedCar.getCarId());
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindById() {
        carRepository.create(car);

        Car foundCar = carRepository.findById(car.getCarId());

        assertNotNull(foundCar);
        assertEquals(car.getCarId(), foundCar.getCarId());
        assertEquals("Toyota Camry", foundCar.getCarName());
        assertEquals("Silver", foundCar.getCarColor());
    }

    @Test
    void testFindByIdNotFound() {
        carRepository.create(car);

        Car foundCar = carRepository.findById("non-existent-id");

        assertNull(foundCar);
    }

    @Test
    void testUpdate() {
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Toyota Camry (Updated)");
        updatedCar.setCarColor("Red");
        updatedCar.setCarQuantity(15);

        Car result = carRepository.update(car.getCarId(), updatedCar);

        assertNotNull(result);
        assertEquals("Toyota Camry (Updated)", result.getCarName());
        assertEquals("Red", result.getCarColor());
        assertEquals(15, result.getCarQuantity());

        // Verify the update persisted
        Car foundCar = carRepository.findById(car.getCarId());
        assertEquals("Toyota Camry (Updated)", foundCar.getCarName());
        assertEquals("Red", foundCar.getCarColor());
        assertEquals(15, foundCar.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Should Not Update");
        updatedCar.setCarColor("Green");
        updatedCar.setCarQuantity(100);

        Car result = carRepository.update("non-existent-id", updatedCar);

        assertNull(result);
    }

    @Test
    void testDelete() {
        carRepository.create(car);

        carRepository.delete(car.getCarId());

        Car deletedCar = carRepository.findById(car.getCarId());
        assertNull(deletedCar);
    }

    @Test
    void testDeleteNonExistent() {
        carRepository.create(car);

        // Delete non-existent car should not throw exception
        carRepository.delete("non-existent-id");

        // Original car should still exist
        Car foundCar = carRepository.findById(car.getCarId());
        assertNotNull(foundCar);
    }

    @Test
    void testDeleteFromMultipleCars() {
        carRepository.create(car);

        Car car2 = new Car();
        car2.setCarId(UUID.randomUUID().toString());
        car2.setCarName("Honda Accord");
        car2.setCarColor("Black");
        car2.setCarQuantity(8);
        carRepository.create(car2);

        carRepository.delete(car.getCarId());

        assertNull(carRepository.findById(car.getCarId()));
        assertNotNull(carRepository.findById(car2.getCarId()));
    }
}
