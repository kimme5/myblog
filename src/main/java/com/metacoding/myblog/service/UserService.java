package com.metacoding.myblog.service;

import com.metacoding.myblog.model.RoleType;
import com.metacoding.myblog.model.User;
import com.metacoding.myblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// @Service 어노테이션을 붙여서 Spring이 Component Scan을 통해 Bean에 등록하게 함(IoC)
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

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
