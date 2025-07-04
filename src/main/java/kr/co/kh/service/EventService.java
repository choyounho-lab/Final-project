package kr.co.kh.service;

import kr.co.kh.model.EventDetailResponse;
import kr.co.kh.model.vo.SearchHelper;

import java.util.HashMap;

public interface EventService {

    HashMap<String, Object> selectEvent(SearchHelper searchHelper);

    EventDetailResponse selectEventDetail(Long eventId);
}

