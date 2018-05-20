package com.thymemother.controller

import com.thymemother.controller.model.User
import com.thymemother.model.Author
import com.thymemother.model.Book
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import io.beanmother.core.*

@Controller
public class Thymeleaf3Controller {

    @RequestMapping(value="/{template}", method=RequestMethod.GET)
    public String selectAdorable(@PathVariable("template") String template, Model model) {
        ObjectMother objectMother = ObjectMother.getInstance();

        // Overwrite source of directory fixture by system properties...
        String fixturePath = System.properties['fixtureDir']
        objectMother.addFixtureLocation(fixturePath)

        // Read the map from .groovy file
        def user1 = objectMother.bear("user1", User.class);
        def user2 = objectMother.bear("user2", User.class);

        // Load the agregate from the model..
        def users = [user1, user2]
        model.addAttribute("users", users)

        return template;
    }

}
