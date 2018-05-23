# thymemother
A "bean mother pattern" for the Thymeleaf templates.

## BeanMother
When I discover [Beanmother](http://beanmother.io) I thought it could be a good "testing purpose" library..

![Beanmother use case](beanmother-use-case.jpg)

to build "object" for junit test, build it from yml... and too, for load the database.​

## The idea
The current project came from my frontend experience...

![Thymemother proposal](thymemother-proposal.jpg)

In my [blog](https://josejuanmontiel.github.io/blog/2016/10/groovy-thymelaf.es.html), few years ago,
I start to think about. Now, the idea it's "extend" beanmother for "build the objects" from an API REST,
and do "spring mvc+thymeleaf+generic controller" + groovy library to put "html+th.xml" for rapid HTML prototyping.

The project could be execute by:

    java -jar thymemother.jar -Dport=8080
        -Dspring.thymeleaf.prefix=file:///path/to/directory/thymemother/external/templates/
        -DfixtureDir=filesystem:///path/to/directory/external/fixture

then you can open in browser http://localhost:8080/template1 and render template1.html+th.xml
or the "groovy version" where bind the model with beanmother objects.

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

Evolving the fixture to indicate the url of the rest and the params, and where attribute of the "beanmother fixture" to bind the response, the project can instantiate this object with the date returned by the rest client...

Using this thymeleaf decouple groovy (.th.xml or .th.groovy):

```
template {
    thlogic {
        attr(sel:'#usersTable', 'th:remove':'all-but-first') {
            attr(sel:'/tr[0]', 'th:each':'user : ${users}') {
                attr(sel:'td.username', 'th:text':'${user.name}')
                attr(sel:'td.usertype', 'th:text':'${user.type}')
            }
        }
    }
}
```

The rest, represent the mappings and the assing to model.
```
root {
    map{
        def map1 = [user1, User.class]
        def map2 = [user2, User.class]
        def map3 = [apiuser, User.class]
    }
    model {
        def users = [user1, user2, apiuser]
    }
```

You define the matching between model class and the fixture.

You define the relation of the instanced fixtures and the thymeleaf model object.

Using all of this, an thymeleaf ![deacopled](https://github.com/thymeleaf/thymeleaf/issues/465) you render the html.

## How to run this project from source code
- Gradle:
    - gradle bootRun
- Maven
    - mvn spring-boot:run
- Overwrite:
    - If you want to use external thymeleaf files
        - -Dspring.thymeleaf.prefix=file:///path/to/directory/thymemother/external/templates/
    - If you want to use external fixture directory
        - -DfixtureDir=filesystem:///path/to/directory/external/fixture

- TODO:
  - Load class model from external directory...

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
    - Ver como usar en DocToolChain la integracion de qdox en gradle y luego maven
        - ./gradlew -b init.gradle initArc42EN -PnewDocDir=/home/jose/git/thymemother/doc
        - doctoolchain /home/jose/git/thymemother/doc generateHTML
        - doctoolchain /home/jose/git/thymemother/doc generatePDF
    - jqassitance, que puede ofrecer con el parseo de clase que se almacena en neo4j
        - mvn jqassistant:server

-Dspring.thymeleaf.prefix=file:///home/jose/git/thymemother/external/templates/ -DfixtureDir=filesystem:///home/jose/git/thymemother/external/fixture
