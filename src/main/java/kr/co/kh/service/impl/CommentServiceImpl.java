package kr.co.kh.service.impl;

import kr.co.kh.model.vo.CommentsVO;
import kr.co.kh.repository.CommentRepository;
import kr.co.kh.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    //
    private final CommentRepository commentRepository;

    @Override
    public CommentsVO addComment(CommentsVO commentsVO) {
        // 댓글 저장
        return commentRepository.save(commentsVO);
    }

    @Override
    public List<CommentsVO> getCommentsByTarget(String targetType, String targetId) {
        // 댓글 목록 조회
        return commentRepository.findByTargetTypeAndTargetId(targetType, targetId);
    }

    @Override
    public void deleteComment(Long commentId) {
        // 댓글 삭제
        commentRepository.deleteById(commentId);
    }
}