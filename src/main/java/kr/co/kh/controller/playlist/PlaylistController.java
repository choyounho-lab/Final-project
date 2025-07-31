package kr.co.kh.controller.playlist;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.PlaylistVO;
import kr.co.kh.service.PlaylistService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@Slf4j
@AllArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    /**
     * 플레이리스트 목록 출력
     * @param currentUser
     * @param playlistId
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "개인 플레이리스트 목록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentUser", value = "사용자 정보", dataType = "CustomUserDetails", dataTypeClass = CustomUserDetails.class, required = true),
            @ApiImplicitParam(name = "playlistId", value = "플레이리스트 아이디", dataType = "Long", required = true)
    })
    public ResponseEntity<?> selectPlaylist(@CurrentUser CustomUserDetails currentUser, @RequestParam(required = false) Long playlistId){
        PlaylistVO playlistVO = new PlaylistVO();
        playlistVO.setUserId(currentUser.getId());
        playlistVO.setPlaylistId(playlistId);
        log.info(playlistVO.toString());
        log.info(playlistService.selectPlaylist(playlistVO).toString());
        return ResponseEntity.ok(playlistService.selectPlaylist(playlistVO));
    }

    @GetMapping("/publicList")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "공개 플레이리스트 단건 조회")
    @ApiImplicitParam(name = "playlistId", value = "플레이리스트 아이디", dataType = "Long", required = true)
    public ResponseEntity<?> selectPublicPlaylist(@RequestParam Long playlistId){
        PlaylistVO playlistVO = new PlaylistVO();
        playlistVO.setPlaylistId(playlistId);

        List<PlaylistVO> playlists = playlistService.selectPlaylist(playlistVO);

        if (playlists == null || playlists.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PlaylistVO found = playlists.get(0);
        if (found.getPlaylistIsPublic() != 1) {
            return ResponseEntity.status(403).body("비공개 플레이리스트입니다.");
        }

        return ResponseEntity.ok(found);
    }

    /**
     * 플레이리스트 생성
     * @param currentUser
     * @param playlistVO
     * @return
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "개인 플레이리스트 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentUser", value = "사용자 정보", dataType = "CustomUserDetails", dataTypeClass = CustomUserDetails.class, required = true),
            @ApiImplicitParam(name = "playlistVO", value = "플레이리스트", dataType = "PlaylistVO", dataTypeClass = PlaylistVO.class, required = true)
    })
    public ResponseEntity<?> createPlaylist(@CurrentUser CustomUserDetails currentUser, @RequestBody PlaylistVO playlistVO) {
        playlistVO.setUserId(currentUser.getId());
        log.info(playlistVO.toString());
        playlistService.insertPlaylist(playlistVO);
        return ResponseEntity.ok().build();
    }

    /**
     * 플레이리스트 제목 및 공유여부 수정
     * @param playlistVO
     * @return
     */
    @PutMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "개인 플레이리스트 업데이트")
    @ApiImplicitParam(name = "playlistVO", value = "플레이리스트", dataType = "PlaylistVO", dataTypeClass = PlaylistVO.class, required = true)
    public ResponseEntity<?> updatePlaylist(@RequestBody PlaylistVO playlistVO) {

        log.info(playlistVO.toString());
        playlistService.updatePlaylist(playlistVO);
        return ResponseEntity.ok().build();
    }

    /**
     * 플레이리스트 삭제
     * @param playlistId
     * @return
     */
    @DeleteMapping("/delete/{playlistId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "개인 플레이리스트 삭제")
    @ApiImplicitParam(name = "playlistId", value = "플레이리스트 아이디", dataType = "Long", required = true)
    public ResponseEntity<?> deletePlaylist(@PathVariable Long playlistId){
        PlaylistVO playlistVO = new PlaylistVO();
        playlistVO.setPlaylistId(playlistId);
        playlistService.deletePlaylist(playlistVO);
        return ResponseEntity.ok().build();
    }

    /**
     * 플레이리스트 삭제 시 플레이리스트에 저장된 트랙 전부 삭제 용도
     * @param playlistId
     * @return
     */
    @DeleteMapping("/delete/track/{playlistId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "플레이리스트에 존재하는 트랙 전부 삭제")
    @ApiImplicitParam(name = "playlistId", value = "플레이리스트 아이디", dataType = "Long", required = true)
    public ResponseEntity<?> deletePlaylistTrackByPlaylistId(@PathVariable Long playlistId){
        PlaylistVO playlistVO = new PlaylistVO();
        playlistVO.setPlaylistId(playlistId);
        log.info(playlistId.toString());
        playlistService.deletePlaylistTrackByPlaylistId(playlistVO);
        return ResponseEntity.ok().build();
    }
    // 신규 플레이리스트 조회 컨트롤러
    @GetMapping("/public")
    @ApiOperation(value = "공개 플레이리스트 목록 조회")
    public ResponseEntity<?> selectPublicPlaylists() {
        return ResponseEntity.ok(playlistService.selectPublicPlaylists());
    }
}
