package kr.co.kh.model.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//2025-07-10 조윤호 비밀번호 찾기 및 재설정
public class ResetPasswordRequest {
    private String name;
    private String email;
    private String newPassword;
    private String confirmPassword; // ← 추가

}
