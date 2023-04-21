package com.metacoding.myblog.controller;

import com.metacoding.myblog.model.RoleType;
import com.metacoding.myblog.model.User;
import com.metacoding.myblog.repository.UserRepository;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
public class DummyControllerTest {

    @Autowired  // D.I 의존성 주입
    private UserRepository userRepository;

    // RequestParam 어노테이션을 이용해 입력 대상 컬럼 정보를 모두 나열함
    @PostMapping("/dummy/join1")
    public String joinRequestParam(@RequestParam("username") String userName
                     , @RequestParam("password") String password
                     , @RequestParam("email") String email) {
        System.out.println("username= " + userName);
        System.out.println("password= " + password);
        System.out.println("email   = " + email);

        return "회원가입이 완료되었습니다.";
    }
    
    // User 모델 정보를 통해 입력값을 받아옴
    @PostMapping("/dummy/join2")
    public String joinModel(@RequestBody User user) {
        System.out.println("username= " + user.getUsername());
        System.out.println("password= " + user.getPassword());
        System.out.println("email   = " + user.getEmail());

        return "회원가입이 완료되었습니다.";
    }

    @PostMapping("/dummy/join3")
    public String joinModel2(User user) {
        System.out.println("id        = " + user.getId());
        System.out.println("username  = " + user.getUsername());
        System.out.println("password  = " + user.getPassword());
        System.out.println("email     = " + user.getEmail());
        System.out.println("role      = " + user.getRole());
        System.out.println("createDate= " + user.getCreateDate());

        return "회원가입이 완료되었습니다.";
    }

    @PostMapping("/dummy/insert")
    public String insertModel(User user) {
        System.out.println("id        = " + user.getId());
        System.out.println("username  = " + user.getUsername());
        System.out.println("password  = " + user.getPassword());
        System.out.println("email     = " + user.getEmail());
        System.out.println("role      = " + user.getRole());
        System.out.println("createDate= " + user.getCreateDate());

        user.setRole(RoleType.USER);

        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }

    /**
     * {id} 주소로 파라미터를 전달받을 수 있음
     * http://localhost:8000/myblog/dummy/user/3
     **/
    @GetMapping("/dummy/user/{id}")
    public User userDetail(@PathVariable int id) {
        /**
         * 사용자가 특정 id를 갖는 하나의 User 정보를 조회하고자 하나 
         * 해당 id를 갖는 User가 없는 경우 Null을 반환하게 되므로, 아래와 같이
         * Exception 처리를 통해 Error가 발행하지 않도록 함
         * */
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("선택한 id " + id + "에 해당하는 사용자가 존재하지 않습니다.");
            }
        });
        return user;
    }

    @GetMapping("/dummy/user2/{id}")
    public User userDetail2(@PathVariable int id) {
        User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
            @Override
            public User get() {
                return new User();
            }
        });
        return user;
    }

    // 람다식 표현
    @GetMapping("/dummy/user3/{id}")
    public User userDetail3(@PathVariable int id) {
        User user = userRepository.findById(id).orElseThrow(()-> {
            return new IllegalArgumentException("선택한 id " + id + "에 해당하는 사용자가 존재하지 않습니다.");
        });
        return user;
    }

    @GetMapping("/dummy/user4/{id}")
    public Optional<User> userDetail4(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);

        /**
         * USER 테이블로부터 전달받은 데이터를 담고 있는 user 객체는 object 이므로
         * 웹브라우저는 이를 곧바로 처리할 수 없음.
         * 이를 웹브라우저가 이해할 수 있는 json 형태의 데이터로 변환해야 함.
         * 이를 처리하기 위해 Respone 시에 Spring에서 "MessageConveter"가 자동으로 작동함
         * "MessageConverter"가 Jackson 라이브러리를 호출하여 user object를
         * json으로 변환하여 웹브라우저에게 전달하게 됨
         * */
        return user;
    }

    @GetMapping("/dummy/users")
    public List<User> userList() {
        return userRepository.findAll();
    }

    // 한페이지당 2건의 데이터를 출력
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2
                                              , sort = "id"
                                              , direction = Sort.Direction.DESC) Pageable pageable) {
//        List<User> users = userRepository.findAll(pageable).getContent();
        Page<User> pageUsers = userRepository.findAll(pageable);
        List<User> users = pageUsers.getContent();

        return users;
    }

    @GetMapping("/dummy/user2")
    public Page<User> pageList2(@PageableDefault(size = 2
                                               , sort = "id"
                                               , direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        return users;
    }

    /**
     * 특정 id를 갖는 user의 password와 email을 수정하는 update
     * 브라우저에서 수정할 대상 값들을 json 형식의 데이터로 request 하면
     * "@RequestBody" 어노테이션을 통해 "MessageConverter"의 Jackson 라이브러리가
     * 이를 Java Object로 변환하여 받아줌
     *
     * */
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) {

        System.out.println("id       = " + id);
        System.out.println("password = " + requestUser.getPassword());
        System.out.println("email    = " + requestUser.getEmail());

        requestUser.setId(id);
        requestUser.setUsername("Tori");
        userRepository.save(requestUser);

        return null;
    }

    @PutMapping("/dummy/user2/{id}")
    public String updateUser2(@PathVariable int id, @RequestBody User requestUser) {

        System.out.println("id       = " + id);
        System.out.println("password = " + requestUser.getPassword());
        System.out.println("email    = " + requestUser.getEmail());
        /**
         * 람다식 표현
         * 업데이트 방식
         * 1) 업데이트하고자 하는 대상 id의 user 정보를 db에서 불러와 user object에 담음
         * 2) 이때, 해당 id를 갖는 user가 없을 수 있으므로, exception 처리를 해줌
         * 3) setter를 이용해 user object에 담겨있는 password와 email 값을 
         *    파라미터로 전달된 새로운 값으로 변경함
         * 4) 변경된 user object를 userRepository.save(user); 형태로 다시 저장함
         * */
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("선택한 id " + id + "에 해당하는 사용자 정보 수정에 실패하였습니다.");
        });

        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        userRepository.save(user);

        return "선택한 id '" + id + "'에 대한 업데이트가 정상적으로 완료되었습니다.";
    }

    @Transactional
    @PutMapping("/dummy/user3/{id}")
    public User updateUser3(@PathVariable int id, @RequestBody User requestUser) {

        System.out.println("id       = " + id);
        System.out.println("password = " + requestUser.getPassword());
        System.out.println("email    = " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("선택한 id " + id + "에 해당하는 사용자 정보 수정에 실패하였습니다.");
        });

        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        // userRepository.save(user);

        return user;
    }

    @DeleteMapping("/dummy/user/{id}")
    public String deleteUser(@PathVariable int id) {

        try {
            userRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            return "선택한 id '" + id + "'에 해당하는 사용자가 존재하지 않습니다.";
        }

        return "선택한 id '" + id + "'에 해당하는 사용자 삭제를 완료했습니다.";
    }

    /**
     * Dirty Checking 방식 Delete
     */
    @Transactional
    @DeleteMapping("/dummy/user2/{id}")
    public String deleteUser2(@PathVariable int id) {

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("선택한 id '" + id + "'에 해당하는 사용자 삭제에 실패했습니다.");
        });

        // userRepository.deleteById(id);

        return "선택한 id '" + id + "'에 해당하는 사용자 삭제를 완료했습니다.";
    }

}
