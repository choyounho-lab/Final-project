import React, { useEffect, useState } from "react";

import { apiGetGenreTrack } from "../api/api";
import { SearchGenreTrackType, Track } from "../types/types";
import { Link } from "react-router-dom";
import axios from "axios";

function GenreList() {
  interface GenreListType {
    id: number;
    name: String;
    color: string;
  }

  const genreList: GenreListType[] = [
    { id: 0, name: "Set Lists", color: "#FF6B6B" },
    { id: 1, name: "Spatial Audio", color: "#6BCB77" },
    { id: 2, name: "Charts", color: "#4D96FF" },
    { id: 3, name: "Sound Therapy", color: "#FFD93D" },
    { id: 4, name: "Hip-Hop", color: "#A66DD4" },
    { id: 5, name: "Country", color: "#FF7F50" },
    { id: 6, name: "Pop", color: "#3EC1D3" },
    { id: 7, name: "R&B", color: "#F7B801" },
    { id: 8, name: "Latin", color: "#6A0572" },
    { id: 9, name: "Indie", color: "#FF9F1C" },
    { id: 10, name: "Alternative", color: "#845EC2" },
    { id: 11, name: "K-Pop", color: "#0081CF" },
    { id: 12, name: "Dance", color: "#FFC75F" },
    { id: 13, name: "DJ Mixes", color: "#FF6F91" },
    { id: 14, name: "Hits", color: "#3F72AF" },
    { id: 15, name: "Essentials", color: "#D65DB1" },
    { id: 16, name: "Party", color: "#3E8E7E" },
    { id: 17, name: "Feel Good", color: "#FF9671" },
    { id: 18, name: "Rock", color: "#00C9A7" },
    { id: 19, name: "Classic Rock", color: "#845EC2" },
    { id: 20, name: "Classical", color: "#FFC300" },
    { id: 21, name: "Music Videos", color: "#D45087" },
    { id: 22, name: "Electronic", color: "#5FAD56" },
    { id: 23, name: "Urbano Latino", color: "#00B8A9" },
    { id: 24, name: "Metal", color: "#F86624" },
    { id: 25, name: "Film, TV & Stage", color: "#2D3047" },
    { id: 26, name: "Hard Rock", color: "#B80C09" },
    { id: 27, name: "Sleep", color: "#A8D0E6" },
    { id: 28, name: "Chill", color: "#FFB6B9" },
    { id: 29, name: "Wellbeing", color: "#9B5DE5" },
    { id: 30, name: "Kids", color: "#F4D35E" },
    { id: 31, name: "Familly", color: "#2EC4B6" },
    { id: 32, name: "Christian", color: "#DF5E88" },
    { id: 33, name: "Pop Latino", color: "#FFE156" },
    { id: 34, name: "Jazz", color: "#3C91E6" },
    { id: 35, name: "Decades", color: "#DB5461" },
    { id: 36, name: "2000s", color: "#6A0572" },
    { id: 37, name: "'80s", color: "#FFC857" },
    { id: 38, name: "Afrobeats", color: "#2E294E" },
    { id: 39, name: "Reggae", color: "#37FF8B" },
    { id: 40, name: "Fitness", color: "#FEC260" },
    { id: 41, name: "Sports", color: "#5863F8" },
    { id: 42, name: "Soul/Funk", color: "#EA526F" },
    { id: 43, name: "Worldwide", color: "#A8E6CF" },
    { id: 44, name: "Americana", color: "#E98074" },
    { id: 45, name: "Acoustic", color: "#C06C84" },
    { id: 46, name: "Blues", color: "#355C7D" },
  ];

  const [data, setData] = useState<SearchGenreTrackType>({
    tracks: {
      items: [],
    },
  });

  useEffect(() => {
    apiGetGenreTrack("k-pop BTS").then((res) => {
      console.log(res);
      setData(res);
    });
  }, []);
  useEffect(() => {
    const script = document.createElement("script");
    script.src = "https://sdk.scdn.co/spotify-player.js";
    script.async = true;
    document.body.appendChild(script);
  }, []);

  return (
    <div className="">
      <ul>
        {genreList.map((item: GenreListType, index: number) => (
          <li className="inline-block " key={item.id}>
            {
              <div className="h-100 flex flex-col ">
                <Link to={`/genreListDetail/${item.id}`}>
                  <div
                    className="m-10 mb-5 w-70 h-70 shadow-xl shadow-gray-600 rounded-xl"
                    style={{ background: `${item.color}` }}
                  ></div>
                </Link>
                <p className="mx-10 w-70 text-lg font-bold line-clamp-1">{`${item.name}`}</p>
              </div>
            }
          </li>
        ))}
      </ul>
    </div>
  );
}

export default GenreList;
