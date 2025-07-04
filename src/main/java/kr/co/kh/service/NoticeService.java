package kr.co.kh.service;

import kr.co.kh.model.vo.SearchHelper;

import java.util.HashMap;

public interface NoticeService {

    HashMap<String, Object> selectNotices(SearchHelper searchHelper);
}
