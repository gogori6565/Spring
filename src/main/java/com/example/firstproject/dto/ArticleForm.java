package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //생성자를 사용한 것과 똑같은 의미의 어노테이션
@ToString //toString()을 사용한 것과 똑같은 의미의 어노테이션
public class ArticleForm {

    private Long id;
    private String title;
    private String content;

    //public Article 이기에 Article 타입으로 반환해야함 - entity 반환
    public Article toEntity() {
        return new Article(id, title, content);
    }
}

/*
* 생성자 - new 키워드를 통해 클래스로부터 객체를 생성할 때 호출되어 객체의 초기화를 담당
* */

    /* Refactoring 을 위해 제거
    //우클릭>Generate>Constructor
    //생성자(Constructor) = 객체가 생성될 때 자동으로 호출되는 특수 목적의 멤버함수(메소드)로 객체의 초기화를 위해 사용
    public ArticleForm(String content, String title) {
        this.content = content;
        this.title = title;
    }
     */

    /* Refactoring 을 위해 제거
    //우클릭>Generate>toString()
    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
     */