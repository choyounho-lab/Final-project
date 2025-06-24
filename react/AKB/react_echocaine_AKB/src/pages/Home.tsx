// React 및 필요 모듈 import
import React, { useEffect, useState } from "react";
import { apiGetNewReleases, apiGetTracksByAlbumId } from "../api/api";
import { NewReleasesResponse, AlbumItem, Track } from "../types/types";
import "../styles/Home.css";
import gsap from "gsap";
import ScrollTrigger from "gsap/ScrollTrigger";
import ScrollSmoother from "gsap/ScrollSmoother";
import { FastAverageColor } from "fast-average-color";

// GSAP 플러그인 등록
gsap.registerPlugin(ScrollTrigger, ScrollSmoother);

// 메인 컴포넌트
function Home() {
  // 신보, 트랙 목록, 평균 색상 상태 정의
  const [newReleasesData, setNewReleasesData] =
    useState<NewReleasesResponse | null>(null);
  const [albumTracksMap, setAlbumTracksMap] = useState<Record<string, Track[]>>(
    {}
  );
  const [albumColors, setAlbumColors] = useState<Record<string, string>>({});

  // 앨범 리스트 스크롤 버튼 기능
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

  // Scrolly 이미지 애니메이션 효과 적용
  useEffect(() => {
    if (!newReleasesData) return;

    gsap.utils.toArray(".scrolly-image").forEach((img: any, i) => {
      gsap.fromTo(
        img,
        { y: 50, opacity: 0, scale: 0.8 },
        {
          scrollTrigger: {
            trigger: img,
            start: "top 80%",
            toggleActions: "play none none none",
          },
          y: 0,
          opacity: 1,
          scale: 1,
          duration: 1,
          ease: "power2.out",
          delay: i * 0.1,
        }
      );
    });
  }, [newReleasesData]);

  // API 호출하여 신보 정보 및 트랙 목록 불러오기
  useEffect(() => {
    apiGetNewReleases()
      .then(async (data) => {
        setNewReleasesData(data);
        const albumItems = data.albums.items.slice(0, 5); // 원하는 만큼 제한
        const tracksMap: Record<string, Track[]> = {};

        for (const album of albumItems) {
          if (album.id) {
            try {
              await new Promise((resolve) => setTimeout(resolve, 300)); // 딜레이 줘서 API 연속 호출 방지
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

  // 텍스트가 넘치면 hover 시 스크롤되도록 처리
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
      }
    });
  }, [newReleasesData, albumTracksMap]);

  // GSAP ScrollSmoother 효과 적용 (스크롤 부드럽게 + 스크롤 속도에 따라 이미지 왜곡)
  useEffect(() => {
    const skewSetter = gsap.quickTo("img", "skewY");
    const clamp = gsap.utils.clamp(-20, 20);

    const smoother = ScrollSmoother.create({
      wrapper: "#wrapper",
      content: "#content",
      smooth: 2,
      speed: 3,
      effects: true,
      onUpdate: (self) => skewSetter(clamp(self.getVelocity() / -50)),
      onStop: () => skewSetter(0),
    });

    return () => {
      smoother.kill();
    };
  }, []);

  // 앨범 이미지의 평균 색상 추출 (fast-average-color 사용)
  useEffect(() => {
    if (!newReleasesData) return;

    const fac = new FastAverageColor();
    const colorMap: Record<string, string> = {};

    const fetchColors = async () => {
      const promises = newReleasesData.albums.items.map((album) => {
        return new Promise<void>((resolve) => {
          const img = new Image();
          img.crossOrigin = "anonymous";
          img.src = album.images?.[0]?.url ?? "";

          img.onload = async () => {
            try {
              const color = await fac.getColorAsync(img);
              colorMap[album.id] = color.hex;
            } catch {
              colorMap[album.id] = "#726d77";
            }
            resolve();
          };

          img.onerror = () => {
            colorMap[album.id] = "#726d77";
            resolve();
          };
        });
      });

      await Promise.all(promises);
      setAlbumColors(colorMap);
    };

    fetchColors();
  }, [newReleasesData]);

  // 렌더링 부분
  return (
    <div className="home-container">
      {/* Parallax Scrolly 이미지 */}
      <div id="wrapper">
        <section id="content">
          <h1 className="text">E c h o c a i n e</h1>
          <h1 aria-hidden="true" className="text outline-text">
            E c h o c a i n e
          </h1>
          <h1 aria-hidden="true" className="text filter-text">
            E c h o c a i n e
          </h1>

          <section className="images">
            {[
              "https://images.unsplash.com/photo-1556856425-366d6618905d",
              "https://images.unsplash.com/photo-1520271348391-049dd132bb7c",
              "https://images.unsplash.com/photo-1609166214994-502d326bafee",
              "https://images.unsplash.com/photo-1589882265634-84f7eb9a3414",
              "https://images.unsplash.com/photo-1514689832698-319d3bcac5d5",
              "https://images.unsplash.com/photo-1535207010348-71e47296838a",
              "https://images.unsplash.com/photo-1588007375246-3ee823ef4851",
              "https://images.unsplash.com/photo-1571450669798-fcb4c543f6a4",
            ].map((src, idx) => (
              <img
                key={idx}
                className="scrolly-image"
                src={src + "?auto=format&fit=crop&w=400&q=60"}
                alt={`Parallax ${idx + 1}`}
              />
            ))}
          </section>
        </section>
      </div>

      {/* 최신 발매 캐러셀 */}
      <section className="home-section">
        <h2 className="home-title">최신 발매</h2>
        <div className="carousel-wrapper">
          <button className="arrow left" onClick={() => scrollThumbnail(-1)}>
            &#10094;
          </button>
          <div className="thumbnail-list" id="thumbnailList">
            {newReleasesData?.albums?.items.slice(0, 10).map((album) => (
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
      </section>

      <hr style={{ margin: "2rem 0", border: "0.5px solid #797979" }} />

      {/* 최신곡 목록 (앨범 배경색 포함) */}
      <section className="home-section">
        <h3 className="home-title">최신곡</h3>
        <div className="carousel-wrapper">
          <button className="arrow left" onClick={() => scrollList(-1)}>
            &#10094;
          </button>
          <ul className="album-list" id="albumList">
            {newReleasesData?.albums?.items.map((album: AlbumItem) => (
              <li
                key={album.id}
                className="album-card"
                style={{
                  backgroundColor: albumColors[album.id] ?? "#726d77",
                  color: "#000",
                }}
              >
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
                      <li
                        key={albumTracksMap[album.id][0].id}
                        className="track-item"
                      >
                        <div className="scroll-text-container">
                          <span className="scroll-text">
                            {albumTracksMap[album.id][0].name}
                          </span>
                        </div>
                      </li>
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
    </div>
  );
}

export default Home;
