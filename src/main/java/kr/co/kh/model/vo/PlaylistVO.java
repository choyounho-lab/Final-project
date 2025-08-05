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
public class PlaylistVO {
    private Long playlistId;
    private Long userId;
    private String playlistTitle;
    private Long playlistIsPublic;
    private LocalDate playlistCreateDate;
    private String resultMsg;
}
