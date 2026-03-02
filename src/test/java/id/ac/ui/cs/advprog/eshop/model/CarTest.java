package id.ac.ui.cs.advprog.eshop.model;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    Car car;
    String carId = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        this.car = new Car();
        this.car.setCarId(carId);
        this.car.setCarName("Toyota Camry");
        this.car.setCarColor("Silver");
        this.car.setCarQuantity(10);
    }

    @Test
    void testGetCarId() {
        assertEquals(carId, car.getCarId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Toyota Camry", car.getCarName());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Silver", car.getCarColor());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(10, car.getCarQuantity());
    }

    @Test
    void testSetCarId() {
        String newId = UUID.randomUUID().toString();
        car.setCarId(newId);
        assertEquals(newId, car.getCarId());
    }

    @Test
    void testSetCarName() {
        car.setCarName("Honda Civic");
        assertEquals("Honda Civic", car.getCarName());
    }

    @Test
    void testSetCarColor() {
        car.setCarColor("Red");
        assertEquals("Red", car.getCarColor());
    }

    @Test
    void testSetCarQuantity() {
        car.setCarQuantity(25);
        assertEquals(25, car.getCarQuantity());
    }

    // Negative scenario tests
    @Test
    void testGetCarNameNegative() {
        assertNotEquals("Honda Accord", car.getCarName());
    }

    @Test
    void testGetCarColorNegative() {
        assertNotEquals("Black", car.getCarColor());
    }

    @Test
    void testGetCarQuantityNegative() {
        assertNotEquals(5, car.getCarQuantity());
    }

    @Test
    void testCarNotNull() {
        assertNotNull(car);
    }

    @Test
    void testCarIdNotNull() {
        assertNotNull(car.getCarId());
    }

    @Test
    void testCarNameNotNull() {
        assertNotNull(car.getCarName());
    }

    @Test
    void testCarColorNotNull() {
        assertNotNull(car.getCarColor());
    }
}
