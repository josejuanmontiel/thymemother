import com.thymemother.dsl.RootSpec
import com.thymemother.dsl.Root
// Import using package inside classpath directory...
import com.thymemother.controller.model.ExtUser

Root.root binding, {
    def user1 = map {
        fixture "user1", ExtUser.class
    }

    def user2 = map {
        fixture "user2", ExtUser.class
    }

    def users = [user1, user2]

    model {
        add "users", users
    }
}
