package kr.co.kh.mapper;

import kr.co.kh.model.EventDetailResponse;
import kr.co.kh.model.vo.NoticeVO;
import kr.co.kh.model.vo.SearchHelper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventMapper {

    List<NoticeVO> selectEventWithPrizes(SearchHelper searchHelper);

    Long countEvent(SearchHelper searchHelper);

    EventDetailResponse selectEventDetail(Long eventId);
}



