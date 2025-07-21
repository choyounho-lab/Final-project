package kr.co.kh.controller.auth;

import ch.qos.logback.core.encoder.EchoEncoder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.exception.TokenRefreshException;
import kr.co.kh.exception.UserLoginException;
import kr.co.kh.exception.UserRegistrationException;
import kr.co.kh.model.User;
import kr.co.kh.model.payload.request.*;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.payload.response.ApiResponse;
import kr.co.kh.model.payload.response.JwtAuthenticationResponse;
import kr.co.kh.model.token.RefreshToken;
import kr.co.kh.repository.UserRepository;
import kr.co.kh.security.JwtTokenProvider;
import kr.co.kh.service.AuthService;
import kr.co.kh.service.MailService;
import kr.co.kh.service.UserAuthorityService;
import kr.co.kh.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;

    private final UserService userService;

    private final UserAuthorityService userAuthorityService;

    private final MailService mailService;

    /**
     * 이메일 사용여부 확인
     */
    @ApiOperation(value = "이메일 사용여부 확인")
    @ApiImplicitParam(name = "email", value = "이메일", dataType = "String", required = true)
    @GetMapping("/check/email")
    public ResponseEntity<?> checkEmailInUse(@RequestParam("email") String email) {
        boolean emailExists = authService.emailAlreadyExists(email);
        return ResponseEntity.ok(new ApiResponse(true, emailExists ? "이미 사용중인 이메일입니다." : "사용 가능한 이메일입니다."));
    }

    /**
     * username 사용여부 확인
     */
    @ApiOperation(value = "아이디 사용여부 확인")
    @ApiImplicitParam(name = "username", value = "아이디", dataType = "String", required = true)
    @GetMapping("/check/username")
    public ResponseEntity<?> checkUsernameInUse(@RequestParam(
            "username") String username) {
        boolean usernameExists = authService.usernameAlreadyExists(username);
        return ResponseEntity.ok(new ApiResponse(true, usernameExists ? "이미 사용중인 아이디입니다.": ""));
    }


    /**
     * 로그인 성공시 access token, refresh token 반환
     */
    @ApiOperation(value = "로그인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "아이디", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "비밀번호", dataType = "String", required = true),
            @ApiImplicitParam(name = "deviceInfo", value = "장치정보", dataType = "DeviceInfo", required = true)
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authService.authenticateUser(loginRequest)
                .orElseThrow(() -> new UserLoginException("Couldn't login user [" + loginRequest + "]"));
log.info(authentication.toString());
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("사용자 정보: {}", customUserDetails.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
                .map(RefreshToken::getToken)
                .map(refreshToken -> {
                    String jwtToken = authService.generateToken(customUserDetails);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken, tokenProvider.getExpiryDuration()));
                })
                .orElseThrow(() -> new UserLoginException("Couldn't create refresh token for: [" + loginRequest + "]"));
    }

    /**
     * 특정 장치에 대한 refresh token 을 사용하여 만료된 jwt token 을 갱신 후 새로운 token 을 반환
     */
    @ApiOperation(value = "리프레시 토큰")
    @ApiImplicitParam(name = "refreshToken", value = "TokenRefreshRequest 객체", dataType = "String", required = true)
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshJwtToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {

        log.info(tokenRefreshRequest.toString());

        return authService.refreshJwtToken(tokenRefreshRequest)
                .map(updatedToken -> {
                    String refreshToken = tokenRefreshRequest.getRefreshToken();
                    log.info("Created new Jwt Auth token: {}", updatedToken);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(updatedToken, refreshToken, tokenProvider.getExpiryDuration()));
                })
                .orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.getRefreshToken(), "토큰 갱신 중 오류가 발생했습니다. 다시 로그인 해 주세요."));
    }

    /**
     * 회원 가입
     * @param request
     * @return
     */
    @ApiOperation(value = "회원가입")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "아이디", dataType = "String", required = true),
            @ApiImplicitParam(name = "email", value = "이메일", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "비밀번호", dataType = "String", required = true),
            @ApiImplicitParam(name = "name", value = "이름", dataType = "String", required = true)
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        log.info(request.toString());
        return authService.registerUser(request).map(user -> {
            return ResponseEntity.ok(new ApiResponse(true, "등록되었습니다."));
        }).orElseThrow(() -> new UserRegistrationException(request.getUsername(), "가입오류"));
    }

    @PostMapping("/mail")
    public ResponseEntity<?> mail(@RequestBody EmailRequest emailRequest) {
        log.info(emailRequest.toString());
        // 인증번호 포함한 메일 전송 후 인증코드 리턴
        String authCode = mailService.sendMimeMessage(emailRequest);
        return ResponseEntity.ok(new ApiResponse(true, "인증번호가 전송되었습니다. 인증코드: " + authCode));
    }
    // AuthController.java
    @GetMapping("/find-id")
    public ResponseEntity<?> findId(@RequestParam String name, @RequestParam String birthDate) {
        System.out.println("요청 들어옴 name: " + name + ", birthDate: " + birthDate); // 👈 이거 찍어봐
        HashMap<String, Object> userOpt = userService.findByNameAndBirthDate(name, birthDate);
        log.info(userOpt.toString());
        if (userOpt != null) {
            System.out.println("찾음! 이메일: " + userOpt.get("EMAIL")); // 👈 이것도 찍기
            return ResponseEntity.ok(userOpt.get("EMAIL"));
        } else {
            System.out.println("못 찾음");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> handlePasswordReset(@RequestBody EmailRequest request) {

        log.info(request.toString());

        switch (request.getStep()) {
         case "send":
             mailService.sendMimeMessage(request);
             break;

         case "verify":
             HashMap<String,Object> requestMap = new HashMap<>();
             requestMap.put("userName" , request.getName());
             requestMap.put("authCode", request.getAuthCode());
             return ResponseEntity.ok(userAuthorityService.selectAuthCodeByUserId(requestMap));



            case "change":
                Optional<User> userOpt = userService.resetPassword(request);
                log.info(userOpt.get().toString());
                 return ResponseEntity.ok("비밀번호가 재설정되었습니다");




                //첫번쨰 상황에서 보내기 상황의 버튼을 누르게 되면 메일서비스 안의 요청을 가져오레된다 두번째 상솽은 가져온 인증 번호가 유효한지 대조해보고
         //맞다면 인증을 해준다
         //세번쨰 상황은 디비정보가 맞고  이름이 동일하면 새로운 비밀번호 를 설정한다. 그러면 새로운 비밀번호가 생기면 그전 번호는 지워진다.
     }
//        switch (step) {
//
//            case "send": {
//                Optional<User> user = userService.findByNameAndEmail(name, email);
//                if (user == null || user.isEmpty()) {
//                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자가 없습니다.");
//                }
//                String code = authService.sendVerificationCode(email);
//
//                return ResponseEntity.ok("인증번호 전송 완료");
//            }
//
//            case "verify": {
//                String inputCode = (String) payload.get("code");
//
//
//                return ResponseEntity.ok("인증 성공");
//            }
//
//            case "change": {
//                String newPassword = (String) payload.get("newPassword");
//
//
//                HashMap<String, Object> paramMap = new HashMap<>();
//                paramMap.put("name", name);
//                paramMap.put("email", email);
//                EchoEncoder<String> passwordEncoder = new EchoEncoder<>();
//                paramMap.put("password", passwordEncoder.encode(newPassword));
//
//                userService.resetPassword(paramMap);
//
//
//
//                return ResponseEntity.ok("비밀번호 변경 완료");
//            }
//
//            default:
//                return ResponseEntity.badRequest().body("올바르지 않은 요청 단계입니다.");
//        }

        return ResponseEntity.ok().build();
    }
}
