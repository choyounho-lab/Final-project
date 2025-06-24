export interface ItemType {
  id: number;
  title: string;
  content: string;
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

// ApiGetAlbum 타입
export interface ApiGetAlbumType {
  albums: {
    href: string | null;
    items: ApiGetAlbumItemsType[];
    limit: number;
    next: string | null;
    offset: number;
    previous: string | null;
    total: number;
  };
}
export interface ApiGetAlbumItemsType {
  album_type: "album" | "single" | "compilation";
  artists: {
    external_urls: { [key: string]: string };
    href: string;
    id: string;
    name: string;
    type: "artist";
    uri: string;
  }[];
  external_urls: {
    spotify: string;
  };
  href: string;
  id: string;
  images: {
    url: string;
    height: number;
    width: number;
  }[];
  is_playable: boolean;
  name: string;
  release_date: string;
  release_date_precision: "year" | "month" | "day";
  total_tracks: number;
  type: "album";
  uri: string;
}
// ApiGetAlbum 타입 여기까지

// ApiGetArtist 타입
export interface ApiGetArtistType {
  artists: {
    href: string | null;
    items: ApiGetArtisItemsType[];
    limit: number;
    next: string | null;
    offset: number;
    previous: string | null;
    total: number;
  };
}
export interface ApiGetArtisItemsType {
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
  images: {
    url: string;
    height: number;
    width: number;
  }[];
  name: string;
  popularity: number;
  type: "artist";
  uri: string;
}
// ApiGetAlbum 타입 여기까지

// ApiGetShow 타입
export interface ApiGetShowType {
  shows: {
    href: string | null;
    items: ApiGetShowItemsType[];
    limit: number;
    next: string | null;
    offset: number;
    previous: string | null;
    total: number;
  };
}
export interface ApiGetShowItemsType {
  copyrights: string[];
  description: string;
  explicit: boolean;
  external_urls: {
    spotify: string;
  };
  href: string;
  html_description: string;
  id: string;
  images: {
    url: string;
    height: number;
    width: number;
  }[];
  is_externally_hosted: boolean;
  languages: string[];
  media_type: string;
  name: string;
  publisher: string;
  total_episodes: number;
  type: string;
  uri: string;
}
// ApiGetShow 타입 여기까지

// ApiGetAudioBook 타입
export interface ApiGetAudioBookType {
  audiobooks: {
    href: string | null;
    items: ApiGetShowItemsType[];
    limit: number;
    next: string | null;
    offset: number;
    previous: string | null;
    total: number;
  };
}
export interface ApiGetAudioBookItemsType {
  copyrights: string[];
  description: string;
  explicit: boolean;
  external_urls: {
    spotify: string;
  };
  href: string;
  html_description: string;
  id: string;
  images: {
    url: string;
    height: number;
    width: number;
  }[];
  is_externally_hosted: boolean;
  languages: string[];
  media_type: string;
  name: string;
  publisher: string;
  total_episodes: number;
  type: string;
  uri: string;
}
// ApiGetShow 타입 여기까지

// ApiGetEpisode 타입
export interface ApiGetEpisodeType {
  episodes: {
    href: string | null;
    items: ApiGetShowItemsType[];
    limit: number;
    next: string | null;
    offset: number;
    previous: string | null;
    total: number;
  };
}
export interface ApiGetEpisodeItemsType {
  copyrights: string[];
  description: string;
  explicit: boolean;
  external_urls: {
    spotify: string;
  };
  href: string;
  html_description: string;
  id: string;
  images: {
    url: string;
    height: number;
    width: number;
  }[];
  is_externally_hosted: boolean;
  languages: string[];
  media_type: string;
  name: string;
  publisher: string;
  total_episodes: number;
  type: string;
  uri: string;
}
// ApiGetShow 타입 여기까지

// ApiGetPlayList 타입
export interface ApiGetPlayListType {
  playlists: {
    href: string | null;
    items: ApiGetPlayListItemsType[];
    limit: number;
    next: string | null;
    offset: number;
    previous: string | null;
    total: number;
  };
}
export interface ApiGetPlayListItemsType {
  collaborative: boolean;
  description: string;
  external_urls: {
    spotify: string;
  };
  href: string;
  id: string;
  images: Array<{
    url: string;
    height?: number;
    width?: number;
  }>;
  name: string;
  owner: {
    display_name: string;
    external_urls: {
      spotify: string;
    };
    href: string;
    id: string;
    type: string;
    uri: string;
  };
  primary_color: string | null;
  public: boolean;
  snapshot_id: string;
  tracks: {
    href: string;
    total: number;
  };
  type: string;
  uri: string;
}
// ApiGetPlayList 타입 여기까지

// 최신 앨범 목록 응답 타입
export interface NewReleasesResponse {
  albums: {
    href: string;
    items: AlbumItem[];
    limit: number;
    next: string | null;
    offset: number;
    previous: string | null;
    total: number;
  };
}

// 앨범 아이템 타입
export interface AlbumItem {
  id: string;
  name: string;
  release_date?: string;
  images: SpotifyImage[];
  href: string;
  artists: Artist[];
  // 추가 필드 필요시 선언
}
