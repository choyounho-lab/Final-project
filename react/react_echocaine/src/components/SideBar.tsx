import React, { useState } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import Logo from "../assets/image/logo4.png";

const Sidebar = () => {
  const navigate = useNavigate();
  const logoCss: string =
    "py-2  hover:bg-gray-700 rounded  flex items-center justify-center text-white m-2 text-3xl w-9/10 mx-auto font-bold";
  const sideBtnCss: string =
    "py-2 px-4 hover:bg-gray-700 rounded h-10 flex items-center justify-start text-white w-9/10 mx-auto";
  const playListCss: string =
    "py-2 px-4 bg-white hover:bg-gray-700 rounded h-10 flex items-center justify-start text-black w-9/10 m-1 mx-auto ";
  const myPlayListCss: string =
    "py-2 px-4 bg-white hover:bg-gray-700 rounded h-10 flex items-center justify-start text-black w-9/10 m-1 mx-auto ";

  const [openPlayList, setOpenPlayList] = useState<boolean>(false);
  const playList = () => {
    setOpenPlayList(!openPlayList);
  };
  const [openMyPlayList, setOpenMyPlayList] = useState<boolean>(false);
  const myPlayList = () => {
    setOpenMyPlayList(!openMyPlayList);
  };

  const [search, setSearch] = useState<string>("");
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearch(e.target.value);
  };

  return (
    <div className="fixed">
      <div
        className="w-70 h-screen bg-black text-white flex flex-col relative "
        style={{
          overflow: "hidden",
        }}
      >
        <div
          style={{
            background: `url(${Logo})`,
            position: "absolute",
            width: "2000%",
            height: "2000%",
            opacity: 0.2,
            transform: "rotate(-10deg) translate(-10%, -50%)",
          }}
        />
        <div className="w-70 " style={{ position: "absolute" }}>
          <Link to="/" className={logoCss}>
            <img src={Logo} className="mr-3" />
            Echocaine
          </Link>
          <input
            type="text"
            className="py-2 px-4 hover:bg-gray-700 rounded h-10 flex items-center justify-center border rounded-sm text-white w-9/10 mx-auto"
            placeholder="검색"
            onClick={() => navigate("/genreList")}
            onChange={onChange}
            value={search}
          />
          <Link to="/" className={sideBtnCss}>
            Home
          </Link>
          <Link to="/new" className={sideBtnCss}>
            NewSic
          </Link>

          <Link to="/genre" className={sideBtnCss}>
            추천 음악
          </Link>
          <Link to="/callback" className={sideBtnCss}>
            음원재생
          </Link>
          <Link to="/list" className={`${sideBtnCss} mb-5`}>
            장르 검색결과
          </Link>
          <Link to="/test" className={sideBtnCss}>
            test
          </Link>

          <div className="border border-white mb-3"></div>
          <button onClick={playList} className={sideBtnCss}>
            PlayList
          </button>
          {openPlayList && (
            <div className="border p-2 flex justify-center align items-center flex-col m-1 rounded-lg">
              <button className={playListCss} type="button">
                곡이름1
              </button>
              <button className={playListCss} type="button">
                곡이름2
              </button>
            </div>
          )}
          <button
            onClick={myPlayList}
            className="py-2 px-4 hover:bg-gray-700 rounded h-10 flex items-center justify-start text-white w-9/10 mx-auto"
          >
            나만의 플리
          </button>
          {openMyPlayList && (
            <div className="border p-2 flex justify-center align items-center flex-col m-2 rounded-lg">
              <button className={myPlayListCss} type="button">
                개인플리1
              </button>
              <button className={myPlayListCss} type="button">
                개인플리2
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Sidebar;
