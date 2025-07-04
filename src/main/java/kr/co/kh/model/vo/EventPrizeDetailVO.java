package kr.co.kh.model.vo;

import lombok.*;

import java.time.LocalDateTime;

// AdminEventVO와 EventPrizeVO의 주요 정보를 결합하여 조회할 때 사용
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EventPrizeDetailVO {

    // ADMIN_EVENT 테이블 컬럼
    private Long eventId;
    private Long eventCode;
    private String eventTitle;
    private String eventContent;
    private String eventCreateId;
    private LocalDateTime eventCreateDate;
    private String eventUpdateId;
    private LocalDateTime eventUpdateDate;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;

    // EVENT_PRIZE 테이블 컬럼
    private Long prizeId;
    private String prizeName;
    private Long quantity;
    private String prizeDesc;

    // 필요에 따라 추가적인 필드를 정의할 수 있습니다.
    // 예: 당첨자 정보가 필요하다면 EventWinnerVO의 필드도 추가 가능
}
