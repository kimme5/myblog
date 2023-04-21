package com.metacoding.myblog.repository;

import com.metacoding.myblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository // 해당 어노테이션이 없어도 spring에서 자동으로 baen으로 등록함
public interface UserRepository extends JpaRepository<User, Integer> {

    // "SELECT * FROM USER WHERE username = 1?;"
    Optional<User> findByUsername(String username);

    /**
     * JPA Naming Query 전략
     * SELECT * FROM user WHERE username = ? AND password = ?;
     * Method명과 매개변수만으로 select 문장을 생성
     * Spring Security를 사용하지 않는 전통적인 login
     * 49강 이후로는 해당 메소드를 이용한 login을 사용하지 않음     *
     */
    User findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
    User loginBef(String username, String password);

}
