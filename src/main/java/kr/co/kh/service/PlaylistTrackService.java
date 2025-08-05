package kr.co.kh.service;


import kr.co.kh.mapper.PlaylistTrackMapper;
import kr.co.kh.model.vo.PlaylistTrackVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaylistTrackService {

    private final PlaylistTrackMapper playlistTrackMapper;

    public List<PlaylistTrackVO> selectPlaylistTrack(PlaylistTrackVO playlistTrackVO){
        return playlistTrackMapper.selectPlaylistTrack(playlistTrackVO);
    }
    public void insertPlaylistTrack(PlaylistTrackVO playlistTrackVO) {
        playlistTrackMapper.insertPlaylistTrack(playlistTrackVO);
    }

    public void deletePlaylistTrack(PlaylistTrackVO playlistTrackVO) {
        playlistTrackMapper.deletePlaylistTrack(playlistTrackVO);
    }
}
