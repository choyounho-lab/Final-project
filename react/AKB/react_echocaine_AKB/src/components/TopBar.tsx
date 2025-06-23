import React, { useState } from "react";
import { Link } from "react-router-dom";
import Logo from "../assets/image/logo4.png";

const TopBar = () => {
  const [loginStatus, setLoginStatus] = useState<boolean>(true);
  const toggleLoginStatus = () => {
    setLoginStatus(!loginStatus);
  };

  const [volume, setVolume] = useState(50);
  const getVolumImage = (value: number) => {
    if (value === 0) return "🔇";
    if (value < 30) return "🔈";
    if (value < 70) return "🔉";
    return "🔊";
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
        <div className="flex justify-between">
          <div className="inline-block">
            <div className="flex w-60 justify-between bg-gradient-to-r from-blue-500/50 to-purple-500/50 p-2 rounded-lg">
              <button className="text-xl cursor-pointer">🔀</button>{" "}
              {/* 랜덤 */}
              <button className="text-xl cursor-pointer">⏮</button>{" "}
              {/* 이전곡 */}
              <button className="text-xl cursor-pointer">▶</button> {/* 재생 */}
              <button className="text-xl cursor-pointer">⏭</button>{" "}
              {/* 다음곡 */}
              <button className="text-xl cursor-pointer">🔁</button>{" "}
              {/* 반복 */}
            </div>
          </div>
          <input type="range" className="w-1/4 mx-10" value="0" />

          <div className="flex items-center w-50">
            <span className="text-2xl">
              {getVolumImage(volume)}
              <input
                type="range"
                className="w-3/5 mx-3"
                value={volume}
                max="100"
                onChange={(e) => setVolume(Number(e.target.value))}
              />
            </span>
          </div>

          <div className="flex items-center justify-center w-40">
            {loginStatus ? (
              <button
                type="button"
                onClick={toggleLoginStatus}
                className="w-25"
              >
                <Link to="/Login" className="mx-5">
                  로그인
                </Link>
              </button>
            ) : (
              <button
                type="button"
                onClick={toggleLoginStatus}
                className="w-25"
              >
                <Link to="/" className="mx-2">
                  로그아웃
                </Link>
              </button>
            )}

            <Link to="/signUp" className="w-20">
              회원가입
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TopBar;
