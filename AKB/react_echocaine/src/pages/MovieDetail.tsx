import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { CreditsType, MovieDetailType } from "../types/types";
import { apiCreditInfo, apiMovieDetailInfo } from "../api/api";

const MovieDetail = () => {
  // 이전 페이지에서 url 파라미터 또는 path 경로로 전달된 값을 읽기 위해 useParams() 메서드를 사용한다.
  const params = useParams();
  // params에서 id값을 읽고 상태값에 저장한다.
  const [id] = useState(params.id);

  // 영화 정보
  const [movieDetailInfo, setMovieDetailInfo] = useState<MovieDetailType>({
    adult: false,
    backdrop_path: "",
    belongs_to_collection: "",
    budget: 0,
    genres: [],
    homepage: "",
    id: 0,
    imbd_id: "",
    origin_country: [],
    original_language: "",
    original_title: "",
    overview: "",
    popularity: 0,
    poster_path: "",
    production_companies: [],
    production_countries: [],
    release_date: "",
    revenue: 0,
    runtime: 0,
    spoken_languages: [],
    status: "",
    tagline: "",
    title: "",
    video: false,
    vote_average: 0,
    vote_count: 0,
  });
  // 제작자 정보
  const [creditInfo, setCreditInfo] = useState<CreditsType>({
    id: 0,
    cast: [],
    crew: [],
  });

  // 영화 정보 및 제작자 정보 api 호출
  const getMovieInfo = () => {
    if (id) {
      apiMovieDetailInfo(id).then((res) => {
        console.log(res);
        setMovieDetailInfo(res);
      });
      apiCreditInfo(id).then((res) => {
        console.log(res);
        setCreditInfo(res);
      });
    }
  };

  useEffect(() => {
    getMovieInfo();
  }, []);

  return (
    <>
      <h1>{movieDetailInfo.title}</h1>
      <img
        src={`${process.env.REACT_APP_IMG_URL}w500/${movieDetailInfo.poster_path}`}
        alt={movieDetailInfo.title}
      />
      <p>{movieDetailInfo.overview}</p>
      <p>{movieDetailInfo.release_date}</p>
      <p>{movieDetailInfo.runtime}</p>
      <ul>
        {creditInfo.cast.map((item) => (
          <li key={item.id}>
            <img
              src={`${process.env.REACT_APP_IMG_URL}w500/${item.profile_path}`}
              alt={item.name}
            />
            <p>{item.name}</p>
            <p>{item.character}</p>
          </li>
        ))}
      </ul>
    </>
  );
};

export default MovieDetail;
