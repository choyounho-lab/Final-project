package kr.co.kh.model.vo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrackVO {
    private Long trackId;
    private Long albumId;
    private String TrackTitle;
    private Long TrackDuration;
    private String TrackAudioUrl;
    private String Lyrics;
    private String TrackArtistName;
}
