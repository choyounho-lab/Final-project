package kr.co.kh.service;

import kr.co.kh.mapper.UserAuthorityMapper;
import kr.co.kh.model.User;
import kr.co.kh.model.payload.request.EmailRequest;
import kr.co.kh.model.payload.request.RegistrationRequest;
import kr.co.kh.model.vo.UserAuthorityVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserAuthorityService {

    private final UserAuthorityMapper userAuthorityMapper;

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


}
