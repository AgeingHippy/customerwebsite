package com.ageinghippy.customerwebsite.repository;

import com.ageinghippy.customerwebsite.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
}
