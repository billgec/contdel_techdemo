package at.fhjoanneum.lanfinderkotlin.mockdata

import at.fhjoanneum.lanfinderkotlin.models.LanParty
import java.util.Arrays
import java.util.GregorianCalendar

object MockLanParties {
    var mockLanParties = ArrayList(
        Arrays.asList(
            LanParty(
                1,
                "Best LAN Party in Graz",
                "8010",
                "Graz",
                GregorianCalendar(2023, 1, 20, 16, 0, 0),
                10,
                HashSet(Arrays.asList("Battlefield", "Team Fortress")),
                "This is the best LAN Party in Graz. You should definitely come!",
                MockUsers.mockUsers[0]
            ),
            LanParty(
                2,
                "Battlefield LAN party",
                "8010",
                "Graz",
                GregorianCalendar(2023, 2, 5, 20, 0, 0),
                4,
                HashSet(Arrays.asList("Battlefield")),
                "This is a Battlefield LAN party. You should definitely come!",
                MockUsers.mockUsers[0]
            ),
            LanParty(
                3,
                "Hand Simulator party",
                "8020",
                "Graz",
                GregorianCalendar(2023, 2, 6, 19, 0, 0),
                15,
                HashSet(Arrays.asList("Hand Simulator")),
                "This is a Hand simulator LAN party. You should definitely come!",
                MockUsers.mockUsers[1]
            ),
            LanParty(
                4,
                "This is the best lan party on Earth. If you have another opinion, I don't care.",
                "8020",
                "Graz",
                GregorianCalendar(2023, 2, 18, 19, 0, 0),
                10,
                HashSet(Arrays.asList("Battlefield")),
                "This is a Battlefield LAN party. You should definitely come!",
                MockUsers.mockUsers[2]
            ),
            LanParty(
                5,
                "League of Legends in Graz",
                "8020",
                "Graz",
                GregorianCalendar(2023, 1, 18, 19, 0, 0),
                8,
                HashSet(Arrays.asList("League of Legends")),
                "This is a League of Legends LAN party. You should definitely come!",
                MockUsers.mockUsers[3]
            ),
            LanParty(
                6,
                "GTFO",
                "8010",
                "Graz",
                GregorianCalendar(2023, 1, 19, 18, 0, 0),
                15,
                HashSet(Arrays.asList("GTFO")),
                "This is a GTFO LAN party. You should definitely come!",
                MockUsers.mockUsers[4]
            ),
            LanParty(
                7,
                "Rogue Company Party",
                "8010",
                "Graz",
                GregorianCalendar(2023, 2, 13, 19, 0, 0),
                5,
                HashSet(Arrays.asList("Rogue Company")),
                "This is a Rogue Company LAN party. You should definitely come!",
                MockUsers.mockUsers[4]
            ),
            LanParty(
                8,
                "World of Warcraft Lan Party",
                "8010",
                "Graz",
                GregorianCalendar(2023, 2, 10, 20, 0, 0),
                10,
                HashSet(Arrays.asList("World of Warcraft")),
                "This is a World of Warcraft LAN party. You should definitely come!",
                MockUsers.mockUsers[2]
            ),
            LanParty(
                9,
                "Brawlhalla",
                "8020",
                "Graz",
                GregorianCalendar(2023, 2, 10, 20, 0, 0),
                10,
                HashSet(Arrays.asList("Brawlhalla")),
                "This is a Brawlhalla LAN party. You should definitely come!",
                MockUsers.mockUsers[3]
            ),
            LanParty(
                10,
                "LAN party for students",
                "8020",
                "Graz",
                GregorianCalendar(2023, 2, 22, 21, 0, 0),
                10,
                HashSet(Arrays.asList("Fortnite", "Counter Strike")),
                "This is a LAN party for students. You should definitely come!",
                MockUsers.mockUsers[3]
            ),
            LanParty(
                11,
                "The LAN party without a name",
                "8020",
                "Graz",
                GregorianCalendar(2023, 3, 1, 19, 0, 0),
                10,
                HashSet(Arrays.asList("Fortnite", "Minecraft", "Call of Duty")),
                "This is an anonymous LAN party. You should definitely come!",
                MockUsers.mockUsers[1]
            )
        )
    )
}