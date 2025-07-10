package kr.co.kh.controller.playlist;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

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

    @GetMapping
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
}
