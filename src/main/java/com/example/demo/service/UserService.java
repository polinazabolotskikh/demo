package com.example.demo.service;

import com.example.demo.model.db.entity.User;
import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.dto.response.UserInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface UserService {
    UserInfoResponse createUser(UserInfoRequest request);

    UserInfoResponse getUser(Long id);

    User getUserDb(Long id);

    UserInfoResponse updateUser(Long id, UserInfoRequest request);

    void deleteUser(Long id);

    Page<UserInfoResponse> getAllUsers(Integer page, Integer perPage, String sort, Sort.Direction order);

    User updateCarList(User user);
}
