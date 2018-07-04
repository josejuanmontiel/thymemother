package io.beanmother.core

import io.beanmother.core.script.std.ExtendFakerScriptRunner

class NewObjectMother extends NewAbstractBeanMother {

    public NewObjectMother() {
        super();
        getScriptHandler().register(new ExtendFakerScriptRunner());
    }
}
