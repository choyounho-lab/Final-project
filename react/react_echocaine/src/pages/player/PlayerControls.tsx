import { formatTime } from "./formatTime";
import { usePlayer } from "./PlayerContext";

const PlayerControls = () => {
  const {
    isPlaying,
    setIsPlaying,
    played,
    duration,
    setPlayed,
    playerRef,
    toggleRepeat,
    repeat,
    toggleShuffle,
    shuffle,
    nextTrack,
  } = usePlayer();

  const togglePlay = () => setIsPlaying(!isPlaying);
  const stop = () => {
    setIsPlaying(false);
    playerRef.current?.seekTo(0);
  };

  const seek = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = parseFloat(e.target.value);
    setPlayed(value);
    playerRef.current?.seekTo(value);
  };

  return (
    <div>
      <div>
        <button onClick={togglePlay}>{isPlaying ? "⏸️" : "▶️"}</button>
        <button onClick={stop}>⏹️</button>
        <button onClick={toggleRepeat}>{repeat ? "🔁" : "🔂"}</button>
        <button onClick={toggleShuffle}>{shuffle ? "🔀" : "➡️"}</button>
      </div>
      <input
        type="range"
        min={0}
        max={1}
        step="any"
        value={played}
        onChange={seek}
      />
      <div>
        {formatTime(played * duration)} / {formatTime(duration)}
      </div>
    </div>
  );
};
