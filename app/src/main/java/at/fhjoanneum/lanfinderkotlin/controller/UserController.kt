package at.fhjoanneum.lanfinderkotlin.controller

import at.fhjoanneum.lanfinderkotlin.api.model.User
import at.fhjoanneum.lanfinderkotlin.service.UserService
import java.util.*

class UserController constructor(private val userService: UserService) {

    fun getUser(id: String): User? {
        val user: Optional<User> = userService.getUser(id)
        return if (user.isPresent) user.get() else null
    }
}
