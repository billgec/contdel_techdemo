package at.fhjoanneum.lanfinderkotlin.service

import at.fhjoanneum.lanfinderkotlin.restapi.api.model.LanParty
import com.google.firebase.database.DatabaseReference
import java.util.*

class LanPartyService {

    private lateinit var dbRef: DatabaseReference

    private val lanPartyList: MutableList<LanParty>

    init {
        lanPartyList = ArrayList()

        val party1 = LanParty(
                "1",
                "Best LAN Party in Graz",
                "8010",
                "Graz",
                GregorianCalendar(2023, 1, 20, 16, 0, 0),
                10,
                "This is the best LAN Party in Graz. You should definitely come!",
                0,
                listOf("Battlefield", "Team Fortress"),
        )

        val party2 = LanParty(
                "2",
                "Battlefield LAN party",
                "8010",
                "Graz",
                GregorianCalendar(2023, 2, 5, 20, 0, 0),
                4,
                "This is a Battlefield LAN party. You should definitely come!",
                1,
                listOf("Battlefield"),
                listOf()
        )
        val party3 = LanParty(
                "3",
                "Hand Simulator party",
                "8020",
                "Graz",
                GregorianCalendar(2023, 2, 6, 19, 0, 0),
                15,
                "This is a Hand simulator LAN party. You should definitely come!",
                2,
                listOf("Hand Simulator"),
                listOf()
        )

        val party4 = LanParty(
                "4",
                "This is the best lan party on Earth. If you have another opinion, I don't care.",
                "8020",
                "Graz",
                GregorianCalendar(2023, 2, 18, 19, 0, 0),
                10,
                "This is a Battlefield LAN party. You should definitely come!",
                3,
                listOf("Battlefield"),
                listOf()
        )

        val party5 = LanParty(
                "5",
                "League of Legends in Graz",
                "8020",
                "Graz",
                GregorianCalendar(2023, 1, 18, 19, 0, 0),
                8,
                "This is a League of Legends LAN party. You should definitely come!",
                4,
                listOf("League of Legends"),
                listOf()
        )

        val party6 = LanParty(
                "6",
                "GTFO",
                "8010",
                "Graz",
                GregorianCalendar(2023, 1, 19, 18, 0, 0),
                15,
                "This is a GTFO LAN party. You should definitely come!",
                5,
                listOf("GTFO"),
                listOf()
        )

        val party7 = LanParty(
                "7",
                "Rogue Company Party",
                "8010",
                "Graz",
                GregorianCalendar(2023, 2, 13, 19, 0, 0),
                5,
                "This is a Rogue Company LAN party. You should definitely come!",
                6,
                listOf("Rogue Company"),
                listOf()
        )

        val party8 = LanParty(
                "8",
                "World of Warcraft Lan Party",
                "8010",
                "Graz",
                GregorianCalendar(2023, 2, 10, 20, 0, 0),
                10,
                "This is a World of Warcraft LAN party. You should definitely come!",
                7,
                listOf("World of Warcraft"),
                listOf()
        )

        val party9 = LanParty(
                "9",
                "Brawlhalla",
                "8020",
                "Graz",
                GregorianCalendar(2023, 2, 10, 20, 0, 0),
                10,
                "This is a Brawlhalla LAN party. You should definitely come!",
                8,
                listOf("Brawlhalla"),
                listOf()
        )

        val party10 = LanParty(
                "10",
                "LAN party for students",
                "8020",
                "Graz",
                GregorianCalendar(2023, 2, 22, 21, 0, 0),
                10,
                "This is a LAN party for students. You should definitely come!",
                9,
                listOf("Fortnite", "Counter Strike"),
                listOf()
        )

        val party11 = LanParty(
                "11",
                "The LAN party without a name",
                "8020",
                "Graz",
                GregorianCalendar(2023, 3, 1, 19, 0, 0),
                10,
                "This is an anonymous LAN party. You should definitely come!",
                10,
                listOf("Fortnite", "Minecraft", "Call of Duty"),
                listOf()
        )

        lanPartyList.addAll(listOf(party1, party2, party3, party4, party5, party6, party7, party8, party9, party10,party11))
    }

    fun getLanParty(id: String): Optional<LanParty> {
        for (LanParty in lanPartyList) {
            if (id.equals(LanParty.id)) {
                return Optional.of(LanParty)
            }
        }
        return Optional.empty()
    }
}
