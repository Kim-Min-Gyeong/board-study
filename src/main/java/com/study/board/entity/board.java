package com.study.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity // entity는 table을 의미
@Data //board를 데이터로 받아오기 위해
public class board { // DB의 table이름과 일치하게 하는 것이 좋음.
    //jpa가 이를 다 읽어서 처리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //아이덴티티는 mysql에서 사용, sequence는  오라클, auto는 알아서 지정해줌.
    private Integer id;
    private String title;
    private String content;
    private String filename;
    private String filepath;

}
