package com.example.demo.model.db.entity;

import com.example.demo.model.enums.CarStatus;
import com.example.demo.model.enums.CarType;
import com.example.demo.model.enums.Color;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="cars")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String brand;
    String model;
    Color color;
    Integer year;
    Long price;

    @Column(name="is_new")
    Boolean isNew;
    CarType type;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    CarStatus status;

    @ManyToOne
    @JsonBackReference(value="driver_cars")
    User user;
}
