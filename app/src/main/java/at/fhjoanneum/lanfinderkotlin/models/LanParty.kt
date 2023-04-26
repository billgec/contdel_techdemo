package at.fhjoanneum.lanfinderkotlin.models

import java.io.Serializable
import java.util.Arrays
import java.util.GregorianCalendar

class LanParty : Serializable {
    var id = 0
    var name: String
    var zipCode: String
    var city: String
    var date: GregorianCalendar?
    var amountMaxPlayers: Int
    var registeredPlayers: HashSet<User?>?
    var games: HashSet<String>
    var description: String
    var organizer: User?

    constructor(
        name: String,
        zipCode: String,
        city: String,
        date: GregorianCalendar?,
        amountMaxPlayers: Int,
        games: HashSet<String>,
        description: String,
        organizer: User?
    ) {
        this.name = name
        this.zipCode = zipCode
        this.city = city
        this.date = date
        this.amountMaxPlayers = amountMaxPlayers
        registeredPlayers = HashSet(Arrays.asList(organizer))
        this.games = games
        this.description = description
        this.organizer = organizer
    }

    constructor(
        id: Int, name: String, zipCode: String, city: String, date: GregorianCalendar?,
        amountMaxPlayers: Int, games: HashSet<String>, description: String, organizer: User?
    ) {
        this.id = id
        this.name = name
        this.zipCode = zipCode
        this.city = city
        this.date = date
        this.amountMaxPlayers = amountMaxPlayers
        registeredPlayers = HashSet(Arrays.asList(organizer))
        this.games = games
        this.description = description
        this.organizer = organizer
    }
}