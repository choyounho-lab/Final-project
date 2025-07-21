package kr.co.kh.model.vo;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class AlbumLikeVO {

    private Long likeId;      // LIKE_ID (NUMBER GENERATED ALWAYS AS IDENTITY) - 좋아요 고유 ID
    private Long albumId;     // ALBUM_ID (NUMBER) - 좋아요를 누른 앨범의 ID
    private Long userId;      // USER_ID (NUMBER) - 좋아요를 누른 사용자 ID
    private Timestamp createdAt; // CREATED_AT (TIMESTAMP) - 좋아요 생성 시각 (기본값: SYSTIMESTAMP)

    // @Data가 모든 getter/setter/toString 등을 자동으로 생성해줍니다.
}
