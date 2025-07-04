package kr.co.kh.service.impl;

import kr.co.kh.mapper.EventMapper;
import kr.co.kh.model.EventDetailResponse;
import kr.co.kh.model.vo.SearchHelper;
import kr.co.kh.service.EventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;

    /**
     * 게시물 목록 + 카운트
     * @param searchHelper
     * @return
     */
    @Override
    public HashMap<String, Object> selectEvent(SearchHelper searchHelper) {
        HashMap<String, Object> resultMap = new HashMap<>();

        float totalElements = (float) eventMapper.countEvent(searchHelper);

        resultMap.put("list", eventMapper.selectEventWithPrizes(searchHelper));
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
        EventDetailResponse response = eventMapper.selectEventDetail(eventId);
        // 결과 없을 경우 예외 처리 (선택사항)
        if (response == null) {
            throw new IllegalArgumentException("해당 이벤트가 존재하지 않습니다.");
        }

        // VO → DTO 수동 매핑 (필요한 필드만 전달)
        return response;
    }
}
