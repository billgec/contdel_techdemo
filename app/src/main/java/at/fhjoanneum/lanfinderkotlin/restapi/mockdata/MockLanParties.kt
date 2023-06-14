package at.fhjoanneum.lanfinderkotlin.restapi.mockdata

import at.fhjoanneum.lanfinderkotlin.restapi.models.LanParty
import java.util.GregorianCalendar

object MockLanParties {
    var mockLanParties = arrayListOf(
        LanParty(
            "1",
            "Best LAN Party in Graz",
            "8010",
            "Graz",
            GregorianCalendar(2023, 1, 20, 16, 0, 0),
            10,
            HashSet(listOf("Battlefield", "Team Fortress")),
            "This is the best LAN Party in Graz. You should definitely come!",
            MockUsers.mockUsers[0]
        ),
        LanParty(
            "2",
            "Battlefield LAN party",
            "8010",
            "Graz",
            GregorianCalendar(2023, 2, 5, 20, 0, 0),
            4,
            HashSet(listOf("Battlefield")),
            "This is a Battlefield LAN party. You should definitely come!",
            MockUsers.mockUsers[0]
        ),
        LanParty(
            "3",
            "Hand Simulator party",
            "8020",
            "Graz",
            GregorianCalendar(2023, 2, 6, 19, 0, 0),
            15,
            HashSet(listOf("Hand Simulator")),
            "This is a Hand simulator LAN party. You should definitely come!",
            MockUsers.mockUsers[1]
        ),
        LanParty(
            "4",
            "This is the best lan party on Earth. If you have another opinion, I don't care.",
            "8020",
            "Graz",
            GregorianCalendar(2023, 2, 18, 19, 0, 0),
            10,
            HashSet(listOf("Battlefield")),
            "This is a Battlefield LAN party. You should definitely come!",
            MockUsers.mockUsers[2]
        ),
        LanParty(
            "5",
            "League of Legends in Graz",
            "8020",
            "Graz",
            GregorianCalendar(2023, 1, 18, 19, 0, 0),
            8,
            HashSet(listOf("League of Legends")),
            "This is a League of Legends LAN party. You should definitely come!",
            MockUsers.mockUsers[3]
        ),
        LanParty(
            "6",
            "GTFO",
            "8010",
            "Graz",
            GregorianCalendar(2023, 1, 19, 18, 0, 0),
            15,
            HashSet(listOf("GTFO")),
            "This is a GTFO LAN party. You should definitely come!",
            MockUsers.mockUsers[4]
        ),
        LanParty(
            "7",
            "Rogue Company Party",
            "8010",
            "Graz",
            GregorianCalendar(2023, 2, 13, 19, 0, 0),
            5,
            HashSet(listOf("Rogue Company")),
            "This is a Rogue Company LAN party. You should definitely come!",
            MockUsers.mockUsers[4]
        ),
        LanParty(
            "8",
            "World of Warcraft Lan Party",
            "8010",
            "Graz",
            GregorianCalendar(2023, 2, 10, 20, 0, 0),
            10,
            HashSet(listOf("World of Warcraft")),
            "This is a World of Warcraft LAN party. You should definitely come!",
            MockUsers.mockUsers[2]
        ),
        LanParty(
            "9",
            "Brawlhalla",
            "8020",
            "Graz",
            GregorianCalendar(2023, 2, 10, 20, 0, 0),
            10,
            HashSet(listOf("Brawlhalla")),
            "This is a Brawlhalla LAN party. You should definitely come!",
            MockUsers.mockUsers[3]
        ),
        LanParty(
            "10",
            "LAN party for students",
            "8020",
            "Graz",
            GregorianCalendar(2023, 2, 22, 21, 0, 0),
            10,
            HashSet(listOf("Fortnite", "Counter Strike")),
            "This is a LAN party for students. You should definitely come!",
            MockUsers.mockUsers[3]
        ),
        LanParty(
            "11",
            "The LAN party without a name",
            "8020",
            "Graz",
            GregorianCalendar(2023, 3, 1, 19, 0, 0),
            10,
            HashSet(listOf("Fortnite", "Minecraft", "Call of Duty")),
            "This is an anonymous LAN party. You should definitely come!",
            MockUsers.mockUsers[1]
        )
    )
}
