package com.thymemother.thymeleaf.dialect;

import com.thymemother.thymeleaf.engine.ReloadableScriptEngineFactory;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class ServerJsDialect extends AbstractProcessorDialect {
    private ReloadableScriptEngineFactory engineFactory;

    public ServerJsDialect(ReloadableScriptEngineFactory engineFactory) {
        super("ServerJsDialect", "serverjs", 1000);
        this.engineFactory = engineFactory;
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> procs = new HashSet<>();
        procs.add(new ServerJsReplaceAttrProcessor(engineFactory, dialectPrefix));
        return procs;
    }
}
