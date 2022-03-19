package com.example.entityProject.user.service;

import com.example.entityProject.drugdata.service.DrugService;
import com.example.entityProject.user.entity.Carrier;
import com.example.entityProject.user.entity.User;
import com.example.entityProject.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //이게 있어야 롤백이 된다!!(Test 에서만 작용되는것! Service 이런데서는 롤백안돼!!), + jpa 쓸거면 무조건 넣자!
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception{
        // given
        // 카카오 로그인 정보
        String nickname = "홍길동";
        String email = "dydrkfl@naver.com";
        // 추가 정보 입력
        String name = "홍길동";
        String encryptedJumin = "qwer1234";
        String phoneNumber = "01012345678";
        Carrier carrier = Carrier.SKT;

        User user = User.builder()
                .nickname(nickname)
                .email(email)
                .name(name)
                .encryptedJumin(encryptedJumin)
                .phoneNumber(phoneNumber)
                .carrier(carrier)
                .build();
        //when
        Long savedId = userService.join(user);

        //then
        em.flush();
        Assertions.assertEquals(user, userRepository.findOne(savedId));

    }
}