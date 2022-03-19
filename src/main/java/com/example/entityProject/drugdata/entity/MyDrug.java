package com.example.entityProject.drugdata.entity;

import com.example.entityProject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "myDrug")
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@AllArgsConstructor//(access = AccessLevel.PROTECTED)
public class MyDrug {

    @Id
    @Column(name = "mydrug_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // n:1 매핑
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "myDrug") // 1:다 매핑
    private List<MyDrugDetail> myDrugDetails = new ArrayList<>();

    // 투약정보 type1
    @Column(name = "treat_type")
    private String treatType; // 진료형태

    @Column(name = "visit_count")
    private Long visitCnt; // 방문일수

    @Column(name = "treat_dsnm")
    private String treatDsnm; // 진료대상자명

    @Column(name = "treat_date")
    private Long treatDate; // 진료개시일

    @Column(name = "medicine_count")
    private Long medicineCnt; // 투약(요양)횟수

    @Column(name = "treat_dsgb")
    private String treatdsgb; // 1:본인, 2:자녀

    @Column(name = "prescribe_cnt")
    private Long prescribeCnt; // 처방 횟수

    @Column(name = "treat_medicalnm")
    private String treatMedicalnm;

    public void changeUser(User user) {
        this.user = user;
        user.getMyDrugs().add(this);
    }

}
