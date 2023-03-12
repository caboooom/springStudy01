package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity //DB가 해당 객체를 인식 가능하게 해주는 어노테이션
@AllArgsConstructor
@NoArgsConstructor //디폴트생성자
@ToString
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //대표값인 id를 DB가 자동 생성해줌
    private Long id;

    @Column
    private String title;

    @Column
    private String content;


}
