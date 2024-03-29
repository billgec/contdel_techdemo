package at.fhjoanneum.lanfinderkotlin.restapi.services

import android.content.ContentValues.TAG
import android.util.Log
import at.fhjoanneum.lanfinderkotlin.restapi.models.AccessLan
import at.fhjoanneum.lanfinderkotlin.restapi.models.LanParty
import at.fhjoanneum.lanfinderkotlin.restapi.models.User
import java.util.GregorianCalendar
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/*
 * LanPartyController
 * Manages the creation, deletion, and retrieval of LAN parties.
 * May 25, 2023
 */
object LanPartyController {
    private val accessLan = AccessLan()
    private val userController = UserController
    var lanPartyList = arrayListOf<LanParty>()

    val users: ArrayList<User>
        get() = userController.users

    suspend fun init() {
        userController.init()
        suspendCoroutine<Unit> { continuation ->
            accessLan.getLan { lanList ->
                lanPartyList = arrayListOf<LanParty>()
                for (lan in lanList) {
                    val convertedLan = applicationCompliance(lan)
                    if (convertedLan != null) {
                        lanPartyList.add(convertedLan)
                    }
                }
                continuation.resume(Unit)
            }
        }
    }


    fun createLan(lan: LanParty){
        val newLan = databaseCompliance(lan)
        accessLan.createLan(newLan)
    }

    fun deleteLan(id :String) {
        accessLan.deleteLan(id)
    }

    private fun applicationCompliance(lanParty: AccessLan.LanParty): LanParty? {
        try {
            //parse values
            val date = if (!lanParty.date.isNullOrEmpty()) {
                val gregorianCalendar = GregorianCalendar()
                gregorianCalendar.timeInMillis = lanParty.date.toLong()
                gregorianCalendar
            } else {
                null
            }

            val games = lanParty.games.split(",").toHashSet()

            val players = HashSet<User?>()
            for (playerId in lanParty.registeredPlayers.split(",").toHashSet()) {
                users.find { user -> user.id == playerId }?.let { players.add(it) }
            }
            val convertedOrganizer = users.find { user -> user.id == lanParty.organizer }

            //create Lanparty with values
            var lanparty = LanParty(
                lanParty.id,
                lanParty.name,
                lanParty.zipCode,
                lanParty.city,
                date,
                lanParty.amountMaxPlayers,
                games,
                lanParty.description,
                convertedOrganizer
            )
            lanparty.registeredPlayers = players;
            return lanparty
        } catch (e: Exception) {
            Log.e(TAG, "Error converting LanParty: ${e.message}")
        }
        return null
    }

    private fun databaseCompliance(lanParty: LanParty): AccessLan.LanParty {
        val registeredPlayers = lanParty.registeredPlayers?.map {
                user -> user?.id ?: ""
        }?.toHashSet()
            ?.joinToString(",")
            ?: ""

        var games = lanParty.games?.joinToString(",") ?: ""

        return AccessLan.LanParty(
            lanParty.name,
            lanParty.zipCode,
            lanParty.city,
            lanParty.date?.timeInMillis?.toString() ?: "",
            lanParty.amountMaxPlayers,
            registeredPlayers,
            games,
            lanParty.description,
            lanParty.organizer?.id ?: ""
        )
    }

    fun updateLan(updatedLan: LanParty) {
        accessLan.updateLan(updatedLan.id, databaseCompliance(updatedLan))
    }
}
