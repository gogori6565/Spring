package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j //로깅을 위한 어노테이션
public class ArticleController {

    @Autowired //스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결! -> 따라서 new로 객체 생성 안해도 됨
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new"; //연결할 View 페이지 이름 (package가 있다면 package이름까지 연결)
    }

    @PostMapping("/articles/create") //form 태그에서 method를 post로 했기 때문에 PostMapping
    public String createArticle(ArticleForm form){ //ArticleForm form -> DTO 객체
        log.info(form.toString());
        //System.out.println(form.toString()); -> 로깅 기능으로 대체

        //1. DTO를 변환! Entity로 바꾸기!
        Article article = form.toEntity();
        log.info(article.toString());

        //2. Repository에게 Entity를 DB안에 저장하게 함
        Article saved = articleRepository.save(article); //CrudRepository에서 기본적으로 save()를 제공함
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId(); //리다이렉트 적용
    }

    /*데이터 조회하기*/
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);

        //1. id로 데이터를 가져옴
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        //2. 가져온 데이터(articleEntity)를 모델에 등록
        // 모델은 key와 value로 이루어져 있는 HashMap으로써 .addAttribute를  통해 view에 전달할 데이터를 저장할 수 있다
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        //3. 보여줄 페이지를 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){

        //1. 모든 Article을 가져온다
        //.findAll() : 해당 Repository에 있는 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();

        //2. 가져온 Article 묶음을 뷰로 전달!
        model.addAttribute("articleList", articleEntityList);

        //3. 뷰 페이지를 설정!
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정할 데이터를 가져오기!
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //모델에 데이터를 등록!
        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    //15. 데이터 수정하기
    @PostMapping("/articles/update")
    public String update(ArticleForm form){

        //1. DTO를 entity로 변환한다!
        Article articleEntity = form.toEntity();

        //2. entity를 DB로 저장한다!
        //2-1. DB에 기존 데이터를 가져온다 (수정하는 것이기 때문에)
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        //2-2. 기존 데이터에 값을 갱신한다!
        if(target != null){
            articleRepository.save(articleEntity); //entity가 DB로 갱신됨
        }

        //3. 수정 결과 페이지로 리다이렉트 한다!
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        //1. 삭제 대상을 가져온다
        Article target = articleRepository.findById(id).orElse(null);

        //2. 그 대상을 삭제한다.
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }

        //3. 결과 페이지로 리다이렉트 한다.
        return "redirect:/articles";
    }
}
