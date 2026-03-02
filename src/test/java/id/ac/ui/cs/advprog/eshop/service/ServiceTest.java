package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    // ==================== PRODUCT SERVICE MOCKS ====================
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    // ==================== CAR SERVICE MOCKS ====================
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Product product;
    private Car car;

    @BeforeEach
    void setUp() {
        // Setup Product
        product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        // Setup Car
        car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Test Car");
        car.setCarColor("Red");
        car.setCarQuantity(5);
    }

    // ==================== PRODUCT SERVICE TESTS ====================

    @Test
    void testCreateProduct() {
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertEquals(product.getProductId(), createdProduct.getProductId());
        assertEquals("Test Product", createdProduct.getProductName());
        assertEquals(10, createdProduct.getProductQuantity());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testEditProductSuccess() {
        String updatedName = "Updated Product";
        int updatedQuantity = 20;

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName(updatedName);
        updatedProduct.setProductQuantity(updatedQuantity);

        when(productRepository.findByProductId(product.getProductId())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.edit(product, updatedName, updatedQuantity);

        assertNotNull(result);
        assertEquals(updatedName, result.getProductName());
        assertEquals(updatedQuantity, result.getProductQuantity());
        verify(productRepository, times(1)).findByProductId(product.getProductId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testEditProductNotFound() {
        String updatedName = "Updated Product";
        int updatedQuantity = 20;

        when(productRepository.findByProductId(product.getProductId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.edit(product, updatedName, updatedQuantity);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findByProductId(product.getProductId());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testFindAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Product product2 = new Product();
        product2.setProductId(UUID.randomUUID());
        product2.setProductName("Product 2");
        product2.setProductQuantity(15);
        productList.add(product2);

        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Product", result.get(0).getProductName());
        assertEquals("Product 2", result.get(1).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindAllProductsEmpty() {
        List<Product> emptyList = new ArrayList<>();
        Iterator<Product> iterator = emptyList.iterator();

        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindByProductIdSuccess() {
        when(productRepository.findByProductId(product.getProductId())).thenReturn(Optional.of(product));

        Product result = productService.findByProductId(product.getProductId().toString());

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        assertEquals("Test Product", result.getProductName());
        verify(productRepository, times(1)).findByProductId(product.getProductId());
    }

    @Test
    void testFindByProductIdNotFound() {
        UUID randomId = UUID.randomUUID();
        when(productRepository.findByProductId(randomId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.findByProductId(randomId.toString());
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findByProductId(randomId);
    }

    @Test
    void testUpdateProductSuccess() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(50);

        when(productRepository.findByProductId(product.getProductId())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        productService.update(product.getProductId().toString(), updatedProduct);

        verify(productRepository, times(1)).findByProductId(product.getProductId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProductNotFound() {
        UUID randomId = UUID.randomUUID();
        when(productRepository.findByProductId(randomId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.update(randomId.toString(), product);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findByProductId(randomId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testDeleteProductByIdSuccess() {
        when(productRepository.findByProductId(product.getProductId())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.deleteProductById(product.getProductId().toString());

        verify(productRepository, times(1)).findByProductId(product.getProductId());
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProductByIdNotFound() {
        UUID randomId = UUID.randomUUID();
        when(productRepository.findByProductId(randomId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteProductById(randomId.toString());
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findByProductId(randomId);
        verify(productRepository, never()).delete(any(Product.class));
    }

    // ==================== CAR SERVICE TESTS ====================

    @Test
    void testCreateCar() {
        when(carRepository.create(any(Car.class))).thenReturn(car);

        Car createdCar = carService.create(car);

        assertNotNull(createdCar);
        assertEquals(car.getCarId(), createdCar.getCarId());
        assertEquals("Test Car", createdCar.getCarName());
        assertEquals("Red", createdCar.getCarColor());
        assertEquals(5, createdCar.getCarQuantity());
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAllCars() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);

        Car car2 = new Car();
        car2.setCarId(UUID.randomUUID().toString());
        car2.setCarName("Car 2");
        car2.setCarColor("Blue");
        car2.setCarQuantity(10);
        carList.add(car2);

        Iterator<Car> iterator = carList.iterator();
        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> result = carService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Car", result.get(0).getCarName());
        assertEquals("Car 2", result.get(1).getCarName());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindAllCarsEmpty() {
        List<Car> emptyList = new ArrayList<>();
        Iterator<Car> iterator = emptyList.iterator();

        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> result = carService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindCarById() {
        when(carRepository.findById(car.getCarId())).thenReturn(car);

        Car result = carService.findById(car.getCarId());

        assertNotNull(result);
        assertEquals(car.getCarId(), result.getCarId());
        assertEquals("Test Car", result.getCarName());
        verify(carRepository, times(1)).findById(car.getCarId());
    }

    @Test
    void testFindCarByIdNotFound() {
        String randomId = UUID.randomUUID().toString();
        when(carRepository.findById(randomId)).thenReturn(null);

        Car result = carService.findById(randomId);

        assertNull(result);
        verify(carRepository, times(1)).findById(randomId);
    }

    @Test
    void testUpdateCar() {
        Car updatedCar = new Car();
        updatedCar.setCarId(car.getCarId());
        updatedCar.setCarName("Updated Car");
        updatedCar.setCarColor("Green");
        updatedCar.setCarQuantity(20);

        when(carRepository.update(car.getCarId(), updatedCar)).thenReturn(updatedCar);

        carService.update(car.getCarId(), updatedCar);

        verify(carRepository, times(1)).update(car.getCarId(), updatedCar);
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carRepository).delete(car.getCarId());

        carService.deleteCarById(car.getCarId());

        verify(carRepository, times(1)).delete(car.getCarId());
    }
}
