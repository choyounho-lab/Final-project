package kr.co.kh.repository;

import kr.co.kh.model.vo.AlbumLikeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param; // 여러 파라미터 전달 시 사용

@Mapper
public interface AlbumLikeRepository {

    /**
     * 앨범에 대한 좋아요를 추가합니다.
     * @param vo 앨범 ID와 사용자 ID를 포함하는 AlbumLikeVO 객체
     * @return 삽입된 레코드 수 (일반적으로 1)
     */
    int insertAlbumLike(AlbumLikeVO vo);

    /**
     * 앨범에 대한 좋아요를 삭제합니다.
     * @param vo 앨범 ID와 사용자 ID를 포함하는 AlbumLikeVO 객체
     * @return 삭제된 레코드 수 (일반적으로 1)
     */
    int deleteAlbumLike(@Param("userId")Long userId, @Param("albumId") Long albumId);

    /**
     * 특정 사용자가 특정 앨범에 좋아요를 눌렀는지 여부를 확인합니다.
     *
     * @param albumId 앨범 ID
     * @param userId 사용자 ID
     * @return 좋아요를 눌렀으면 true, 아니면 false
     */
    boolean isAlbumLiked(@Param("albumId") Long albumId, @Param("userId") Long userId);

    // TODO: 현재 XML 매퍼에서 countAlbumLikes 쿼리를 제거했으므로,
    //       이 메서드도 주석 처리하거나 삭제합니다.
    // int countAlbumLikes(Long albumId);
}