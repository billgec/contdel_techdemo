package at.fhjoanneum.lanfinderkotlin.restapi.models

import at.fhjoanneum.lanfinderkotlin.restapi.service.api.model.BaseEntity
import java.io.Serializable

data class User(
    var username: String,
    var email: String
) : BaseEntity() {
    constructor() : this("", "")
}
