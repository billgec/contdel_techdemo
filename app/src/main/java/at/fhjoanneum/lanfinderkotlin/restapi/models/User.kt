package at.fhjoanneum.lanfinderkotlin.restapi.models

import at.fhjoanneum.lanfinderkotlin.restapi.service.api.model.BaseEntity

/*
* Model
* Provides methods for creating, retrieving, updating, and deleting user data from Firestore.
* May 25, 2023
*/
data class User(
    var username: String,
    var email: String
) : BaseEntity() {
    constructor() : this("", "")
}
