package kr.co.kh.service;

import kr.co.kh.mapper.PlaylistMapper;
import kr.co.kh.model.vo.PlaylistVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


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
    public void updatePlaylist(PlaylistVO playlistVO) {playlistMapper.updatePlaylist(playlistVO);}
    public void deletePlaylist(PlaylistVO playlistVO) {playlistMapper.deletePlaylist(playlistVO);}
    public void deletePlaylistTrackByPlaylistId(PlaylistVO playlistVO) {
        playlistMapper.deletePlaylistTrackByPlaylistId(playlistVO);
    }
    // 신규 플레이리스트 조회 서비스
    public List<PlaylistVO> selectPublicPlaylists() {
        return playlistMapper.selectPublicPlaylists();
    }

    public PlaylistVO getPlaylistDetail(PlaylistVO playlistVO) {
        List<PlaylistVO> resultList = playlistMapper.selectPlaylist(playlistVO);

        if (resultList == null || resultList.isEmpty()) {
            throw new RuntimeException("플레이리스트를 찾을 수 없습니다.");
        }

        PlaylistVO result = resultList.get(0);

        // 비공개일 경우, 본인만 접근 가능
        if (result.getPlaylistIsPublic() == 0) {
            if (playlistVO.getUserId() == null || !playlistVO.getUserId().equals(result.getUserId())) {
                throw new SecurityException("비공개 플레이리스트는 접근할 수 없습니다.");
            }
        }

        return result;
    }

}
