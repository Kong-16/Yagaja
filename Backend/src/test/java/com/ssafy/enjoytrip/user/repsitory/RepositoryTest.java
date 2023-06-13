package com.ssafy.enjoytrip.user.repsitory;

import com.ssafy.enjoytrip.user.model.User;
import com.ssafy.enjoytrip.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest

public class RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void test1() {
        User user = new User("kimWonPyo", "1234", "김원표", "kim", "naver", 1);
        User user1 = userRepository.save(user);
        Assertions.assertThat(user1.getId()).isEqualTo(user.getId());

        userRepository.save(user);
    }

}
