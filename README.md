# thymemother

A "bean mother pattern" for the Thymeleaf templates.

## The idea

The idea behind the project is simple, allow the designers of a web project,
have the necessary tools to identify and model the entities that will be represented
in each of the parts of a website, and even to connect the REST services with the parties
from the web where they are going to show. The designers are usually closer to the UX and UI
and therefore can know first hand what data is shown in each part.

This could be an scheme of the parts

![Thymemother proposal](thymemother-proposal.jpg)

In my [blog](https://josejuanmontiel.github.io/blog/2016/10/groovy-thymelaf.es.html), few years ago,
I start to think about. Now, the idea it's build a springboot + thymeleaf + groovy project, that
use beanmother for "build the objects", and when new module will be developed this will connect from an API REST.

There will be other ways to do it, this is my way ;)

## How to run it

Clone the project and: gradle bootRun

## How to use it

Then you can open in browser http://localhost:8080/thymeleaf3 and this will render src/main/resources/templates/thymeleaf3.html
and thanks to src/main/resources/templates/thymeleaf3.th.groovy this will a groovy version of ![deacopled](https://github.com/thymeleaf/thymeleaf/issues/465)
that render the .th.xml version. or if doesn't exist .th.groovy file the src/main/resources/templates/thymeleaf3.th.xml will be use,
and witch binding apply?

## BeanMother
The folder src/main/resources/fixtures contains the fixtures for beanmother

```YAML
user1: &user1
  name: user1
  type: type1

user2: &user2
  name: user2
  type: type2
```

Using a groovy DSL (src/main/groovy/com/thymemother/dsl/thymemotherbuilder.groovy) defined in a file behind the other
src/main/resources/templates/thymeleaf3.groovy

```
// TODO use compilation customizer
import com.thymemother.dsl.RootSpec
import com.thymemother.controller.model.User

// TODO ???
def m = binding.getVariable("m")
def objectMother = binding.getVariable("objectMother")

// TODO ???
def root(Closure cl) {
    def root = new RootSpec()
    def code = cl.rehydrate(root, this, this)
    code.resolveStrategy = Closure.DELEGATE_ONLY
    code()
}

root {
    def user1 = map {
        fixture objectMother, "user1", User.class
    }
    def user2 = map {
        fixture objectMother, "user2", User.class
    }

    def users = [user1, user2]

    model {
        add m, "users", users
    }
}

```

You can define variables, and using this DSL

    fixture objectMother, "user1", User.class

You define generation of an object using a fixture.

And with this part

    add m, "users", users

you define the relation of the instanced fixtures (users) in the variables and the thymeleaf model object ("users").

Note that User class is a groovy class

```
package com.thymemother.controller.model

class User {

    String name
    String type
}
```
and you can and should hierchary class as you need model the info inside html.

- WIP to remove the TODO and clean the DSL, but inside

## How to run it using "external configurations"

- Overwrite:
    - By default in build.gradle define the external directory as classpath
        - runtime files('external/classpath')
    - If you want to use external thymeleaf files
        - -Dspring.thymeleaf.prefix=file:///path/to/directory/thymemother/external/templates/
    - If you want to use external fixture directory
        - -DfixtureDir=filesystem:///path/to/directory/external/fixture

gradle -Dspring.thymeleaf.prefix=file:///path/.../templates/ -DfixtureDir=filesystem:///path/.../fixture bootRun

## NEXT STEPS
  - Add compilation customizer to hide this imports

    import com.thymemother.dsl.RootSpec
    import com.thymemother.controller.model.User

  - How can i put this "outside" the dsl

    def m = binding.getVariable("m")
    def objectMother = binding.getVariable("objectMother")

  - Make another method inside specs, without m and objectMother?

  - Is this necesary inside the dsl?

    def root(Closure cl) {
        def root = new RootSpec()
        def code = cl.rehydrate(root, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }


## TODO:
  - Thymeleaf
    - Upgrade to latest thymeleaf version
    - Generalize parameter url/query string
    - Generalize form parameter
    - And option to render the generate HTML to an external dir

  - How to run it
    - Run gradle daemon with classreload and disable thymeleaf cache
    - Run with fatjar

      java -jar thymemother.jar -Dport=8080
          -Dspring.thymeleaf.prefix=file:///path/to/directory/thymemother/external/templates/
          -DfixtureDir=filesystem:///path/to/directory/external/fixture

  - Beanmother
    - Can instantiate only list?
    - Autoscan and best fit to fixture the yml...
    - Module to rest client

        apiuser: &apiuser
          rest.url: http://rest/api/method?$1
          rest.method: POST
          rest.param: {"param1":"value1","param2":$2}

          name: response.param_name
          type: response.param_type
          other: ${faker.book.title}

  - Faker
      - Review issues:
          - https://github.com/DiUS/java-faker/issues/294
          - https://github.com/DiUS/java-faker/issues/295

  - Review this links
    - https://github.com/josejuanmontiel/adorable-avatar
    - https://github.com/josejuanmontiel/dynamic-rest-template

- Another related things to review
    - Fix maven
        - mvn spring-boot:run

    - DocToolChain + qdox + gradle and later maven
        - ./gradlew -b init.gradle initArc42EN -PnewDocDir=/home/jose/git/thymemother/doc
        - doctoolchain /home/jose/git/thymemother/doc generateHTML
        - doctoolchain /home/jose/git/thymemother/doc generatePDF
    - jqassitance + neo4j
        - mvn jqassistant:server

## Another project for beanMother

When I discover [Beanmother](http://beanmother.io) I thought it could be a good "testing purpose" library..

   ![Beanmother use case](beanmother-use-case.jpg)

to build "object" for junit test, build it from yml... and too, for load the database.​