package kr.co.kh.controller.album;
import lombok.RequiredArgsConstructor;
import kr.co.kh.model.vo.AlbumVO;
import kr.co.kh.service.impl.WeeklyPopularAlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WeeklyPopularAlbumController {

    private final WeeklyPopularAlbumService weeklyPopularAlbumService;

    // 최근 7일간 인기 앨범을 limit 수 만큼 조회
    @GetMapping("/api/albums/popular-weekly")
    public List<AlbumVO> getWeeklyPopularAlbums() {
        int limit = 15; // 예: 최대 15개 조회
        log.info("도달");
        List<AlbumVO> test = new ArrayList<>();
        log.info("test111{}",weeklyPopularAlbumService.getPopularAlbums(limit).toString());
        return weeklyPopularAlbumService.getPopularAlbums(limit);
    }
}
