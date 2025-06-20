import { SearchMovieType } from "../types/types";
import { instance } from "./instance";

/**
 * 인기 영화 목록
 * @param page
 * @returns
 */
export const apiGetMovieList = async (page: number) => {
  return await instance
    .get(`movie/popular?page=${page}`)
    .then((res) => res.data);
};

/**
 * 키워드로 영화 검색 (페이지번호 포함)
 * @param search
 * @returns
 */
export const apiSearchMovieByKeyword = async (search: SearchMovieType) => {
  return await instance
    .get(`search/movie`, { params: { ...search } })
    .then((res) => res.data);
};

/**
 * 영화 정보
 * @param id
 * @returns
 */
export const apiMovieDetailInfo = async (id: string) => {
  return await instance.get(`movie/${id}`).then((res) => res.data);
};

/**
 * 영화 제작 정보
 * @param id
 * @returns
 */
export const apiCreditInfo = async (id: string) => {
  return await instance.get(`/movie/${id}/credits`).then((res) => res.data);
};

// export const apiGetMusicList = async () => {
//   return await instance
//     .get(`search?offset=0&limit=20&query=BTS&type=artist`)
//     .then((res) => res.data);
// };

// 입력한 장르의 아티스트 정보
export const apiGetGenreArtist = async (genre: string) => {
  return await instance
    .get(`search?q=*&genre:${genre}&type=artist`)
    .then((res) => res.data);
};

// 입력한 장르의 트랙 정보
export const apiGetGenreTrack = async (genre: string) => {
  return await instance
    .get(`search?q=genre:${genre}&type=track&market=KR`)
    .then((res) => res.data);
};

export const apiGetMusicList = async () => {
  return await instance
    .get(`search?offset=0&limit=20&q=BTS&type=artist&market=KR`)
    .then((res) => res.data);
};

// 앨범 검색
export const apiGetAlbumList = async (search: string) => {
  return await instance
    .get(`search?offset=0&limit=20&q=${search}&type=album&market=KR`)
    .then((res) => res.data);
};

// 공개 재생목록 검색
export const apiGetPlayList = async (search: string) => {
  return await instance
    .get(`search?offset=0&limit=20&q=${search}&type=playlist&market=KR`)
    .then((res) => res.data);
};

// 팟캐스트 쇼 검색
export const apiGetShowList = async (search: string) => {
  return await instance
    .get(`search?offset=0&limit=20&q=${search}&type=show&market=KR`)
    .then((res) => res.data);
};

// 오디오북 검색(일부 국가만 지원)
export const apiGetAudioBookList = async (search: string) => {
  return await instance
    .get(`search?offset=0&limit=20&q=${search}&type=audiobook&market=KR`)
    .then((res) => res.data);
};

// 팟캐스트 에피소드 검색
export const apiGetEpisodeList = async (search: string) => {
  return await instance
    .get(`search?offset=0&limit=20&q=${search}&type=episode&market=KR`)
    .then((res) => res.data);
};

export const apiGetGenreList = async () => {
  return await instance
    .get(`recommendations/available-genre-seeds`, {
      headers: {
        Authorization: `Bearer BQAUSnNPdYaVXGr4ZxtX2ggBRfghMTMm3BO4NZaMPsqEvxr5WgsjgBBjUVPpPf4t36kOT2QOsRYJvfuH_egEYUc6X3OFv5IBF_MOhJK9iLJGzTYavfn0ZQ2UR2pl3PpYJNm899FmG2PIYje-Auu_G1QdYpgca35oATuR4Nadrnt6cxxVCKxTb7KVpfcIDwktRlZmUjoXIADEHgW4lz7Y_YjJ--DGskN3Yf2I-tZkABdA7bd4AePYcfdFnGMiR1d-mxSCGTBDlzqSNChVz9FEbKtMjA6d_HgYayvwEbXxKKjEP0w_f1e4OP-WsYnRob-Uxz3Gfd1kEQ_G7Q`,
      },
    })
    .then((res) => res.data);
};
