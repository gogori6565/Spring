package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity //DB가 해당 객체를 인식 가능!
@AllArgsConstructor //클래스의 모든 필드 값을 파라미터로 받는 생성자 자동 생성
@NoArgsConstructor //디폴트 생성자 추가하는 어노테이션
@ToString
@Getter
public class Article {

    @Id //대표값을 지정! like a 주민등록번호
    //@GeneratedValue : 1, 2, 3, ... 자동 생성 어노테이션!
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id를 자동 생성 어노테이션!
    private Long id; //DB의 대표값

    @Column
    private String title;

    @Column
    private String content;

    //(수정 요청 시)기존 데이터에 새 데이터 붙이기
    public void patch(Article article) {
        if(article.title != null) //새 데이터에 title이 있다면
            this.title = article.title; //title 변경 (없다면 기존 유지)
        if(article.content != null)
            this.content = article.content;
    }
}
