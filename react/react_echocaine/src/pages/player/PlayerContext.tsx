import React, {
  createContext,
  useContext,
  useRef,
  useState,
  ReactNode,
  MutableRefObject,
} from "react";
import ReactPlayer from "react-player";

export interface Track {
  title: string;
  artist: string;
  videoId: string;
}

interface PlayerContextType {
  isPlaying: boolean;
  setIsPlaying: (value: boolean) => void;
  played: number;
  setPlayed: (value: number) => void;
  duration: number;
  setDuration: (value: number) => void;
  currentTrack: Track;
  trackList: Track[];
  nextTrack: () => void;
  repeat: boolean;
  toggleRepeat: () => void;
  shuffle: boolean;
  toggleShuffle: () => void;
  volume: number;
  setVolume: (value: number) => void;
  playerRef: MutableRefObject<ReactPlayer | null>;
}

const PlayerContext = createContext<PlayerContextType | undefined>(undefined);

export const PlayerProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [isPlaying, setIsPlaying] = useState(false);
  const [played, setPlayed] = useState(0);
  const [duration, setDuration] = useState(0);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [repeat, setRepeat] = useState(false);
  const [shuffle, setShuffle] = useState(false);
  const [volume, setVolume] = useState(100);
  const playerRef = useRef<ReactPlayer | null>(null);

  const trackList: Track[] = [
    { title: "STAY", artist: "BLACKPINK", videoId: "FzVR_fymZw4" },
    {
      title: "AS IF IT'S YOUR LAST",
      artist: "BLACKPINK",
      videoId: "Amq-qlqbjYA",
    },
    { title: "DDU-DU DDU-DU", artist: "BLACKPINK", videoId: "IHNzOHi8sJs" },
  ];

  const currentTrack = trackList[currentIndex];

  const nextTrack = () => {
    if (repeat) {
      playerRef.current?.seekTo(0);
      return;
    }

    if (shuffle) {
      let next = Math.floor(Math.random() * trackList.length);
      while (next === currentIndex && trackList.length > 1) {
        next = Math.floor(Math.random() * trackList.length);
      }
      setCurrentIndex(next);
    } else {
      setCurrentIndex((prev) => (prev + 1) % trackList.length);
    }
  };

  const toggleRepeat = () => setRepeat((prev) => !prev);
  const toggleShuffle = () => setShuffle((prev) => !prev);

  return (
    <PlayerContext.Provider
      value={{
        isPlaying,
        setIsPlaying,
        played,
        setPlayed,
        duration,
        setDuration,
        currentTrack,
        trackList,
        nextTrack,
        repeat,
        toggleRepeat,
        shuffle,
        toggleShuffle,
        volume,
        setVolume,
        playerRef,
      }}
    >
      {children}
    </PlayerContext.Provider>
  );
};

export const usePlayer = () => {
  const context = useContext(PlayerContext);
  if (!context)
    throw new Error("usePlayer must be used within a PlayerProvider");
  return context;
};
