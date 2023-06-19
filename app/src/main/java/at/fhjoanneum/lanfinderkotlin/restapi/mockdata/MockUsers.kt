package at.fhjoanneum.lanfinderkotlin.restapi.mockdata

import at.fhjoanneum.lanfinderkotlin.restapi.models.User
import at.fhjoanneum.lanfinderkotlin.restapi.services.UserController

object MockUsers {
    val usercontroller = UserController
    var mockUsers = ArrayList(
        listOf(
            User("isi1903", "juergen.guoekdemir@gmail.com"),
            User("billgec", "juergen.guoekdemir@gmail.com"),
            User("kathi123", "juergen.guoekdemir@gmail.com"),
            User("jan_the_gamer", "juergen.guoekdemir@gmail.com"),
            User("tommy_99", "juergen.guoekdemir@gmail.com"),
            User("jurgengoekdemir", "juergen.guoekdemir@gmail.com")
        )
    )

    fun pushMockdata() {
        for (user in mockUsers) {
            usercontroller.createUser(user)
        }
    }
}