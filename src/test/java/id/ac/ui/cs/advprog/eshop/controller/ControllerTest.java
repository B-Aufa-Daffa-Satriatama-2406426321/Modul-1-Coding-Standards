package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    private MockMvc productMockMvc;
    private MockMvc carMockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private CarService carService;

    @InjectMocks
    private ProductController productController;

    @InjectMocks
    private CarController carController;

    private InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    @BeforeEach
    void setUp() {
        productMockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setViewResolvers(viewResolver())
                .build();
        carMockMvc = MockMvcBuilders.standaloneSetup(carController)
                .setViewResolvers(viewResolver())
                .build();
    }

    // ==================== PRODUCT CONTROLLER TESTS ====================

    @Test
    void testCreateProductPage() throws Exception {
        productMockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        when(productService.create(any(Product.class))).thenReturn(product);

        productMockMvc.perform(post("/product/create")
                        .param("productName", "Test Product")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    void testProductListPage() throws Exception {
        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId(UUID.randomUUID());
        product1.setProductName("Product 1");
        product1.setProductQuantity(5);

        Product product2 = new Product();
        product2.setProductId(UUID.randomUUID());
        product2.setProductName("Product 2");
        product2.setProductQuantity(15);

        productList.add(product1);
        productList.add(product2);

        when(productService.findAll()).thenReturn(productList);

        productMockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", productList));

        verify(productService, times(1)).findAll();
    }

    @Test
    void testProductListPageEmpty() throws Exception {
        List<Product> emptyList = new ArrayList<>();

        when(productService.findAll()).thenReturn(emptyList);

        productMockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", emptyList));

        verify(productService, times(1)).findAll();
    }

    @Test
    void testEditProductPage() throws Exception {
        Product product = new Product();
        UUID productId = UUID.randomUUID();
        product.setProductId(productId);
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        when(productService.findByProductId(productId.toString())).thenReturn(product);

        productMockMvc.perform(get("/product/edit/" + productId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"));

        verify(productService, times(1)).findByProductId(productId.toString());
    }

    @Test
    void testDeleteProduct() throws Exception {
        String productId = UUID.randomUUID().toString();

        doNothing().when(productService).deleteProductById(productId);

        productMockMvc.perform(post("/product/delete")
                        .param("productId", productId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(productService, times(1)).deleteProductById(productId);
    }

    @Test
    void testHomePage() throws Exception {
        productMockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("Home"));
    }

    // ==================== CAR CONTROLLER TESTS ====================

    @Test
    void testCreateCarPage() throws Exception {
        carMockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("createCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        Car car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Test Car");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        when(carService.create(any(Car.class))).thenReturn(car);

        carMockMvc.perform(post("/car/createCar")
                        .param("carName", "Test Car")
                        .param("carColor", "Red")
                        .param("carQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    void testCarListPage() throws Exception {
        List<Car> carList = new ArrayList<>();

        Car car1 = new Car();
        car1.setCarId(UUID.randomUUID().toString());
        car1.setCarName("Car 1");
        car1.setCarColor("Red");
        car1.setCarQuantity(5);

        Car car2 = new Car();
        car2.setCarId(UUID.randomUUID().toString());
        car2.setCarName("Car 2");
        car2.setCarColor("Blue");
        car2.setCarQuantity(10);

        carList.add(car1);
        carList.add(car2);

        when(carService.findAll()).thenReturn(carList);

        carMockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attribute("cars", carList));

        verify(carService, times(1)).findAll();
    }

    @Test
    void testCarListPageEmpty() throws Exception {
        List<Car> emptyList = new ArrayList<>();

        when(carService.findAll()).thenReturn(emptyList);

        carMockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attribute("cars", emptyList));

        verify(carService, times(1)).findAll();
    }

    @Test
    void testEditCarPage() throws Exception {
        Car car = new Car();
        String carId = UUID.randomUUID().toString();
        car.setCarId(carId);
        car.setCarName("Test Car");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        when(carService.findById(carId)).thenReturn(car);

        carMockMvc.perform(get("/car/editCar/" + carId))
                .andExpect(status().isOk())
                .andExpect(view().name("editCar"))
                .andExpect(model().attributeExists("car"));

        verify(carService, times(1)).findById(carId);
    }

    @Test
    void testDeleteCar() throws Exception {
        String carId = UUID.randomUUID().toString();

        doNothing().when(carService).deleteCarById(carId);

        carMockMvc.perform(post("/car/deleteCar")
                        .param("carId", carId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).deleteCarById(carId);
    }
}
