package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //JSON을 반환하는 RestAPI용 컨트롤러
public class FirstApiController {
    @GetMapping("api/hello")
    public String hello(){
        return "hello world!"; //뷰템플릿 페이지가 아니라 데이터(텍스트나 json)를 반환
    }
}
