package kr.co.kh.repository;

import kr.co.kh.model.vo.FollowVO; // FollowVO 임포트
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FollowRepository {

    /**
     * 새로운 팔로우 관계를 데이터베이스에 삽입합니다.
     * @param followVO 팔로우할 사용자 ID와 아티스트 ID를 담은 FollowVO 객체
     */
    void insertFollow(FollowVO followVO);

    /**
     * 특정 팔로우 관계를 데이터베이스에서 삭제합니다.
     * @param userId 팔로우한 사용자 ID
     * @param artistId 팔로우 당한 아티스트 ID
     * @return 삭제된 레코드 수 (성공 시 1, 실패 시 0)
     */
    int deleteFollow(@Param("userId") Long userId, @Param("artistId") Long artistId);

    /**
     * 특정 사용자가 특정 아티스트를 이미 팔로우하고 있는지 확인합니다.
     * @param userId 팔로우한 사용자 ID
     * @param artistId 팔로우 당한 아티스트 ID
     * @return 이미 팔로우 중이면 true, 아니면 false
     */
    boolean isAlreadyFollowing(@Param("userId") Long userId, @Param("artistId") Long artistId);

    /**
     * 특정 사용자가 팔로우하는 모든 아티스트 ID 목록을 조회합니다.
     * @param userId 팔로우한 사용자 ID
     * @return 팔로우하는 아티스트 ID 목록
     */
    List<Long> selectFollowedArtistIdsByUserId(Long userId);
}