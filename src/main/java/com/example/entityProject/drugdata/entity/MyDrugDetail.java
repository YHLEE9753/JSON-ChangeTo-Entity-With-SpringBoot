package com.example.entityProject.drugdata.entity;

import com.example.entityProject.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "MyDrugDetail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MyDrugDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // n:1 매핑
    @JoinColumn(name = "mydrug_id")
    private MyDrug myDrug;

    @OneToMany(mappedBy = "myDrugDetail") // 1:다 매핑
    @Builder.Default
    private List<MyDrugInfo> myDrugInfos = new ArrayList<>();

    @Column(name = "detail_treat_type")
    private String treatType; // 진료형태

    @Column(name = "detail_medicine_name")
    private String medicineNm; // 약이름

    @Column(name = "detail_treat_date")
    private Long treatDate; // 진료개시일

    @Column(name = "detail_medicine_effect")
    private String medicineEffect; // 처방 약품 효능

    @Column(name = "detail_prescribe_count")
    private String prescribeCnt; //처방횟수

    @Column(name = "detail_administer_count")
    private String administerCnt; // 투약일수

    public void changeMyDrug(MyDrug myDrug) {
        this.myDrug = myDrug;
        myDrug.getMyDrugDetails().add(this);
    }
}
