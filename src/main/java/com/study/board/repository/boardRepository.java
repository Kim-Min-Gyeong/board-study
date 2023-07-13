package com.study.board.repository;

import com.study.board.entity.board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface boardRepository extends JpaRepository <board, Integer> {
    Page<board> findByTitleContaining(String searchKeyword, Pageable pageable);

    // findBy(column name) -> 컬럼에서 키워드를 넣어 찾겠다.
    // findBy(column name)Containing -> 컬럼에서 키워드가 포함된 것을 찾겠다.
}
