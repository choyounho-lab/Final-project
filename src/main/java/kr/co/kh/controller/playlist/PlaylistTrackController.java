package kr.co.kh.controller.playlist;

import io.swagger.annotations.ApiOperation;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.PlaylistTrackVO;
import kr.co.kh.service.PlaylistTrackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlistTrack")
@Slf4j
@AllArgsConstructor
public class PlaylistTrackController {

    private final PlaylistTrackService playlistTrackService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "개인 플레이리스트 트랙 목록")
    public ResponseEntity<?> PlaylistTrackList(
            @CurrentUser CustomUserDetails currentUser,
            @RequestParam Long playlistId
    ) {
        PlaylistTrackVO playlistTrackVO = new PlaylistTrackVO();
        playlistTrackVO.setPlaylistId(playlistId);
        log.info(playlistTrackVO.toString());
        log.info(playlistTrackService.selectPlaylistTrack(playlistTrackVO).toString());
        return ResponseEntity.ok(playlistTrackService.selectPlaylistTrack(playlistTrackVO));
    }


    @PostMapping("/save")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "개인 플레이리스트 트랙 추가")
    public ResponseEntity<?> addPlaylistTrack(@CurrentUser CustomUserDetails currentUser, @RequestBody PlaylistTrackVO playlistTrackVO) {
        log.info(playlistTrackVO.toString());
        playlistTrackService.insertPlaylistTrack(playlistTrackVO);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/delete/playlist/{playlistId}/track/{trackId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "개인 플레이리스트 트랙 삭제")
    public ResponseEntity<?> deletePlaylistTrack(@PathVariable String trackId, @PathVariable Long playlistId){
        PlaylistTrackVO playlistTrackVO = new PlaylistTrackVO();
        playlistTrackVO.setTrackId(trackId);
        playlistTrackVO.setPlaylistId(playlistId);
        playlistTrackService.deletePlaylistTrack(playlistTrackVO);
        return ResponseEntity.ok().build();
    }
}
