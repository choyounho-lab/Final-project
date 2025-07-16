package kr.co.kh.model.vo;

import lombok.Data; // @Data 애너테이션을 사용하기 위해 import 합니다.
import java.sql.Timestamp; // DEBUT_DATE 컬럼을 위해 import 합니다.

@Data // 이 애너테이션이 @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor를 자동으로 생성해줍니다.
public class ArtistVO {

    private Long artistId; // ARTIST_ID (NUMBER GENERATED ALWAYS AS IDENTITY) - DB에서 자동 생성되지만, 조회 시 사용됩니다.
    private Long entId; // ENT_ID (NUMBER) - 소속 엔터테인먼트 ID
    private String artistName; // ARTIST_NAME (VARCHAR2(255)) - 아티스트 이름
    private String artistBio; // ARTIST_BIO (CLOB) - 아티스트 바이오그래피 (CLOB은 String으로 매핑)
    private Timestamp debutDate; // DEBUT_DATE (TIMESTAMP(6)) - 데뷔 날짜
    private String profileImage; // PROFILE_IMAGE (VARCHAR2(500)) - 프로필 이미지 경로/URL
    private String artistExternalId; // ARTIST_EXTERNAL_ID (VARCHAR2(255)) - 외부 시스템 아티스트 ID

    // Lombok의 @Data 애너테이션이 위 필드들에 대한
    // getter, setter, toString(), equals(), hashCode() 메서드를 자동으로 생성해줍니다.
    // 따라서 별도로 코드를 작성할 필요가 없습니다.
}