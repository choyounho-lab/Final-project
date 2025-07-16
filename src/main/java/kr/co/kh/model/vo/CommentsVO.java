package kr.co.kh.model.vo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 댓글 정보를 관리하는 VO (Value Object) / Entity 클래스
 */
@Entity
@Table(name = "COMMENTS") // 데이터베이스의 COMMENTS 테이블과 매핑
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommentsVO {

    /**
     * 댓글 고유 번호 (PK)
     * Oracle의 IDENTITY 컬럼과 매핑됩니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long commentId;

    /**
     * 작성자 ID (FK: USERS)
     * USERS 테이블의 USER_ID를 참조합니다.
     * @ManyToOne 같은 연관관계 매핑을 통해 UserVO 객체로 직접 참조할 수도 있습니다.
     */
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    /**
     * 대상 ID (예: 앨범 ID, 공지사항 ID 등)
     */
    @Column(name = "TARGET_ID", nullable = false)
    private String targetId;

    /**
     * 대상 유형 (예: 'ALBUM', 'NOTICE', 'TRACK')
     */
    @Column(name = "TARGET_TYPE", nullable = false, length = 50)
    private String targetType;

    /**
     * 댓글 내용
     */
    @Column(name = "CONTENT", nullable = false, length = 1000)
    private String content;

    /**
     * 작성일시
     * 엔티티가 처음 저장될 때 현재 시간이 자동으로 할당됩니다.
     * updatable = false 설정을 통해 수정 시에는 이 값이 변경되지 않도록 합니다.
     */
    @CreationTimestamp
    @Column(name = "CREATE_DATE", nullable = false, updatable = false)
    private LocalDateTime createDate;

    /**
     * 수정일시
     * 엔티티가 수정될 때마다 현재 시간이 자동으로 할당됩니다.
     */
    @UpdateTimestamp
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    // 새로운 필드: 유저가 신고한 댓글인지 여부
    //@Transient 어노테이션을 사용한 이유는 hasReportedByUser가 데이터베이스 컬럼이 아닌 뷰 로직에 필요한 값이기 때문
    @Transient
    private int hasReportedByUser; // 1: 신고한 경우, 0: 신고하지 않은 경우

}