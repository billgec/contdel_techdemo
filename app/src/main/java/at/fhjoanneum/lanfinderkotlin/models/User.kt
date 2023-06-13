package at.fhjoanneum.lanfinderkotlin.models

import java.io.Serializable

class User : Serializable {
    var id = ""
    var username: String
    var email: String

    constructor(id: String, username: String, email: String) {
        this.id = id
        this.username = username
        this.email = email
    }
}