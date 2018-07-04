package com.thymemother.dsl

import io.beanmother.core.NewObjectMother
import io.beanmother.core.ObjectMother
import org.springframework.ui.Model
import org.springframework.validation.support.BindingAwareModelMap

// http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html#section-delegatesto

class MapSpec {
    def bm

//    ObjectMother objectMother
    NewObjectMother objectMother

//    MapSpec(ObjectMother o) {
    MapSpec(NewObjectMother o) {
        objectMother = o;
    }

    void fixture(String fixture, Class clazz) {
        bm = objectMother.bear(fixture, clazz);
    }
}

class ModelSpec {
    BindingAwareModelMap model

    ModelSpec(BindingAwareModelMap m) {
        model = m
    }

    void add(String name, Object value) {
        model.addAttribute(name, value)
    }
}

class RootSpec {

//    ObjectMother objectMother
    NewObjectMother objectMother

    Model model

    RootSpec(Binding binding){
        model  = binding.getVariable('m')
        objectMother = binding.getVariable('objectMother')
    }

    def map(Closure c) {
        def mapSpec = new MapSpec(objectMother)
        def code = c.rehydrate(mapSpec, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        return mapSpec.bm
    }
    void model(Closure c) {
        def modelSpec = new ModelSpec(model)
        def code = c.rehydrate(modelSpec, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }
}

class Root {
    def static root(Binding binding, @DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = RootSpec) Closure cl) {
        def root = new RootSpec(binding)
        def code = cl.rehydrate(root, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }
}