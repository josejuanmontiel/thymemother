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
