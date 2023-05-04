package com.metacoding.myblog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "BOARD")
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob    // 대용량 데이터
    private String content; // 섬머노트 라이브러리 사용 <html> 태그가 섞여서 디자인이 됨

    private int count;

    /**
     * ORM을 사용하면 userID를 Foreign Key로 설정하지 않고 User Object를 사용할 수 있음
     * JoinColumn(name="userId") 어노테이션을 통해 테이블 생성시 실제 컬럼명은 "userId"로 생성됨
     * ManyToOne 어노테이션을 사용하여 해당 Board 클래스가 'Many'이고 User 클래스가 'One'인
     * 다대일 형태의 foreign key가 자동으로 생성됨
     * Fetchtype.EAGER : 데이터 조회시 한꺼번에 조회해서 가져옴
     * */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user;

    /**
     * 하나의 게시글에는 여러개의 댓글(Reply)이 달려 있을 수 있으므로,
     * Board 테이블 생성시 Reply 테이블에 대한 연관관계를 같이 정의해 줌
     * 하나의 게시글(Board)에는 여러개의 댓글(Reply)이 달릴 수 있으므로 연관관계는 'OneToMany' 가 되고
     * 여러 개의 Reply 클래스를 담을 수 있는 List 클래스 형태로 선언해야 함
     * Board 테이블 내에는 Reply 테이블 정보를 가질 필요가 없으므로, 외래키도 필요없으며,
     * Board 클래스를 통해 테이블 생성시 이에 대한 컬럼 또한 생성하면 안됨
     * 이를 처리하기 위해 mappedBy = "board" 어노테이션을 정의하여 대상 컬럼이 board 테이블에 의해 매핑되는 컬럼이므로,
     * DB에 컬럼을 생성하지 않게 되며, Reply.board 컬럼과 매핑된다는 정보를 기술함
     * FetchType.LAZY : 해당 쿼리 실행시 동시에 가져오지 않고, 해당 정보가 필요한 경우에 따로 데이터를 불러옴
     * 여기서 FetchType을 EAGER로 가져온 것은 게시글이 조회됨과 동시에 해당 게시글에 포함된 모든 댓글 정보도
     * 가져와야 하기 때문임
     */
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp
    private Timestamp createDate;

}
