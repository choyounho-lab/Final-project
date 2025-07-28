package kr.co.kh.mapper;

import kr.co.kh.model.User;
import kr.co.kh.model.payload.request.EmailRequest;
import kr.co.kh.model.payload.request.RegistrationRequest;
import kr.co.kh.model.vo.UserAuthorityVO;
import lombok.Lombok;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface UserAuthorityMapper {

    void save(UserAuthorityVO userAuthorityVO);

    void insertUser(User request);

    HashMap<String, Object> findByNameAndBirthDate(HashMap<String, Object> map);


    User findByNameAndEmail(String name, String email);
   void resetPassword(Map<String, Object> paramMap);

    HashMap<String, Object> selectAuthCodeByUserId (HashMap<String,Object> map);

    void deleteAuthCodeByUserId(HashMap<String,Object> map);

    void insertAuthCode(HashMap<String,Object> map);

    Optional<User> selectUserByEmailAndName(EmailRequest emailRequest);

    void updateAuthCodeByUserId(HashMap<String, Object> map);

    void updatePassword(HashMap<String,Object> map);

    void updateSub(HashMap<String,Object> map);

    Long selectSub(HashMap<String, Object> map);

}
