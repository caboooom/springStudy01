package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {

    @Autowired //spring boot가 미리 생성해놓은 객체를 가져와서 자동으로 연결해줌
    private ArticleRepository articleRepository;

    @GetMapping("articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());

        // 1.dto를 entity로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        // 2.repository가 entity를 db 안에 저장하게 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/"+saved.getId(); //리다이렉트 하도록 함
    }

    @GetMapping("/articles/{id}") //중괄호 내부에 있는 id는 변수
    public String show(@PathVariable Long id, Model model){
        log.info("id = "+id);

        // 1. repository에서 id로 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); //orElse 대신 Optional<Article> 타입으로 받아와도 댐

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        // 3. 보여줄 페이지를 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){

        // 1. repository에서 모든 Article을 가져온다.
        List<Article> articleEntityList = articleRepository.findAll();
        //원래 findAll()은 iterable 타입으로 반환을 해주는데,
        //우리는 ArticleRepository에서 이 method를 오버라이딩하여 ArrayList로 반환하도록 하였음

        // 2. 가져온 Article 묶음을 view로 전달한다.
        model.addAttribute("articleList",articleEntityList);

        // 3, 뷰 페이지를 설정한다.
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // 수정할 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터를 등록
        model.addAttribute("article",articleEntity);

        // 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());

        // 1.dto를 entity로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2.entity를 db에 저장
        // 2-1. db에서 기존 데이터를 가져오기
        Article tartget = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2. 기존 데이터 값을 갱신
        if(tartget!=null){
            articleRepository.save(articleEntity);
        }

        // 3.수정 결과 페이지로 redirect
        return "redirect:/articles/"+articleEntity.getId();
    }

    //html에서 DELETE요청을 지원하지 않으므로 임시방편으로 GET을 이용한다
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다");

        // 1. 삭제 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        // 2. 대상을 삭제
        if(target!=null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제가 완료되었습니다!");
        }

        // 3. 결과 페이지로 리다이렉트
        return "redirect:/articles";
       }
}
