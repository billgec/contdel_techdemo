package at.fhjoanneum.lanfinderkotlin.services

import at.fhjoanneum.lanfinderkotlin.mockdata.MockLanParties
import at.fhjoanneum.lanfinderkotlin.mockdata.MockUsers
import at.fhjoanneum.lanfinderkotlin.models.LanParty
import at.fhjoanneum.lanfinderkotlin.models.User
import java.util.stream.Collectors

object MockApiService {
    fun createUser(user: User): Boolean {
        val filteredUser = MockUsers.mockUsers.stream().filter { element: User? ->
            user.username ==
                    element!!.username
        }.findAny().orElse(null)
        return if (filteredUser == null) {
            user.id = MockUsers.mockUsers.size + 1
            MockUsers.mockUsers.add(user)
            true
        } else {
            false
        }
    }

    fun createLanParty(lanParty: LanParty): Boolean {
        lanParty.id = (MockLanParties.mockLanParties.size + 1).toString()
        MockLanParties.mockLanParties.add(lanParty)
        return true
    }

    fun addUserToLanParty(user: User?, lanPartyId: String) {
        val mockLanParty = MockLanParties.mockLanParties.stream()
            .filter { x: LanParty? -> x!!.id == lanPartyId }
            .collect(Collectors.toList())[0]
        val currentRegisteredPlayers = mockLanParty.registeredPlayers
        currentRegisteredPlayers!!.add(user)
        mockLanParty.registeredPlayers = currentRegisteredPlayers
    }

    val lanParties: ArrayList<LanParty?>?
        get() = MockLanParties.mockLanParties
    val lanPartiesForCurrentUser: Any?
        get() {
            val allLanParties = lanParties
            val currentUser = currentUser
            return ArrayList<Any?>(
                allLanParties!!.stream()
                    .filter { x: LanParty? -> x!!.registeredPlayers!!.contains(currentUser) }
                    .collect(Collectors.toList()))
        }
    val lanPartiesWhereCurrentUserIsNotSignedUpYet: Any?
        get() {
            val allLanParties = lanParties
            val currentUser = currentUser
            return ArrayList<Any?>(
                allLanParties!!.stream()
                    .filter { x: LanParty? -> !x!!.registeredPlayers!!.contains(currentUser) }
                    .collect(Collectors.toList()))
        }
    val currentUser: User?
        get() = MockUsers.mockUsers[5]

    fun removeUserFromLanParty(user: User?, selectedLanPartyId: String) {
        val mockLanParty = MockLanParties.mockLanParties.stream()
            .filter { x: LanParty? -> x!!.id == selectedLanPartyId }
            .collect(Collectors.toList())[0]
        val currentRegisteredPlayers = mockLanParty.registeredPlayers
        currentRegisteredPlayers!!.remove(user)
        mockLanParty.registeredPlayers = currentRegisteredPlayers
    }

    fun deleteLanParty(lanPartyId: String) {
        val mockLanParty = MockLanParties.mockLanParties.stream()
            .filter { x: LanParty? -> x!!.id == lanPartyId }
            .collect(Collectors.toList())[0]
        MockLanParties.mockLanParties.remove(mockLanParty)
    }
}