package com.example.entityProject.user.service;

import com.example.entityProject.user.entity.User;
import com.example.entityProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원 가입
    @Transactional
    public Long join(User user){
//        validateDuplicateMeber(user);
        userRepository.save(user);
        return user.getId();
    }

//    private void validateDuplicateMeber(User user) {
//        List<User> findMembers = userRepository.findByName(user.getName());
//        if(!findMembers.isEmpty()){
//            throw new IllegalStateException("이미 존재하는 회원 입니다.");
//        }
//    }

    // 회원 전체 조회
    public List<User> findUsers(){
        return userRepository.findAll();
    }

    // 단건 조회
    public User findOne(Long userId){
        return userRepository.findOne(userId);
    }
}
