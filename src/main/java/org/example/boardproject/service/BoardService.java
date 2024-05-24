package org.example.boardproject.service;

import lombok.RequiredArgsConstructor;
import org.example.boardproject.domain.Board;
import org.example.boardproject.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Iterable<Board> findAllBoards(){
        return boardRepository.findAll();
    }

    @Transactional
    public Page<Board> findAllBoards(Pageable pageable){
        Pageable sortedByDescId =  PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC,"id"));
        return boardRepository.findAll(sortedByDescId);
    }

    @Transactional
    public Board saveBoard(Board board){
        return boardRepository.save(board);
    }

    @Transactional
    public Board findBoardById(Long id){
        return boardRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteBoard(Long id){
        boardRepository.deleteById(id);
    }

    public String findPasswordById(Long id){
        Board board = boardRepository.findById(id).orElse(null);
        return board != null ? board.getPassword() : null;
    }
}
