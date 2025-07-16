package kr.co.kh.controller.admin;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.payload.request.BoardDeleteRequest;
import kr.co.kh.model.payload.request.BoardRequest;
import kr.co.kh.model.payload.response.ApiResponse;
import kr.co.kh.model.vo.CommentReportVO;
import kr.co.kh.model.vo.CommentsVO;
import kr.co.kh.model.vo.SearchHelper;
import kr.co.kh.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/board")
@Slf4j
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시물 목록
     * @param request
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "게시물 목록 조회")
    @ApiImplicitParam(name = "request", value = "검색 객체", dataType = "SearchHelper", dataTypeClass = SearchHelper.class, required = true)
    public ResponseEntity<?> boardList(
            @ModelAttribute SearchHelper request
    ) {
        log.info(request.toString());
        return ResponseEntity.ok(boardService.selectBoard(request));

    }

    /**
     * 게시물 조회
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "게시물 1건 조회")
    @ApiImplicitParam(name = "id", value = "게시물 id", dataType = "Long", dataTypeClass = Long.class, required = true)
    public ResponseEntity<?> boardView(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.boardInfo(id));
    }

    /**
     * 저장
     * @param currentUser
     * @param boardRequest
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "게시물 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentUser", value = "사용자 정보", dataType = "CustomUserDetails", dataTypeClass = CustomUserDetails.class, required = true),
            @ApiImplicitParam(name = "boardRequest", value = "게시물 요청 VO", dataType = "BoardRequest", dataTypeClass = BoardRequest.class, required = true)
    })
    public ResponseEntity<?> boardSave(@CurrentUser CustomUserDetails currentUser, @RequestBody BoardRequest boardRequest) {
        log.info(boardRequest.toString());
        boardService.saveBoard(currentUser, boardRequest);
        return ResponseEntity.ok(new ApiResponse(true, "저장 되었습니다."));
    }

    /**
     * 삭제 (파일 포함)
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SYSTEM')")
    @ApiOperation(value = "게시물 삭제")
    @ApiImplicitParam(name = "request", value = "게시물 삭제 VO (id값의 배열)", dataType = "BoardDeleteRequest", dataTypeClass = BoardDeleteRequest.class, required = true)
    public ResponseEntity<?> boardDelete(@RequestBody BoardDeleteRequest request) {
        boardService.deleteBoard(request);
        return ResponseEntity.ok(new ApiResponse(true, "삭제 되었습니다."));
    }

//    ==============================================================================
//    메인화면단 EventController
    /**
     * 현재 사용자의 프로필 리턴
     * @param request
     * @return
     */
    // 이벤트 리스트 페이지
    @GetMapping("/eventList")
    @ApiOperation(value = "이벤트 목록 조회")
    @ApiImplicitParam(name = "request", value = "검색 객체", dataType = "SearchHelper", dataTypeClass = SearchHelper.class, required = true)
    public ResponseEntity<?> EventList(@ModelAttribute SearchHelper request) {
        // 예: 이벤트 서비스에서 목록 조회

        return ResponseEntity.ok(boardService.selectEvent(request));  // JSON으로 반환
//        return ResponseEntity.ok(boardService.selectEvent(request));  // JSON으로 반환
    }

    @GetMapping("/eventDetail/{eventId}")
    @ApiOperation(value = "이벤트 세부정보 조회")
    public ResponseEntity<?> getEventDetail(@ApiParam(value = "이벤트 ID", required = true)@PathVariable Long eventId) {
        log.info("이벤트 ID: {}", eventId);

        return ResponseEntity.ok(boardService.selectEventDetail(eventId));  // 서비스에 eventId 전달
    }


    @PostMapping("/apply")
    public ResponseEntity<String> applyForEvent()
//            @RequestBody EventApplicationRequest request
    {
        // 요청 바디에서 userId와 eventId를 받음
//        Long userId = request.getUserId();

        // 이벤트 신청 처리 로직 (예: DB에 신청 정보 저장)
//        boolean isSuccess = eventService.applyForEvent(userId, eventId);

//        if (isSuccess) {
//            return ResponseEntity.ok("신청 성공");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("신청 실패");
//        }
        return null;
    }


    //    ==============================================================================
    //    메인화면단 NoticeController

    /**
     * 현재 사용자의 프로필 리턴
     * @param request
     * @return
     */
    // 공지사항 리스트 페이지
    @GetMapping("/noticeList")
    @ApiOperation(value = "공지사항 목록 조회")
    @ApiImplicitParam(name = "request", value = "검색 객체", dataType = "SearchHelper", dataTypeClass = SearchHelper.class, required = true)
    public ResponseEntity<?> NoticeList(@ModelAttribute SearchHelper request) {
        // 예: 공지사항 서비스에서 목록 조회

        log.info("테스트중");
//        List<NoticeVO> noticeList =
        return ResponseEntity.ok(boardService.selectNotices(request));  // JSON으로 반환
    }


    //    ==============================================================================
    //    메인화면단 CommentsController

    // 댓글 추가
    @PostMapping("/comment")
    @ApiOperation(value = "댓글 등록", notes = "새로운 댓글을 생성하여 데이터베이스에 저장합니다.")
    public ResponseEntity<?> addComment(
            @ApiParam(value = "등록할 댓글 정보", required = true) @RequestBody CommentsVO commentsVO) {
        try {
            log.info("새 댓글 등록 요청: {}", commentsVO);
            CommentsVO savedComment = boardService.addComment(commentsVO);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("댓글 등록 중 오류 발생", e);
            return new ResponseEntity<>("댓글 등록에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //댓글 수정
    @PutMapping("/commentUpdate/{commentId}")
    @ApiOperation(value = "댓글 수정", notes = "특정 댓글을 수정합니다.")
    public ResponseEntity<?> updateComment(
            @PathVariable Long commentId,
            @RequestBody HashMap<String, Object> content) { // 단순히 String 타입으로 받음
        log.info("댓글 수정 요청: commentId={}, newContent={}", commentId, content.toString());

        // DB에 저장하는 부분에서 쌍따옴표가 들어가지 않도록 처리
        boardService.updateComment(commentId, String.valueOf(content.get("content")));
        return ResponseEntity.ok("댓글이 수정되었습니다.");
    }

@GetMapping("/comments/list")
@ApiOperation(value = "댓글 목록 조회", notes = "특정 대상에 달린 댓글 목록을 조회합니다.")
public ResponseEntity<?> getComments(
        @ApiParam(value = "대상 유형 (예: ALBUM, NOTICE)", required = true) @RequestParam String targetType,
        @ApiParam(value = "대상 ID", required = true) @RequestParam String targetId,
        @ApiParam(value = "로그인한 유저 ID", required = true) @RequestParam Long userId) {
    try {
        // 댓글 목록과 신고 여부를 서비스에서 처리
        List<CommentsVO> comments = boardService.getCommentsByTargetWithReportStatus(targetType, targetId, userId);

        log.info("테스트함 : " + comments);
        return ResponseEntity.ok(comments);
    } catch (Exception e) {
        log.error("댓글 목록 조회 중 오류 발생", e);
        return new ResponseEntity<>("댓글 목록을 불러오는 데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}




    // 댓글 삭제
    @DeleteMapping("/commentDelete/{commentId}")
    @ApiOperation(value = "댓글 삭제", notes = "특정 댓글을 삭제합니다.")
    public ResponseEntity<?> deleteComment(
            @ApiParam(value = "삭제할 댓글 ID", required = true) @PathVariable Long commentId) {
        try {
            log.info("댓글 삭제 요청: commentId={}", commentId);
            boardService.deleteComment(commentId);
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } catch (Exception e) {
            log.error("댓글 삭제 중 오류 발생", e);
            return new ResponseEntity<>("댓글 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 신고
     * @param commentId 신고할 댓글의 ID
     * @param currentUser 현재 로그인한 유저 정보
     * @param commentReportVO 신고 사유를 포함한 요청 객체
     * @return ResponseEntity 처리 결과
     */
    @PostMapping("/commentReport/{commentId}")
    @ApiOperation(value = "댓글 신고", notes = "댓글을 신고하고 신고 사유를 데이터베이스에 저장합니다.")
    public ResponseEntity<?> reportComment(
            @PathVariable Long commentId, // 신고하려는 댓글의 ID
            @CurrentUser CustomUserDetails currentUser, // 현재 로그인한 유저 정보
            @RequestBody CommentReportVO commentReportVO) { // 신고 사유를 포함한 요청 객체

        // 로그인한 유저 ID와 신고 사유
        Long userId = currentUser.getId();
        String reportReason = commentReportVO.getReportReason();

        // 신고 사유가 비어있는지 확인
        if (reportReason == null || reportReason.trim().isEmpty()) {
            return new ResponseEntity<>("신고 사유를 입력해 주세요.", HttpStatus.BAD_REQUEST);
        }

        // 해당 댓글을 신고한 유저가 있는지 확인
        boolean alreadyReported = boardService.hasUserReportedComment(commentId, userId);
        if (alreadyReported) {
            return new ResponseEntity<>("이미 신고한 댓글입니다.", HttpStatus.FORBIDDEN);
        }

        // 신고 정보 저장
        commentReportVO.setCommentId(commentId);  // 댓글 ID 설정
        commentReportVO.setUserId(userId);  // 신고한 유저 ID 설정
        boardService.saveCommentReport(commentReportVO);  // 신고 정보 저장

        return new ResponseEntity<>("댓글이 신고되었습니다.", HttpStatus.CREATED);
    }
}


