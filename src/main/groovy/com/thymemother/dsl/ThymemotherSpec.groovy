package com.thymemother.dsl

import io.beanmother.core.ObjectMother
import org.springframework.ui.Model

class ThymemotherSpec{

    Binding binding

    ObjectMother objectMother

    Model model

    ThymemotherSpec(Binding binding){
        model  = binding.getVariable('m')
        objectMother = binding.getVariable('objectMother')
    }

    ThymemotherSpec(Binding binding, String variable){
        model  = binding.getVariable(variable, 'm')
        objectMother = binding.getVariable('objectMother')
    }

    def fixtureInstance(String name, Class aClass){
        objectMother.bear(name, aClass)
    }

    void add(Object object){
        model.addAttribute(object)
    }

    void add(String name, Object object){
        model.addAttribute(name, object)
    }
}