package com.thymemother.controller

import com.thymemother.controller.model.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
public class Thymeleaf3Controller {

    @RequestMapping(value="/thymeleaf3", method=RequestMethod.GET)
    public String selectAdorable(Model model) {
        def users = [new User(name:'User1', type: 'type1'), new User(name:'User2', type: 'type1')]
        model.addAttribute("users", users)
        return "thymeleaf3";
    }

}
