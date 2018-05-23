package com.thymemother.test.dsl

// https://wiki.lappsgrid.org/technical/dsl

class Delegate {
    String uri
    String description

    void uri(String uri) { this.uri = uri }
    void description(String description) { this.description = description }
}

class Dsl {
    public static void main(String[] params)
    {
        String code = new File(params[0]).text
        def objects = [:]
        Script script = new GroovyShell().parse(code)

        script.metaClass.methodMissing = { String name, args ->
            Closure cl = args[0]
            cl.delegate = new Delegate()
            cl();
            annotations[name] = cl.delegate
        }

        script.run()
        println new groovy.json.JsonBuilder(objects).toPrettyString()
    }
}

