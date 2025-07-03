package kr.co.kh.mapper;

import kr.co.kh.model.vo.PlaylistVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PlaylistMapper {
    List<PlaylistVO> selectPlaylist(PlaylistVO playlistVO);
    void insertPlaylist(PlaylistVO playlistVO);
}
