package kr.co.kh.controller.follow;

import io.swagger.annotations.ApiOperation;
import kr.co.kh.model.vo.ArtistVO;
import kr.co.kh.service.FollowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/popular-artists")
@Slf4j
@AllArgsConstructor
public class PopularArtistController {

    private final FollowService followService;

    @GetMapping("/weekly")
    @ApiOperation(value = "주간 인기 아티스트 조회", notes = "주간 팔로잉 수 기준 인기 아티스트 목록 반환")
    public ResponseEntity<List<ArtistVO>> getWeeklyPopularArtists(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int fetch
    ) {
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        if (startDate == null) {
            startDate = endDate.minusDays(7);
        }
        log.info("주간 인기 아티스트 조회 요청: {} ~ {}, offset: {}, fetch: {}", startDate, endDate, offset, fetch);

        List<ArtistVO> popularArtists = followService.getWeeklyPopularArtists(
                startDate.toString(),
                endDate.toString(),
                offset,
                fetch);

        return ResponseEntity.ok(popularArtists);
    }
}
