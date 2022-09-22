package com.tosan.blog.moudules.post.controller;


import com.tosan.blog.moudules.category.service.CategoryService;
import com.tosan.blog.moudules.post.model.Post;
import com.tosan.blog.moudules.post.service.PostService;
import com.tosan.blog.moudules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;

@Controller
@RequestMapping("/posts")
public class PostController {
    private PostService postService;
    private CategoryService categoryService;
    private UserService userService;
    @Autowired
    PostController(PostService postService, CategoryService categoryService, UserService userService){

        this.postService=postService;
        this.categoryService = categoryService;
        this.userService=userService;
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String postsView(Model model , Principal principal)
    {
        model.addAttribute("posts",postService.findAllByUser(userService.findByEmail(principal.getName())));
        model.addAttribute("user",principal.getName());
        return "posts/posts";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String registerView(Model model){
        model.addAttribute("post",new Post());
        model.addAttribute("categories", categoryService.findAll());
        return "posts/registerPosts";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute(name = "post") Post post,
                           BindingResult bindingResult, Model model, Principal principal) throws IllegalAccessException, IOException, InvocationTargetException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "posts/registerPosts";
        }

        post.setUser(userService.findByEmail(principal.getName()));
        postService.postRegister(post);
        return "redirect:/posts";
    }
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editPage(Model model ,@PathVariable("id") Long id) {
        model.addAttribute("post", postService.findById(id));
        return "posts/registerPosts";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {
        postService.deleteById(id);
        return "redirect:/posts";
    }
}
