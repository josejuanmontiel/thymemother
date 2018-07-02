package com.thymemother.thymeleaf.dialect;

import com.thymemother.thymeleaf.engine.ReloadableScriptEngine;
import com.thymemother.thymeleaf.engine.ReloadableScriptEngineFactory;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class ServerJsReplaceAttrProcessor extends AbstractAttributeTagProcessor {
    private static final String ATTR_NAME = "replace";
    private static final int PRECEDENCE = 10000;

    private ReloadableScriptEngineFactory engineFactory;

    public ServerJsReplaceAttrProcessor(ReloadableScriptEngineFactory engineFactory, String dialectPrefix) {
        super(TemplateMode.HTML,            // this processor will apply only to HTML mode
                dialectPrefix,              // prefix to be applied to name for matching
                null,         // no tag name: match any tag name
                false,   // no prefix to be applied to tag name
                ATTR_NAME,                  // name of the attribute that will be matched
                true,    // apply dialect prefix to attribute name
                PRECEDENCE,                 // precedence (inside dialect's precedence)
                true);      // remove the matched attribute afterwards

        this.engineFactory = engineFactory;
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
                             String attributeValue, IElementTagStructureHandler structureHandler) {
        try {
            ReloadableScriptEngine engine = engineFactory.getScriptEngine();
            Object val = engine.get().eval(attributeValue);
            structureHandler.replaceWith(String.valueOf(val), false);
        } catch (Exception e) {
            throw new TemplateProcessingException("failed to eval js: '" + attributeValue + "'", e);
        }
    }
}

