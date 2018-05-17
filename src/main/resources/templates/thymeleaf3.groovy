thlogic {
    attr(sel:'#usersTable', 'th:remove':'all-but-first') {
        attr(sel:'/tr[0]', 'th:each':'user : ${users}') {
            attr(sel:'td.username', 'th:text':'${user.name}')
            attr(sel:'td.usertype', 'th:text':'${user.type}')
        }
    }
}

