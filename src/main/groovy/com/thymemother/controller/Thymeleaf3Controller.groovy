package com.thymemother.controller

import com.thymemother.controller.model.User
import com.thymemother.model.Author
import com.thymemother.model.Book
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import io.beanmother.core.*

@Controller
public class Thymeleaf3Controller {

    @RequestMapping(value="/thymeleaf3", method=RequestMethod.GET)
    public String selectAdorable(Model model) {
        ObjectMother objectMother = ObjectMother.getInstance();

        // Overwrite source of directory fixture by system properties...
        String fixturePath = System.properties['fixtureDir']
        objectMother.addFixtureLocation(fixturePath)

        Book book = objectMother.bear("book", Book.class);
        Author author = objectMother.bear("author", Author.class);
        List<Author> authors = objectMother.bear("author", Author.class, 10);


        def users = [new User(name:'User1', type: 'type1'), new User(name:'User2', type: 'type1')]
        model.addAttribute("users", users)
        return "thymeleaf3";
    }

}
