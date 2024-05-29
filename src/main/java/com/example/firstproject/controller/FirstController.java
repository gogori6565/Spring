package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi") //hi라는 url을 입력받았을 때 greetings.mustache를 찾아 반환해준다.
    public String niceToMeetYou(Model model){
        model.addAttribute("username", "juhyeon");
        return "greetings"; //templates/greetings.mustache 찾아서 -> 브라우저로 전송!
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "홍길동");
        return "goodbye";
    }
}
