package com.example.demo.service.impl;

import com.example.demo.model.db.entity.Car;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.CarRepo;
import com.example.demo.model.dto.request.CarInfoRequest;
import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.dto.response.CarInfoResponse;
import com.example.demo.model.dto.response.UserInfoResponse;
import com.example.demo.model.enums.CarStatus;
import com.example.demo.model.enums.Color;
import com.example.demo.model.enums.UserStatus;
import com.example.demo.service.CarService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    public final CarRepo carRepo;
    private final ObjectMapper mapper;
    private final UserService userService;


    @Override
    public CarInfoResponse createCar(CarInfoRequest request) {
        Car car = mapper.convertValue(request, Car.class);
        car.setStatus(CarStatus.CREATED);
        car.setCreatedAt(LocalDateTime.now());
        car=carRepo.save(car);

        return mapper.convertValue(car,CarInfoResponse.class);
    }

    @Override
    public CarInfoResponse getCar(Long id) {
        Optional <Car> optionalCar = carRepo.findById(id);
        Car car = optionalCar.orElse(new Car());
        return mapper.convertValue(car,CarInfoResponse.class);
    }

    private Car getCarDb(Long id){
        return carRepo.findById(id).orElse(new Car());
    }

    @Override
    public CarInfoResponse updateCar(Long id, CarInfoRequest request) {
        Car car = getCarDb(id);
        if (car.getId() != null) {
            car.setBrand(request.getBrand() == null ? car.getBrand() : request.getBrand());
            car.setModel(request.getModel() == null ? car.getModel() : request.getModel());
            car.setColor(Color.BLACK);
            car.setYear(request.getYear() == null ? car.getYear() : request.getYear());
            car.setPrice(request.getPrice() == null ? car.getPrice() : request.getPrice());
            car.setIsNew(request.getIsNew()== null ? car.getIsNew() : request.getIsNew());
            car.setType(request.getType() == null ? car.getType() : request.getType());
            car.setStatus(CarStatus.UPDATED);
            car.setUpdatedAt(LocalDateTime.now());
            car = carRepo.save(car);
        } else {
            log.error("Car not found");
        }

        return mapper.convertValue(car,CarInfoResponse.class);
    }

    @Override
    public void deleteCar(Long id) {
        Car car = getCarDb(id);
        if (car.getId()!=null){
            car.setStatus(CarStatus.DELETED);
            car.setUpdatedAt(LocalDateTime.now());
            carRepo.save(car);
        }else{
            log.error("User not found");
        }
    }

    @Override
    public List<CarInfoResponse> getAllCars() {
        return carRepo.findAll()
                .stream()
                .map(car -> {
                    CarInfoResponse carInfoResponse=mapper.convertValue(car,CarInfoResponse.class);
                    carInfoResponse.setUser(mapper.convertValue(car.getUser(),UserInfoResponse.class));
                    return carInfoResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CarInfoResponse linkCarAndDriver(Long userId, Long carId) {
        Car car = getCarDb(carId);
        User user=userService.getUserDb(userId);
        user.getCars().add(car);
        userService.updateCarList(user);
        car.setUser(user);
        car=carRepo.save(car);

        UserInfoResponse userInfoResponse=mapper.convertValue(user,UserInfoResponse.class);
        CarInfoResponse carInfoResponse=mapper.convertValue(car,CarInfoResponse.class);

        carInfoResponse.setUser(userInfoResponse);
        return carInfoResponse;
    }


}
