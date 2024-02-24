package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.User;
import com.example.demo.model.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    List<User> findAllByStatus(UserStatus status);
    @Query(nativeQuery = true,value = "select * from users where first_name=:firstName order by first_name desc limit 1")
    User findFirstName(@Param("firstName") String firstName);

    @Query("select u from User u where u.firstName=:firstName")
    List<User> findAllFirstName(@Param("firstName") String firstName);

}
