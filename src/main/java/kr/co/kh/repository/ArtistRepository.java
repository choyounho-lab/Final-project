package kr.co.kh.repository;

import kr.co.kh.model.vo.ArtistVO; // ArtistVO 임포트
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper // 이 인터페이스가 MyBatis의 매퍼임을 나타냅니다.
public interface ArtistRepository {

    /**
     * 특정 ARTIST_ID에 해당하는 아티스트 정보를 조회합니다.
     * @param artistId 조회할 아티스트의 고유 ID
     * @return 조회된 ArtistVO 객체, 없으면 null
     */
    ArtistVO selectArtistById(@Param("artistId") Long artistId);

    /**
     * 외부 시스템 ID (ARTIST_EXTERNAL_ID)로 아티스트 정보를 조회합니다.
     * @param artistExternalId 외부 시스템의 아티스트 ID
     * @return 조회된 ArtistVO 객체, 없으면 null
     */
    ArtistVO selectArtistByExternalId(@Param("artistExternalId") String artistExternalId);

    /**
     * 새로운 아티스트 정보를 데이터베이스에 삽입합니다.
     * @param artistVO 삽입할 아티스트 정보를 담은 ArtistVO 객체. 삽입 후 DB에서 생성된 artistId가 이 객체에 채워집니다.
     */
    void insertArtist(ArtistVO artistVO); // ArtistVO 사용

    /**
     * 모든 아티스트 목록을 조회합니다.
     * @return ArtistVO 객체 리스트
     */
    List<ArtistVO> selectAllArtists();
}