package at.fhjoanneum.lanfinderkotlin.mockdata

import at.fhjoanneum.lanfinderkotlin.models.User
import java.util.Arrays

object MockUsers {
    var mockUsers = ArrayList(
        listOf(
            User(1, "isi1903", "juergen.guoekdemir@gmail.com"),
            User(2, "billgec", "juergen.guoekdemir@gmail.com"),
            User(3, "kathi123", "juergen.guoekdemir@gmail.com"),
            User(4, "jan_the_gamer", "juergen.guoekdemir@gmail.com"),
            User(5, "tommy_99", "juergen.guoekdemir@gmail.com"),
            User(6, "jurgengoekdemir", "juergen.guoekdemir@gmail.com")
        )
    )
}