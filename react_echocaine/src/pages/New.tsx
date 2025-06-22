import React, { useEffect, useState } from "react";
import { SpotifyImage } from "../types/types";
import { apiGetGenreArtist } from "../api/api";
import { buildStyles, CircularProgressbar } from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import { Link } from "react-router-dom";

function New() {
  const [musicResult, setMusicResult] = useState<SpotifyImage>({
    url: "",
    height: 0,
    width: 0,
  });

  const [data, setData] = useState<any>({});

  useEffect(() => {
    apiGetGenreArtist("kpop").then((res) => {
      console.log(res);
      setData(res);
    });
  }, []);

  return (
    <div className="">
      <ul>
        {data?.artists?.items?.map((item: any, index: number) => (
          <li className="inline-block" key={item.id}>
            {item.images?.[1] && (
              <img className="m-10 w-70 h-70" src={item.images[1].url} alt="" />
            )}
            {/* {item.images.map((item2: any, index2: number) => (
              <img src={item2.url} key={index2} alt="" />
            ))} */}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default New;
