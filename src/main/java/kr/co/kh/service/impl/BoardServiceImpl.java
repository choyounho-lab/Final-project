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
import kr.co.kh.repository.CommentRepository;
import kr.co.kh.service.BoardService;
import kr.co.kh.service.FileMapService;
import kr.co.kh.service.UploadFileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final UploadFileService uploadFileService;
    private final FileMapService fileMapService;
    private final CommentRepository commentRepository;

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

    // 댓글 목록 조회
    @Override
    public List<CommentsVO> getCommentsByTarget(String targetType, String targetId) {
        return boardMapper.selectCommentsByTarget(targetType, targetId);  // Mapper를 통해 댓글 목록 조회
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId) {
        boardMapper.deleteComment(commentId);  // Mapper를 통해 댓글 삭제
    }

}
