package at.fhjoanneum.lanfinderkotlin.restapi.models

import java.util.GregorianCalendar

/*
* Filter
* Represents a filter for searching LAN parties.
* May 25, 2023
*/
class Filter {
    lateinit var name: String
    lateinit var zipCode: String
    lateinit var city: String
    var date: GregorianCalendar? = null
    var amountMaxPlayers = 0
    lateinit var games: HashSet<String>

    companion object {
        private var instance: Filter? = null

        fun getInstance(): Filter {
            if (instance == null) {
                instance = Filter()
            }
            return instance as Filter
        }

        fun deleteFilter() {
            instance = null
        }

        fun isFilter(): Boolean {
            return instance != null
        }
    }
}
