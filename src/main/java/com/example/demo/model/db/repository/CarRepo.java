package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepo extends JpaRepository<Car,Long> {
    @Query("select c from Car c where c.user.firstName=:userFirstName")
    List<Car>findCars(@Param("userFirstName") String userFirstName);

}
