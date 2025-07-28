package kr.co.kh.model.vo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Getter
@Setter
@Slf4j
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrackVO {
    private String trackId;
    private String trackName;
    private String artists;
    private Long trackDuration;
    private LocalDate releaseDate;
    private String trackImageUrl;
    private String resultMsg;
    private Long userId;
    private LocalDate playDate;
    private String youtubeVideoId;
}
