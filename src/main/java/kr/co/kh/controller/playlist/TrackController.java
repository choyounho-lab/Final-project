package kr.co.kh.controller.playlist;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.PlaylistVO;
import kr.co.kh.model.vo.TrackVO;
import kr.co.kh.service.TrackService;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/track")
public class TrackController {

    private final TrackService trackService;

    /**
     * 검색
     * @param trackId
     * @return
     */
    @GetMapping("list")
    @ApiOperation(value = "트랙 목록")
    @ApiImplicitParam(name = "trackVO", value = "트랙", dataType = "TrackVO", dataTypeClass = TrackVO.class, required = true)
    public ResponseEntity<?> selectTrack(@RequestParam String trackId){
        log.info(trackService.selectTrack(trackId).toString());
        return ResponseEntity.ok(trackService.selectTrack(trackId));
    }

    /**
     * DB에 곡 정보가 없을 경우 재생 시 DB에 곡 정보 저장
     * @param trackVO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "재생한 트랙 DB에 추가")
    @ApiImplicitParam(name = "trackVO", value = "트랙", dataType = "TrackVO", dataTypeClass = TrackVO.class, required = true)
    public ResponseEntity<?> insertTrack(@RequestBody TrackVO trackVO){
        trackService.insertTrack(trackVO);
        return ResponseEntity.ok().build();
    }


    /**
     * 곡 정보와 재생했던 시각 목록 같이 출력
     * @param trackVO
     * @return
     */
    @GetMapping("/list/date")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "트랙 날짜 목록")
    @ApiImplicitParam(name = "trackVO", value = "트랙", dataType = "TrackVO", dataTypeClass = TrackVO.class, required = true)
    public ResponseEntity<?> selectTrackListDate(@RequestBody TrackVO trackVO){
        log.info(trackService.selectTrackPlayDate(trackVO).toString());
        return ResponseEntity.ok(trackService.selectTrackPlayDate(trackVO));
    }

    /**
     * 곡 재생한 시각 DB에 저장
     * @param currentUser
     * @param trackVO
     * @return
     */
    @PostMapping("/save/date")
    @ApiOperation(value = "재생한 트랙날짜 DB에 추가")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentUser", value = "사용자 정보", dataType = "CustomUserDetails", dataTypeClass = CustomUserDetails.class, required = true),
            @ApiImplicitParam(name = "trackVO", value = "트랙", dataType = "TrackVO", dataTypeClass = TrackVO.class, required = true)
    })
    public ResponseEntity<?> insertTrackPlayDate(
            @CurrentUser CustomUserDetails currentUser,
            @RequestBody TrackVO trackVO) {
        if (currentUser != null) {
            trackVO.setUserId(currentUser.getId());
        } else {
            trackVO.setUserId(0L);
        }
        trackService.insertTrackPlayDate(trackVO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/saveYoutubeVideoId")
    @ApiOperation(value = "유튜브 비디오 아이디 저장")
    @ApiImplicitParam(name = "trackVO", value = "트랙", dataType = "TrackVO", dataTypeClass = TrackVO.class, required = true)
    public ResponseEntity<?> saveYoutubeVideoId(@RequestBody TrackVO trackVO) {
        log.info(trackVO.toString());
        trackService.updateTrackYoutubeVideoId(trackVO);
        return ResponseEntity.ok().build();
    }

}
