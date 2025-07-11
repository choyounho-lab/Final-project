package kr.co.kh.service;

import kr.co.kh.mapper.UserAuthorityMapper;
import kr.co.kh.model.User;
import kr.co.kh.model.payload.request.RegistrationRequest;
import kr.co.kh.model.vo.UserAuthorityVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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
}
