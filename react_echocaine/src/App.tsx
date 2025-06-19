import React, { useEffect, useState } from "react";
import { NavLink, Route, Routes } from "react-router-dom";

import TopBar from "./components/TopBar";
import Sidebar from "./components/SideBar";

import MovieDetail from "./pages/MovieDetail";
import New from "./pages/New";

import Genre from "./pages/Genre";

import Login from "./pages/Login";
import SearchGenreTrack from "./pages/SearchGenreTrack";
import Home from "./pages/Home";
import AlbumDetail from "./pages/AlbumDetail";
import SpotifyPlayer from "./pages/SpotifyPlayer";
import Test2 from "./pages/Test2";

const Radio = () => <h1 className="p-4 text-2xl font-bold">추천음악</h1>;
const PlayList = () => <h1 className="p-4 text-2xl font-bold">Playlist</h1>;

const App = () => {
  const [loginOpen, setLoginOpen] = useState<boolean>(false);
  const accessToken =
    "BQBB8UuByuo09o8sBjCcPuV3Q_NZ8Lf-CQS3L_VJPwufBJduStQLfJvMrLd-VkcVf4RLTsgQjvPWLhCqH8nEAurYOUCd4nQYJ9eYNobOjIfwrojjGxqhH5U6c87JF_rZPDlX7A1oR1FKrIbqplkiBPSaGWeos7-clfuhLRCSKqDsgVFGsI5GKohA6-6vIY_g5X38Y-hT7Kk7zEFskD3Xod_xu9cI0eIp6_s9ro6qT5jRxkuBfPhGxnyBWbXeLXSOW7ay9zUIHOuJhZ-ry842GHyVx6Rwj3LZ5z59aD7tSoq6Er8gmpUE_ukh_L8gyHD5-jBcPAK9ExUwkA";

  return (
    <div className="">
      <Sidebar />
      <div className="ml-48 bg-gray-100">
        <TopBar />




        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/new" element={<New />} />
          <Route path="/genre" element={<Genre />} />
          <Route path="/playlist" element={<PlayList />} />
          <Route path="/movieDetail/:id" element={<MovieDetail />} />
          <Route path="/test" element={<SearchGenreTrack />} />
          <Route path="/login" element={<Login />} />
          <Route path="/albumDetail/:id" element={<AlbumDetail />} />
          <Route
            path="/callback"
            element={<Test2 accessToken={accessToken} />}
          />
        </Routes>
      </div>
    </div>
  );
};

export default App;
