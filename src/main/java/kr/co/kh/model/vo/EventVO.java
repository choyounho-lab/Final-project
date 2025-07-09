package kr.co.kh.model.vo;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime; // DATE, TIMESTAMP 타입 매핑에 적합

@Entity
@Table(name = "ADMIN_EVENT") // SQL 테이블명과 정확히 일치하도록 수정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString // 디버깅 편의를 위해 추가
public class EventVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle의 IDENTITY 사용
    @Column(name = "EVENT_ID") // SQL 컬럼명과 일치
    private Long eventId;

    @Column(name = "EVENT_CODE", nullable = false) // NUMBER(11) -> Long
    private Long eventCode;

    @Column(name = "EVENT_TITLE", nullable = false, length = 1000) // NVARCHAR2(1000) -> String
    private String eventTitle;

    @Lob // CLOB 타입 매핑을 위해 @Lob 어노테이션 사용
    @Column(name = "EVENT_CONTENT") // CLOB -> String, nullable은 DDL에 따라 조정 (CLOB은 기본적으로 nullable)
    private String eventContent;

    @Column(name = "EVENT_CREATE_ID", nullable = false, length = 30) // VARCHAR2(30) -> String
    private String eventCreateId;

    @Column(name = "EVENT_CREATE_DATE", nullable = false) // TIMESTAMP -> LocalDateTime
    private LocalDateTime eventCreateDate;

    // DDL에서 EVENT_UPDATE_ID가 NOT NULL이었으나, 일반적으로 초기 등록 시에는 NULL일 수 있어 nullable=true로 설정합니다.
    // 만약 항상 값이 있어야 한다면 DDL과 동일하게 nullable=false로 설정해야 합니다.
    @Column(name = "EVENT_UPDATE_ID", length = 30) // VARCHAR2(30) -> String
    private String eventUpdateId;

    @Column(name = "EVENT_UPDATE_DATE") // TIMESTAMP -> LocalDateTime
    private LocalDateTime eventUpdateDate;

    @Column(name = "EVENT_START_DATE", nullable = false) // DATE -> LocalDateTime (시간 정보 포함)
    private LocalDateTime eventStartDate;

    @Column(name = "EVENT_END_DATE", nullable = false) // DATE -> LocalDateTime (시간 정보 포함)
    private LocalDateTime eventEndDate;

}