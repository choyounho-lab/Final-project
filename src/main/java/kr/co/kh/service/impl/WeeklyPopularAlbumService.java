package kr.co.kh.service.impl;

import kr.co.kh.model.vo.AlbumVO;
import kr.co.kh.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeeklyPopularAlbumService {

    private final AlbumRepository albumRepository;

    public WeeklyPopularAlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    /**
     * 최근 7일간 좋아요가 많은 인기 앨범을 조회합니다.
     * @param limit 조회할 앨범 수 제한
     * @return 인기 앨범 리스트``
     */
    public List<AlbumVO> getPopularAlbums(int limit) {
        return albumRepository.selectWeeklyPopularAlbums(limit);
    }
}
