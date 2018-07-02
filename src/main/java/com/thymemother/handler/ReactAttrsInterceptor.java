package com.thymemother.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReactAttrsInterceptor extends HandlerInterceptorAdapter {
    private ObjectMapper mapper;

    public ReactAttrsInterceptor(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (request.getRequestURL().toString().contains("ssr")) {
            if (modelAndView != null) {
                ModelMap model = modelAndView.getModelMap();
                model.addAttribute("actionPath", buildActionPath(handler, modelAndView));
                if (!isJson(request)) {
                    // TODO Esto es especifico de ssr
                    modelAndView.setViewName("ssr/layout");
                    model.addAttribute("props", mapper.writeValueAsString(model));
                }
            }
        }
    }

    private String buildActionPath(Object handler, ModelAndView mav) {
        if (handler instanceof HandlerMethod) {
            return buildActionPath((HandlerMethod) handler);
        }
        else {
            System.err.println("Unsupported handler: " + (handler != null ? handler.getClass() : "null"));
            return "";
        }
    }

    private String buildActionPath(HandlerMethod handler) {
        String typeName = handler.getBeanType().getSimpleName();
        if (typeName.endsWith("Controller"))
            typeName = typeName.substring(0, typeName.length() - "Controller".length());

        String methodName = handler.getMethod().getName();
        return typeName + "#" + methodName;
    }

    private boolean isJson(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        if (accept != null)
            return accept.contains("application/json");
        else
            return false;
    }
}

