package com.metacoding.myblog.controller;

import com.metacoding.myblog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"", "/"})
    public String indexPage(Model model
                          , @PageableDefault(size = 3
                                           , sort = "id"
                                           , direction = Sort.Direction.DESC) Pageable pageable) {
//    public String index(@AuthenticationPrincipal PrincipalDetail principalDetail) {
//        System.out.println("로그인 사용자: " + principalDetail.getUsername());
        model.addAttribute("boards", boardService.boardPage(pageable));
        return "index";
    }

    /**
     * 특정 사용자가 정상적으로 로그인 인증 후 index 페이지가 호출됐을 때
     * 해당 사용자명을 출력해서 session 보유 여부를 확인해 봄
     */
    @GetMapping("/list")
    public String indexList(Model model) {
//    public String index(@AuthenticationPrincipal PrincipalDetail principalDetail) {
//        System.out.println("로그인 사용자: " + principalDetail.getUsername());
        model.addAttribute("boards", boardService.boardList());
        return "index";
    }

    // User 권한 필요
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "/board/saveForm";
    }

}
