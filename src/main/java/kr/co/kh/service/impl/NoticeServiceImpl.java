package kr.co.kh.service.impl;

import kr.co.kh.mapper.NoticeMapper;
import kr.co.kh.model.vo.SearchHelper;
import kr.co.kh.service.NoticeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
@AllArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    /**
     * 게시물 목록 + 카운트
     * @param searchHelper
     * @return
     */
    @Override
    public HashMap<String, Object> selectNotices(SearchHelper searchHelper) {
        HashMap<String, Object> resultMap = new HashMap<>();

        float totalElements = (float) noticeMapper.countNotice(searchHelper);

        resultMap.put("list", noticeMapper.selectNotices(searchHelper));
        resultMap.put("totalElements", totalElements);
        resultMap.put("size", searchHelper.getSize());
        resultMap.put("currentPage", Math.ceil((double) searchHelper.getPage() / searchHelper.getSize()) + 1);
        resultMap.put("totalPages", Math.ceil(totalElements / searchHelper.getSize()));
        resultMap.put("last", searchHelper.getPage() >= searchHelper.getSize());

        return resultMap;
    }
}
