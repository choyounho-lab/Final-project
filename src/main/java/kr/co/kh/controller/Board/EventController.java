package kr.co.kh.controller.Board;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.kh.model.vo.SearchHelper;
import kr.co.kh.service.EventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event")
@Slf4j
@AllArgsConstructor
public class EventController {

    private final EventService eventService;
    /**
     * 현재 사용자의 프로필 리턴
     * @param request
     * @return
     */
    // 이벤트 리스트 페이지
    @GetMapping("/eventList")
    @ApiOperation(value = "이벤트 목록 조회")
    @ApiImplicitParam(name = "request", value = "검색 객체", dataType = "SearchHelper", dataTypeClass = SearchHelper.class, required = true)
    public ResponseEntity<?> EventList(@ModelAttribute SearchHelper request) {
        // 예: 이벤트 서비스에서 목록 조회

        return ResponseEntity.ok(eventService.selectEvent(request));  // JSON으로 반환
    }

    @GetMapping("/eventDetail/{eventId}")
    @ApiOperation(value = "이벤트 세부정보 조회")
    public ResponseEntity<?> getEventDetail(@ApiParam(value = "이벤트 ID", required = true)@PathVariable Long eventId) {
        log.info("이벤트 ID: {}", eventId);

        return ResponseEntity.ok(eventService.selectEventDetail(eventId));  // 서비스에 eventId 전달
    }


    @PostMapping("/apply")
    public ResponseEntity<String> applyForEvent()
//            @RequestBody EventApplicationRequest request
            {
        // 요청 바디에서 userId와 eventId를 받음
//        Long userId = request.getUserId();

        // 이벤트 신청 처리 로직 (예: DB에 신청 정보 저장)
//        boolean isSuccess = eventService.applyForEvent(userId, eventId);

//        if (isSuccess) {
//            return ResponseEntity.ok("신청 성공");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("신청 실패");
//        }
                return null;
    }


}
