package com.thymemother.thymeleaf.view.component.react;

import javax.script.ScriptEngine;

public class ReactComponent {
    public String render(ScriptEngine engine, String component, String props) throws Exception {
        Object html = engine.eval("render('" + component + "', " + props + ")");
        return String.valueOf(html);
    }
}
