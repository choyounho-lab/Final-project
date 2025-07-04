package kr.co.kh.mapper;


import kr.co.kh.model.vo.BoardVO;
import kr.co.kh.model.vo.NoticeVO;
import kr.co.kh.model.vo.SearchHelper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface NoticeMapper {

    List<NoticeVO> selectNotices(SearchHelper searchHelper);

    int countNotice(SearchHelper searchHelper);

    void boardSave(BoardVO board);

    Optional<BoardVO> boardInfo(Long id);

    void updateBoard(BoardVO board);

    void deleteBoard(Long id);

}
