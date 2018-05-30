package com.thymemother.test.dsl

import java.util.List

class  HelperExtension {
    public static <T,U> List<U> myCollect(List<T> items, Closure<U> action) {
        def clone = action.clone()
        clone.resolveStrategy = Closure.DELEGATE_FIRST
        def result = []
        items.each {
            clone.delegate = it
            result << clone()
        }
        result
    }
}
