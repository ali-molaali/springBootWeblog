package com.tosan.blog.moudules.user.controller;

import com.tosan.blog.moudules.authority.model.Authority;
import com.tosan.blog.moudules.role.model.Role;
import com.tosan.blog.moudules.role.service.RoleService;
import com.tosan.blog.moudules.user.model.User;
import com.tosan.blog.moudules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private RoleService roleService;
    @Autowired
    UserController(UserService userService, RoleService roleService){
        this.userService=userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
   // @PreAuthorize("hasAuthority('OP_ACCESS_USER')")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/users";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
  //  @PreAuthorize("hasAuthority('OP_NEW_USER')")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allroles", roleService.findAll());
        return "users/registerUser";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
   // @PreAuthorize("hasAuthority('OP_EDIT_USER')")
    public String registerPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("allroles", roleService.findAll());
        return "users/registerUser";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
   // @PreAuthorize("hasAuthority('OP_DELETE_USER')")
    public String delete(@PathVariable("id") User user) {
        userService.deleteById(user);
        return "redirect:/users";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    //@PreAuthorize("hasAuthority('OP_ACCESS_USER')")
    public String register(@ModelAttribute(name = "user") @Valid User user, BindingResult bindingResult ,Model model) throws IOException, InvocationTargetException, IllegalAccessException {
        if (bindingResult.hasErrors()){
            model.addAttribute("allroles", roleService.findAll());
            return "users/registerUser";}

        userService.userRegister(user);
        return "redirect:/users";
    }
    @RequestMapping(value = "/register/guest", method = RequestMethod.POST)
    public String registerGuest(@ModelAttribute(name = "user") @Valid User user, BindingResult bindingResult , Model model) throws IOException, InvocationTargetException, IllegalAccessException {
        if (bindingResult.hasErrors()){
            return "users/registerGuest";}
        List<Role> roles=new ArrayList<>();
        Role role=new Role();
        role.setId(32L);
        roles.add(role);
        user.setRoles(roles);
        userService.userRegister(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/register/guest", method = RequestMethod.GET)
    public String registerGuestPage(Model model) {
        model.addAttribute("user", new User());
        return "users/registerGuest";
    }
}
