package com.metacoding.myblog.repository;


import com.metacoding.myblog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository // 해당 어노테이션이 없어도 spring에서 자동으로 baen으로 등록함
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
