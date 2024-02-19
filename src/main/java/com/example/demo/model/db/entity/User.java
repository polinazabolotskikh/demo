package com.example.demo.model.db.entity;

import com.example.demo.model.enums.Gender;
import com.example.demo.model.enums.UserStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String email;
    String password;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "middle_name")
    String middleName;
    Integer age;
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Column(name = "created at")
    LocalDateTime createdAt;

    @Column(name = "updated at")
    LocalDateTime updatedAt;

    UserStatus status;
}
