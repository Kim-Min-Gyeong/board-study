package com.study.board.service;

import com.study.board.entity.board;
import com.study.board.repository.boardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class boardService {

    @Autowired //DI
    private boardRepository boardRepository;

    //글 작성 처리
    public void write(board b){
        boardRepository.save(b);
    }

    //게시글 목록 처리
    public List<board> boardList(){
        return boardRepository.findAll();
    }

    //특정 게시글 불러오기
    public board boardView(Integer id){
        return boardRepository.findById(id).get();
    }


}
