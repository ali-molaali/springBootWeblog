package com.tosan.blog.moudules.category.controller;

import com.tosan.blog.moudules.category.model.Category;
import com.tosan.blog.moudules.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;
    @Autowired
    CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @RequestMapping(value = {"/",""},method = RequestMethod.GET)
    public String listCategories (Model model){

        model.addAttribute("categories", categoryService.findAll());
        return "categories/categories";
    }

    @RequestMapping(value ="/register",method = RequestMethod.GET)
    public String showRegister (Model model){
        model.addAttribute("category", new Category());
        //redirectAttributes.addFlashAttribute("success", "Everything went just fine1.");
        return "categories/registerCategories";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register (@ModelAttribute @Valid Category category , BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("error", "complete the form !");
            return "categories/registerCategories";
        }
            categoryService.CategoryRegister(category);
        return"redirect:/categories";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editPage(Model model ,@PathVariable("id") Long id) {
        model.addAttribute("category", categoryService.findById(id));
        return "categories/registerCategories";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
        categoryService.deleteById(id);
        } catch (Exception e) {
            if(e instanceof DataIntegrityViolationException) {
                redirectAttributes.addFlashAttribute("error", "you can not delete this category");
            }
        }
        return "redirect:/categories";
    }

}
