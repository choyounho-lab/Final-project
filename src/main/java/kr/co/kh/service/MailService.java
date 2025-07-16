package kr.co.kh.service;

import kr.co.kh.model.User;
import kr.co.kh.model.payload.request.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Optional;


@Service
@Slf4j

public class MailService {
 private final UserAuthorityService userAuthorityService;
    private final JavaMailSender javaMailSender;

    public MailService(UserAuthorityService userAuthorityService, JavaMailSender javaMailSender) {
        this.userAuthorityService = userAuthorityService;
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        try {
            simpleMailMessage.setTo("choyh8@naver.com");
            simpleMailMessage.setSubject("테스트 메일 제목");
            simpleMailMessage.setText("테스트 메일 내용");

            javaMailSender.send(simpleMailMessage);

            log.info("메일 발송 성공!");
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }

    public String sendMimeMessage(EmailRequest emailRequest) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        String authCode = null;
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            mimeMessageHelper.setTo(emailRequest.getEmail()); // ✅ 여기를 고쳐야 함
//            mimeMessageHelper.setTo("choyh8@naver.com");
            mimeMessageHelper.setSubject("제목쓰는곳");

            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>");
            sb.append("<html lang='ko'>");
            sb.append("<body>");


            if (emailRequest.getMailType().equals("emailAuth")) {
                // ✅ 인증번호 생성
                authCode = generateAuthCode();
                emailRequest.setAuthCode(authCode); // 필요한 경우 외부 전달용
                log.info(emailRequest.toString());

                // db에서 파라미터로 받은 이름 + 이메일로 where 처리후 데이터 찾는다
                Optional<User> user = userAuthorityService.selectUserByEmailAndName(emailRequest);
                // User user = 찾아라 메서드

                if (user.isPresent()) {
                    // db에 넣기
                    // user.getUserName();
                    // map.put("userName", user.getUserName());


                    HashMap<String, Object> map = new HashMap<>();
                    map.put("userName" , user.get().getName());

                    map.put("authCode", authCode);
                    log.info(map.toString());
                    userAuthorityService.insertAuthCode(map);


                    sb.append("<div style=\"margin:100px;\">");
                    sb.append("<h1> Echocaine 회원가입 인증번호 </h1>");
                    sb.append("<div align=\"center\" style=\"border:1px solid black; padding:20px;\">");
                    sb.append("<h3> </h3>");

                    sb.append("<h1 style='color:blue;'>" + authCode + "</h1>"); // ✅ 인증번호 삽입
                    sb.append("</div>");
                    sb.append("</div>");

                    log.info("발송된 인증번호: {}", authCode);
                    // 비번 찾기 >>> 비번 자동 변경 후 메일 발송
                    // 아이디를 db에서 찾는다 > 찾아 졌으면 비번을 랜덤하게 바꾸고 바꾼 비번을 메일로 날린다.

                } else {
                    throw new UsernameNotFoundException("사용자를 찾을수 없다");
                }


            } else if (emailRequest.getMailType().equals("passwordAuth")) {
                sb.append("<div style=\"margin:100px;\">");
                sb.append("<h1> 테스트 메일 </h1>");
                sb.append("<div align=\"center\" style=\"border:1px solid black;\">");
                sb.append("<h3> 테스트 메일 내용 </h3>");
                sb.append("</div>");
                sb.append("</div>");
            }

            sb.append("</body>");
            sb.append("</html>");

            mimeMessageHelper.setText(sb.toString(), true);
            javaMailSender.send(mimeMessage);

            log.info("메일 발송 성공!");
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }
        return authCode; // ✅ 인증번호 반환

    }

    // ✅ 인증번호 생성 메서드 추가
    private String generateAuthCode() {
        int length = 6;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((int)(Math.random() * 10));
        }
        return sb.toString();
    }
}
