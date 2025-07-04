package kr.co.kh.service;

import kr.co.kh.mapper.PlaylistMapper;
import kr.co.kh.model.vo.PlaylistVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistMapper playlistMapper;

    public List<PlaylistVO> selectPlaylist(PlaylistVO playlistVO){
        return playlistMapper.selectPlaylist(playlistVO);
    }
    public void insertPlaylist(PlaylistVO playlistVO) {
        playlistMapper.insertPlaylist(playlistVO);
    }

    public void deletePlaylist(PlaylistVO playlistVO) {playlistMapper.deletePlaylist(playlistVO);}
}
