package com.thymemother.controller

import com.thymemother.controller.model.User
import com.thymemother.dsl.RootSpec
import com.thymemother.dsl.MapSpec
import com.thymemother.dsl.ModelSpec
import com.thymemother.dsl.HelperExtension
import com.thymemother.model.Author
import com.thymemother.model.Book
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import io.beanmother.core.*

@Controller
public class Thymeleaf3Controller {

    def root(Closure cl) {
        def root = new RootSpec()
        def code = cl.rehydrate(root, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }

    Logger log = LoggerFactory.getLogger(Thymeleaf3Controller.class);

    @RequestMapping(value="/{template}", method=RequestMethod.GET)
    public String selectAdorable(@PathVariable("template") String template, Model m) {
        ObjectMother objectMother = ObjectMother.getInstance();

        // Overwrite source of directory fixture by system properties...
        String fixturePath = System.properties['fixtureDir']
        objectMother.addFixtureLocation(fixturePath)

        root {
            model {
                item 'def users = [user1, user2]'
            }
            map{
                item 'def map1 = [user1, User.class]'
                item 'def map2 = [user2, User.class]'
            }
        }

        // Read the map from .groovy file
        def user1 = objectMother.bear("user1", User.class);
        def user2 = objectMother.bear("user2", User.class);

        // Load the agregate from the m..
        def users = [user1, user2]
        m.addAttribute("users", users)

        return template;
    }

}
