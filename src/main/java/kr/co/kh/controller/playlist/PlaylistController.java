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

@RestController
@RequestMapping("/api/playlist")
@Slf4j
@AllArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "개인 플레이리스트 목록")
    @ApiImplicitParam(name = "currentUser", value = "사용자 정보", dataType = "CustomUserDetails", dataTypeClass = CustomUserDetails.class, required = true)
    public ResponseEntity<?> selectPlaylist(@CurrentUser CustomUserDetails currentUser, @RequestParam(required = false) Long playlistId){
        PlaylistVO playlistVO = new PlaylistVO();
        playlistVO.setUserId(currentUser.getId());
        playlistVO.setPlaylistId(playlistId);
        log.info(playlistVO.toString());
        return ResponseEntity.ok(playlistService.selectPlaylist(playlistVO));
    }

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
}
