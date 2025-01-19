package com.ageinghippy.customerwebsite.service;

import com.ageinghippy.customerwebsite.model.Car;
import com.ageinghippy.customerwebsite.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    @Transactional
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car getCar(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Car> saveAllCars(List<Car> cars) {
        return carRepository.saveAll(cars);
    }
}
