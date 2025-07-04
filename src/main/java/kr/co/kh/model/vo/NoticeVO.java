package kr.co.kh.model.vo;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTICE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle의 IDENTITY 사용
    @Column(name = "NOTICE_ID")
    private Long noticeId;

    @Column(name = "NOTICE_CODE_ID", nullable = false, length = 100)
    private Long noticeCodeId;

    @Column(name = "NOTICE_TITLE", nullable = false, length = 100)
    private String noticeTitle;

    @Lob
    @Column(name = "NOTICE_CONTENT", nullable = false)
    private String noticeContent;

    @Column(name = "NOTICE_CREATE_ID", nullable = false)
    private String noticeCreateId;

    @Column(name = "NOTICE_CREATE_DATE", nullable = false)
    private LocalDateTime noticeCreateDate;

    @Column(name = "NOTICE_UPDATE_ID", nullable = false)
    private String noticeUpdateId;

    @Column(name = "NOTICE_UPDATE_DATE")
    private LocalDateTime noticeUpdateDate;
}