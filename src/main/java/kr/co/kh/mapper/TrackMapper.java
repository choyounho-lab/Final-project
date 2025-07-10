package kr.co.kh.mapper;

import kr.co.kh.model.vo.TrackVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrackMapper {
    List<TrackVO> selectTrack(TrackVO trackVO);
    void insertTrack(TrackVO trackVO);
}
