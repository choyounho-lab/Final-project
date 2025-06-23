// 📁 /api/youtubeSearch.ts
import axios from 'axios';

const YOUTUBE_API_KEY = process.env.REACT_APP_YOUTUBE_API_KEY;

export const searchYoutubeVideoId = async (
    query: string
): Promise<string | null> => {
    try {
        const response = await axios.get(
            'https://www.googleapis.com/youtube/v3/search',
            {
                params: {
                    part: 'snippet',
                    q: query,
                    type: 'video',
                    maxResults: 1,
                    key: YOUTUBE_API_KEY,
                },
            }
        );

        const items = response.data.items;
        if (items && items.length > 0) {
            return items[0].id.videoId; // ✅ 가장 관련도 높은 videoId 추출
        }

        return null;
    } catch (err) {
        console.error('YouTube API 검색 오류:', err);
        return null;
    }
};
