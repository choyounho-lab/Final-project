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
    CommentsVO getCommentById(Long commentId);

    void updateComment(Long commentId, String newContent);

    void commentReport(CommentReportVO commentReportVO);
    void reportComment(CommentReportVO commentReportVO);

    void saveCommentReport(CommentReportVO report);

    boolean hasUserReportedComment(Long commentId, Long userId);  // 신고 여부 확인
    boolean countReportsByCommentAndUser(Long commentId, Long userId);  // 신고 내역을 카운트하는 메서드

    List<CommentsVO> getCommentsByTargetWithReportStatus(String targetType, String targetId, Long userId);
}
