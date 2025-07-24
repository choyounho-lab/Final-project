package kr.co.kh.repository;

import kr.co.kh.model.vo.AlbumVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper // 이 인터페이스가 MyBatis 매퍼임을 나타냅니다.
public interface AlbumRepository {

    /**
     * 새로운 앨범 정보를 데이터베이스에 삽입합니다.
     * ALBUM_ID는 DB에서 자동으로 생성됩니다.
     * @param album 삽입할 앨범 정보를 담고 있는 AlbumVO 객체.
     */
    void insertAlbum(AlbumVO album);

    /**
     * 내부 앨범 ID(ALBUM_ID)를 기준으로 앨범 정보를 조회합니다.
     * @param albumId 조회할 앨범의 내부 고유 ID.
     * @return 조회된 AlbumVO 객체, 해당 ID의 앨범이 없으면 null.
     */
    AlbumVO selectAlbumById(Long albumId);

    /**
     * Spotify 앨범 ID(SPOTIFY_ALBUM_ID)를 기준으로 앨범 정보를 조회합니다.
     * 이 ID는 앨범의 외부 고유 식별자입니다.
     * @param spotifyAlbumId 조회할 앨범의 Spotify ID.
     * @return 조회된 AlbumVO 객체, 해당 Spotify ID의 앨범이 없으면 null.
     */
    AlbumVO selectAlbumBySpotifyId(String spotifyAlbumId);

    /**
     * 데이터베이스에 저장된 모든 앨범 정보를 조회합니다.
     * 결과는 앨범 제목(ALBUM_TITLE)을 기준으로 오름차순 정렬됩니다.
     * @return 모든 앨범 정보를 담고 있는 AlbumVO 리스트. 앨범이 없으면 빈 리스트 반환.
     */
    List<AlbumVO> selectAllAlbums();

    /**
     * 앨범 정보를 업데이트합니다.
     * 앨범의 내부 ID(ALBUM_ID)를 기준으로 해당 앨범의 제목, 유형, 발매일, 총 트랙 수, 커버 이미지를 변경합니다.
     * @param album 업데이트할 앨범 정보를 담고 있는 AlbumVO 객체. ALBUM_ID 필드가 필수적으로 포함되어야 합니다.
     * @return 업데이트된 레코드의 수 (성공 시 1).
     */
    int updateAlbum(AlbumVO album);

    /**
     * 내부 앨범 ID(ALBUM_ID)를 기준으로 앨범 정보를 데이터베이스에서 삭제합니다.
     * @param albumId 삭제할 앨범의 내부 고유 ID.
     * @return 삭제된 레코드의 수 (성공 시 1).
     */
    int deleteAlbum(Long albumId);

    /**
     * 최근 7일간 좋아요가 많은 인기 앨범을 조회합니다.
     * @param limit 조회할 앨범 수 제한
     * @return 인기 앨범 리스트
     */
    List<AlbumVO> selectWeeklyPopularAlbums(int limit);
}