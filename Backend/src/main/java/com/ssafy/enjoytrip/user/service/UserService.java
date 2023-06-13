package com.ssafy.enjoytrip.user.service;

import com.ssafy.enjoytrip.user.dto.UserJoinDto;
import com.ssafy.enjoytrip.user.dto.UserModifyDto;
import com.ssafy.enjoytrip.user.model.User;
import org.springframework.dao.DuplicateKeyException;

public interface UserService {

    User getUserById(final String userId);

    boolean addUser(final UserJoinDto user) throws DuplicateKeyException;

    boolean removeUser(final String userId);

    boolean updateUser(final User user);

    boolean modifyUser(String user, UserModifyDto userModifyDto);

}
