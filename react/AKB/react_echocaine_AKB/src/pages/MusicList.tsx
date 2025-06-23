import React, { useEffect, useState } from "react";
import { MovieResultType, SearchMovieType } from "../types/types";
import { apiGetMovieList, apiSearchMovieByKeyword } from "../api/api";
import { buildStyles, CircularProgressbar } from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import { Link } from "react-router-dom";

function MusicList() {
  // 영화 목록 상태값
  const [movieResult, setMovieResult] = useState<MovieResultType>({
    page: 0,
    results: [],
    total_pages: 0,
    total_results: 0,
  });

  // 영화 목록 호출
  const getMovieList = () => {
    // 1페이지부터 가져오기 위해 page값에 1을 더한 상태로 api 호출
    apiGetMovieList(movieResult.page + 1).then((res) => {
      console.log(res);
      // 기존에 출력되어 있는 데이터 목록 유지를 위해 배열을 합쳐서 영화 목록 상태값에 넣는다.
      const results = [...movieResult.results, ...res.results];
      setMovieResult({
        ...res,
        results,
      });
    });
  };

  // 검색어를 입력하고 검색을하면 영화 목록을 출력하는것인지 검색을 한것인지 구별하기 위한 용도로 searchTarget 상태값을 만들었다.
  const [searchTarget, setSerachTarget] = useState("");

  // 영화 검색 파라미터용 상태값
  const [search, setSearch] = useState<SearchMovieType>({
    query: "",
    page: 1,
  });

  // 영화 검색 메서드드
  const getMovieByKeyword = () => {
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
          // 검색할때 page 상태값이 1이면 모두 지우고 결과를 그린다. 즉 검색어 입력 후 엔터를 누른 상태
          results = [...res.results];
        } else {
          // 기존에 출력되어 있는 데이터 목록 유지를 위해 배열을 합쳐서 영화 목록 상태값에 넣는다.
          results = [...movieResult.results, ...res.results];
        }
        setMovieResult({
          ...res,
          results,
        });
      }
    );
  };

  // 검색창에 포커스를 맞췄을 때를 판단하기위한 상태값... html에서 3항 연산자로 클래스를 추가 삭제하게 된다.
  const [isFocus, setIsFocus] = useState<boolean>(false);
  // form을 submit 했을때 메서드
  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    getMovieByKeyword();
  };

  // const [count, setCount] = useState(0);

  useEffect(() => {
    getMovieList();

    // const intervalId = setInterval(() => {
    //   setCount((prev) => prev + 1);
    //   console.log("타이머 실행중");
    // }, 1000);

    // 클린업함수
    // return () => {
    //   clearInterval(intervalId);
    //   console.log("타이머 종료");
    // };
  }, []);

  return (
    <div className="bg-slate-900">
      <form
        onSubmit={onSubmit}
        className="sticky top-0 left-0 right-0 p-4 flex justify-between items-center bg-slate-900 z-100"
      >
        {/* 인풋에 포커스를 맞췄을때 동적으로 클래스를 토글해야하는 경우 아래와 같이 3항 연산자를 사용한다. */}
        <input
          className={`placeholder:text-gray-200 w-full border border-rose-500 p-1 transition-all ${
            isFocus ? "bg-white" : ""
          }`}
          placeholder="영화 제목으로 검색"
          autoComplete="off"
          type="text"
          name="query"
          value={search.query}
          onChange={(e) => {
            setSearch({ ...search, query: e.target.value });
          }}
          onFocus={() => setIsFocus(true)} // 인풋을 클릭한 경우
          onBlur={() => setIsFocus(false)} // 인풋에서 빠져 나간 경우
        />
        <button
          type="submit"
          className="w-15 block text-center bg-rose-500 border border-rose-500 text-white p-1"
        >
          검색
        </button>
      </form>
      <ul className="flex flex-wrap">
        {movieResult.results.map((item) => (
          <li className="lg:w-1/4 md:w-1/2 p-4" key={item.id}>
            <Link to={`/movieDetail/${item.id}`}>
              <div className="bg-slate-800 flex flex-col justify-between hover:-translate-y-2 transition-all hover:shadow-sm shadow-none h-full">
                <img
                  src={`${process.env.REACT_APP_IMG_URL}w500${item.poster_path}`}
                  alt=""
                />
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
      {/* 더보기가 영화 목록 더보기 상태의 클릭인지 검색 후 더보기 상태인지 구분해서 onClick 메서드를 구분한다. */}
      <button
        type="button"
        onClick={searchTarget === "search" ? getMovieByKeyword : getMovieList}
        className="block mx-auto p-4 bg-sky-400 text-stone-50 mb-4"
      >
        더보기
      </button>
    </div>
  );
}

export default MusicList;
