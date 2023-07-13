package com.study.board.service;

import com.study.board.entity.board;
import com.study.board.repository.boardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class boardService {

    @Autowired //DI
    private boardRepository boardRepository;

    //글 작성 처리
    public void write(board b, MultipartFile file) throws Exception{
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files"; //저장할 경로 생성
        UUID uuid = UUID.randomUUID(); //식별자(랜덤 이름 생성)
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName); // projectPath에 fileName으로 저장.
        file.transferTo(saveFile);

        b.setFilename(fileName);
        b.setFilepath("/files/" + fileName);

        boardRepository.save(b);
    }

    //게시글 목록 처리
    public Page<board> boardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Page<board> boardSearchList(String searchKeyword, Pageable pageable){
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    //특정 게시글 불러오기
    public board boardView(Integer id){
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }

    //게시물 수정

}
