-- 플레이리스트 생성 Procedure
CREATE OR REPLACE PROCEDURE INSERT_PLAYLIST(
	p_userId IN PLAYLIST.USER_ID%TYPE,
	p_playlistTitle IN PLAYLIST.PLAYLIST_TITLE%TYPE,
	p_playlistIsPublic IN PLAYLIST.PLAYLIST_IS_PUBLIC%TYPE,
	p_resultMsg OUT VARCHAR2,
	p_playlistId OUT PLAYLIST.PLAYLIST_ID%TYPE
)
IS
	v_cnt NUMBER;
BEGIN
	 -- 제목 중복 체크
      SELECT COUNT(*) INTO v_cnt FROM PLAYLIST
      WHERE USER_ID = p_userId AND PLAYLIST_TITLE = p_playlistTitle;

      IF v_cnt > 0 THEN
        RAISE_APPLICATION_ERROR(-20000, '이미 동일한 제목의 플레이리스트가 존재합니다.');
      END IF;

      -- 사용자당 10개 이하 제한 (선택적으로 포함)
      SELECT COUNT(*) INTO v_cnt FROM PLAYLIST
      WHERE USER_ID = p_userId;

      IF v_cnt >= 10 THEN
        RAISE_APPLICATION_ERROR(-20001, '사용자는 최대 10개의 플레이리스트만 생성할 수 있습니다.');
      END IF;

	INSERT INTO PLAYLIST(
		USER_ID,
		PLAYLIST_TITLE,
		PLAYLIST_IS_PUBLIC
	)
	VALUES (
		p_userId,
		p_playlistTitle,
		p_playlistIsPublic
	) RETURNING PLAYLIST_ID INTO p_playlistId;
	p_resultMsg := 'SUCCESS';
	EXCEPTION
		WHEN OTHERS THEN
			p_resultMsg := 'ERROR: ' || SQLERRM;
			p_playlistId := NULL;
END;


-- 플레이리스트 삭제 Procedure
CREATE OR REPLACE PROCEDURE DELETE_PLAYLIST(
	playlistId IN PLAYLIST.PLAYLIST_ID%TYPE,
	resultMsg OUT VARCHAR2
)
IS
	v_exists NUMBER := 0;
BEGIN
	SELECT COUNT(*) INTO v_exists FROM PLAYLIST WHERE PLAYLIST_ID = playlistId;
	IF v_exists = 0 THEN
		resultMsg := '데이터가 없습니다.';
		RETURN;
	END IF;
	DELETE FROM PLAYLIST WHERE PLAYLIST_ID  = playlistId;
	resultMsg := 'SUCCESS';
	COMMIT;
	EXCEPTION
		WHEN OTHERS THEN
			ROLLBACK;
END;

-- 플레이리스트 수정 Procedure
CREATE OR REPLACE PROCEDURE ECHO.UPDATE_PLAYLIST(
	playlistId IN PLAYLIST.PLAYLIST_ID%TYPE,
	playlistIsPublic IN PLAYLIST.PLAYLIST_IS_PUBLIC%TYPE,
	playlistTitle IN PLAYLIST.PLAYLIST_TITLE%TYPE,
	resultMsg OUT VARCHAR2
)
IS
	v_exists NUMBER:= 0;
BEGIN
	SELECT COUNT(*) INTO v_exists FROM PLAYLIST WHERE PLAYLIST_ID = playlistId;
	IF v_exists = 0 THEN
		resultMsg := '데이터가 없습니다.';
		RETURN;
	END IF;
	UPDATE PLAYLIST SET PLAYLIST_TITLE = playlistTitle, PLAYLIST_IS_PUBLIC = playlistIsPublic WHERE PLAYLIST_ID = playlistId;
	resultMsg := 'SUCCESS';
	EXCEPTION
		WHEN OTHERS THEN
			resultMsg := 'ERROR: ' || SQLERRM;
END;