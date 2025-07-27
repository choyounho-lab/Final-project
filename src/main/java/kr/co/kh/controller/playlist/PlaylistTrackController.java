package kr.co.kh.controller.playlist;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.PlaylistTrackVO;
import kr.co.kh.model.vo.PlaylistVO;
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

    /**
     * 플레이리스트에 등록된 트랙 목록 출력
     * @param currentUser
     * @param playlistId
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "개인 플레이리스트 트랙 목록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentUser", value = "사용자 정보", dataType = "CustomUserDetails", dataTypeClass = CustomUserDetails.class, required = true),
            @ApiImplicitParam(name = "playlistId", value = "플레이리스트 아이디", dataType = "Long", required = true)
    })
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

    /**
     * 플레이리스트에 트랙 추가
     * @param currentUser
     * @param playlistTrackVO
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "개인 플레이리스트 트랙 추가")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentUser", value = "사용자 정보", dataType = "CustomUserDetails", dataTypeClass = CustomUserDetails.class, required = true),
            @ApiImplicitParam(name = "playlistTrackVO", value = "플레이리스트 트랙", dataType = "PlaylistTrackVO", dataTypeClass = PlaylistTrackVO.class, required = true)
    })
    public ResponseEntity<?> addPlaylistTrack(@CurrentUser CustomUserDetails currentUser, @RequestBody PlaylistTrackVO playlistTrackVO) {
        log.info(playlistTrackVO.toString());
        playlistTrackService.insertPlaylistTrack(playlistTrackVO);
        return ResponseEntity.ok().build();
    }

    /**
     * 플레이리스트에서 트랙 삭제
     * @param trackId
     * @param playlistId
     * @return
     */
    @DeleteMapping("/delete/playlist/{playlistId}/track/{trackId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "개인 플레이리스트 트랙 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trackId", value = "트랙 아이디", dataType = "String", required = true),
            @ApiImplicitParam(name = "playlistId", value = "플레이리스트 아이디", dataType = "Long", required = true)
    })
    public ResponseEntity<?> deletePlaylistTrack(@PathVariable String trackId, @PathVariable Long playlistId){
        PlaylistTrackVO playlistTrackVO = new PlaylistTrackVO();
        playlistTrackVO.setTrackId(trackId);
        playlistTrackVO.setPlaylistId(playlistId);
        playlistTrackService.deletePlaylistTrack(playlistTrackVO);
        return ResponseEntity.ok().build();
    }
}
