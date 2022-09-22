package com.tosan.blog.moudules;

import com.tosan.blog.moudules.post.service.PostService;
import com.tosan.blog.moudules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MainController {
    private PostService postsService;

    @Autowired
    MainController(PostService postsService){
        this.postsService=postsService;

    }
    @RequestMapping("/")
    public String index(Model model, Principal principal){
        model.addAttribute("posts",postsService.findAll());
        return "index";
    }
    @RequestMapping("/login")
    public String login(){

        return "login";
    }
    @RequestMapping("/notaccess")
    public String error(){

        return "notaccess";
    }

}
