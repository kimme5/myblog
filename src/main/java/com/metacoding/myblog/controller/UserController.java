package com.metacoding.myblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.myblog.config.auth.PrincipalDetail;
import com.metacoding.myblog.model.KakaoProfile;
import com.metacoding.myblog.model.OAuthToken;
import com.metacoding.myblog.model.User;
import com.metacoding.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Controller
public class UserController {

    @Value("${cos.key}")
    private String cosKey;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // @ResponseBody 어노테이션을 붙여 Data를 return해주는 Controller로 정의함
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {

        /**
         * 카카오 인증 선터에 POST 방식으로 key=value 데이터를 요청
         */

        // HttpHeader 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // HttpBody 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "b5f8de641726a4aa2ab2c95f7bd085c8");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader 객체와 HttpBody 객체를 갖는 단일한 HttpEntity 객체를 생성함
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);
        // POST 방식으로 Http 요청하기
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token"
                                                    , HttpMethod.POST
                                                    , kakaoTokenRequest
                                                    , String.class);
        // Gson, Json Simple, ObjectMapper 라이브러리를 통해 응답받은 데이터를 담음
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch(JsonProcessingException ex) {
            ex.printStackTrace();
        }
        System.out.println("Kakao Access Token: " + oAuthToken.getAccess_token());

        /**
         * 위에서 응답받은 Access Token을 이용해 Kakao에서 가지고 있는 사용자 정보를 다시 한번 요청함
         * 방식은 위에서 Access Token을 얻어오는 방식과 동일함
         */
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader 객체를 갖는 단일한 HttpEntity 객체를 생성함
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        RestTemplate rt2 = new RestTemplate();
        // POST 방식으로 Http 요청하기
        ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me"
                , HttpMethod.POST
                , kakaoProfileRequest
                , String.class);

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch(JsonProcessingException ex) {
            ex.printStackTrace();
        }
        System.out.println("Kakao Profile ID      : " + kakaoProfile.getId());
        System.out.println("Kakao Profile email   : " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("Kakao Profile nickname: " + kakaoProfile.getKakao_account().getProfile().getNickname());

        // User Object : username, password, email
        System.out.println("MyBlog username: " + kakaoProfile.getId() + "_" + kakaoProfile.getKakao_account().getProfile().getNickname());

        System.out.println("MyBlog password: " + cosKey);
        System.out.println("MyBlog email   : " + kakaoProfile.getKakao_account().getEmail());

        User kakaoUser = User.builder()
                .username(kakaoProfile.getId() + "_" + kakaoProfile.getKakao_account().getProfile().getNickname())
                .password(cosKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakako")
                .build();
        // 기존 가입자 체크
        User beUser = userService.findByUsername(kakaoUser.getUsername());

        if(beUser.getUsername() == null) {
            System.out.println("신규 회원이므로 자동 회원가입을 진행합니다.");
            userService.saveJoin(kakaoUser);
        }
        System.out.println("자동 로그인을 진행합니다.");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        kakaoUser.getUsername(), cosKey
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

    @GetMapping("/user/updateForm")
    public String updateUser(@AuthenticationPrincipal PrincipalDetail principal) {
        return "user/updateForm";
    }

    @GetMapping("/joinForm")
    public String joinFormBef() {
        return "/user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginFormBef() {
        return "/user/loginForm";
    }

    /**
     * 인증이 안된 사용자들이 출입할 수 있는 경로 /auth/** 허용
     * url이 "/"인 경우 index.jsp 허용
     * static 이하에 있는 /js/**, /css/**, /image/** 허용
     */
    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "/user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "/user/loginForm";
    }
}
