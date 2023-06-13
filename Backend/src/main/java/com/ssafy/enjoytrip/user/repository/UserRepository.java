package com.ssafy.enjoytrip.user.repository;

import com.ssafy.enjoytrip.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
    //    UserDto getUserById(final String userId);
//
//    int addUser(final UserDto user) throws DuplicateKeyException;
//
//    boolean updateUser(final UserDto user);
//
//    int removeUser(final String userId);
    User getUserById(final String userId);

    @Override
    <S extends User> S save(S entity);

    void deleteById(String userId);


}
