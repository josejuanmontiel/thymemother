import com.thymemother.dsl.RootSpec
// Import using package inside classpath directory...
import com.thymemother.controller.model.ExtUser

def m = binding.getVariable("m")
def objectMother = binding.getVariable("objectMother")

def root(Closure cl) {
    def root = new RootSpec()
    def code = cl.rehydrate(root, this, this)
    code.resolveStrategy = Closure.DELEGATE_ONLY
    code()
}

root {
    def user1 = map {
        fixture objectMother, "user1", ExtUser.class
    }
    def user2 = map {
        fixture objectMother, "user2", ExtUser.class
    }

    def users = [user1, user2]

    model {
        add m, "users", users
    }
}
