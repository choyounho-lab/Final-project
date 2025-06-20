import React, { useEffect, useState } from "react";
import { MovieResultType, SearchMovieType, SpotifyImage } from "../types/types";
import {
  apiGetGenreArtist,
  apiGetMovieList,
  apiGetMusicList,
  apiSearchMovieByKeyword,
} from "../api/api";
import { buildStyles, CircularProgressbar } from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import { Link } from "react-router-dom";

function New() {
  // 🎵 음원(곡/앨범) 목록 상태값
  const [musicResult, setMusicResult] = useState<MovieResultType>({
    page: 0,
    results: [],
    total_pages: 0,
    total_results: 0,
  });

  const [musicImage, setMusicImage] = useState<SpotifyImage>({
    url: "",
    height: 0,
    width: 0,
  });

  // 🎷 음원 리스트 불러오기 (페이지 단위로 더보기 기능)
  const getMusicList = () => {
    apiGetMovieList(musicResult.page + 1).then((res) => {
      console.log(res);
      const results = [...musicResult.results, ...res.results];
      setMusicResult({
        ...res,
        results,
      });
    });
  };

  // 🔍 검색인지 더보기인지 구분
  const [searchTarget, setSerachTarget] = useState("");

  // 🄠 음악 검색어 및 페이지 상태
  const [search, setSearch] = useState<SearchMovieType>({
    query: "",
    page: 1,
  });

  // 🔍 음악 검색 함수
  const getMusicByKeyword = () => {
    setSerachTarget("search");
    const prevPage = search.page;
    apiSearchMovieByKeyword({ ...search, page: search.page + 1 }).then(
      (res) => {
        setSearch({
          ...search,
          page: search.page + 1,
        });
        let results: any[] = [];
        if (prevPage === 1) {
          results = [...res.results];
        } else {
          results = [...musicResult.results, ...res.results];
        }
        setMusicResult({
          ...res,
          results,
        });
      }
    );
  };

  // 🔘 검색창 포커스 상태 (배경색 전환용)
  const [isFocus, setIsFocus] = useState<boolean>(false);

  // 🔍 검색 폼 제출 처리
  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    getMusicByKeyword();
  };

  // 🎴 K-POP 아티스트 목록 상태
  const [data, setData] = useState<any>({});

  // 🎵 Spotify API를 통해 K-POP 아티스트 목록 호출
  useEffect(() => {
    apiGetGenreArtist("kpop").then((res) => {
      console.log(res);
      setData(res);
    });
  }, []);

  return (
    <div className="">
      <ul>
        {/* 🎶 K-POP 아티스트 리스트 출력 */}
        {data?.artists?.items?.map((item: any, index: number) => (
          <li className="inline-block" key={item.id}>
            {item.images?.[1] && (
              <img className="m-10 w-70 h-70" src={item.images[1].url} alt="" />
            )}
          </li>
        ))}
      </ul>

      <form
        onSubmit={onSubmit}
        className="sticky top-0 left-0 right-0 p-4 flex justify-between items-center bg-slate-900 z-100"
      >
        {/* 🔍 음악 검색 입력창 */}
        <input
          className={`placeholder:text-gray-200 w-full border border-rose-500 p-1 transition-all ${
            isFocus ? "bg-white" : ""
          }`}
          placeholder="아티스트명 또는 곡명으로 검색"
          autoComplete="off"
          type="text"
          name="query"
          value={search.query}
          onChange={(e) => {
            setSearch({ ...search, query: e.target.value });
          }}
          onFocus={() => setIsFocus(true)}
          onBlur={() => setIsFocus(false)}
        />
        <button
          type="submit"
          className="w-15 block text-center bg-rose-500 border border-rose-500 text-white p-1"
        >
          검색
        </button>
      </form>

      {/* 🎼 음원 목록 출력 */}
      <ul className="flex flex-wrap">
        {musicResult.results.map((item) => (
          <li className="lg:w-1/4 md:w-1/2 p-4" key={item.id}>
            <Link to={`/movieDetail/${item.id}`}>
              <div className="bg-slate-800 flex flex-col justify-between hover:-translate-y-2 transition-all hover:shadow-sm shadow-none h-full">
                <img src={`https://i.scdn.co/image/`} alt="" />
                <div className="p-2 justify-between items-center flex">
                  <div>
                    <p className="text-xs text-white">{item.release_date}</p>
                    <p className="text-base text-rose-500 noto-sans-kr-700">
                      {item.title}
                    </p>
                  </div>
                  <p
                    className="text-xs text-white"
                    style={{ width: "30px", height: 30 }}
                  >
                    <CircularProgressbar
                      value={item.vote_average * 10}
                      text={`${item.vote_average}`}
                      styles={buildStyles({
                        pathColor: `red`,
                        textColor: "#f88",
                        trailColor: "#d6d6d6",
                        backgroundColor: "#3e98c7",
                      })}
                    />
                  </p>
                </div>
              </div>
            </Link>
          </li>
        ))}
      </ul>

      {/* ▶️ 더보기 버튼: 검색 결과 or 전체 리스트 */}
      <button
        type="button"
        onClick={searchTarget === "search" ? getMusicByKeyword : getMusicList}
        className="block mx-auto p-4 bg-sky-400 text-stone-50 mb-4"
      >
        더보기
      </button>
    </div>
  );
}

export default New;
