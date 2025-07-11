package kr.co.kh.mapper;

import kr.co.kh.model.vo.PlaylistTrackVO;
import kr.co.kh.model.vo.PlaylistVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaylistTrackMapper {
    List<PlaylistVO> selectPlaylistTrack(PlaylistTrackVO playlistTrackVO);
    void insertPlaylistTrack(PlaylistTrackVO playlistTrackVO);
    void deletePlaylistTrack(PlaylistTrackVO playlistTrackVO);
}
