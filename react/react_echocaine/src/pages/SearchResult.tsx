import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import {
  ApiGetAlbumItemsType,
  ApiGetAlbumType,
  ApiGetArtisItemsType,
  ApiGetArtistType,
  SearchGenreTrackType,
  Track,
} from "../types/types";
import {
  apiGetAlbumList,
  apiGetArtistList,
  apiGetGenreTrack,
  apiGetTrackList,
} from "../api/api";

const SearchResult = () => {
  const { search } = useParams();
  const { genre } = useParams<string>();
  const [data, setData] = useState<SearchGenreTrackType>({
    tracks: {
      items: [],
    },
  });
  const [albumData, setAlbumData] = useState<ApiGetAlbumType>({
    albums: {
      href: "",
      items: [],
      limit: 0,
      next: "",
      offset: 0,
      previous: "",
      total: 0,
    },
  });
  const [artistData, setArtistData] = useState<ApiGetArtistType>({
    artists: {
      href: "",
      items: [],
      limit: 0,
      next: "",
      offset: 0,
      previous: "",
      total: 0,
    },
  });
  useEffect(() => {
    if (search) {
      apiGetTrackList(search).then((res) => {
        console.log(genre);
        console.log(res);
        setData(res);
      });
      apiGetAlbumList(search, 0).then((abbumList) => {
        console.log(genre);
        console.log(abbumList);
        setAlbumData(abbumList);
      });
      apiGetArtistList(search).then((artistList) => {
        console.log(genre);
        console.log(artistList);
        setArtistData(artistList);
      });
    }
  }, []);

  return (
    <div className="mt-10">
      <div className="my-15 ">
        <Link to="/" className="ml-10 text-3xl">
          {`아티스트`}{" "}
        </Link>
        <ul>
          {artistData.artists.items.map(
            (item: ApiGetArtisItemsType, index: number) =>
              item.images[1]?.url || item.images[0]?.url ? (
                <li className="inline-block " key={item.id}>
                  {
                    <div className="h-100 flex flex-col">
                      <Link to={`/artistDetail/${item.id}`}>
                        <img
                          className="m-10 mb-5 w-70 h-70 shadow-xl shadow-gray-600 rounded-full"
                          src={item.images[1]?.url || item.images[0]?.url}
                          alt=""
                        />
                      </Link>
                      <p className="mx-10 w-70 text-center text-lg font-bold line-clamp-1">{`${item.name}`}</p>
                      <p className="mx-10 w-70 text-center text-sm font-bold text-gray-400">{`Followers : ${item.followers.total.toLocaleString()}`}</p>
                    </div>
                  }
                </li>
              ) : null
          )}
        </ul>
      </div>
      <Link to="/" className="ml-10 mt-20 text-3xl">{`앨범`}</Link>
      <div className="mb-20">
        <ul>
          {albumData.albums.items.map(
            (item: ApiGetAlbumItemsType, index: number) => (
              <li className="inline-block " key={item.id}>
                {
                  <div className="h-100 flex flex-col">
                    <Link to={`/albumDetail/${item.id}`}>
                      <img
                        className="m-10 mb-5 w-70 h-70 shadow-xl shadow-gray-600 rounded-xl"
                        src={item.images[1].url}
                        alt=""
                      />
                    </Link>
                    <p className="mx-10 w-70 text-lg font-bold line-clamp-1">{`${item.name}`}</p>
                    <p className="mx-10 w-70 text-sm font-bold text-gray-400">{`${item.artists[0].name}`}</p>
                  </div>
                }
              </li>
            )
          )}
        </ul>
      </div>

      <div className="">
        <Link to="/" className="m-10 text-3xl">{`노래`}</Link>
        <ul>
          {data.tracks.items.map((item: Track, index: number) => (
            <li className="inline-block w-full" key={item.id}>
              {
                <div className="h-30 flex items-center">
                  <Link
                    to={`/trackDetail?search=${search}&id=${item.id}`}
                    className="inline-block"
                  >
                    <img
                      className="ml-10 w-20 h-20 shadow-lg shadow-gray-600 rounded-xl"
                      src={item.album.images[1].url}
                      alt=""
                    />
                  </Link>
                  <div className=" inline-block ">
                    <span className="mx-10 h-10  text-xl font-bold line-clamp-1">{`${item.name}`}</span>
                    <span className="mx-10 h-10  text-sm font-bold text-gray-400">{`${item.artists[0].name}`}</span>
                  </div>
                </div>
              }
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default SearchResult;
