package kr.co.kh.service;


import kr.co.kh.mapper.PlaylistTrackMapper;
import kr.co.kh.model.vo.PlaylistTrackVO;
import kr.co.kh.model.vo.PlaylistVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaylistTrackService {

    private final PlaylistTrackMapper playlistTrackMapper;

    public List<PlaylistVO> selectPlaylist(PlaylistTrackVO playlistTrackVO){
        return playlistTrackMapper.selectPlaylistTrack(playlistTrackVO);
    }
    public void insertPlaylist(PlaylistTrackVO playlistTrackVO) {
        playlistTrackMapper.insertPlaylistTrack(playlistTrackVO);
    }

    public void deletePlaylist(PlaylistTrackVO playlistTrackVO) {playlistTrackMapper.deletePlaylistTrack(playlistTrackVO);}
}
