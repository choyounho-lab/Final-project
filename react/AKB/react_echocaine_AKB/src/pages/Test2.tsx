// src/components/SpotifyPlayer.tsx
import React, { useEffect, useRef, useState } from "react";
import axios from "axios";
import { useLocation } from "react-router-dom";

interface SpotifyPlayerProps {
  accessToken?: string;
}

declare global {
  interface Window {
    onSpotifyWebPlaybackSDKReady: () => void;
    Spotify: any;
  }
}

const Test2: React.FC<SpotifyPlayerProps> = () => {
  const CLIENT_ID = "b1107307c96d435f81afc5cf343035ba";
  const CLIENT_SECRET = "52585eb1c18f481fa2b252c0ff7500a7";
  const REDIRECT_URI = "http://127.0.0.1:8000/callback";
  const SCOPE = [
    "user-read-private",
    "user-read-email",
    "streaming",
    "playlist-read-private",
    "playlist-read-collaborative",
    "playlist-modify-public",
    "playlist-modify-private",
  ].join(" ");

  const deviceIdRef = useRef<string | null>(null);
  const tempTokenRef = useRef<string>("");
  const [tempToken, setTempToken] = useState<string>("");
  const [playerReady, setPlayerReady] = useState<boolean>(false);
  const { search } = useLocation();

  const loginWithSpotify = () => {
    const authUrl = `https://accounts.spotify.com/authorize?response_type=code&client_id=${CLIENT_ID}&scope=${encodeURIComponent(
      SCOPE
    )}&redirect_uri=${encodeURIComponent(REDIRECT_URI)}&state=custom-state`;
    window.location.href = authUrl;
  };

  const initializePlayer = () => {
    const player = new window.Spotify.Player({
      name: "My Web Player",
      getOAuthToken: (cb: (token: string) => void) => cb(tempTokenRef.current),
      volume: 0.5,
    });

    player.addListener("ready", ({ device_id }: { device_id: string }) => {
      console.log("✅ Player ready:", device_id);
      deviceIdRef.current = device_id;
      setPlayerReady(true);
    });

    player.addListener("initialization_error", ({ message }: any) =>
      console.error("init error:", message)
    );
    player.addListener("authentication_error", ({ message }: any) =>
      console.error("auth error:", message)
    );
    player.addListener("account_error", ({ message }: any) =>
      console.error("account error:", message)
    );
    player.addListener("playback_error", ({ message }: any) =>
      console.error("playback error:", message)
    );

    player.connect();
  };

  useEffect(() => {
    const scriptId = "spotify-sdk";
    if (!document.getElementById(scriptId)) {
      const script = document.createElement("script");
      script.id = scriptId;
      script.src = "https://sdk.scdn.co/spotify-player.js";
      script.async = true;
      document.body.appendChild(script);
    }

    window.onSpotifyWebPlaybackSDKReady = () => {
      if (tempTokenRef.current) {
        initializePlayer();
      }
    };

    const code = new URLSearchParams(search).get("code");
    if (code) {
      const tokenRequestHeaders = {
        "Content-Type": "application/x-www-form-urlencoded",
        Authorization: "Basic " + btoa(`${CLIENT_ID}:${CLIENT_SECRET}`),
      };

      const data = new URLSearchParams();
      data.append("grant_type", "authorization_code");
      data.append("code", code);
      data.append("redirect_uri", REDIRECT_URI);

      axios
        .post("https://accounts.spotify.com/api/token", data, {
          headers: tokenRequestHeaders,
        })
        .then((res) => {
          const token = res.data.access_token;
          tempTokenRef.current = token;
          setTempToken(token);
          initializePlayer();
        })
        .catch((err) => {
          console.error("🧨 Token Error:", err.response?.data || err.message);
        });
    }
  }, [search]);

  const handlePlay = async () => {
    if (!deviceIdRef.current) return;

    try {
      await axios.put(
        "https://api.spotify.com/v1/me/player/play",
        {
          uris: ["spotify:track:4xeugB5MqWh0jwvXZPxahq"],
          device_id: deviceIdRef.current,
        },
        {
          headers: {
            Authorization: `Bearer ${tempTokenRef.current}`,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("▶️ 재생 성공");
    } catch (err: any) {
      console.error("🎵 재생 실패:", err.response?.data || err.message);
    }
  };

  return (
    <div>
      <button onClick={loginWithSpotify}>로그인</button>
      <h2>🎧 Spotify Web Playback SDK</h2>
      <button onClick={handlePlay} disabled={!playerReady}>
        ▶️ 재생
      </button>
      <p style={{ wordBreak: "break-all", fontSize: "12px" }}>
        현재 토큰: {tempToken || "없음"}
      </p>
    </div>
  );
};

export default Test2;
