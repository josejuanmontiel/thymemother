package io.beanmother.core.script.std

import com.github.javafaker.ExtendFaker
import com.github.javafaker.Faker
import io.beanmother.core.script.MethodReflectionEvalScriptRunner
import io.beanmother.core.script.ScriptFragment

class ExtendFakerScriptRunner extends MethodReflectionEvalScriptRunner {

    private final static String SCRIPT_NAMESPACE = "efaker";
    private final static String OPTION_FAKER_FRAGMENT_METHOD_NAME = "options";

//    private final static Faker faker = new Faker();
    private final static Faker faker = new ExtendFaker();

    @Override
    public Object run(ScriptFragment scriptFragment) {
        if (scriptFragment.getNext().getMethodName().equals(OPTION_FAKER_FRAGMENT_METHOD_NAME)) {
            return runOptions(scriptFragment);
        } else {
            return super.run(scriptFragment);
        }

    }

    private Object runOptions(ScriptFragment scriptFragment) {
        List<String> options = scriptFragment.getNext().getArguments();
        if (options.isEmpty()) {
            throw new IllegalArgumentException("faker.options must have arguments");
        }
        return options.get(new Random().nextInt(options.size()));
    }

    @Override
    public Object getTargetObject() {
        return faker;
    }

    @Override
    public String getScriptNamespace() {
        return SCRIPT_NAMESPACE;
    }
}
