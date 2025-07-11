package kr.co.kh.service;

import kr.co.kh.model.vo.CommentsVO;

import java.util.List;

public interface CommentService {

    // 댓글 추가
    CommentsVO addComment(CommentsVO commentsVO);

    // 특정 대상에 대한 댓글 목록 조회
    List<CommentsVO> getCommentsByTarget(String targetType, String targetId);

    // 댓글 삭제
    void deleteComment(Long commentId);
}