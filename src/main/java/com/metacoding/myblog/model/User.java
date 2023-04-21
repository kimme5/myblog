package com.metacoding.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.TypeAlias;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM --> Java 와 같은 개발언어의 Object 를 Table 로 매핑해주는 기술

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert  // insert시 값이 null인 필드 제외
@Table(name = "USER")
@Entity // User 클래스를 통해 MariaDB에 테이블을 생성하는 어노테이션
public class User {

    @Id // Primary Key
    // 프로젝트에서 연결된 DB의 Auto Increment Numbering 전략을 따름
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length= 50)
    private String email;

//    @ColumnDefault("'user'")
//    private String role;    // Domain 설정을 위해 Enum 을 쓰는게 좋음

    // DB는 RoleType이 없으므로 @Enumerated 어노테이션을 통해 RoleType ENUM 클래스가 String임을 알려줘야 함
    @Enumerated(EnumType.STRING)
    private RoleType role;  // ADMIN, USER

    /**
     * CreationTimestamp 어노테이션으로 시간이 자동으로 입력됨
     * 직접 시간을 입력하려면 Timestamp.valueOf(LocalDateTime.now());
     */
    @CreationTimestamp
    private Timestamp createDate;

}
