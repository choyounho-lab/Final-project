import React, { useEffect, useState } from "react";
import { NavLink, Route, Routes } from "react-router-dom";

import TopBar from "./components/TopBar";
import Sidebar from "./components/SideBar";

import New from "./pages/New";

import Login from "./pages/Login";
import SearchGenreTrack from "./pages/SearchGenreTrack";
import Home from "./pages/Home";
import AlbumDetail from "./pages/AlbumDetail";

import Test2 from "./pages/MusicPlayer";
import GenreList from "./pages/GenreList";
import GenreListDetail from "./pages/GenreListDetail";
import ApiGetAlbumSample from "./pages/apiSample/ApiGetAlbumSample";
import ApiGetArtistSample from "./pages/apiSample/ApiGetArtistSample";
import ApiGetTrackSample from "./pages/apiSample/ApiGetTrackSample";
import ApiGetShowSample from "./pages/apiSample/ApiGetShowSample";
import { Provider, useDispatch } from "react-redux";
import { createStore } from "redux";
import { v4 } from "uuid";
import { getCurrentUser } from "./helper/storage";
import { osName } from "react-device-detect";
import ApiGetAudioBookSample from "./pages/apiSample/ApiGetAudioBookSample";
import ApiGetEpisodeSample from "./pages/apiSample/ApiGetEpisodeSample";
import YouTubePlayer from "./YouTubePlayer";
import YoutubeAudioPlayer from "./pages/YoutubeAudioPlayer";

const Radio = () => <h1 className="p-4 text-2xl font-bold">추천음악</h1>;
const PlayList = () => <h1 className="p-4 text-2xl font-bold">Playlist</h1>;

const reducer = () => {};

const App = () => {
  const accessToken =
    "BQBB8UuByuo09o8sBjCcPuV3Q_NZ8Lf-CQS3L_VJPwufBJduStQLfJvMrLd-VkcVf4RLTsgQjvPWLhCqH8nEAurYOUCd4nQYJ9eYNobOjIfwrojjGxqhH5U6c87JF_rZPDlX7A1oR1FKrIbqplkiBPSaGWeos7-clfuhLRCSKqDsgVFGsI5GKohA6-6vIY_g5X38Y-hT7Kk7zEFskD3Xod_xu9cI0eIp6_s9ro6qT5jRxkuBfPhGxnyBWbXeLXSOW7ay9zUIHOuJhZ-ry842GHyVx6Rwj3LZ5z59aD7tSoq6Er8gmpUE_ukh_L8gyHD5-jBcPAK9ExUwkA";

  return (
    <div className="1ww11">
      <Sidebar />
      <div className="ml-70 bg-gray-100">
        <TopBar />

        <Routes>
          <Route path="/genreList" element={<GenreList />} />
          <Route path="/genreListDetail/:genre" element={<GenreListDetail />} />
          <Route path="/" element={<Home />} />
          <Route path="/new" element={<New />} />
          <Route
            path="/genre"
            element={<YouTubePlayer title={"STAY"} artist={"BLACKPINK"} />}
          />
          {/* <Route
            path="/genre"
            element={<YoutubeAudioPlayer videoId="FzVR_fymZw4" />}
          /> */}
          <Route path="/playlist" element={<PlayList />} />
          <Route path="/list" element={<SearchGenreTrack />} />
          <Route path="/login" element={<Login />} />
          <Route path="/albumDetail/:id" element={<AlbumDetail />} />
          <Route
            path="/callback"
            element={<Test2 accessToken={accessToken} />}
          />
          <Route path="/test" element={<ApiGetEpisodeSample />} />
        </Routes>
      </div>
    </div>
  );
};

export default App;

// test ffff
