package kr.co.kh.controller.playlist;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
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

    @GetMapping("list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "트랙 목록")
    public ResponseEntity<?> selectTrack(@RequestBody TrackVO trackVO){
        log.info(trackService.selectTrack(trackVO).toString());
        return ResponseEntity.ok(trackService.selectTrack(trackVO));
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "재생한 트랙 DB에 추가")
    public ResponseEntity<?> insertTrack(@RequestBody TrackVO trackVO){
        trackService.insertTrack(trackVO);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/list/date")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "트랙 날짜 목록")
    public ResponseEntity<?> selectTrackListDate(@RequestBody TrackVO trackVO){
        log.info(trackService.selectTrackPlayDate(trackVO).toString());
        return ResponseEntity.ok(trackService.selectTrackPlayDate(trackVO));
    }


    @PostMapping("/save/date")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "재생한 트랙날짜 DB에 추가")
    public ResponseEntity<?> insertTrackPlayDate(
            @CurrentUser CustomUserDetails currentUser,
            @RequestBody TrackVO trackVO) {
        trackVO.setUserId(currentUser.getId());
        trackService.insertTrackPlayDate(trackVO);
        return ResponseEntity.ok().build();
    }


}
