package kr.co.kh.model.vo;

import lombok.Data;
import java.sql.Date; // 또는 java.time.LocalDate

@Data
public class AlbumVO {

    private Long albumId;               // ALBUM_ID: 내부 DB에서 사용하는 고유 ID (NUMBER GENERATED ALWAYS AS IDENTITY)
    private String spotifyAlbumId;      // SPOTIFY_ALBUM_ID: 외부 Spotify API용 ID (VARCHAR2(100) UNIQUE NOT NULL)
    private String albumTitle;          // ALBUM_TITLE: 앨범 제목 (VARCHAR2(255) NOT NULL)
    private String albumType;           // ALBUM_TYPE: 앨범 유형 (VARCHAR2(50))
    private Date albumReleaseDate;      // ALBUM_RELEASE_DATE: 앨범 발매일 (DATE)
    private Integer albumTotalTracks;   // ALBUM_TOTAL_TRACKS: 총 트랙 수 (NUMBER)
    private String albumCoverImage;     // ALBUM_COVER_IMAGE: 커버 이미지 URL (VARCHAR2(500))

    // Lombok @Data 어노테이션으로 인해 Getter, Setter, equals, hashCode, toString 등이 자동 생성됩니다.
    // 따라서 별도의 생성자나 메서드 코드를 작성할 필요는 없습니다.
}