package com.thymemother.dsl

// http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html#section-delegatesto

class MapSpec {
    void item(String item) {
        println "item: $item"
    }
}

class ModelSpec {
    void item(String item) {
        println "item: $item"
    }
}

class RootSpec {
    void map(Closure map) {
        def mapSpec = new MapSpec()
        def code = map.rehydrate(mapSpec, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
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

root {
    map{
        item 'def map1 = [user1, User.class]'
        item 'def map2 = [user2, User.class]'
    }
    model {
        item 'def users = [user1, user2]'
    }
}