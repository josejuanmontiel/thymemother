package com.thymemother.dsl

class Builder{

    def static bindModel(
            Binding binding,
            @DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = ThymemotherSpec) Closure closure){
        ThymemotherSpec spec = new ThymemotherSpec(binding)
        Closure clone = closure.rehydrate(spec,spec,spec)
        clone()
    }

}