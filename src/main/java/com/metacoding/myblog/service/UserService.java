package com.metacoding.myblog.service;

import com.metacoding.myblog.model.RoleType;
import com.metacoding.myblog.model.User;
import com.metacoding.myblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


// @Service 어노테이션을 붙여서 Spring이 Component Scan을 통해 Bean에 등록하게 함(IoC)
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        // 회원정보가 없는 경우 비어있는 User 오브젝트를 반환함
        User user = userRepository.findByUsername(username).orElseGet(() -> {
           return new User();
        });
        return user;
    }

    @Transactional
    public void updateUser(User rqstUser) {
        /**
         * Persistent Context에 기존 User 정보를 영속화시킨 후,
         * 변경된 정보들을 이 영속화된 User 오브젝트에 반영하여 수정함
         */
        User persUser = userRepository.findById(rqstUser.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("해당 사용자가 존재하지 않습니다.");
        });

        // kakako 로그인 사용자가 아닌 경우에만 패스워드 및 email 변경 가능
        if(persUser.getOauth() == null || persUser.getOauth().equals("")) {
            String rawPassword = rqstUser.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persUser.setPassword(encPassword);
            persUser.setEmail(rqstUser.getEmail());
        }
        /**
         * 회원 수정 함수 종료시 서비스, 즉 Transaction이 종료되는 것이며,
         * 자동으로 commit이 실행됨
         * 이때, 영속화된 persUser 객체가 변경되었다면 더티체킹이 되어 자동으로
         * update transaction이 실행되어 DB 값은 변경됨
         * 하단의 userRepository.save(persUser); 없어도 됨
         */
        // userRepository.save(persUser);
    }

    @Transactional
    public int saveJoin1(User user) {
        try {
            userRepository.save(user);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("UserService.saveJoin(): " + e.getMessage());
            return -1;
        }
        return 1;
    }

    @Transactional
    public void saveJoin(User user) {

        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        try {
            // user 오브젝트에는 username, password, email만 존재하므로, role은 별도로 입력해 줌
            user.setRole(RoleType.USER);
            user.setPassword(encPassword);
            userRepository.save(user);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * select 할때 transaction 시작, 서비스 종료시에 transaction 종료(정합성 유지)
     * Spring Security를 사용하지 않는 전통적인 login
     * 49강 이후로는 해당 메소드를 이용한 login을 사용하지 않음
     */
    @Transactional(readOnly = true)
    public User loginBef(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

}
