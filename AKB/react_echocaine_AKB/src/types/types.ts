export interface ItemType {
  id: number;
  title: string;
  content: string;
}

export interface MovieType {
  adult: boolean;
  backdrop_path: string;
  genre_ids: number[];
  id: number;
  original_language: string;
  original_title: string;
  overview: string;
  popularity: number;
  poster_path: string;
  release_date: string;
  title: string;
  video: boolean;
  vote_average: number;
  vote_count: number;
}

export interface MovieResultType {
  page: number;
  results: MovieType[];
  total_pages: number;
  total_results: number;
}

export interface SearchMovieType {
  query: string;
  page: number;
}

export interface MovieDetailType {
  adult: boolean;
  backdrop_path: string;
  belongs_to_collection: string | number;
  budget: number;
  genres: GenreType[];
  homepage: string;
  id: number;
  imbd_id: string;
  origin_country: string[];
  original_language: string;
  original_title: string;
  overview: string;
  popularity: number;
  poster_path: string;
  production_companies: ProductionCompanyType[];
  production_countries: ProductionCountryType[];
  release_date: string;
  revenue: number;
  runtime: number;
  spoken_languages: SpokenLanguageType[];
  status: string;
  tagline: string;
  title: string;
  video: boolean;
  vote_average: number;
  vote_count: number;
}

export interface GenreType {
  id: number;
  name: string;
}

export interface ProductionCompanyType {
  id: number;
  logoPath: string;
  name: string;
  originCountry: string;
}

export interface ProductionCountryType {
  isoCode: string;
  name: string;
}

export interface SpokenLanguageType {
  englishName: string;
  isoCode: string;
  name: string;
}

type CreditCastType = {
  adult: boolean;
  gender: number;
  id: number;
  known_for_department: string;
  name: string;
  original_name: string;
  popularity: number;
  profile_path: string;
  cast_id: string;
  character: string;
  credit_id: string;
  order: number;
};
type CreditCrewType = {
  adult: boolean;
  gender: number;
  id: number;
  known_for_department: string;
  name: string;
  original_name: string;
  popularity: number;
  profile_path: string;
  credit_id: string;
  department: string;
  job: string;
};
export interface CreditsType {
  id: number;
  cast: CreditCastType[];
  crew: CreditCrewType[];
}

// 아티스트 검색 결과 타입
export interface SpotifySearchArtistResponse {
  artists: {
    href: string;
    limit: number;
    next: string | null;
    offset: number;
    previous: string | null;
    total: number;
    items: ArtistItems[];
  };
}

export interface ArtistItems {
  external_urls: {
    spotify: string;
  };
  followers: {
    href: string | null;
    total: number;
  };
  genres: string[];
  href: string;
  id: string;
  images: SpotifyImage[];
  name: string;
  popularity: number;
  type: string; // usually 'artist'
  uri: string;
}

export interface SpotifyImage {
  url: string;
  height: number;
  width: number;
}
// 아티스트 검색 결과 타입 여기까지

// 장르 검색 시 트랙 타입
export interface Artist {
  id: string;
  name: string;
}

export interface Album {
  id: string;
  name: string;
  release_date: string;
  images: { url: string; height: number; width: number }[];
  href: string;
}

export interface ExternalUrls {
  spotify: string;
}

export interface Track {
  id: string;
  name: string;
  artists: Artist[];
  album: Album;
  duration_ms: number;
  preview_url: string | null;
  external_urls: ExternalUrls;
}

export interface SearchGenreTrackType {
  tracks: {
    items: Track[];
  };
}
// 장르 검색 시 트랙 타입 여기까지
