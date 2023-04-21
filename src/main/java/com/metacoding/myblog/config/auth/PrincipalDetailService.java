package com.metacoding.myblog.config.auth;

import com.metacoding.myblog.model.User;
import com.metacoding.myblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Security가 로그인 인증을 진행하는 과정에서 사용자가 입력한 username과
     * password를 가져가는데 password 인증은 알아서 처리하고,
     * 여기서 username이 DB 정보와 일치하는지 확인함
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("해당 사용자가 존재하지 않습니다: " + username);
        });
        return new PrincipalDetail(principal);
    }
}
