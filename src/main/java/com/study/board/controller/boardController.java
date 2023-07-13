package com.study.board.controller;

import com.study.board.entity.board;
import com.study.board.service.boardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class boardController {

    @Autowired //컨트롤러 입장에서 boardService가 뭔지 모르기 때문에, DI 해줌.
    private boardService boardService;

    @GetMapping("/board/write") // localhost:8080/board/write //게시글 작성
    public String boardWriteForm(){
        return "boardWrite";
    }

    @PostMapping("/board/writepro") //작성한 게시글 저장
    public String boardWritePro(board b, Model model, MultipartFile file) throws Exception{

        boardService.write(b, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }


    // localhost:8080/board/list?page=1&size=10 = page는 0page부터 시작, size는 보여지는 게시글 수
    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword) {

        Page<board> list = null;

        if(searchKeyword == null) { //검색하지 않는 경우
            list = boardService.boardList(pageable);
        }else { //검색 하는 경우
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardList";
    }

    @GetMapping("/board/view") //localhost:8080/board/view?id=1 입력하면 id가 1 - query string 방식으로 전달
    public String boardView(Model model, Integer id){
        model.addAttribute("board", boardService.boardView(id));
        return "boardView";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id, Model model) {
        boardService.boardDelete(id);

        model.addAttribute("message", "글 삭제가 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){
        model.addAttribute("board", boardService.boardView(id));
        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, board b, Model model, MultipartFile file) throws Exception{
        board bTemp = boardService.boardView(id); //기존의 내용 가져오기
        bTemp.setTitle(b.getTitle()); //수정된 제목으로 바꿈
        bTemp.setContent(b.getContent()); //수정된 내용으로 바꿈
        boardService.write(bTemp, file);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }
}
