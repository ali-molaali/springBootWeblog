package com.tosan.blog.moudules.role.controller;


import com.tosan.blog.moudules.authority.service.AuthorityService;
import com.tosan.blog.moudules.role.model.Role;
import com.tosan.blog.moudules.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping("/roles")
public class RoleController {
    private final AuthorityService authorityService;
    private RoleService roleService;
    @Autowired
    RoleController(RoleService roleService, AuthorityService authorityService){
        this.roleService = roleService;
        this.authorityService = authorityService;
    }

    @RequestMapping(value = {"/",""},method = RequestMethod.GET)
    public String listRoles (Model model){
        model.addAttribute("roles", roleService.findAll());
        return "roles/roles";
    }

    @RequestMapping(value ="/register",method = RequestMethod.GET)
    public String showRegister (Model model){
        model.addAttribute("role", new Role());
        model.addAttribute("authorities", authorityService.findAll());
        return "roles/registerRoles";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register (@ModelAttribute Role role){
        roleService.roleRegister(role);
        return"redirect:/roles";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editPage(Model model ,@PathVariable("id") Long id) {
        model.addAttribute("role", roleService.findById(id));
        model.addAttribute("authorities", authorityService.findAll());
        return "roles/registerRoles";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {
        roleService.deleteById(id);
        return "redirect:/roles";
    }
}
