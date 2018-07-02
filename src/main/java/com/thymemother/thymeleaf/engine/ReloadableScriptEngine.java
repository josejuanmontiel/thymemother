package com.thymemother.thymeleaf.engine;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.script.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReloadableScriptEngine {
    private final List<String> scripts = new ArrayList<>();
    private final Map<String, Long> timestamps = new HashMap<>();
    private ResourceLoader resourceLoader;
    private ScriptEngine engine;
    private Bindings scriptBindings;

    public ReloadableScriptEngine(ScriptEngine engine, ResourceLoader resourceLoader) {
        this.engine = engine;
        this.resourceLoader = resourceLoader;
        this.scriptBindings = new SimpleBindings();
    }

    public void addScript(String script) {
        this.scripts.add(script);
        this.timestamps.put(script, -1L);
    }

    public ScriptEngine get() {
        return engine;
    }

    public ReloadableScriptEngine reload() throws ScriptException, IOException {
        for (String script : scripts) {
            Resource resource = resourceLoader.getResource(script);
            long lastModified = resource.lastModified();
            if (timestamps.get(script) < lastModified) {
                engine.eval(new InputStreamReader(resource.getInputStream()), scriptBindings);
                timestamps.put(script, lastModified);
            }
        }

        return this;
    }

    public ReloadableScriptEngine newBindings() {
        this.engine.setBindings(new SimpleBindings(scriptBindings), ScriptContext.ENGINE_SCOPE);
        return this;
    }
}

