package at.fhjoanneum.lanfinderkotlin.restapi.services

import android.content.ContentValues.TAG
import android.util.Log
import at.fhjoanneum.lanfinderkotlin.restapi.models.AccessUser
import at.fhjoanneum.lanfinderkotlin.restapi.models.User

object UserController {
    val accessUser = AccessUser()
    val users = mutableListOf<User>()
    var currentUser: User = User("no Input", "no Input") // Default value

    // Retrieve a user by ID
    val userId = "00FHLxEP82s9EQaEVplQ" // id in cloud firestore

    init {
        loadUsers()
    }

    fun getUser(userId: String): User? {
        return users.find { it.id == userId }
    }

    fun getUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    fun createUser(user: User) {
        accessUser.createUser(user)
    }

    fun loadUsers() {
        accessUser.getUser { userList ->
            // Iterate through the userList and process the users
            for (user in userList) {
                Log.d(TAG, "loading user... ${user.id} => ${user.username}")
                users.add(user)
            }

            currentUser = users.find { it.id == userId } ?: User("no Input", "no Input")
            Log.d(TAG, "ATTENTION ${currentUser.id} => ${currentUser.username}")
        }
    }
}
