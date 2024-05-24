package org.example.boardproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.domain.Board;
import org.example.boardproject.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

/*    @GetMapping
    public String boards(Model model){
        Iterable<Board> boards = boardService.findAllBoards();
        model.addAttribute("boards", boards);
        return "boards/list";
    }*/
    @GetMapping
    public String boards(Model model, @RequestParam(defaultValue = "1")int page,
                         @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page -1, size);

        Page<Board> boards = boardService.findAllBoards(pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("currentPage", page);
        return "boards/list";
    }

    @GetMapping("/write")
    public String addBoard(Model model){
        Board board = new Board();
        board.setCreated_at(LocalDateTime.now());
        model.addAttribute("board", board);
        return "boards/writeform";
    }

    @PostMapping("/write")
    public String writeBoard(@ModelAttribute Board board, RedirectAttributes redirectAttributes){
        boardService.saveBoard(board);
        redirectAttributes.addFlashAttribute("message", "Create New Board!!");
        return "redirect:/boards";
    }

    @GetMapping("/view/{id}")
    public String viewBoard(@PathVariable Long id, Model model){
        Board board = boardService.findBoardById(id);
        model.addAttribute("board", board);
        return "boards/view";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        model.addAttribute("board", boardService.findBoardById(id));
        return "boards/updateform";
    }
    @PostMapping("/update") // 나중에 BoardService 에서 Update_at이나 Current_at 저장하는 메서드 추가해야 될 듯.
    public String updateBoard(@ModelAttribute Board board, @RequestParam("id") Long id, @RequestParam("password") String password, RedirectAttributes redirectAttributes){
        String storedPassword = boardService.findPasswordById(id);
        if(storedPassword.equals(password)){
            boardService.saveBoard(board);
            return "redirect:/boards/view/" + id;
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "redirect:/boards/update/" + id;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable Long id, Model model){
        model.addAttribute("board", boardService.findBoardById(id));
        return "boards/deleteform";
    }

    @PostMapping("/delete")
    public String deleteBoard(@RequestParam("id") Long id, @RequestParam("password") String password, RedirectAttributes redirectAttributes){
        String storedPassword = boardService.findPasswordById(id);
        if(storedPassword.equals(password)){
            boardService.deleteBoard(id);
            return "redirect:/boards";
        } else{
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "redirect:/boards/delete/" + id;
        }
    }
}
