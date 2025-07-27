package kr.co.kh.mapper;

import kr.co.kh.model.vo.TrackVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrackMapper {
    List<TrackVO> selectTrack(String trackId);
    void insertTrack(TrackVO trackVO);
    List<TrackVO> selectTrackPlayDate(TrackVO trackVO);
    void insertTrackPlayDate(TrackVO trackVO);
    void updateTrackYoutubeVideoId(TrackVO trackVO);
}
