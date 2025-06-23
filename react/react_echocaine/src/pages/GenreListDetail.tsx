import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import {
  ApiGetAlbumItemsType,
  ApiGetAlbumType,
  SearchGenreTrackType,
  Track,
} from "../types/types";
import { apiGetAlbumList, apiGetGenreTrack } from "../api/api";

const GenreListDetail = () => {
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
  useEffect(() => {
    if (genre) {
      apiGetGenreTrack(genre).then((res) => {
        console.log(genre);
        console.log(res);
        setData(res);
      });
      apiGetAlbumList(genre, 0).then((abbumList) => {
        console.log(genre);
        console.log(abbumList);
        setAlbumData(abbumList);
      });
    }
  }, []);

  return (
    <div className="">
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

      <ul>
        {data.tracks.items.map((item: Track, index: number) => (
          <li className="inline-block w-full" key={item.id}>
            {
              <div className="h-30 flex items-center">
                <Link to={`/albumDetail/${item.id}`} className="inline-block">
                  <img
                    className="ml-20 w-20 h-20 shadow-lg shadow-gray-600 rounded-xl"
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
  );
};

export default GenreListDetail;
