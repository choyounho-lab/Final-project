package kr.co.kh.controller.playlist;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.payload.response.UserResponse;
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


    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "개인 플레이리스트 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentUser", value = "사용자 정보", dataType = "CustomUserDetails", dataTypeClass = CustomUserDetails.class, required = true),
            @ApiImplicitParam(name = "playlist", value = "플레이리스트", dataType = "PlaylistVO", dataTypeClass = PlaylistVO.class, required = true)
    })
    public ResponseEntity<?> getUserProfile(@CurrentUser CustomUserDetails currentUser, @RequestBody PlaylistVO playlistVO) {
//        UserResponse userResponse = new UserResponse(currentUser.getUsername(), currentUser.getEmail(), currentUser.getRoles());

        playlistVO.setUserId(currentUser.getId());
        log.info(playlistVO.toString());
        playlistService.insertPlaylist(playlistVO);
        return ResponseEntity.ok().build();
    }
}
