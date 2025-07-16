package kr.co.kh.service;

import kr.co.kh.model.vo.FollowVO;
import kr.co.kh.model.vo.ArtistVO;
import kr.co.kh.repository.FollowRepository;
import kr.co.kh.repository.ArtistRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class FollowService {

    private final FollowRepository followRepository;
    private final ArtistRepository artistRepository;

    @Transactional
    public void followArtist(Long followerUserId, ArtistVO artistDataFromClient) {
        ArtistVO existingArtist = artistRepository.selectArtistByExternalId(artistDataFromClient.getArtistExternalId());

        Long actualArtistId;

        if (existingArtist == null) {
            log.info("ARTIST 테이블에 아티스트 '{}' (External ID: {}) 정보가 없어 새로 저장합니다.",
                    artistDataFromClient.getArtistName(), artistDataFromClient.getArtistExternalId());

            artistRepository.insertArtist(artistDataFromClient);


            // insert 후 새로 조회해서 artistId 가져오기
            ArtistVO newArtist = artistRepository.selectArtistByExternalId(artistDataFromClient.getArtistExternalId());
            actualArtistId = newArtist.getArtistId();
        } else {
            log.info("ARTIST 테이블에 아티스트 '{}' (External ID: {}) 정보가 이미 존재합니다. 기존 ID 사용.",
                    existingArtist.getArtistName(), existingArtist.getArtistExternalId());
            actualArtistId = existingArtist.getArtistId();
        }

        if (followRepository.isAlreadyFollowing(followerUserId, actualArtistId)) {
            throw new IllegalStateException("이미 팔로우 중인 아티스트입니다.");
        }

        FollowVO followVO = new FollowVO();
        followVO.setUserId(followerUserId);
        followVO.setArtistId(actualArtistId);
        followRepository.insertFollow(followVO);

        log.info("유저 ID {}가 아티스트 ID {}를 팔로우했습니다.", followerUserId, actualArtistId);
    }

    @Transactional
    public void unfollowArtist(Long followerUserId, Long artistId) {
        int deletedRows = followRepository.deleteFollow(followerUserId, artistId);
        if (deletedRows == 0) {
            log.warn("유저 ID {}가 아티스트 ID {}를 언팔로우하려 했으나, 해당 팔로우 관계가 존재하지 않습니다.", followerUserId, artistId);
        } else {
            log.info("유저 ID {}가 아티스트 ID {}를 언팔로우했습니다.", followerUserId, artistId);
        }
    }

    public boolean isFollowing(Long followerUserId, Long artistId) {
        return followRepository.isAlreadyFollowing(followerUserId, artistId);
    }

    public boolean isFollowingExternal(Long userId, String artistExternalId) {
        ArtistVO artist = artistRepository.selectArtistByExternalId(artistExternalId);
        if (artist == null) {
            return false;
        }
        return followRepository.isAlreadyFollowing(userId, artist.getArtistId());
    }

    @Transactional
    public void unfollowArtistByExternalId(Long userId, String artistExternalId) {
        ArtistVO artist = artistRepository.selectArtistByExternalId(artistExternalId);
        if (artist == null) {
            throw new IllegalArgumentException("해당 아티스트가 존재하지 않습니다.");
        }
        followRepository.deleteFollow(userId, artist.getArtistId());
        log.info("유저 ID {}가 아티스트 External ID {}를 언팔로우했습니다.", userId, artistExternalId);
    }
}
