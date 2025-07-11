package kr.co.kh.service;

import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.EventDetailResponse;
import kr.co.kh.model.payload.request.BoardDeleteRequest;
import kr.co.kh.model.payload.request.BoardRequest;
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

    // 특정 대상에 대한 댓글 목록 조회
    List<CommentsVO> getCommentsByTarget(String targetType, String targetId);

    // 댓글 삭제
    void deleteComment(Long commentId);


}
