package com.example.entityProject.user.entity;

import com.example.entityProject.drugdata.entity.MyDrug;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@Table(name = "user")
@NoArgsConstructor//(access = AccessLevel.PROTECTED) // 테스트를 위해 임시로 Public, 의도한 코드는 PROTECTED
@AllArgsConstructor//(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "user") // 1:다 매핑
    private List<MyDrug> myDrugs = new ArrayList<>();

    // 카카오 로그인 입력 데이터
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", nullable = true)
    private String email;

    // 추가 정보 입력 데이터
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "encrypted_jumin", nullable = false)
    private String encryptedJumin;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier", nullable = false)
    private Carrier carrier;

    // 자동 생성 시간 작성
    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    // 데이터 업데이트 시간 작성성
    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

}
