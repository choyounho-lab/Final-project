package kr.co.kh.controller.follow;

import io.swagger.annotations.ApiOperation;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.ArtistVO;
import kr.co.kh.service.FollowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@Slf4j
@AllArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/artist")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "아티스트 팔로우", notes = "아티스트 정보가 없는 경우 ARTIST 테이블에 저장 후 팔로우 관계를 생성합니다. 클라이언트에서 아티스트 상세 정보를 ArtistVO 형태로 전달해야 합니다.")
    public ResponseEntity<?> followArtist(
            @CurrentUser CustomUserDetails currentUser,
            @RequestBody ArtistVO artistData) {
        try {
            log.info("유저 ID {}가 아티스트 '{}' (External ID: {})를 팔로우 요청",
                    currentUser.getId(), artistData.getArtistName(), artistData.getArtistExternalId());
            followService.followArtist(currentUser.getId(), artistData);
            return ResponseEntity.ok().body("아티스트 팔로우 성공");
        } catch (IllegalStateException e) {
            log.warn("팔로우 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("아티스트 팔로우 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("아티스트 팔로우 중 서버 오류 발생");
        }
    }

    @DeleteMapping("/artist/{artistExternalId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "아티스트 언팔로우", notes = "특정 아티스트를 언팔로우합니다.")
    public ResponseEntity<?> unfollowArtist(
            @CurrentUser CustomUserDetails currentUser,
            @PathVariable String artistExternalId) {
        try {
            log.info("유저 ID {}가 아티스트 External ID {}를 언팔로우 요청", currentUser.getId(), artistExternalId);
            followService.unfollowArtistByExternalId(currentUser.getId(), artistExternalId);
            return ResponseEntity.ok().body("아티스트 언팔로우 성공");
        } catch (Exception e) {
            log.error("아티스트 언팔로우 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("아티스트 언팔로우 중 서버 오류 발생");
        }
    }

    @GetMapping("/artist/status/{artistExternalId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "특정 아티스트 팔로우 여부 확인", notes = "현재 로그인한 사용자가 특정 아티스트를 팔로우하고 있는지 여부를 반환합니다.")
    public ResponseEntity<Boolean> getFollowStatus(
            @CurrentUser CustomUserDetails currentUser,
            @PathVariable String artistExternalId) {
        boolean isFollowing = followService.isFollowingExternal(currentUser.getId(), artistExternalId);
        log.info("유저 ID {}의 아티스트 External ID {} 팔로우 상태: {}", currentUser.getId(), artistExternalId, isFollowing);
        return ResponseEntity.ok(isFollowing);
    }
}
