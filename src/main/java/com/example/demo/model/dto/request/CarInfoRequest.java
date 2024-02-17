package com.example.demo.model.dto.request;

import com.example.demo.model.enums.CarType;
import com.example.demo.model.enums.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarInfoRequest {
    String brand;
    String model;
    Color color;
    Integer year;
    Long price;
    Boolean isNew;
    CarType type;
}
