package at.fhjoanneum.lanfinderkotlin.restapi.services

import at.fhjoanneum.lanfinderkotlin.restapi.mockdata.MockLanParties
import at.fhjoanneum.lanfinderkotlin.restapi.mockdata.MockUsers
import at.fhjoanneum.lanfinderkotlin.restapi.models.AccessUser
import at.fhjoanneum.lanfinderkotlin.restapi.models.LanParty
import at.fhjoanneum.lanfinderkotlin.restapi.models.User

object MockApiService {
    val lanPartyController = LanPartyController
    val currentUser: User = UserController.currentUser as User
    val lanParties: ArrayList<LanParty>
        get() = lanPartyController.lanPartyList


    // Use a suspend function to populate lanParties asynchronously
    suspend fun initializeLanParties() {
        // Call the init function to populate lanPartyList
        lanPartyController.init()

    }

    fun createLanParty(lanParty: LanParty): Boolean {
        //createLan() in Access Logic
        MockLanParties.mockLanParties.add(lanParty)
        return true
    }

    fun addUserToLanParty(user: User?, lanPartyId: String) {
        val mockLanParty = MockLanParties.mockLanParties.find { it?.id == lanPartyId }
        val currentRegisteredPlayers = mockLanParty?.registeredPlayers
        currentRegisteredPlayers?.add(user)
        mockLanParty?.registeredPlayers = currentRegisteredPlayers
    }

    val lanPartiesForCurrentUser: List<LanParty?>
        get() {
            val allLanParties = lanParties
            val currentUser = currentUser
            return allLanParties.filter { it?.registeredPlayers?.contains(currentUser) == true }
        }

    val lanPartiesWhereCurrentUserIsNotSignedUpYet: List<LanParty?>
        get() {
            val allLanParties = lanParties
            val currentUser = currentUser
            return if (currentUser != null) {
                allLanParties.filter { !it?.registeredPlayers?.contains(currentUser)!! }
            } else {
                listOf()
            }
        }

    fun removeUserFromLanParty(user: User?, selectedLanPartyId: String) {
        val mockLanParty = MockLanParties.mockLanParties.find { it?.id == selectedLanPartyId }
        val currentRegisteredPlayers = mockLanParty?.registeredPlayers
        currentRegisteredPlayers?.remove(user)
        mockLanParty?.registeredPlayers = currentRegisteredPlayers
    }

    fun deleteLanParty(lanPartyId: String) {
        val mockLanParty = MockLanParties.mockLanParties.find { it?.id == lanPartyId }
        MockLanParties.mockLanParties.remove(mockLanParty)
    }
}
