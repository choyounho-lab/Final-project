package kr.co.kh.service;

import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.EventDetailResponse;
import kr.co.kh.model.payload.request.BoardDeleteRequest;
import kr.co.kh.model.payload.request.BoardRequest;
import kr.co.kh.model.vo.CommentReportVO;
import kr.co.kh.model.vo.CommentsVO;
import kr.co.kh.model.vo.SearchHelper;

import java.util.HashMap;
import java.util.List;

public interface BoardService {

    HashMap<String, Object> selectBoard(SearchHelper searchHelper);

    void saveBoard(CustomUserDetails currentUser, BoardRequest boardRequest);

    HashMap<String, Object> boardInfo(Long id);

    void deleteBoard(BoardDeleteRequest request);

    // EventService
    HashMap<String, Object> selectEvent(SearchHelper searchHelper);
    EventDetailResponse selectEventDetail(Long eventId);

    // NoticeService
    HashMap<String, Object> selectNotices(SearchHelper searchHelper);

    // CommentService
    // 댓글 추가
    CommentsVO addComment(CommentsVO commentsVO);

    // 댓글 삭제
    void deleteComment(Long commentId);

    // 댓글 수정
    void updateComment(Long commentId, String newContent);

    //댓글 신고
    void saveCommentReport(CommentReportVO report);

    // 신고 여부 확인
    boolean hasUserReportedComment(Long commentId, Long userId);

    // 댓글 신고
    List<CommentsVO> getCommentsByTargetWithReportStatus(String targetType, String targetId, Long userId);

    // 비로그인 상태의 댓글 리스트
    List<CommentsVO> getCommentsByTarget(String targetType, String targetId);

    //이벤트 신청자
    boolean applyForEvent(Long userId, Long eventId);

    // 이벤트 신청 중복 방지
    boolean hasUserAlreadyApplied(Long userId, Long eventId);
}
