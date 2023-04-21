package com.metacoding.myblog.service;

import com.metacoding.myblog.model.Board;
import com.metacoding.myblog.model.User;
import com.metacoding.myblog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// @Service 어노테이션을 붙여서 Spring이 Component Scan을 통해 Bean에 등록하게 함(IoC)
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Page<Board> boardPage(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public List<Board> boardList() {
        return boardRepository.findAll();
    }
    @Transactional
    public void saveBoard(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

}
