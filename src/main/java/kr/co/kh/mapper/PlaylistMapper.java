package kr.co.kh.mapper;

import kr.co.kh.model.vo.PlaylistVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PlaylistMapper {
    List<PlaylistVO> selectPlaylist(PlaylistVO playlistVO);
    void insertPlaylist(PlaylistVO playlistVO);
    void deletePlaylist(PlaylistVO playlistVO);
    void updatePlaylist(PlaylistVO playlistVO);
    void deletePlaylistTrackByPlaylistId(PlaylistVO playlistVO);
    // 신규 플레이리스트 조회 인터페이서 매퍼
    List<PlaylistVO> selectPublicPlaylists();
}
