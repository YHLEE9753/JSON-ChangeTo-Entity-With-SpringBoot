package com.example.entityProject.drugdata.entity;

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
@Table(name = "myDrugInfo")
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@AllArgsConstructor//(access = AccessLevel.PROTECTED)
public class MyDrugInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // n:1 매핑
    @JoinColumn(name = "detail_id")
    private MyDrugDetail myDrugDetail;

    // 일대 다 투약 성분(타입 3번의 경우에 해당)
    @OneToMany(mappedBy = "myDrugInfo") // 1:다 매핑
    private List<IngredientList> ingredientList = new ArrayList<>(); // Drug_성분명

    @OneToMany(mappedBy = "myDrugInfo") // 1:다 매핑
    private List<KPICList> kpicLists = new ArrayList<>(); // Drug_KPIC 약효분류


    @Column(name = "drug_dur_age_taboo")
    private String durAgeTaboo; //연령금기

    @Column(name = "drug_pregnant_taboo")
    private String pregnantTaboo; //임부금기

    @Column(name = "drug_combined_taboo")
    private String combinedTaboo; //병용금기

    @Column(name = "drug_making_company")
    private String makingCompany; // 제조/수입사

    @Column(name = "drug_product_name")
    private String productNm; // 제품명

    @Column(name = "drug_medicine_group")
    private String medicineGroup; // 복지부 분류

    @Column(name = "drug_sales_company")
    private String salesCompany; // 판매사

    @Column(name = "drug_pay_info")
    private String payInfo; // 급여정보

    @Column(name = "drug_administer_path")
    private String administerPath; // 투여경로

    @Column(name = "drug_shape")
    private String shape; // 제형

    @Column(name = "drug_single_yn")
    private String singleYn; // 단일/복합

    @Column(name = "drug_special_yn")
    private String specialYn; // 전문/일반

    public void changeMyDrugDetail(MyDrugDetail myDrugDetail) {
        this.myDrugDetail = myDrugDetail;
        myDrugDetail.getMyDrugInfos().add(this);
    }
}
