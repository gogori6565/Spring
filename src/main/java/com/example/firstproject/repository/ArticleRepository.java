package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

//CRUD = Create, Read, Update, Delete
public interface ArticleRepository extends CrudRepository<Article, Long> {
    
    //Iterable -> ArrayList로 반환하도록 리턴타입 오버라이드
    @Override
    ArrayList<Article> findAll();
}
