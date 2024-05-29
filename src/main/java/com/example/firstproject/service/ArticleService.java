package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service //서비스 선언! (서비스 객체를 스프링부트에 생성)
public class ArticleService {

    @Autowired //DI
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll(); //DB에서 데이터 가져오기 -> Repository 역할
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null) //id를 넣으려고 하면 못하게 해라
            return null;
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1. 수정용 entity 생성
        Article article = dto.toEntity();
        log.info("생성된 dto entity : " + article.toString());

        //2. 대상 entity 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if (target == null || id != article.getId()) {
            return null;
        }

        //4. 업데이트
        target.patch(article);
        Article updated = articleRepository.save(target);
        log.info("updated : " + updated); //updated 값 : Article(id=1, title=가나다라마, content=@@@@@@@@)
        return updated;
    }

    public Article delete(Long id) {
        //1. 대상 entity 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //2. 잘못된 요청 처리 (대상이 없는 경우)
        if(target == null)
            return null;

        //3. 대상 삭제 후 응답 변환
        articleRepository.delete(target);
        return target;
    }

    //트랜잭션
    @Transactional //해당 메소드를 트랜잭션으로 묶는다!
                    //-> 해당 메소드가 수행되다 실패하면 메소드 수행 이전 상태로 롤백한다!
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1. dto 묶음을 entity 묶음으로 변환
        //스트림 문법 - 1
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        //2. entity 묶음을 DB로 저장
        //스트림 문법 - 2
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        //3. 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );

        //4. 결과값 반환
        return articleList;
    }
}

    /*스트림 문법-1을 for문으로 작성하면...
    List<Article> articleList = new ArrayList<>();
    for(int i = 0; i<dtos.size(); i++){
        ArticleForm dto = dtos.get(i);
        Article entity = dto.toEntity();
        articleList.add(entity);
    } */

    /*스트림 문법-2을 for문으로 작성하면...
    for(int i = 0; i < articleList.size(); i++) {
        Article article = articleList.get(i);
        articleRepository.save(article);
    } */