package kr.co.kh.mapper;

import kr.co.kh.model.EventDetailResponse;
import kr.co.kh.model.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    List<BoardVO> selectBoard(SearchHelper searchHelper);

    int countBoard(SearchHelper searchHelper);

    void boardSave(BoardVO board);

    Optional<BoardVO> boardInfo(Long id);

    void updateBoard(BoardVO board);

    void deleteBoard(Long id);

    // EventMapper
    List<NoticeVO> selectEventWithPrizes(SearchHelper searchHelper);
    Long countEvent(SearchHelper searchHelper);
    EventDetailResponse selectEventDetail(Long eventId);

    // NoticeMapper
    List<NoticeVO> selectNotices(SearchHelper searchHelper);

    // CommentMapper
    // 댓글 목록 조회
    List<CommentsVO> selectCommentsByTarget(@Param("targetType") String targetType, @Param("targetId") String targetId);

    // 댓글 추가
    void insertComment(CommentsVO commentsVO);

    // 댓글 삭제
    void deleteComment(Long commentId);

    // 댓글 수 조회 (선택사항, 예를 들어 특정 대상의 댓글 수가 필요할 경우)
    int countCommentsByTarget(String targetType, Long targetId);

    //댓글 수정
    void updateComment(Map<String, Object> paramMap);

    //댓글 신고
    void commentReport(CommentReportVO commentReportVO);

    // 특정 대상에 달린 댓글 목록을 조회
    List<CommentsVO> findCommentsByTarget(String targetType, String targetId);

    // 특정 댓글에 신고한 유저들의 ID 목록을 조회
    List<Long> findReportedByUserIds(Long commentId);

    // 특정 댓글에 대해 특정 유저가 신고했는지 여부를 확인하는 메서드
    int countReportsByCommentAndUser(Long commentId, Long userId);



}
