package kr.co.kh.mapper;

import kr.co.kh.model.User;
import kr.co.kh.model.payload.request.RegistrationRequest;
import kr.co.kh.model.vo.UserAuthorityVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface UserAuthorityMapper {

    void save(UserAuthorityVO userAuthorityVO);

    void insertUser(User request);

    HashMap<String, Object> findByNameAndBirthDate(HashMap<String, Object> map);


    User findByNameAndEmail(String name, String email);
    void resetPassword(Map<String, Object> paramMap);
}
