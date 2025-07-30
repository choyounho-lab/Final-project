package kr.co.kh.controller.album;

import io.swagger.annotations.ApiOperation;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.AlbumVO;
import kr.co.kh.service.AlbumLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/album-like")
@RequiredArgsConstructor
@Slf4j
public class AlbumLikeController {

    private final AlbumLikeService albumLikeService;

    @PostMapping("/Album")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "앨범 좋아요", notes = "앨범 정보가 없는경우 ALBUM 테이블에 저장 후 팔로우 관계를 생성하고, 클라이언트에서 앨범 상세정보를 AlbumVO 형태로 전달해야함")
    public ResponseEntity<?> niceAlbum(
            @CurrentUser CustomUserDetails currentUser,
            @RequestBody AlbumVO albumData) {
        try {
            log.info("유저 ID{}가 아티스트'{}' (External ID{})를 팔로우 요청",
                    currentUser.getId(), albumData.getAlbumTitle(), albumData.getSpotifyAlbumId());
            albumLikeService.likeAlbum(currentUser.getId(), albumData);
            return ResponseEntity.ok().body("앨범좋아요 성공");
        } catch (IllegalStateException e) {
            log.warn("좋아요 실패:{}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("앨범좋아요 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("앨범 좋아요중 서버 오류 발생");
        }
    }

    @DeleteMapping("/album/{albumExternalId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "앨범 좋아요 취소", notes = "특정 앨범 좋아요를 취소합니다.")
    public ResponseEntity<?> unlikeAlbum(
            @CurrentUser CustomUserDetails currentUser,
            @PathVariable String albumExternalId) {
        try {
            log.info("유저 ID {}가 앨범 External ID {} 를 언팔로우 요청", currentUser.getId(), albumExternalId);
            albumLikeService.unNiceAlbumByExternalId(currentUser.getId(), albumExternalId);
            return ResponseEntity.ok().body("앨범 좋아요 취소 성공");

        } catch (Exception e) {
            log.error("앨범 좋아요 취소중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("앨범 좋아요 취소중 서버 오류발생");
        }
    }


    @GetMapping("/album/status/{albumExternalId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "특정 앨범 좋아요 여부 확인", notes = "현재 로그인한 사용자가 특정 앨범을 좋아요 하고 있는지 여부를 반환한다.")
    public ResponseEntity<Boolean>getLikeStatus(
            @CurrentUser CustomUserDetails currentUSer,
            @PathVariable String albumExternalId) {
        boolean isLike = albumLikeService.isLikeAlbumExternal(currentUSer.getId(), albumExternalId);
        log.info("유저 ID{}의 앨범 External ID {} 팔로우 상태:{}", currentUSer.getId(), albumExternalId, isLike);
        return ResponseEntity.ok(isLike);
    }
}



















