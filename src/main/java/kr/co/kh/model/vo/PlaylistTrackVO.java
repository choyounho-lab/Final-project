package kr.co.kh.model.vo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Slf4j
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistTrackVO {
    private Long playlistId;
    private String trackId;
    private Long trackOrder;
    private Timestamp playlistTrackCreateDate;
    private String trackName;
    private String artists;
    private Long trackDuration;
    private Timestamp releaseDate;
    private String trackImageUrl;
    private String resultMsg;
    private String youtubeVideoId;
}
