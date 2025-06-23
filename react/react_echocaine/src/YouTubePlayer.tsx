import React, { useEffect, useState } from 'react';
import { searchYoutubeVideoId } from './searchYoutubeVideoId';

interface Props {
    title: string; // 예: "STAY"
    artist: string; // 예: "BLACKPINK"
}

const YouTubePlayer: React.FC<Props> = ({ title, artist }) => {
    const [videoId, setVideoId] = useState<string | null>(null);

    useEffect(() => {
        const fetchVideo = async () => {
            const query = `${title} ${artist}`;
            const id = await searchYoutubeVideoId(query);
            setVideoId(id);
        };

        fetchVideo();
    }, [title, artist]);

    if (!videoId) return <div>로딩 중...</div>;

    return (
        <iframe
            width="100%"
            height="360"
            src={`https://www.youtube.com/embed/${videoId}?autoplay=1`}
            title="YouTube video player"
            frameBorder="0"
            allow="autoplay; encrypted-media"
            allowFullScreen
        />
    );
};

export default YouTubePlayer;
