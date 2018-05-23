package com.thymemother.dsl

// http://melix.github.io/javaone-groovy-dsls/#/implementing-modern-dsls

def result = HelperExtension.myCollect(['Paris', 'Washington', 'Berlin']) {
    length() == 5
}
print result
