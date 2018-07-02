package com.thymemother.decoupled

import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templateparser.markup.decoupled.IDecoupledTemplateLogicResolver

import java.util.Set;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresource.ITemplateResource;

/**
 * <p>
 *   Default implementation of the {@link IDecoupledTemplateLogicResolver} interface.
 * </p>
 * <p>
 *   This class computes a {@link ITemplateResource} for the decoupled template logic by resolving a resource considered
 *   <em>relative</em> to the template resource (see {@link ITemplateResource#relative(String)}).
 * </p>
 * <p>
 *   By default, the relative location resolved will be formed as
 *   <tt>resource.getBaseName() + DECOUPLED_TEMPLATE_LOGIC_FILE_SUFFIX</tt> (see
 *   {@link ITemplateResource#getBaseName()} and {@link #DECOUPLED_TEMPLATE_LOGIC_FILE_SUFFIX}.
 *   So for a template resource <tt>/WEB-INF/templates/main.html</tt>, the <tt>main.th.xml</tt> relative
 *   location will be used to call {@link ITemplateResource#relative(String)}.
 * </p>
 * <p>
 *   However this can be modified by specifying different <tt>prefix</tt> and <tt>suffix</tt> values so that, if a
 *   <tt>prefix</tt> with value <tt>"../logic/"</tt> is specified, for a template resource
 *   <tt>/WEB-INF/templates/main.html</tt>, the <tt>../viewlogic/main.th.xml</tt> relative path will be resolved, normally
 *   resulting in the <tt>/WEB-INF/viewlogic/main.th.xml</tt> resource.
 * </p>
 * <p>
 *   This class is <strong>thread-safe</strong>.
 * </p>
 *
 * @author Daniel Fern&aacute;ndez
 * @since 3.0.0
 *
 */
public final class GroovyDecoupledTemplateLogicResolver implements IDecoupledTemplateLogicResolver {

    /**
     * <p>
     *   Default suffix applied to the relative resources resolved: <tt>.th.xml</tt>
     * </p>
     */
    public static final String DECOUPLED_TEMPLATE_LOGIC_FILE_SUFFIX = ".th.groovy";

    private String prefix = null;
    private String suffix = DECOUPLED_TEMPLATE_LOGIC_FILE_SUFFIX;

    public GroovyDecoupledTemplateLogicResolver() {
        super();
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public ITemplateResource resolveDecoupledTemplateLogic(
            final IEngineConfiguration configuration,
            final String ownerTemplate, final String template, final Set<String> templateSelectors,
            final ITemplateResource resource, final TemplateMode templateMode) {

        String relativeLocation = resource.getBaseName();

        if (this.prefix != null) {
            relativeLocation = this.prefix + relativeLocation;
        }

        if (resource.relative(relativeLocation+ this.suffix).exists()) {
            if (this.suffix != null) {
                relativeLocation = relativeLocation + this.suffix;
            }
            GroovyTemplateResource result = new GroovyTemplateResource(resource.relative(relativeLocation).reader());
            return result;
        } else {
            return resource.relative("ssr/"+resource.getBaseName()+".html");
        }
    }
}
