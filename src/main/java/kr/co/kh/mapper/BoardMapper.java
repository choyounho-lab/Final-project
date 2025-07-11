package kr.co.kh.mapper;

import kr.co.kh.model.EventDetailResponse;
import kr.co.kh.model.vo.BoardVO;
import kr.co.kh.model.vo.CommentsVO;
import kr.co.kh.model.vo.NoticeVO;
import kr.co.kh.model.vo.SearchHelper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
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
}
