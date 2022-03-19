package com.example.entityProject.drugdata.entity;

import com.example.entityProject.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Table(name = "Ingredient_list")
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@AllArgsConstructor//(access = AccessLevel.PROTECTED)
public class IngredientList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ingredient_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // n:1 매핑
    @JoinColumn(name = "info_id")
    private MyDrugInfo myDrugInfo;


    @Column(name = "ingredient_name")
    private String ingredientName;

    public void changeMyDrugInfo(MyDrugInfo myDrugInfo) {
        this.myDrugInfo = myDrugInfo;
        myDrugInfo.getIngredientList().add(this);
    }
}
