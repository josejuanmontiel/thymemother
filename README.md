# thymemother
A "bean mother pattern" for the Thymeleaf templates.

## BeanMother
When I discover [Beanmother](http://beanmother.io) I thought it could be a good "testing purpose" library..

![Beanmother use case](beanmother-use-case.jpg)

to build "object" for junit test, build it from yml... and too, for load the database.​

This will be another project, but now...

## The idea
The current project came from my frontend experience...

![Thymemother proposal](thymemother-proposal.jpg)

In my [blog](https://josejuanmontiel.github.io/blog/2016/10/groovy-thymelaf.es.html), few years ago,
I start to think about. Now, the idea it's "extend" beanmother for "build the objects" from an API REST,
and do "spring mvc+thymeleaf+generic controller" + groovy library to put "html+th.xml" for rapid HTML prototyping.

## How to run it

Clone the project and: gradle bootRun

### TODO

- Arrancar gradle en modo daemon con autorefresco de clases y thymeleaf
- Version maven
- Arrancar como libreria java

    java -jar thymemother.jar -Dport=8080
        -Dspring.thymeleaf.prefix=file:///path/to/directory/thymemother/external/templates/
        -DfixtureDir=filesystem:///path/to/directory/external/fixture

## How to use it

Then you can open in browser http://localhost:8080/thymeleaf3 and render src/main/resources/templates/thymeleaf3.html
using ![deacopled](https://github.com/thymeleaf/thymeleaf/issues/465) with src/main/resources/templates/thymeleaf3.th.groovy
or if doesn't exist .th.groovy file the src/main/resources/templates/thymeleaf3.th.xml version, and witch binding apply?

## Evolution of BeanMother 
You could think how to make a dinamic rest client in a new module of beanmother where witha fixture like this

```YAML
user1: &user1
  name: user1
  type: type1

user2: &user2
  name: user2
  type: type2

apiuser: &apiuser
  rest.url: http://rest/api/method?$1
  rest.method: POST
  rest.param: {"param1":"value1","param2":$2}
  
  name: response.param_name
  type: response.param_type
  other: ${faker.book.title}
```

Evolving the fixture to indicate the url of the rest and the params, and where attribute of the "beanmother fixture" to bind the response,
the project can instantiate this object with the date returned by the rest client...

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

- WIP to remove the TODO and clean the DSL, but inside:
- You define the matching between model class and the fixture.
- You define the relation of the instanced fixtures and the thymeleaf model object.

## How to run it using "external configurations"

- Overwrite:
    - By default in build.gradle define the external directory as classpath
        - runtime files('external/classpath')
    - If you want to use external thymeleaf files
        - -Dspring.thymeleaf.prefix=file:///path/to/directory/thymemother/external/templates/
    - If you want to use external fixture directory
        - -DfixtureDir=filesystem:///path/to/directory/external/fixture

gradle -Dspring.thymeleaf.prefix=file:///path/.../templates/ -DfixtureDir=filesystem:///path/.../fixture bootRun

## How to run this project from source code
- Maven
    - mvn spring-boot:run

- TODO:
  - Beanmother
    - Can instantiate only list?
    - Autoscan and best fit to fixture the yml...

  - Faker
      - Review issues:
          - https://github.com/DiUS/java-faker/issues/294
          - https://github.com/DiUS/java-faker/issues/295

  - Review this links
    - https://github.com/josejuanmontiel/adorable-avatar
    - https://github.com/josejuanmontiel/dynamic-rest-template

- WIP
    - Pendiente:
        - Añadir a los "compilation customizer" los necesario para "ocultar"

            import com.thymemother.dsl.RootSpec
            import com.thymemother.controller.model.User

            def m = binding.getVariable("m")
            def objectMother = binding.getVariable("objectMother")

            def root(Closure cl) {
                def root = new RootSpec()
                def code = cl.rehydrate(root, this, this)
                code.resolveStrategy = Closure.DELEGATE_ONLY
                code()
            }
        - Hacer dentro de los specs un metodo sin: m y objectMother para que vayan por "dentro"
    - Ver como usar en DocToolChain la integracion de qdox en gradle y luego maven
        - ./gradlew -b init.gradle initArc42EN -PnewDocDir=/home/jose/git/thymemother/doc
        - doctoolchain /home/jose/git/thymemother/doc generateHTML
        - doctoolchain /home/jose/git/thymemother/doc generatePDF
    - jqassitance, que puede ofrecer con el parseo de clase que se almacena en neo4j
        - mvn jqassistant:server