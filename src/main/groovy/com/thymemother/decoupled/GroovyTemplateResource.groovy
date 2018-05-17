package com.thymemother.decoupled

import groovy.text.Template
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration
import groovy.xml.StreamingMarkupBuilder
import org.thymeleaf.templateresource.ITemplateResource
import org.thymeleaf.templateresource.TemplateResourceUtils

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;

import org.thymeleaf.util.StringUtils;
import org.thymeleaf.util.Validate;

/**
 * <p>
 *   Implementation of {@link ITemplateResource} representing a file in the file system.
 * </p>
 * <p>
 *   Objects of this class are usually created by {@link org.thymeleaf.templateresolver.FileTemplateResolver}.
 * </p>
 *
 * @author Daniel Fern&aacute;ndez
 * @since 3.0.0
 *
 */
public final class GroovyTemplateResource implements ITemplateResource, Serializable {

    private java.io.Reader reader

    public GroovyTemplateResource(java.io.Reader reader) {
        super();
        this.reader = reader
    }

    public String getDescription() {
        return null
    }

    public String getBaseName() {
        return null
    }

    // TODO Optimize
    public Reader reader() throws IOException {

        TemplateConfiguration config = new TemplateConfiguration();
        MarkupTemplateEngine engine = new MarkupTemplateEngine(config);

        Template template = engine.createTemplate(reader);

        Map<String, Object> model = new HashMap<>();
        Writable output = template.make(model);

        StringWriter writer = new StringWriter();
        output.writeTo(writer)

        return new StringReader(writer.getBuffer().toString())
    }

    public ITemplateResource relative(final String relativeLocation) {
        return null
    }

    public boolean exists() {
        return true;
    }

}
