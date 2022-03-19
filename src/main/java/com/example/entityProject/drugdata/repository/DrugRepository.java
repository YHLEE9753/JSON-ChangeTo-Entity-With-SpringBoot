package com.example.entityProject.drugdata.repository;

import com.example.entityProject.drugdata.entity.MyDrug;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DrugRepository {
    private final EntityManager em;

    public void save(MyDrug myDrug){
        em.persist(myDrug);
    }

    public MyDrug findOne(Long id){
        return em.find(MyDrug.class, id);
    }

//    public List<MyDrug> findAll(){
//        return em.createQuery("select i from myDrug i", MyDrug.class)
//                .getResultList();
//    }
}
