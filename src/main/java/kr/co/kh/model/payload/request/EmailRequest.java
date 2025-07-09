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
 private  String passwordAuth;
 private String authCode;

 public String getEmail() {
     return this.mailTo;
 }
}
