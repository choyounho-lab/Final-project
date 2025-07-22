package kr.co.kh.service;

import kr.co.kh.exception.BadRequestException;
import kr.co.kh.mapper.UserAuthorityMapper;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.User;
import kr.co.kh.model.payload.request.EmailRequest;
import kr.co.kh.model.payload.request.RegistrationRequest;
import kr.co.kh.model.vo.UserAuthorityVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserAuthorityService {

    private final UserAuthorityMapper userAuthorityMapper;

    private final PasswordEncoder passwordEncoder;

    public void save(UserAuthorityVO userAuthorityVO) {
        userAuthorityMapper.save(userAuthorityVO);
    }

    public void insertUser(User request){
        userAuthorityMapper.insertUser(request);
    }

    public HashMap<String, Object> findByNameAndBirthDate(HashMap<String, Object> map) {
        return userAuthorityMapper.findByNameAndBirthDate(map);
    }

    public User findByNameAndEmail(String name, String email) {
        return userAuthorityMapper.findByNameAndEmail(name, email);
    }

    public void insertAuthCode (HashMap<String , Object> map){
       userAuthorityMapper.insertAuthCode(map);
    }

    public void deleteAuthCodeByUserId (HashMap<String, Object> map){
        userAuthorityMapper.deleteAuthCodeByUserId(map);
    }

    public HashMap<String,Object> selectAuthCodeByUserId (HashMap<String,Object> map){
        HashMap<String,Object> resultMap = userAuthorityMapper.selectAuthCodeByUserId(map);
        HashMap<String,Object> returnMap = new HashMap<>();
        log.info(resultMap.toString());
        log.info(map.toString());
        if( resultMap.get("AUTH_CODE").equals( map.get("authCode")) && resultMap.get("USER_NAME").equals(map.get("userName"))){
            returnMap.put("SUCCESS" , true);
            log.info("ok");
        }else {
            returnMap.put("SUCCESS" , false);
            log.info("no");
        }
        return  returnMap;
    }

    public Optional<User> selectUserByEmailAndName(EmailRequest emailRequest) {
        return userAuthorityMapper.selectUserByEmailAndName(emailRequest);
    }

    public void updateAuthCodeByUserId(HashMap<String,Object> map){
        userAuthorityMapper.updateAuthCodeByUserId(map);
    }

    public void updatePassword(HashMap<String,Object> map, CustomUserDetails user) {

        //1번 패스워드랑 뉴패스 워드 같은지 확인
        // 두 패스워드가 다르면 exception 처리
        //2반 헤시맵으로 받은 아이디값과 유저의 아이디값이 같은지 확인 다르면 exception 처리
        //3번 패스워드가 같으면 뉴패스워드를 패스워드 엔코드 함수를 써서 해싱한다.
        //4번 3번에서 바뀐 패스워드를 맵에 집어넣는다 패스워드라는 이름으로 (그래야지 웨어절이 업데이트 쿼리문에 들어간다.)


        // 기존비밀번호 확인 (쿼리필요) 조건 패스워드 해실된값 과 유저의 아이디값 또는 유저의 아이디값만 웨어절로하고 카운트하는방법 또는 웨어절에는 아이디값만 지정한다(아이디가127번인사람) 해잉 나온데이터에 패스

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setName(user.getName());
        emailRequest.setEmail(user.getEmail());
        Optional<User> result = this.selectUserByEmailAndName(emailRequest);
        if(result.isPresent()) {
            // map으로 받은 비번 이랑 result에 있는 비번이랑 일치확인
            String  orgPw = (String)map.get("password");
            String  resultPw = result.get().getPassword();

            //SALT :원래 비밀번호에 특정한 문자열이 합쳐져서 비밀번호가 해싱됨 (재밌다..)

            boolean pwCompare = passwordEncoder.matches(orgPw, resultPw);
            if(!pwCompare){
                throw new BadRequestException("현재 비밀번호가 다릅니다.");
            }
            log.info(String.valueOf(pwCompare));



            String password = (String) map.get("newPwConfirm");
            String newpassword = (String) map.get("newpassword");
            if (!password.equals(newpassword)) {
                log.info("비밀번호가 다릅니다.");
                throw new BadRequestException("비밀번호가 다릅니다.");
            }
            Long mapUserid = Long.parseLong(String.valueOf((Integer) map.get("id")));
            Long userId = user.getId();
            if (!mapUserid.equals(userId)) {
                log.info("사용자 정보가 일치하지않습니다.");
                throw new BadRequestException("사용자 정보가 일치하지않습니다.");
            }
            String hashPassword = passwordEncoder.encode(newpassword);
            map.put("password", hashPassword);
            userAuthorityMapper.updatePassword(map);
        }else {
            throw new BadRequestException("비밀번호 틀렸습니다");
        }






    }

}
