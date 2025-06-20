import React, { useEffect, useState } from "react";

import { apiGetMusicList } from "../api/api";
import { ArtistItems, SpotifySearchArtistResponse } from "../types/types";

function Home() {
  const [data, setData] = useState<SpotifySearchArtistResponse>({
    artists: {
      href: "",
      limit: 0,
      next: "",
      offset: 0,
      previous: "",
      total: 0,
      items: [],
    },
  });

  useEffect(() => {
    apiGetMusicList().then((res) => {
      console.log(res);
      setData(res);
    });
  }, []);

  return (
    <div className="">
      <ul>
        {data.artists.items.map((item: ArtistItems, index: number) => (
          <li className="inline-block" key={index}>
            <div className="h-100 flex flex-col">
              <img
                className="m-10 mb-5 w-70 h-70 shadow-xl shadow-gray-600 rounded-xl"
                src={
                  item.images.length >= 2
                    ? item.images[1].url
                    : item.images[0]?.url || "/fallback.jpg"
                }
                alt=""
              />
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Home;
