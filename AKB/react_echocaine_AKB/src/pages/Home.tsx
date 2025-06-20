import React, { useEffect, useState } from "react";
import { apiGetNewReleases, apiGetTracksByAlbumId } from "../api/api";
import { NewReleasesResponse, AlbumItem, Track } from "../types/types";

function Home() {
  // 최신 앨범 데이터 상태
  const [newReleasesData, setNewReleasesData] =
    useState<NewReleasesResponse | null>(null);

  // 앨범별 수록곡을 저장하는 상태: key는 앨범 id, value는 Track 배열
  const [albumTracksMap, setAlbumTracksMap] = useState<Record<string, Track[]>>(
    {}
  );

  useEffect(() => {
    apiGetNewReleases()
      .then(async (data) => {
        setNewReleasesData(data);

        const albumItems = data.albums.items; // 모든 앨범 불러오기
        const tracksMap: Record<string, Track[]> = {};

        // 각 앨범별 트랙을 최대 5개씩 불러오기
        for (const album of albumItems) {
          if (album.id) {
            try {
              const res = await apiGetTracksByAlbumId(album.id);
              tracksMap[album.id] = res.items.slice(0, 5); // 최대 5곡씩
            } catch (e) {
              console.error(`앨범(${album.id}) 트랙 불러오기 오류:`, e);
              tracksMap[album.id] = [];
            }
          }
        }

        setAlbumTracksMap(tracksMap);
      })
      .catch((e) => console.error("신보 불러오기 오류:", e));
  }, []);

  return (
    <div className="p-4 space-y-1 bg-gray-130 min-h-screen">
      {/* 최신 앨범 */}
      <section>
        <h2 className="text-2xl font-bold mb-4">최신 앨범</h2>
        <ul className="flex gap-10 overflow-x-auto pb-4">
          {newReleasesData?.albums?.items.map((album: AlbumItem) => (
            <li
              key={album.id}
              className="bg-gray-1000 rounded-xl shadow-md p-3 flex gap-10 items-start max-w-100 h-55"
            >
              {/* 왼쪽 앨범 이미지 */}
              <img
                className="w-50 h-50 rounded-xl object-cover flex-shrink-0"
                src={album.images?.[0]?.url ?? "/fallback.jpg"}
                alt={album.name}
                loading="lazy"
              />

              {/* 오른쪽 수록곡 목록 */}
              <div className="flex-1 flex flex-col">
                <span className="text-lg font-bold mb-4">{album.name}</span>
                <ul className="space-y-1 max-h-50 overflow-y-auto">
                  {albumTracksMap[album.id]?.length ? (
                    albumTracksMap[album.id].map((track) => (
                      <li
                        key={track.id}
                        className="text-gray-700 break-keep max-w-40 whitespace-normal border-b border-gray-300 pb-1"
                        title={track.name}
                      >
                        {track.name}
                      </li>
                    ))
                  ) : (
                    <li className="text-gray-400">수록곡 없음</li>
                  )}
                </ul>
              </div>
            </li>
          ))}
        </ul>
      </section>
    </div>
  );
}

export default Home;
