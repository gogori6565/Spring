package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //해당 클래스는 스프링부트와 연동되어 테스팅 된다
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        //예상 시나리오
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));

        //실제 결과
        List<Article> articles = articleService.index();

        //예상과 실제 비교
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공__존재하는id입력() {
        //예상
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        //실제
        Article article = articleService.show(id);

        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패__존재하지않는id입력() {
        //예상
        Long id = -1L;
        Article expected = null; //-1 입력되면 null이 나와야 함

        //실제
        Article article = articleService.show(id);

        //비교
        assertEquals(expected, article); //null은 toString() 사용 X
    }

    @Test
    @Transactional //create가 먼저 수행되고 데이터 조회를 하면 테스트 케이스가 실패로 뜨기 때문에!!
    void create_성공___title과_content만_있는_dto입력() {
        //예상
        String title = "라라라라";
        String content = "사사사사";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        //실제
        Article article = articleService.create(dto);

        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_실패___id가_포함된_dto가_입력된_경우() {
        //예상
        String title = "라라라라";
        String content = "사사사사";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;

        //실제
        Article article = articleService.create(dto);

        //비교
        assertEquals(expected, article);
    }


    @Test
    @Transactional
    void update_성공___존재하는_id와_title만_있는_dto_입력() {
        //예상
        Long id = 1L;
        String title = "라라라라";
        ArticleForm dto = new ArticleForm(id, title, null);
        Article expected = new Article(id, title, "1111");

        //실제
        Article article = articleService.update(id, dto);

        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_실패___존재하지_않는_id의_dto_입력() {
        //예상
        Long id = -1L;
        String title = "라라라라";
        ArticleForm dto = new ArticleForm(id, title, null);
        Article expected = null;

        //실제
        Article article = articleService.update(id, dto);

        //비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_실패___id만_있는_dto_입력() {
        //예상
        Long id = 1L;
        ArticleForm dto = new ArticleForm(id, null, null);
        Article expected = new Article(id, "가가가가", "1111");

        //실제
        Article article = articleService.update(id, dto);

        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_성공__존재하는_id_입력() {
        //예상
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        //실제
        Article article = articleService.delete(id);

        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_실패__존재하지_않는_id_입력() {
        //예상
        Long id = -1L;
        Article expected = null;

        //실제
        Article article = articleService.delete(id);

        //비교
        assertEquals(expected, article);
    }
}