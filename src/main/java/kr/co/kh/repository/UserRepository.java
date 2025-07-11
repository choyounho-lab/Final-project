package kr.co.kh.repository;

import kr.co.kh.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    @Query(value = "SELECT * FROM user WHERE username LIKE CONCAT('%', :searchKeyword, '%') ORDER BY user_id DESC", countQuery = "SELECT COUNT(*) FROM users WHERE username LIKE CONCAT('%', :searchKeyword, '%')", nativeQuery = true)
    Page<User> findByUsername(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query(value = "SELECT * FROM user WHERE email LIKE CONCAT('%', :searchKeyword, '%') ORDER BY user_id DESC", countQuery = "SELECT COUNT(*) FROM users WHERE email LIKE CONCAT('%', :searchKeyword, '%')", nativeQuery = true)
    Page<User> findByUserEmail(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    List<User> findAll();

    List<User> findByEmailIsContaining(String searchKeyword);

    List<User> findByUsernameIsContaining(String searchKeyword);

    List<User> findByNameIsContaining(String searchKeyword);

    //2025.07.03 조윤호
    @Query(value = "SELECT * FROM USERS WHERE email = :str OR username = :str", nativeQuery = true)
    Optional<User> findByUsernameOrEmail(String str);
    //2025-07-10 비밀번호 찾기할때 이름과 이메일로
    Optional<User> findByNameAndEmail(String name, String email);

    // UserRepository.java
    @Query(value = "SELECT u.* FROM USERS u WHERE u.NAME = :name AND u.BIRTH_DATE = :birthDate", nativeQuery = true)
    Optional<User> findByNameAndBirthDate(@Param("name") String name, @Param("birthDate") String birthDate);

}
