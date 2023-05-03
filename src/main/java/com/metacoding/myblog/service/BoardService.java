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

    @Transactional
    public void updateBoard(int id, Board requestBoard) {

        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 id의 게시글을 찾을 수 없습니다.");
        }); // 영속화 완료

        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
//        board.setCount(board.getCount() + 1);
        boardRepository.save(board);
    }


    @Transactional
    public void deleteBoardById(int id) {
        boardRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public Board boardDetail(int id) {
        return boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 id의 사용자가 존재하지 않습니다.");
        });
    }

    @Transactional(readOnly = true)
    public Page<Board> boardPage(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
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
