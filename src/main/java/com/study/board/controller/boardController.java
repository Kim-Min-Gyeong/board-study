package com.study.board.controller;

import com.study.board.entity.board;
import com.study.board.service.boardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class boardController {

    @Autowired //컨트롤러 입장에서 boardService가 뭔지 모르기 때문에, DI 해줌.
    private boardService boardService;

    @GetMapping("/board/write") // localhost:8080/board/write //게시글 작성
    public String boardWriteForm(){
        return "boardWrite";
    }

    @PostMapping("/board/writepro") //작성한 게시글 저장
    public String boardWritePro(board b){

        boardService.write(b);

        return "";
    }

    @GetMapping("/board/list") //게시글 리스트
    public String boardList(Model model){
        model.addAttribute("list", boardService.boardList());

        return "boardList";
    }

    @GetMapping("/board/view") //localhost:8080/board/view?id=1 입력하면 id가 1
    public String boardView(Model model, Integer id){
        model.addAttribute("board", boardService.boardView(id));
        return "boardView";
    }
}
