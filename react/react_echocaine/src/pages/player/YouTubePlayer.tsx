import React, { useEffect, useState } from "react";
import ReactPlayer from "react-player";
import { usePlayer } from "./PlayerContext";
import { searchYoutubeVideoId } from "./searchYoutubeVideoId";

interface Props {
  title: string;
  artist: string;
}

const YouTubePlayer: React.FC<Props> = ({ title, artist }) => {
  const [videoId, setVideoId] = useState<string | null>(null);
  const { isPlaying, setPlayed, setDuration, volume, nextTrack, playerRef } =
    usePlayer();

  useEffect(() => {
    const fetchVideo = async () => {
      const query = `${title} ${artist}`;
      const id = await searchYoutubeVideoId(query);
      setVideoId(id);
    };
    fetchVideo();
  }, [title, artist]);

  if (!videoId) return <div>로딩 중...</div>;

  return (
    <ReactPlayer
      ref={playerRef}
      url={`https://www.youtube.com/watch?v=${videoId}`}
      playing={isPlaying}
      controls={false}
      width="100%"
      height="360px"
      volume={volume / 100}
      onProgress={({ played }) => setPlayed(played)}
      onDuration={setDuration}
      onEnded={nextTrack}
    />
  );
};

export default YouTubePlayer;
