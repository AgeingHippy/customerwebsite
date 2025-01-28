package com.ageinghippy.customerwebsite.controller;

import com.ageinghippy.customerwebsite.model.Car;
import com.ageinghippy.customerwebsite.model.Customer;
import com.ageinghippy.customerwebsite.model.CustomerErrorResponse;
import com.ageinghippy.customerwebsite.service.CarService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @RequestMapping(value = {"/view"})
    public String viewAllCars(Model model) {
        model.addAttribute("carList", carService.getAllCars());
        return "view-car";
    }

    @RequestMapping("/new")
    public String showNewCarPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "new-car";
    }

    @RequestMapping("/assign/{customerId}")
    public String showAssignCarPage(@PathVariable Long customerId, Model model) {
        List<Car> cars = carService.getAllUnassignedCars();
        model.addAttribute("customerId",customerId);
        model.addAttribute("carList", cars);
        model.addAttribute("car",new Car());
        return "select-car";
    }

    @RequestMapping("/save")
    public String saveNewCar(@ModelAttribute Car car, Model model) {
        try {
            carService.saveCar(car);
        } catch (ConstraintViolationException e) {
            CustomerErrorResponse customerErrorResponse = new CustomerErrorResponse(e.getConstraintViolations());
            model.addAttribute("customerErrorResponse", customerErrorResponse);
            return "error-page";
        }
        return "redirect:/car/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditCarPage(@PathVariable(name = "id") Long id, Model model) {

//        ModelAndView mav = new ModelAndView("edit-customer");
        Car car = carService.getCar(id);
        model.addAttribute("car", car);
        return "edit-car";
    }

    @PostMapping("/update/{id}")
    public String updateCar(@PathVariable Long id, @ModelAttribute Car car, Model model) {

        if (!Objects.equals(id, car.getId())) {
            model.addAttribute("message",
                    "Cannot update, car id " + car.getId()
                            + " doesn't match id to update: " + id + ".");
            return "error-page";
        }

        try {
            carService.saveCar(car);
        } catch (ConstraintViolationException e) {
            CustomerErrorResponse customerErrorResponse = new CustomerErrorResponse(e.getConstraintViolations());
            model.addAttribute("customerErrorResponse", customerErrorResponse);
            return "error-page";
        }
        return "redirect:/car/view";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/car/view";
    }


}
