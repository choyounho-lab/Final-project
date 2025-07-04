package kr.co.kh.model.vo;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "EVENT_PRIZE") // SQL 테이블명과 정확히 일치
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString // 디버깅 편의를 위해 추가
public class EventPrizeVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle의 IDENTITY 사용
    @Column(name = "PRIZE_ID") // SQL 컬럼명과 일치
    private Long prizeId;

    @Column(name = "EVENT_ID") // NUMBER -> Long
    private Long eventId;

    @Column(name = "PRIZE_NAME", length = 100) // VARCHAR2(100) -> String
    private String prizeName;

    @Column(name = "QUANTITY") // NUMBER -> Long 또는 Integer (수량에 따라)
    private Long quantity; // 수량이 많을 수 있으므로 Long으로 설정

    // DESC는 Description의 약자로 데이터베이스에서 "설명"을 나타내는 데 널리 사용되는 약어
    @Column(name = "PRIZE_DESC", length = 1000) // VARCHAR2(1000) -> String
    private String prizeDesc;

    // 외래 키 관계는 JPA에서 @ManyToOne 등으로 매핑할 수 있으나,
    // 여기서는 단순 VO이므로 직접적인 엔티티 관계 매핑은 포함하지 않습니다.
    // 필요시 AdminEventVO와 관계를 설정할 수 있습니다.
}




