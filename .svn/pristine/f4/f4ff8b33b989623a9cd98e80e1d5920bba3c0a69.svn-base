package com.hy.controller;

import com.hy.domain.User;
import com.hy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
   @RequestMapping("/user")
   public String user(Model model){
       List<User> list = userService.uList();
       model.addAttribute("list",list);
       return "list";
   }
}
