package com.metacoding.myblog.config.auth;

import com.metacoding.myblog.model.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
//
@Data
public class PrincipalDetail implements UserDetails {

    private User user;  // Composition

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    // 계정의 만료여부 반환 -> true가 non-expired
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 계정의 Lock여부 반환 -> true가 unlock
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // passwor 만료여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정이 가지고 있는 권한목록을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        /**
         * java는 getAuthority() method를 매개변수로 직접 담을 수 없기 때문에
         * 이를 Object화해서 담는 방식으로 처리해야 함
         */
//        collectors.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "ROLE_" + user.getRole(); // ROLE_USER
//            }
//        });
        // 위의 방식을 람다식으로 아래와 같이 간결히 표현이 가능함
        collectors.add(() -> {return "ROLE_" + user.getRole();});

        return collectors;
    }
}
