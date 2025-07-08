package kr.co.kh.model.vo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistTrackVO {
    private Long playlistId;
    private Long trackId;
    private Long trackOrder;
    private String resultMsg;
}
