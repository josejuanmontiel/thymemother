package com.thymemother.thymeleaf.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;

public class JsonViewResolver extends AbstractCachingViewResolver {
    private final ObjectMapper mapper;

    public JsonViewResolver(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        MappingJackson2JsonView view = new MappingJackson2JsonView(mapper);
        view.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return view;
    }
}
