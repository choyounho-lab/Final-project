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
public class PlaylistTrackVO {
    private Long playlistId;
    private String trackId;
    private Long trackOrder;
    private LocalDate playlistTrackCreateDate;
    private String trackName;
    private String artists;
    private Long trackDuration;
    private LocalDate releaseDate;
    private String trackImageUrl;
    private String resultMsg;
}
