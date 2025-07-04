package kr.co.kh.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EventDetailResponse {

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
}
