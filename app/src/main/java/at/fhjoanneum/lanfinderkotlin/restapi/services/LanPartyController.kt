package at.fhjoanneum.lanfinderkotlin.restapi.services

import android.content.ContentValues.TAG
import android.util.Log
import at.fhjoanneum.lanfinderkotlin.restapi.models.AccessLan
import at.fhjoanneum.lanfinderkotlin.restapi.models.LanParty
import at.fhjoanneum.lanfinderkotlin.restapi.models.User
import java.util.GregorianCalendar
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
                        Log.d(TAG, "loading lan... ${convertedLan.id}")
                    }
                }
                Log.d(TAG, "SOMETHING HAPPENED")
                continuation.resume(Unit)
            }
        }
    }


    fun createLan(lan: LanParty){
        val newLan = databaseCompliance(lan)
        accessLan.createLan(newLan)
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

            val players = HashSet<User>()
            for (userId in lanParty.registeredPlayers.split(",").toHashSet()) {
                userController.getUser(userId)?.let { players.add(it) }
            }
            val convertedOrganizer = users.find { user -> user.id == lanParty.organizer }

            //create Lanparty with values
            return LanParty(
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
}
