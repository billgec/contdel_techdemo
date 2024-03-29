package at.fhjoanneum.lanfinderkotlin.restapi.services

import at.fhjoanneum.lanfinderkotlin.restapi.models.AccessUser
import at.fhjoanneum.lanfinderkotlin.restapi.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/*
 * UserController
 * Manages the creation, retrieval, and initialization of user data.
 * May 25, 2023
 */
object UserController {
    val accessUser = AccessUser()
    val users = arrayListOf<User>()
    var currentUser: User = User("no Input", "no Input") // Default value

    // Retrieve a user by ID
    var userId = "00FHLxEP82s9EQaEVplQ" // id in cloud firestore

    suspend fun init() {
        withContext(Dispatchers.IO) {
            suspendCoroutine<Unit> { continuation ->
                accessUser.getUser { userList ->
                    for (user in userList) {
                        users.add(user)
                    }
                    currentUser = users.find { it.id == userId } ?: User("no Input", "no Input")
                    continuation.resume(Unit)
                }
            }
        }
    }

    fun getCurrentUser(email:String): User {
        for (user in users)
            if (user.email == email) {
                currentUser = user
            }
        return currentUser
    }

    fun getUser(userId: String): User? {
        return users.find { it.id == userId }
    }

    fun createUser(user: User) {
        accessUser.createUser(user)
    }
}
