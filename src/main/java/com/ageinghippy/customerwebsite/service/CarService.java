package com.ageinghippy.customerwebsite.service;

import com.ageinghippy.customerwebsite.model.Car;

import java.util.List;

public interface CarService {

    public List<Car> getAllCars();

    public Car saveCar(Car car);

    public Car getCar(Long id);

    public void deleteCar(Long id);

    public List<Car> saveAllCars(List<Car> cars);

}
