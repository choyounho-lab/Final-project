-- 트랙 입력
CREATE OR REPLACE PROCEDURE INSERT_TRACK(
	p_trackId IN TRACK.TRACK_ID%TYPE,
	p_trackName IN TRACK.TRACK_NAME%TYPE,
	p_artists IN TRACK.TRACK_ARTIST%TYPE,
	p_trackDuration IN TRACK.TRACK_DURATION%TYPE,
	p_releaseDate IN TRACK.RELEASE_DATE%TYPE,
	p_trackImageUrl IN TRACK.TRACK_IMAGE_URL%TYPE,
	p_resultMsg OUT VARCHAR2
)
IS
	v_cnt NUMBER;
BEGIN
	SELECT COUNT(*) INTO v_cnt FROM TRACK
		WHERE TRACK_ID = p_trackId;
	IF v_cnt > 0 THEN
		RAISE_APPLICATION_ERROR(-20000, '이미 존재하는 트랙입니다.');
	END IF;

	INSERT INTO TRACK(
		TRACK_ID,
		TRACK_NAME,
		TRACK_ARTIST,
		TRACK_DURATION,
		RELEASE_DATE,
		TRACK_IMAGE_URL
	)
	VALUES (
		p_trackId,
		p_trackName,
		p_artists,
		p_trackDuration,
		p_releaseDate,
		p_trackImageUrl
	);
	p_resultMsg := 'SUCCESS';
	EXCEPTION
		WHEN OTHERS THEN
			p_resultMsg := 'ERROR: ' || SQLERRM;
END;
