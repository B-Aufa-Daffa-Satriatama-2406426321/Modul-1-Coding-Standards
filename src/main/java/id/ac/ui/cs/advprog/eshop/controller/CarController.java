package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/car")
public class CarController implements ItemController<Car> {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Override
    @GetMapping("/createCar")
    public String createItemPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "createCar";
    }

    @Override
    @PostMapping("/createCar")
    public String createItemPost(@ModelAttribute Car car, Model model) {
        carService.create(car);
        return "redirect:listCar";
    }

    @Override
    @GetMapping("/listCar")
    public String listItemPage(Model model) {
        List<Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);
        return "carList";
    }

    @Override
    @GetMapping("/editCar/{carId}")
    public String editItemPage(@PathVariable("carId") String carId, Model model) {
        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "editCar";
    }

    @Override
    @PostMapping("/editCar")
    public String editItemPost(@ModelAttribute Car car, Model model) {
        System.out.println(car.getCarId());
        carService.update(car.getCarId(), car);
        return "redirect:listCar";
    }

    @Override
    @PostMapping("/deleteCar")
    public String deleteItem(@RequestParam("carId") String carId) {
        carService.deleteCarById(carId);
        return "redirect:listCar";
    }
}
