import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { SearchGenreTrackType, Track } from "../types/types";
import { apiGetGenreTrack } from "../api/api";

const AlbumDetail = () => {
  const { id } = useParams<string>();

  const [data, setData] = useState<SearchGenreTrackType>({
    tracks: {
      items: [],
    },
  });

  useEffect(() => {
    apiGetGenreTrack("k-pop").then((res) => {
      console.log(res);
      setData(res);
    });
  }, []);

  return (
    <div className="">
      <ul>
        {data.tracks.items.map(
          (item: Track, index: number) =>
            item.id === id && (
              <div className="h-full flex flex-col">
                <img
                  className="m-10 mx-auto mb-5 w-100 h-100 shadow-xl shadow-gray-600 rounded-xl "
                  src={item.album.images[1].url}
                  alt=""
                />

                <p className="mx-10 w-70 text-xl font-black mx-auto line-clamp-2">{`${item.name}`}</p>
                <p className="mx-10 w-70 text-lg font-bold text-gray-400 mx-auto line-clamp-1">{`${item.artists[0].name}`}</p>
                <p className="mx-10 w-70 text-lg font-bold mx-auto">{`${item.album.release_date}`}</p>
                <p className="mx-10 w-70 text-lg font-bold mx-auto">{`재생시간(ms) : ${item.duration_ms}`}</p>
                <p className="mx-10 w-70 text-lg font-bold mx-auto">{`미리듣기 url : ${item.preview_url}`}</p>
                <p className="mx-10 w-70 text-lg font-bold mx-auto">
                  <a
                    href={item.external_urls.spotify}
                  >{`spotify url : ${item.external_urls.spotify}`}</a>
                </p>
              </div>
            )
        )}
      </ul>
    </div>
  );
};

export default AlbumDetail;
