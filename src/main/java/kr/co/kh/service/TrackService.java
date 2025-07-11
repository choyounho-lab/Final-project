package kr.co.kh.service;

import kr.co.kh.mapper.TrackMapper;
import kr.co.kh.model.vo.TrackVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrackService {

    private final TrackMapper trackMapper;

    public List<TrackVO> selectTrack(TrackVO trackVO) {
        return trackMapper.selectTrack(trackVO);
    };

    public void insertTrack(TrackVO trackVO) {
        trackMapper.insertTrack(trackVO);
    }

    public List<TrackVO> selectTrackPlayDate(TrackVO trackVO) {
        return trackMapper.selectTrackPlayDate(trackVO);
    };

    public void insertTrackPlayDate(TrackVO trackVO) {
        trackMapper.insertTrackPlayDate(trackVO);
    }
}
