package kr.co.kh.service;

import kr.co.kh.model.vo.AlbumLikeVO;
import kr.co.kh.model.vo.AlbumVO; // AlbumService를 사용하기 위해 AlbumVO 필요
import kr.co.kh.repository.AlbumLikeRepository;
import kr.co.kh.repository.AlbumRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class AlbumLikeService {

    private final AlbumLikeRepository albumLikeRepository;
    private final AlbumRepository albumRepository;


    @Transactional
    public void likeAlbum(Long userId, AlbumVO albumDataFromClient) {
        AlbumVO niceAlbum = albumRepository.selectAlbumBySpotifyId(albumDataFromClient.getSpotifyAlbumId());

        Long actualAlbumId;

        if (niceAlbum == null) {
            log.info("Album 테이블에 앨범 '{}' (SpotifyAlbumId ID: {}) 정보가 없어 새로 저장합니다.",
                    albumDataFromClient.getAlbumTitle(), albumDataFromClient.getSpotifyAlbumId());

            albumRepository.insertAlbum(albumDataFromClient);

            // insert 후 새로 조회해서 albumId 가져오기
            AlbumVO newAlbum = albumRepository.selectAlbumBySpotifyId(albumDataFromClient.getSpotifyAlbumId());
            actualAlbumId = newAlbum.getAlbumId();
        } else {
            log.info("Album 테이블에 앨범 '{}' (SpotifyAlbumId ID: {}) 정보가 이미 존재합니다. 기존 ID 사용.",
                    niceAlbum.getAlbumTitle(), niceAlbum.getSpotifyAlbumId());
            actualAlbumId = niceAlbum.getAlbumId();
        }

        if (albumLikeRepository.isAlbumLiked(actualAlbumId, userId)) {
            throw new IllegalStateException("이미 좋아요 표시를 한 앨범입니다");
        }

        AlbumLikeVO albumLikeVO = new AlbumLikeVO();
        albumLikeVO.setUserId(userId);
        albumLikeVO.setAlbumId(actualAlbumId);
        albumLikeRepository.insertAlbumLike(albumLikeVO);

        log.info("유저ID {}가 앨범ID {} 를 좋아요 표시했습니다.", userId, actualAlbumId);
    }

    @Transactional
    public void unniceAlbum(Long userId, Long albumId) {
        int deletedRows = albumLikeRepository.deleteAlbumLike(userId, albumId);
        if (deletedRows == 0) {
            log.warn("유저 ID {}가 앨범 ID {}를 좋아요취소 하려 했으나, 해당 관계가 존재하지 않습니다.", userId, albumId);
        } else {
            log.info("유저 ID {} 가 앨범 ID {}를 좋아요 취소 했습니다", userId, albumId);
        }
    }

    public boolean isNiceAlbum(Long userId, Long albumId) {
        return albumLikeRepository.isAlbumLiked(userId, albumId);
    }

    public boolean isLikeAlbumExternal(Long userId, String albumExternalId) {
        AlbumVO album = albumRepository.selectAlbumBySpotifyId(albumExternalId);
        if (album == null) {
            return false;
        }
        return albumLikeRepository.isAlbumLiked(userId, album.getAlbumId());
    }

    @Transactional
    public  void unNiceAlbumByExternalId(Long userId, String albumExternalId){
        AlbumVO album = albumRepository.selectAlbumBySpotifyId(albumExternalId);
        if (album == null){
            throw new IllegalArgumentException("해당 앨범이 존재하지 않습니다");
        }
        albumLikeRepository.deleteAlbumLike(userId,album.getAlbumId());
        log.info("유저 ID {}가 앨범 External ID {} 를 좋아요 취소 했습니다", userId, albumExternalId);
    }
}


