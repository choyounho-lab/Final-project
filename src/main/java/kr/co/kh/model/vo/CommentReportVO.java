package kr.co.kh.model.vo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 댓글 신고 정보를 관리하는 VO (Value Object) / Entity 클래스
 */
@Entity
@Table(name = "COMMENT_REPORT") // 데이터베이스의 COMMENT_REPORT 테이블과 매핑
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@ToString
public class CommentReportVO {

    /**
     * 신고 고유 번호 (PK)
     * Oracle의 IDENTITY 컬럼과 매핑됩니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long reportId;

    /**
     * 신고된 댓글의 ID (FK: COMMENTS)
     * COMMENTS 테이블의 COMMENT_ID를 참조합니다.
     */
    @Column(name = "COMMENT_ID", nullable = false)
    private Long commentId;

    /**
     * 신고한 사용자 ID (FK: USERS)
     * USERS 테이블의 USER_ID를 참조합니다.
     */
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    /**
     * 신고 사유
     */
    @Column(name = "REPORT_REASON", nullable = false, length = 500)
    private String reportReason;

    /**
     * 신고일시
     * 엔티티가 처음 저장될 때 현재 시간이 자동으로 할당됩니다.
     */
    @CreationTimestamp
    @Column(name = "REPORT_DATE", nullable = false, updatable = false)
    private LocalDateTime reportDate;

}