package com.metacoding.myblog.config;

import com.metacoding.myblog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 설정관련 클래스들은 @Configuration 어노테이션을 사용하여
 * Spring Container에서 객체를 관리할 수 있게 bean으로 등록함
 * 활성화되어 있는 Spring Security Security에 대한 설정을 해당 클래스 파일에서
 * 수행하기 위해 @EnableWebSecurity 어노테이션을 사용함
 * 즉, Security Filter 역할을 수행함
 * 특정 주소로 접근시, 권한 및 인증을 체크하기 위해 @EnableGlobalMethodSecurity 어노테이션 사용
 * 이 3개의 어노테이션은 Security Config 클래스에서 모두 선언해줌
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Spring Security가 로그인 인증 과정을 진행하는 과정에서 해당 사용자의 password가 어떤
     * hash 방식으로 암호화되었는지를 알아야 사용자가 로그인창에서 입력한 패스워드를 같은
     * 방식으로 암호화해서 DB에 있는 해쉬된 password와 비교할 수 있음
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()   // csrf 토큰 비활성화
            .authorizeRequests()
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**")
                .permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc")
                .defaultSuccessUrl("/")
//                .failureUrl("/login/error")
                ;
                // Spring Security가 해당 주소를 가리키는 로그인 요청을 가로채 대신 로그인을 수행함
    }

}
