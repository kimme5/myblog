package com.metacoding.myblog.controller.api;

import com.metacoding.myblog.config.auth.PrincipalDetail;
import com.metacoding.myblog.dto.ResponseDto;
import com.metacoding.myblog.model.Board;
import com.metacoding.myblog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> board(@RequestBody Board board
                                    , @AuthenticationPrincipal PrincipalDetail principal) {

        boardService.saveBoard(board, principal.getUser());

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
