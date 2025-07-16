package kr.co.kh.repository;

import kr.co.kh.model.vo.CommentReportVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReportRepository extends JpaRepository<CommentReportVO, Long> {

    // 댓글에 대해 신고한 유저가 있는지 확인
    boolean existsByCommentIdAndUserId(Long commentId, Long userId);
}
