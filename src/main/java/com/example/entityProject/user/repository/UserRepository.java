package com.example.entityProject.user.repository;

import com.example.entityProject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }

    public List<User> findAll(){
        return em.createQuery("select m from user m", User.class)
                .getResultList();
    }

//    public List<User> findByName(String name){
//        return em.createQuery("select m from user m where m.name = :name", User.class)
//                .setParameter("name", name)
//                .getResultList();
//    }

}
