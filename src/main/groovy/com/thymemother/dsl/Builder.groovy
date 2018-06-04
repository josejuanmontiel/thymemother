package com.thymemother.dsl

class Builder{

    def static bindModel( Binding binding, String model, @DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = ThymemotherSpec) Closure closure){
        ThymemotherSpec spec = new ThymemotherSpec(binding, model)
        Closure clone = closure.rehydrate(spec,spec,spec)
        clone()
    }

}