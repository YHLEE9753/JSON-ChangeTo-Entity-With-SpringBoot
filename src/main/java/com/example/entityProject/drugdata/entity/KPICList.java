package com.example.entityProject.drugdata.entity;

import com.example.entityProject.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Table(name = "kpic_list")
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@AllArgsConstructor//(access = AccessLevel.PROTECTED)
public class KPICList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "kpic_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // n:1 매핑
    @JoinColumn(name = "info_id")
    private MyDrugInfo myDrugInfo;

    @Column(name = "kpic")
    private String kpic;

    public void changeMyDrugInfo(MyDrugInfo myDrugInfo) {
        this.myDrugInfo = myDrugInfo;
        myDrugInfo.getKpicLists().add(this);
    }
}
