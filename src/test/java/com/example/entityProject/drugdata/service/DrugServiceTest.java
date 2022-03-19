package com.example.entityProject.drugdata.service;

import com.example.entityProject.drugdata.entity.MyDrug;
import com.example.entityProject.drugdata.repository.DrugJSONRepository;
import com.example.entityProject.drugdata.repository.DrugRepository;
import com.example.entityProject.user.entity.Carrier;
import com.example.entityProject.user.entity.User;
import com.example.entityProject.user.repository.UserRepository;
import com.example.entityProject.user.service.UserService;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //이게 있어야 롤백이 된다!!(Test 에서만 작용되는것! Service 이런데서는 롤백안돼!!), + jpa 쓸거면 무조건 넣자!
class DrugServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;
    @Autowired
    DrugService drugService;
    @Autowired
    DrugRepository drugRepository;
    @Autowired
    DrugJSONRepository drugJSONRepository;

    @Test
    public void json파일_읽어오기() throws Exception{
        drugService.readJson();
    }


    @Test
    @Rollback(false) // db 에서 확인해 보자
    public void drug엔티티_저장하기() throws Exception{
        // given
        // 유저 정보 저장
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
        Long savedId = userService.join(user);
        em.persist(user);

        // myDrug 정보 저장
        drugJSONRepository.saveJson(user);

        System.out.println(user.getMyDrugs());
        System.out.println(user.getMyDrugs().get(0).getMyDrugDetails().get(0).getMedicineEffect());
        System.out.println(user.getMyDrugs().get(0).getMyDrugDetails().get(0).getMyDrugInfos().get(0).getDurAgeTaboo());
//        System.out.println(user.getMyDrugs().get(0).getMyDrugDetails().get(0).getMyDrugInfos().get(0).getKpicLists().get(0).getKpic());
        System.out.println(user.getMyDrugs().get(0).getMyDrugDetails().get(0).getMyDrugInfos().get(0).getIngredientList().get(0).getIngredientName());
        System.out.println("!!!!");

        // when
        JPAQueryFactory query = new JPAQueryFactory(em);
//
//        Hello result = query
//                .selectFrom(qHello)
//                .fetchOne();
        // then

    }

}