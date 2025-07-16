package kr.co.kh.controller.Board;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.model.vo.SearchHelper;
import kr.co.kh.service.NoticeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
@Slf4j
@AllArgsConstructor
public class NoticeController {

    //    private final UserService userService;
    private final NoticeService noticeService;
    /**
     * 현재 사용자의 프로필 리턴
     * @param request
     * @return
     */
    // 공지사항 리스트 페이지
    @GetMapping("/noticeList")
    @ApiOperation(value = "공지사항 목록 조회")
    @ApiImplicitParam(name = "request", value = "검색 객체", dataType = "SearchHelper", dataTypeClass = SearchHelper.class, required = true)
    public ResponseEntity<?> NoticeList(@ModelAttribute SearchHelper request) {
        // 예: 공지사항 서비스에서 목록 조회

        log.info("테스트중");
//        List<NoticeVO> noticeList =
        return ResponseEntity.ok(noticeService.selectNotices(request));  // JSON으로 반환
    }
}
