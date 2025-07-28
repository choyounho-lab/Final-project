package kr.co.kh.service.impl;

import kr.co.kh.exception.BadRequestException;
import kr.co.kh.exception.NotFoundException;
import kr.co.kh.mapper.BoardMapper;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.EventDetailResponse;
import kr.co.kh.model.payload.request.BoardDeleteRequest;
import kr.co.kh.model.payload.request.BoardRequest;
import kr.co.kh.model.payload.request.FileDeleteRequest;
import kr.co.kh.model.vo.*;
import kr.co.kh.repository.CommentReportRepository;
import kr.co.kh.repository.CommentRepository;
import kr.co.kh.service.BoardService;
import kr.co.kh.service.FileMapService;
import kr.co.kh.service.UploadFileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final UploadFileService uploadFileService;
    private final FileMapService fileMapService;
    private final CommentRepository commentRepository;
    private final CommentReportRepository commentReportRepository;
    /**
     * 게시물 목록 + 카운트
     * @param searchHelper
     * @return
     */
    @Override
    public HashMap<String, Object> selectBoard(SearchHelper searchHelper) {
        HashMap<String, Object> resultMap = new HashMap<>();

        float totalElements = (float) boardMapper.countBoard(searchHelper);

        resultMap.put("list", boardMapper.selectBoard(searchHelper));
        resultMap.put("totalElements", totalElements);
        resultMap.put("size", searchHelper.getSize());
        resultMap.put("currentPage", Math.ceil((double) searchHelper.getPage() / searchHelper.getSize()) + 1);
        resultMap.put("totalPages", Math.ceil(totalElements / searchHelper.getSize()));
        resultMap.put("last", searchHelper.getPage() >= searchHelper.getSize());
        return resultMap;
    }

    /**
     * 게시물 저장, 수정
     * @param currentUser
     * @param boardRequest
     */
    @Transactional
    @Override
    public void saveBoard(CustomUserDetails currentUser, BoardRequest boardRequest) {
        log.info("saveBoard : {}", boardRequest.toString());
        if (boardRequest.getId() == 0) {
            // 저장
            BoardVO board = BoardVO.builder()
                    .code(boardRequest.getCode())
                    .title(boardRequest.getTitle())
                    .content(boardRequest.getContent())
                    .regId(currentUser.getUsername())
                    .modId(currentUser.getUsername())
                    .build();

            boardMapper.boardSave(board);

            List<UploadFile> fileList = boardRequest.getFileList();

                for (UploadFile file : fileList) {
                    FileMap fileMap = FileMap.builder()
                            .boardId(board.getId())
                            .fileId(file.getId())
                            .build();
                    fileMapService.insertFileMap(fileMap);
                }
        } else {
            // 수정
            BoardVO board = BoardVO.builder()
                    .id(boardRequest.getId())
                    .code(boardRequest.getCode())
                    .title(boardRequest.getTitle())
                    .content(boardRequest.getContent())
                    .modId(currentUser.getUsername())
                    .build();

            log.info(board.toString());

            boardMapper.updateBoard(board);

            List<UploadFile> fileList = boardRequest.getFileList();

            for (UploadFile file : fileList) {
                FileMap fileMap = FileMap.builder()
                        .boardId(board.getId())
                        .fileId(file.getId())
                        .build();
                // 파일을 체크해서 row가 존재하면 패스, 존재하지 않으면 insert
                Boolean checkFileMap = fileMapService.checkFileMap(fileMap);
                if (!checkFileMap) {
                    fileMapService.insertFileMap(fileMap);
                }
            }
        }

    }

    /**
     * 게시물 정보 + 파일목록
     * @param id
     * @return
     */
    @Override
    public HashMap<String, Object> boardInfo(Long id) {
        HashMap<String, Object> resultMap = new HashMap<>();
        Optional<BoardVO> result = boardMapper.boardInfo(id);
        List<UploadFile> fileList = new ArrayList<>();
        if (result.isPresent()) {
            fileList = uploadFileService.selectFileByBoardId(id);
        } else {
            throw new NotFoundException("해당 게시물을 찾을 수 없습니다.");
        }
        resultMap.put("info", result.get());
        resultMap.put("uploadFiles", fileList);

        return resultMap;
    }

    /**
     * 게시물 + 파일 삭제
     * @param request
     */
    @Transactional
    @Override
    public void deleteBoard(BoardDeleteRequest request) {
        log.info(request.toString());
        if (!request.getId().isEmpty()) {
            for (Long id : request.getId()){
                Optional<BoardVO> info = boardMapper.boardInfo(id);
                List<UploadFile> fileList = uploadFileService.selectFileByBoardId(id);
                if (info.isPresent()) {
                    boardMapper.deleteBoard(id);
                    for (UploadFile item : fileList) {
                        FileDeleteRequest fileDeleteRequest = new FileDeleteRequest();
                        fileDeleteRequest.setId(item.getId());
                        fileDeleteRequest.setFileTarget(item.getFileTarget());
                        uploadFileService.deleteAsResource(fileDeleteRequest);
                    }
                } else {
                    throw new BadRequestException("해당 게시물을 찾을 수 없습니다.");
                }
            }
        }
    }

//    ==============================================================================
//    메인화면단 EventService

    /**
     * 게시물 목록 + 카운트
     * @param searchHelper
     * @return
     */
    @Override
    public HashMap<String, Object> selectEvent(SearchHelper searchHelper) {
        HashMap<String, Object> resultMap = new HashMap<>();

        float totalElements = (float) boardMapper.countEvent(searchHelper);

        resultMap.put("list", boardMapper.selectEventWithPrizes(searchHelper));
        resultMap.put("totalElements", totalElements);
        resultMap.put("size", searchHelper.getSize());
        resultMap.put("currentPage", Math.ceil((double) searchHelper.getPage() / searchHelper.getSize()) + 1);
        resultMap.put("totalPages", Math.ceil(totalElements / searchHelper.getSize()));
        resultMap.put("last", searchHelper.getPage() >= searchHelper.getSize());

        return resultMap;
    }

    @Override
    public EventDetailResponse selectEventDetail(Long eventId) {
        // Mapper에서 VO로 받아오기
        EventDetailResponse response = boardMapper.selectEventDetail(eventId);
        // 결과 없을 경우 예외 처리 (선택사항)
        if (response == null) {
            throw new IllegalArgumentException("해당 이벤트가 존재하지 않습니다.");
        }

        // VO → DTO 수동 매핑 (필요한 필드만 전달)
        return response;
    }

    // 이벤트 신청
    @Override
    public boolean applyForEvent(Long userId, Long eventId) {
        try {
            boardMapper.insertApplicant(userId, eventId, LocalDateTime.now());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //이벤트 중복 처리
    @Override
    public boolean hasUserAlreadyApplied(Long userId, Long eventId) {
        return boardMapper.countUserApplication(userId, eventId) > 0;
    }
//    ==============================================================================
//    메인화면단 NoticeService


    /**
     * 게시물 목록 + 카운트
     * @param searchHelper
     * @return
     */
    @Override
    public HashMap<String, Object> selectNotices(SearchHelper searchHelper) {
        HashMap<String, Object> resultMap = new HashMap<>();

        float totalElements = (float) boardMapper.countBoard(searchHelper);

        resultMap.put("list", boardMapper.selectNotices(searchHelper));
        resultMap.put("totalElements", totalElements);
        resultMap.put("size", searchHelper.getSize());
        resultMap.put("currentPage", Math.ceil((double) searchHelper.getPage() / searchHelper.getSize()) + 1);
        resultMap.put("totalPages", Math.ceil(totalElements / searchHelper.getSize()));
        resultMap.put("last", searchHelper.getPage() >= searchHelper.getSize());

        return resultMap;
    }


    //    ==============================================================================
//    메인화면단 CommentService


    // 댓글 추가
    @Override
    public CommentsVO addComment(CommentsVO commentsVO) {
        boardMapper.insertComment(commentsVO);  // Mapper를 통해 댓글 추가
        return commentsVO;  // 저장된 댓글 객체를 반환
    }

    @Override
    public List<CommentsVO> getCommentsByTargetWithReportStatus(String targetType, String targetId, Long userId) {
        // 댓글 목록을 DB에서 조회
        List<CommentsVO> comments = boardMapper.findCommentsByTarget(targetType, targetId);

        // 각 댓글에 대해 신고 여부를 설정
        for (CommentsVO comment : comments) {
            if (isReportedByUser(comment, userId)) {
                comment.setHasReportedByUser(1);  // 신고한 경우
            } else {
                comment.setHasReportedByUser(0);  // 신고하지 않은 경우
            }
        }
        return comments;
    }


    // 댓글 수정
    public void updateComment(Long commentId, String newContent) {
        // Map으로 파라미터 준비
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("commentId", commentId);
        paramMap.put("newContent", newContent);

        // Mapper 호출
        boardMapper.updateComment(paramMap);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId) {
        boardMapper.deleteComment(commentId);  // Mapper를 통해 댓글 삭제
    }
//    @Override
//    public CommentsVO getCommentById(Long commentId) {
//        Optional<CommentsVO> comment = commentRepository.findById(commentId);
//        // 댓글이 존재하면 반환하고, 없으면 null을 반환합니다.
//        return comment.orElse(null);
//    }


//    @Override
//    public void commentReport(CommentReportVO commentReportVO) {
//        boardMapper.commentReport(commentReportVO);
//    }

    /**
     * 댓글 신고 여부 확인
     * @param commentId 댓글 ID
     * @param userId 유저 ID
     * @return true: 이미 신고한 댓글, false: 신고하지 않은 댓글
     */
    @Override
    public boolean hasUserReportedComment(Long commentId, Long userId) {
        // 신고 내역 확인: 이미 신고한 댓글인지 체크
        int count = boardMapper.countReportsByCommentAndUser(commentId, userId);
        return count > 0;  // 이미 신고한 경우 true 반환
    }

    // 댓글 신고 처리
    public void reportComment(CommentReportVO commentReportVO) {
        // 이미 신고한 댓글인지 확인
        if (hasUserReportedComment(commentReportVO.getCommentId(), commentReportVO.getUserId())) {
            throw new BadRequestException("이미 신고한 댓글입니다.");
        }

        // 신고 기록 추가
        boardMapper.commentReport(commentReportVO);
    }

    public void saveCommentReport(CommentReportVO report) {
        // COMMENT_REPORT 테이블에 신고 정보 저장
        commentReportRepository.save(report);  // COMMENT_REPORT 테이블에 레코드 삽입
    }

    // 유저가 댓글을 신고했는지 여부를 체크하는 메서드
    private boolean isReportedByUser(CommentsVO comment, Long userId) {
        // 댓글에 신고한 유저 목록을 DB에서 조회하는 메서드
        List<Long> reportedByUserIds = boardMapper.findReportedByUserIds(comment.getCommentId());
        return reportedByUserIds.contains(userId);
    }

    // 댓글 신고 내역 카운트 메서드 구현
//    @Override
//    public boolean countReportsByCommentAndUser(Long commentId, Long userId) {
//        // 신고 내역을 카운트하는 메서드 호출
//        int count = boardMapper.countReportsByCommentAndUser(commentId, userId);
//        return count > 0;  // 신고 내역이 있으면 true, 없으면 false 반환
//    }

}
