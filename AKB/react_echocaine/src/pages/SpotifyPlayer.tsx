import axios from "axios";
import { useEffect, useRef, useState } from "react";
import { useLocation, useParams } from "react-router-dom";

interface SpotifyPlayerProps {
  accessToken: string;
}

declare global {
  interface Window {
    onSpotifyWebPlaybackSDKReady: () => void;
    Spotify: any;
  }
}

const SpotifyPlayer: React.FC<SpotifyPlayerProps> = ({ accessToken }) => {
  const deviceIdRef = useRef<string | null>(null);
  const [playerReady, setPlayerReady] = useState(false);

  const { search } = useLocation();
  const q = new URLSearchParams(search);

  const [tempToken, setTempToken] = useState("");
  const tempTokenRef = useRef("");

  const actionLogin = () => {
    const clientId = "b1107307c96d435f81afc5cf343035ba";
    const redirectUri = "http://127.0.0.1:8000/callback";
    const scope =
      "user-read-private user-read-email streaming playlist-read-private playlist-read-collaborative playlist-modify-public playlist-modify-private";
    const state = "okiujyhtgrfedwsv";

    const loginUrl = `https://accounts.spotify.com/authorize?response_type=code&client_id=${clientId}&scope=${encodeURIComponent(
      scope
    )}&redirect_uri=${encodeURIComponent(redirectUri)}&state=${state}`;

    window.location.href = loginUrl; // ← 브라우저를 해당 주소로 리디렉션
  };

  window.onSpotifyWebPlaybackSDKReady = () => {
    console.log(tempTokenRef.current);
    const player = new window.Spotify.Player({
      name: "My Web Player",
      getOAuthToken: (cb: (token: string) => void) => cb(tempTokenRef.current),
      volume: 0.5,
    });

    player.addListener("ready", ({ device_id }: { device_id: string }) => {
      console.log("✅ 플레이어 준비 완료:", device_id);
      deviceIdRef.current = device_id;
      setPlayerReady(true);
    });

    player.addListener("initialization_error", ({ message }: any) =>
      console.error("초기화 에러:", message)
    );
    player.addListener("authentication_error", ({ message }: any) =>
      console.error("인증 에러:", message)
    );
    player.addListener("account_error", ({ message }: any) =>
      console.error("계정 에러:", message)
    );
    player.addListener("playback_error", ({ message }: any) =>
      console.error("재생 에러:", message)
    );

    player.connect();
    console.log(player._messageHandlers);
  };

  useEffect(() => {
    if (
      !document.querySelector(
        'script[src="https://sdk.scdn.co/spotify-player.js"]'
      )
    ) {
      const script = document.createElement("script");
      script.src = "https://sdk.scdn.co/spotify-player.js";
      script.async = true;
      document.body.appendChild(script);
    }

    const code = q.get("code");
    if (code) {
      const headers = {
        "Content-Type": "application/x-www-form-urlencoded",
        Authorization:
          "Basic " +
          btoa(
            `b1107307c96d435f81afc5cf343035ba:52585eb1c18f481fa2b252c0ff7500a7`
          ), // Base64 인코딩된 client_id:client_secret
      };

      console.log(headers.Authorization);

      const data = new URLSearchParams();
      data.append("grant_type", "authorization_code");
      data.append("code", code);
      data.append("redirect_uri", "http://127.0.0.1:8000/callback");

      console.log(data);
      axios
        .post("https://accounts.spotify.com/api/token", data, { headers })
        .then((res) => {
          console.log("data");
          console.log(res.data.access_token);
          setTempToken(res.data.access_token);
          tempTokenRef.current = res.data.access_token;
        });
    }
  }, []);

  useEffect(() => {
    if (tempToken) {
      window.onSpotifyWebPlaybackSDKReady(); // SDK가 이미 로드됐다면 수동 호출
    }
  }, []);

  const handlePlay = async () => {
    if (!deviceIdRef.current) return;
    try {
      console.log({
        uris: ["spotify:track:4uLU6hMCjMI75M1A2tKUQC"], // 트랙 URI (예: Rick Astley – Never Gonna Give You Up)
        device_id: deviceIdRef.current,
      });

      console.log(`Bearer ${tempTokenRef.current}`);

      axios
        .get("https://api.spotify.com/v1/me/player/devices", {
          headers: {
            Authorization: `Bearer ${tempTokenRef.current}`,
            "Content-Type": "application/json",
          },
        })
        .then((data) => console.log(data));

      // const res = await fetch("https://api.spotify.com/v1/me/player/play", {
      //   method: "PUT",
      //   headers: {
      //     Authorization: `Bearer ${accessToken}`,
      //     "Content-Type": "application/json",
      //   },
      //   body: JSON.stringify({
      //     uris: ["spotify:track:4uLU6hMCjMI75M1A2tKUQC"], // 트랙 URI (예: Rick Astley – Never Gonna Give You Up)
      //     device_id: deviceIdRef.current,
      //   }),
      // });

      // if (!res.ok) {
      //   const err = await res.json();
      //   console.error("🎵 재생 실패:", err);
      // } else {
      //   console.log("▶️ 재생 시작");
      // }
    } catch (err) {
      console.error("🎵 재생 중 오류:", err);
    }
  };

  return (
    <div>
      <button type="button" onClick={actionLogin}>
        로그인 {tempToken}
      </button>
      <h1>🎧 Spotify Web Playback SDK 작동 중...</h1>
      <button onClick={handlePlay} disabled={!playerReady}>
        ▶️ 재생하기
      </button>
    </div>
  );
};

export default SpotifyPlayer;
