import React from "react";
import { Link } from "react-router-dom";
import Logo from "../assets/image/logo4.png";
import { usePlayer } from "../pages/player/PlayerContext";
import { formatTime } from "../pages/player/formatTime";

const TopBar = () => {
  const {
    isPlaying,
    setIsPlaying,
    played,
    setPlayed,
    duration,
    repeat,
    toggleRepeat,
    shuffle,
    toggleShuffle,
    nextTrack,
    playerRef,
    volume,
    setVolume,
  } = usePlayer();

  const [loginStatus, setLoginStatus] = React.useState(true);

  const togglePlayStatus = () => setIsPlaying(!isPlaying);

  const getVolumeIcon = (v: number) => {
    if (v === 0) return "🔇";
    if (v < 30) return "🔈";
    if (v < 70) return "🔉";
    return "🔊";
  };

  const seekBarChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = parseFloat(e.target.value);
    setPlayed(value);
    playerRef.current?.seekTo(value);
  };

  const handleVolumeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setVolume(Number(e.target.value));
  };

  return (
    <div
      className="w-full h-17 bg-black text-white px-4 py-3 sticky top-0 right-0"
      style={{
        overflow: "hidden",
      }}
    >
      <div
        style={{
          background: `url(${Logo})`,
          position: "absolute",
          width: "250%",
          height: "500%",
          opacity: 0.2,
          transform: "rotate(5deg) translate(-50px, -50px)",
        }}
      />
      <div className="absolute top-0 right-0 p-3 w-full">
        <div className="flex justify-between items-center">
          <div className="flex w-60 justify-between bg-gradient-to-r from-blue-500/50 to-purple-500/50 p-2 rounded-lg">
            <button className="text-xl" onClick={toggleShuffle}>
              {shuffle ? "🔀" : "➡️"}
            </button>
            <button
              className="text-xl"
              onClick={() => playerRef.current?.seekTo(0)}
              disabled={!playerRef.current}
            >
              ⏮
            </button>
            <button className="text-xl" onClick={togglePlayStatus}>
              {isPlaying ? "⏸️" : "▶️"}
            </button>
            <button className="text-xl" onClick={nextTrack}>
              ⏭
            </button>
            <button className="text-xl" onClick={toggleRepeat}>
              {repeat ? "🔁" : "🔂"}
            </button>
          </div>

          <div className="w-1/2 mx-5">
            <input
              type="range"
              min={0}
              max={1}
              step="any"
              value={played}
              onChange={seekBarChange}
              className="w-full"
            />
            <div className="text-xs text-center mt-1 text-gray-300">
              {formatTime(played * duration)} / {formatTime(duration)}
            </div>
          </div>

          <div className="flex items-center w-50">
            <span className="text-2xl">
              {getVolumeIcon(volume)}
              <input
                type="range"
                className="w-3/5 mx-3"
                value={volume}
                max="100"
                onChange={handleVolumeChange}
              />
            </span>
          </div>

          <div className="flex items-center justify-center w-40">
            {loginStatus ? (
              <Link
                to="/Login"
                onClick={() => setLoginStatus(false)}
                className="mx-5"
              >
                로그인
              </Link>
            ) : (
              <Link
                to="/"
                onClick={() => setLoginStatus(true)}
                className="mx-2"
              >
                로그아웃
              </Link>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default TopBar;
