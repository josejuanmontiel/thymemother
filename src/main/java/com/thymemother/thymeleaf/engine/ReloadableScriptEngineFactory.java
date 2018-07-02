package com.thymemother.thymeleaf.engine;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.List;

public class ReloadableScriptEngineFactory implements ApplicationContextAware {

    private final List<String> scripts = new ArrayList<>();
    private final ThreadLocal<ReloadableScriptEngine> engines = new ThreadLocal<>();
    private String engineName = "nashorn";

    private ApplicationContext context;
    private ScriptEngineManager scriptEngineManager;

    public ReloadableScriptEngineFactory() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        scriptEngineManager = new ScriptEngineManager(context.getClassLoader());
    }

    public void addScript(String script) {
        scripts.add(script);
    }

    public ReloadableScriptEngine getScriptEngine() throws Exception {
        ReloadableScriptEngine e = engines.get();
        if (e != null)
            return e;

        ScriptEngine engine = scriptEngineManager.getEngineByName(engineName);
        e = new ReloadableScriptEngine(engine, context);
        scripts.forEach(e::addScript);
        e.reload();  // initial load
        engines.set(e);
        return e;
    }
}

