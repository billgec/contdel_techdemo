package at.fhjoanneum.lanfinderkotlin.restapi.services

import android.content.ContentValues.TAG
import android.util.Log
import at.fhjoanneum.lanfinderkotlin.restapi.mockdata.MockLanParties
import at.fhjoanneum.lanfinderkotlin.restapi.models.LanParty
import at.fhjoanneum.lanfinderkotlin.restapi.models.User

object ApiService {
    val lanPartyController = LanPartyController
    val currentUser: User
        get() = UserController.currentUser

    val lanParties: ArrayList<LanParty>
        get() = lanPartyController.lanPartyList


    // Use a suspend function to populate lanParties asynchronously
    suspend fun initializeLanParties() {
        // Call the init function to populate lanPartyList
        lanPartyController.init()

    }

    val lanPartiesForCurrentUser: List<LanParty?>
        get() {
            val allLanParties = lanParties
            val currentUser = currentUser
            return allLanParties.filter { lan ->
                lan.registeredPlayers?.any { player -> player?.compareTo(currentUser) == 0 } == true
            }
        }

    val lanPartiesWhereCurrentUserIsNotSignedUpYet: List<LanParty?>
        get() {
            val allLanParties = lanParties
            val currentUser = currentUser
            return if (currentUser != null) {
                allLanParties.filter { lan ->!lan.registeredPlayers?.any { player -> player?.compareTo(currentUser) == 0 }!!
                }
            } else {
                listOf()
            }
        }


    fun createLan(lanParty: LanParty): Boolean {
        //createLan() in Access Logic
        LanPartyController.createLan(lanParty)
        return true
    }

    fun addUserToLanParty(user: User?, lanParty: LanParty): Boolean {
        try {
            Log.e(TAG, "APISERVICE    ${lanParty.registeredPlayers?.first()?.id}")
            Log.e(TAG, "APISERVICE    ${lanParty.registeredPlayers?.last()?.id}")
            lanPartyController.updateLan(lanParty)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun removeUserFromLanParty(user: User?, lanParty: LanParty) :Boolean {
        try {
            lanParty.registeredPlayers?.remove(user)
            LanPartyController.updateLan(lanParty)
            return true
        } catch (e:Exception) {
            return false
        }
    }

    fun deleteLanParty(lanParty: LanParty) : Boolean{
        try {
            LanPartyController.deleteLan(lanParty.id)
            return true
        } catch (e:Exception){
            return false
        }
    }

}
