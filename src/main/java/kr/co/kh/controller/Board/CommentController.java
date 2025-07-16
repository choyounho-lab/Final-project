package kr.co.kh.controller.Board;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.kh.model.vo.CommentsVO;
import kr.co.kh.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "댓글 관리 API")
@RestController
@RequestMapping("/api/comment")
@Slf4j
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성합니다.
public class CommentController {

    private final CommentService commentService;

    /**
     * 새로운 댓글을 등록합니다.
     * React에서 전송된 댓글 정보를 받아 DB에 저장합니다.
     *
     * @param commentsVO 댓글 정보를 담은 객체 (JSON 형태)
     * @return 생성된 댓글 정보와 HTTP 상태 코드 201 (Created)
     */
    @PostMapping("/")
    @ApiOperation(value = "댓글 등록", notes = "새로운 댓글을 생성하여 데이터베이스에 저장합니다.")
    public ResponseEntity<?> addComment(
            @ApiParam(value = "등록할 댓글 정보", required = true) @RequestBody CommentsVO commentsVO) {
        try {
            log.info("새 댓글 등록 요청: {}", commentsVO);
//            CommentsVO savedComment = commentService.addComment(commentVO);
//            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("댓글 등록 중 오류 발생", e);
            return new ResponseEntity<>("댓글 등록에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    /**
     * 특정 대상에 대한 댓글 목록을 조회합니다.
     * 예: /api/comment/list?targetType=ALBUM&targetId=123
     *
     * @param targetType 댓글을 조회할 대상의 유형 (예: 'ALBUM', 'NOTICE')
     * @param targetId   댓글을 조회할 대상의 고유 ID
     * @return 댓글 목록과 HTTP 상태 코드 200 (OK)
     */
    @GetMapping("/list")
    @ApiOperation(value = "댓글 목록 조회", notes = "특정 대상(앨범, 공지사항 등)에 달린 댓글 목록을 조회합니다.")
    public ResponseEntity<?> getComments(
            @ApiParam(value = "대상 유형 (예: ALBUM, NOTICE)", required = true) @RequestParam String targetType,
            @ApiParam(value = "대상 ID", required = true) @RequestParam Long targetId) {
        try {
            log.info("댓글 목록 조회 요청: targetType={}, targetId={}", targetType, targetId);
//            List<CommentVO> comments = commentService.getCommentsByTarget(targetType, targetId);
//            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            log.error("댓글 목록 조회 중 오류 발생", e);
            return new ResponseEntity<>("댓글 목록을 불러오는 데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
