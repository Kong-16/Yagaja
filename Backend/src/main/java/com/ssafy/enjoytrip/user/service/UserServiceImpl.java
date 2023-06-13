package com.ssafy.enjoytrip.user.service;

import com.ssafy.enjoytrip.user.dto.UserJoinDto;
import com.ssafy.enjoytrip.user.dto.UserModifyDto;
import com.ssafy.enjoytrip.user.model.User;
import com.ssafy.enjoytrip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public User getUserById(final String userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    @Transactional
    public boolean addUser(UserJoinDto user) throws IllegalArgumentException, DuplicateKeyException {
        if (user.getId() == null || !user.getId().matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException("User ID must be not empty.");
        }
        if (user.getPassword() == null || !user.getPassword().matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException("User password must be not empty.");
        }
        if (user.getName() == null || user.getName().length() == 0) {
            throw new IllegalArgumentException("User name must be not empty.");
        }
        if (user.getEmailId() == null || user.getEmailId().length() == 0) {
            throw new IllegalArgumentException("User email ID must be not empty.");
        }
        if (user.getEmailDomain() == null || user.getEmailDomain().length() == 0) {
            throw new IllegalArgumentException("User email domain must be not empty.");
        }
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new DuplicateKeyException("User id duplicated");
        }
        userRepository.save(User.builder().id(user.getId())
                .password(user.getPassword()).name(user.getName()).emailId(user.getEmailId()).emailDomain(user.getEmailDomain())
                .roleIndex(0).build());
        return true;
    }

    @Override
    @Transactional
    public boolean removeUser(String userId) {
        try {
            userRepository.deleteById(userId);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    @Override

    public boolean updateUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }


    }

    @Override
    @Transactional
    public boolean modifyUser(String userId, UserModifyDto userModifyDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!(optionalUser.isPresent())) {
            return false;
        }
        User user = optionalUser.get();
        userModifyDto.to(user);
        userRepository.save(user);
        return true;
    }

}
