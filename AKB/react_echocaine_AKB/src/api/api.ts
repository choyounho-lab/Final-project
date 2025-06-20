import { SearchMovieType } from "../types/types";
import { instance } from "./instance";

/**
 * 인기 영화 목록
 */
export const apiGetMovieList = async (page: number) => {
  return await instance
    .get(`movie/popular?page=${page}`)
    .then((res) => res.data);
};

/**
 * 키워드로 영화 검색
 */
export const apiSearchMovieByKeyword = async (search: SearchMovieType) => {
  return await instance
    .get(`search/movie`, { params: { ...search } })
    .then((res) => res.data);
};

/**
 * 영화 상세 정보
 */
export const apiMovieDetailInfo = async (id: string) => {
  return await instance.get(`movie/${id}`).then((res) => res.data);
};

/**
 * 영화 제작 정보
 */
export const apiCreditInfo = async (id: string) => {
  return await instance.get(`/movie/${id}/credits`).then((res) => res.data);
};

/**
 * 특정 아티스트 검색
 */
export const apiGetMusicList = async () => {
  return await instance
    .get(`search?offset=0&limit=20&query=BTS&type=artist`)
    .then((res) => res.data);
};

/**
 * 입력한 장르의 아티스트 정보
 */
export const apiGetGenreArtist = async (genre: string) => {
  return await instance
    .get(`search?q=*&genre:${genre}&type=artist`)
    .then((res) => res.data);
};

/**
 * 입력한 장르의 트랙 정보
 */
export const apiGetGenreTrack = async (genre: string) => {
  return await instance
    .get(`search?q=genre:${genre}&type=track`)
    .then((res) => res.data);
};

/**
 *최신 앨범 목록 가져오기
 */
export const apiGetNewReleases = async () => {
  return await instance
    .get(`browse/new-releases?limit=10`)
    .then((res) => res.data);
};

/**
 * 특정 앨범의 트랙 목록 가져오기
 */
export const apiGetTracksByAlbumId = async (albumId: string) => {
  return await instance.get(`albums/${albumId}/tracks`).then((res) => res.data);
};
