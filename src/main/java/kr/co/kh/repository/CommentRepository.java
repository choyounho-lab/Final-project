package kr.co.kh.repository;

import kr.co.kh.model.vo.CommentsVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentsVO, Long> {

    // 특정 targetType과 targetId에 해당하는 댓글 목록을 조회
    List<CommentsVO> findByTargetTypeAndTargetId(String targetType, String targetId);

    // 댓글 ID로 댓글을 조회하는 메소드
    Optional<CommentsVO> findById(Long commentId);
}
