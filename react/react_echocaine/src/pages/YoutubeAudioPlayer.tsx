import { useEffect, useRef, useState } from "react";

function YoutubeAudioPlayer({ videoId }: { videoId: string }) {
  const playerRef = useRef<any>(null);
  const intervalRef = useRef<number | null>(null);
  const [progress, setProgress] = useState(0);

  useEffect(() => {
    // YouTube API 스크립트 로드
    const tag = document.createElement("script");
    tag.src = "https://www.youtube.com/iframe_api";
    document.body.appendChild(tag);

    // onYouTubeIframeAPIReady 정의 (전역함수)
    (window as any).onYouTubeIframeAPIReady = () => {
      playerRef.current = new (window as any).YT.Player("player", {
        videoId,
        events: {
          onReady: () => {
            intervalRef.current = window.setInterval(() => {
              const current = playerRef.current?.getCurrentTime?.() ?? 0;
              const total = playerRef.current?.getDuration?.() ?? 1;
              setProgress((current / total) * 100);
            }, 1000);
          },
        },
      });
    };

    return () => {
      if (intervalRef.current) clearInterval(intervalRef.current);
      playerRef.current?.destroy?.();
    };
  }, [videoId]);

  return (
    <div>
      <div id="player" style={{ display: "none" }}></div>

      <div style={{ display: "flex", alignItems: "center", gap: "0.5rem" }}>
        <input
          type="range"
          min={0}
          max={100}
          value={progress}
          readOnly
          style={{ flex: 1 }}
        />
      </div>
    </div>
  );
}
export default YoutubeAudioPlayer;
