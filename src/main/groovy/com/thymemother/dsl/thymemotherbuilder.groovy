package com.thymemother.dsl

import com.thymemother.controller.model.User
import io.beanmother.core.ObjectMother
import org.springframework.ui.Model
import org.springframework.validation.support.BindingAwareModelMap

// http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html#section-delegatesto

class MapSpec {
    def bm
    void fixture(ObjectMother objectMother, String fixture, Class clazz) {
        bm = objectMother.bear(fixture, clazz);
    }
}

class ModelSpec {
    void add(BindingAwareModelMap m, String name, Object value) {
        m.addAttribute(name, value)
    }
}

class RootSpec {
    def map(Closure map) {
        def mapSpec = new MapSpec()
        def code = map.rehydrate(mapSpec, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        return mapSpec.bm
    }
    void model(Closure model) {
        def modelSpec = new ModelSpec()
        def code = model.rehydrate(modelSpec, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }
}

def root(Closure cl) {
    def root = new RootSpec()
    def code = cl.rehydrate(root, this, this)
    code.resolveStrategy = Closure.DELEGATE_ONLY
    code()
}
