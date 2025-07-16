package kr.co.kh.model.payload.request;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor


public class EmailRequest {

 private String mailTo;
 private String mailType;
 private String userName;
 private String name;
 private String passwordAuth;
 private String authCode;
 private String email;
 private String subject;
 private String step;
 private String newPassword; // 🔐 추가


}
