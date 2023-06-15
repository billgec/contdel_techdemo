package at.fhjoanneum.lanfinderkotlin.restapi.services

import android.content.ContentValues.TAG
import android.util.Log
import at.fhjoanneum.lanfinderkotlin.restapi.models.AccessUser
import at.fhjoanneum.lanfinderkotlin.restapi.models.User

object UserController {

    val accessUser = AccessUser()

    var currentUser: User? = User("isi1903", "juergen.guoekdemir@gmail.com")

    // Retrieve a user by ID
    val userId = "3dvGtdSd1e48UqvgGrdo" // id in cloud firestore

    init {
        accessUser.getUser(userId) { user ->
            if (user != null) {
                currentUser = user // current user assigned here
                Log.d(TAG, "Retrieved user: ${user.username}, ${user.email}")
            } else {
                Log.d(TAG, "User not found")
            }
        }
    }
}
