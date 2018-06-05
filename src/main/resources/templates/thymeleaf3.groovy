
import com.thymemother.dsl.Builder

Builder.bindModel binding, 'm', {

    def user1 = fixtureInstance('user1', com.thymemother.controller.model.User)
    def user2 = fixtureInstance('user2', com.thymemother.controller.model.User )

    add 'users', [user1,user2]
}