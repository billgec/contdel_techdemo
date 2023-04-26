package at.fhjoanneum.lanfinderkotlin.models

import java.io.Serializable

class User : Serializable {
    var id = 0
    var username: String
    var email: String

    constructor(id: Int, username: String, email: String) {
        this.id = id
        this.username = username
        this.email = email
    }
}