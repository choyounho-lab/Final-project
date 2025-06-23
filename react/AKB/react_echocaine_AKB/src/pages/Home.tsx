import React, { useEffect, useState } from "react";
import { apiGetNewReleases, apiGetTracksByAlbumId } from "../api/api";
import { NewReleasesResponse, AlbumItem, Track } from "../types/types";
import "../styles/Home.css";

function Home() {
  const [newReleasesData, setNewReleasesData] =
    useState<NewReleasesResponse | null>(null);
  const [albumTracksMap, setAlbumTracksMap] = useState<Record<string, Track[]>>(
    {}
  );

  useEffect(() => {
    apiGetNewReleases()
      .then(async (data) => {
        setNewReleasesData(data);
        const albumItems = data.albums.items;
        const tracksMap: Record<string, Track[]> = {};

        for (const album of albumItems) {
          if (album.id) {
            try {
              const res = await apiGetTracksByAlbumId(album.id);
              tracksMap[album.id] = res.items.slice(0, 5);
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

  useEffect(() => {
    const elements = document.querySelectorAll(".scroll-text-container");

    elements.forEach((container) => {
      const scrollText = container.querySelector(".scroll-text") as HTMLElement;
      if (!scrollText) return;

      const containerWidth = (container as HTMLElement).offsetWidth;
      const textWidth = scrollText.scrollWidth;

      scrollText.style.transition = "transform 4s linear";
      scrollText.style.transform = "translateX(0)";

      if (textWidth > containerWidth) {
        const handleEnter = () => {
          scrollText.style.transition = "transform 4s linear";
          scrollText.style.transform = `translateX(-${
            textWidth - containerWidth
          }px)`;
        };

        const handleLeave = () => {
          scrollText.style.transition = "none";
          scrollText.style.transform = "translateX(0)";
          setTimeout(() => {
            scrollText.style.transition = "transform 4s linear";
          }, 20);
        };

        container.addEventListener("mouseenter", handleEnter);
        container.addEventListener("mouseleave", handleLeave);

        return () => {
          container.removeEventListener("mouseenter", handleEnter);
          container.removeEventListener("mouseleave", handleLeave);
        };
      }
    });
  }, [newReleasesData, albumTracksMap]);

  const scrollList = (direction: number) => {
    const list = document.getElementById("albumList");
    if (!list) return;
    list.scrollBy({ left: 600 * direction, behavior: "smooth" });
  };

  const scrollThumbnail = (direction: number) => {
    const list = document.getElementById("thumbnailList");
    if (!list) return;
    list.scrollBy({ left: 600 * direction, behavior: "smooth" });
  };

  return (
    <div className="home-container">
      <section className="home-section">
        <h2 className="home-title">최신 발매</h2>

        {/* 썸네일 이미지 섹션 */}
        <div className="carousel-wrapper">
          <button className="arrow left" onClick={() => scrollThumbnail(-1)}>
            &#10094;
          </button>

          <div className="thumbnail-list" id="thumbnailList">
            {newReleasesData?.albums?.items.slice(0, 15).map((album) => (
              <div key={album.id} className="thumbnail-wrapper">
                <img
                  className="thumbnail-image"
                  src={album.images?.[0]?.url ?? "/fallback.jpg"}
                  alt={album.name}
                  loading="lazy"
                />
                <div className="thumbnail-overlay">
                  <strong>{album.name}</strong>
                  <span>{album.artists.map((a) => a.name).join(", ")}</span>
                </div>
              </div>
            ))}
          </div>

          <button className="arrow right" onClick={() => scrollThumbnail(1)}>
            &#10095;
          </button>
        </div>
        <hr style={{ margin: "2rem 0", border: "0.5px solid #797979" }} />

        {/* 최신곡 섹션 */}
        <h3 className="home-title">최신곡</h3>
        <div className="carousel-wrapper">
          <button className="arrow left" onClick={() => scrollList(-1)}>
            &#10094;
          </button>

          <ul className="album-list" id="albumList">
            {newReleasesData?.albums?.items.map((album: AlbumItem) => (
              <li key={album.id} className="album-card">
                <img
                  className="album-image"
                  src={album.images?.[0]?.url ?? "/fallback.jpg"}
                  alt={album.name}
                  loading="lazy"
                />
                <div
                  className="play-button"
                  onClick={() => console.log("▶ 앨범 클릭:", album.id)}
                >
                  ▶
                </div>
                <div className="album-info">
                  <div className="scroll-text-container album-name">
                    <span className="scroll-text">{album.name}</span>
                  </div>
                  <ul className="track-list">
                    {albumTracksMap[album.id]?.length ? (
                      albumTracksMap[album.id].map((track) => (
                        <li key={track.id} className="track-item">
                          <div className="scroll-text-container">
                            <span className="scroll-text">{track.name}</span>
                          </div>
                        </li>
                      ))
                    ) : (
                      <li className="track-empty">수록곡 없음</li>
                    )}
                  </ul>
                </div>
              </li>
            ))}
          </ul>

          <button className="arrow right" onClick={() => scrollList(1)}>
            &#10095;
          </button>
        </div>
      </section>

      <hr style={{ margin: "2rem 0", border: "0.5px solid #797979" }} />
    </div>
  );
}

export default Home;
