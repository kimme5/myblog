package com.metacoding.myblog.controller.api;

import com.metacoding.myblog.config.auth.PrincipalDetail;
import com.metacoding.myblog.dto.ReplyRequestDto;
import com.metacoding.myblog.dto.ResponseDto;
import com.metacoding.myblog.model.Board;
import com.metacoding.myblog.model.Reply;
import com.metacoding.myblog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
        boardService.updateBoard(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id) {
        boardService.deleteBoardById(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> deleteReplyById(@PathVariable int replyId) {

        boardService.deleteReplyById(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    @PostMapping("/api/board")
    public ResponseDto<Integer> saveBoard(@RequestBody Board board
                                    , @AuthenticationPrincipal PrincipalDetail principal) {

        boardService.saveBoard(board, principal.getUser());

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> saveReply(@PathVariable int boardId
                                        , @RequestBody Reply reply
                                        , @AuthenticationPrincipal PrincipalDetail principal) {

        boardService.saveReply(principal.getUser(), boardId, reply);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    // 데이터를 받을 때 DTO를 만들어서 받는걸 권장함
    @PostMapping("/api/board/{boardId}/dtoreply")
    public ResponseDto<Integer> saveDtoReply(@RequestBody ReplyRequestDto reply) {

        boardService.saveDtoReply(reply);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/{boardId}/nativereply")
    public ResponseDto<Integer> saveNativeReply(@RequestBody ReplyRequestDto reply) {

        boardService.saveNativeReply(reply);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
