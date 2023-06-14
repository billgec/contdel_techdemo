package at.fhjoanneum.lanfinderkotlin.restapi.models

import at.fhjoanneum.lanfinderkotlin.restapi.service.api.model.BaseEntity
import java.io.Serializable

class User : Serializable, BaseEntity {
    var username: String
    var email: String

    constructor(id: String, username: String, email: String) {
        this.id = id
        this.username = username
        this.email = email
    }
}