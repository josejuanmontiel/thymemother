package com.thymemother.thymeleaf.view;

import com.thymemother.thymeleaf.engine.ReloadableScriptEngine;
import com.thymemother.thymeleaf.engine.ReloadableScriptEngineFactory;
import com.thymemother.thymeleaf.view.component.react.ReactComponent;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.spring4.view.ThymeleafView;

import javax.script.ScriptEngine;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ServerJsThymeleafView extends ThymeleafView {
    public static final String KEY_PROPS = "props";
    public static final String KEY_REACT_COMPONENT = "react_component";

    private ReloadableScriptEngineFactory engineFactory;
    private ReactComponent react;

    @Override
    protected void initApplicationContext(ApplicationContext context) {
        super.initApplicationContext(context);
        engineFactory = context.getBean(ReloadableScriptEngineFactory.class);
        react = new ReactComponent();
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (request.getRequestURL().toString().contains("ssr")) {
            ReloadableScriptEngine engine = engineFactory.getScriptEngine().reload().newBindings();
            model = renderReactComponent(model, engine.get());
        }
        super.render(model, request, response);
    }

    private Map<String, ?> renderReactComponent(Map<String, ?> model, ScriptEngine engine) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (model != null)
            map.putAll(model);

        String html = react.render(engine, "Router", (String) map.get(KEY_PROPS));
        map.put(KEY_REACT_COMPONENT, html);

        return map;
    }
}
