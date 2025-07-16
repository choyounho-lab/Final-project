package kr.co.kh.model.vo;

import lombok.Data; // @Data 애너테이션을 사용하기 위해 import 합니다.
import java.sql.Timestamp; // FOLLOW_DATE 컬럼을 위해 import 합니다.

@Data // 이 애너테이션이 @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor를 자동으로 생성해줍니다.
public class FollowVO {
    private Long followId; // FOLLOW_ID (NUMBER GENERATED ALWAYS AS IDENTITY) - DB에서 자동 생성되지만, 조회 시 사용됩니다.
    private Long userId; // USER_ID (NUMBER NOT NULL) - 팔로우한 사용자 ID
    private Long artistId; // ARTIST_ID (NUMBER NOT NULL) - 팔로우 당한 아티스트 ID
    private Timestamp followDate; // FOLLOW_DATE (TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL) - 팔로우 시점

    // Lombok의 @Data 애너테이션이 위 필드들에 대한
    // getter, setter, toString(), equals(), hashCode() 메서드를 자동으로 생성해줍니다.
    // 따라서 별도로 코드를 작성할 필요가 없습니다.
}