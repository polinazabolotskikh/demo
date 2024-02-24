package com.example.demo.service.impl;

import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.UserRepo;
import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.dto.response.UserInfoResponse;
import com.example.demo.model.enums.UserStatus;
import com.example.demo.service.UserService;
import com.example.demo.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserRepo userRepo;
    private final ObjectMapper mapper;



    @Override
    public UserInfoResponse createUser(UserInfoRequest request) {
        User user = mapper.convertValue(request, User.class);
        user.setStatus(UserStatus.CREATED);
        user.setCreatedAt(LocalDateTime.now());
        user=userRepo.save(user);

        return mapper.convertValue(user,UserInfoResponse.class);
    }

    @Override
    public UserInfoResponse getUser(Long id) {
        Optional <User> optionalUser = userRepo.findById(id);
        User user = optionalUser.orElse(new User());
        return mapper.convertValue(user,UserInfoResponse.class);
    }

    @Override
    public User getUserDb(Long id){
        return userRepo.findById(id).orElse(new User());
    }

    @Override
    public UserInfoResponse updateUser(Long id, UserInfoRequest request) {
        User user = getUserDb(id);
        if (user.getId()!=null){
            user.setEmail(request.getEmail()==null ? user.getEmail() : request.getEmail());
            user.setPassword(request.getPassword()==null ? user.getPassword() : request.getPassword());
            user.setAge(request.getAge()==null ? user.getAge() : request.getAge());
            user.setFirstName(request.getFirstName()==null ? user.getFirstName() : request.getFirstName());
            user.setLastName(request.getLastName()==null ? user.getLastName() : request.getLastName());
            user.setMiddleName(request.getMiddleName()==null ? user.getMiddleName() : request.getMiddleName());
            user.setGender(request.getGender()==null ? user.getGender() : request.getGender());
            user.setStatus(UserStatus.UPDATED);
            user.setUpdatedAt(LocalDateTime.now());
            user=userRepo.save(user);
        }else{
            log.error("User not found");
        }
        return mapper.convertValue(user,UserInfoResponse.class);
    }


    @Override
    public void deleteUser(Long id) {
        User user = getUserDb(id);
        if (user.getId()!=null){
            user.setStatus(UserStatus.DELETED);
            user.setUpdatedAt(LocalDateTime.now());
            userRepo.save(user);
        }else{
            log.error("User not found");
        }

    }

    @Override
    public Page<UserInfoResponse> getAllUsers(Integer page, Integer perPage, String sort, Sort.Direction order) {
        Pageable request= PaginationUtil.getPageRequest(page,perPage,sort,order);

        List<UserInfoResponse> all=userRepo.findAll(request)
                .getContent()
                .stream()
                .map(user -> mapper.convertValue(user,UserInfoResponse.class))
                .collect(Collectors.toList());


        return new PageImpl<>(all);
    }

    @Override
    public User updateCarList(User user) {
        return userRepo.save(user);
    }
}
