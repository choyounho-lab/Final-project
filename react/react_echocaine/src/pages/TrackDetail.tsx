import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { SearchGenreTrackType, Track } from "../types/types";
import { apiGetTrackList } from "../api/api";
import { usePlayer } from "./player/PlayerContext";
import { searchYoutubeVideoId } from "./player/searchYoutubeVideoId";
import ReactPlayer from "react-player";

const TrackDetail: React.FC = () => {
  const [searchParams] = useSearchParams();
  const search = searchParams.get("search");
  const id = searchParams.get("id");

  const [data, setData] = useState<SearchGenreTrackType>({
    tracks: {
      items: [],
    },
  });

  useEffect(() => {
    if (!search) return;
    apiGetTrackList(search).then((res) => {
      console.log(res);
      setData(res);

      const track = res.tracks.items.find((item: any) => item.id === id);
      if (!track) return;

      const query = `${track.name} ${track.artists[0].name}`;
      searchYoutubeVideoId(query).then((youtubeId) => {
        setVideoId(youtubeId);
      });
    });
  }, [search, id]);

  const [videoId, setVideoId] = useState<string | null>(null);
  const { isPlaying, setPlayed, setDuration, volume, nextTrack, playerRef } =
    usePlayer();

  if (!videoId) return <div>로딩 중...</div>;

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
                {/* <p className="mx-10 w-70 text-lg font-bold mx-auto">{`재생시간(ms) : ${item.duration_ms}`}</p> */}

                <div className="mt-10">
                  <ReactPlayer
                    ref={playerRef}
                    url={`https://www.youtube.com/watch?v=${videoId}`}
                    playing={isPlaying}
                    controls={false}
                    width="100%"
                    height="300px"
                    volume={volume / 100}
                    onProgress={({ played }) => setPlayed(played)}
                    onDuration={setDuration}
                    onEnded={nextTrack}
                  />
                </div>
              </div>
            )
        )}
      </ul>
    </div>
  );
};

export default TrackDetail;
