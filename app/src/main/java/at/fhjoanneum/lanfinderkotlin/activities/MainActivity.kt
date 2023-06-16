package at.fhjoanneum.lanfinderkotlin.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import at.fhjoanneum.lanfinderkotlin.R
import at.fhjoanneum.lanfinderkotlin.adapters.MainAdapter
import at.fhjoanneum.lanfinderkotlin.restapi.mockdata.MockLanParties
import at.fhjoanneum.lanfinderkotlin.restapi.mockdata.MockUsers
import at.fhjoanneum.lanfinderkotlin.restapi.models.Filter.Companion.getInstance
import at.fhjoanneum.lanfinderkotlin.restapi.models.Filter.Companion.isFilter
import at.fhjoanneum.lanfinderkotlin.restapi.models.LanParty
import at.fhjoanneum.lanfinderkotlin.restapi.services.LanPartyController
import at.fhjoanneum.lanfinderkotlin.restapi.services.MockApiService
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * This activity shows all LANs the user can sign up for.
 */
class MainActivity : AppCompatActivity() {
    private var datasource: ArrayList<LanParty?>? = null
    lateinit var name: String
    lateinit var plz: String
    lateinit var city: String
    lateinit var maxPlayers: String
    lateinit var errorGames: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * Action Bar settings
         */
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setLogo(R.drawable.ic_logo_actionbar)
            setDisplayUseLogoEnabled(true)
        }
    }

    override fun onStart() {
        super.onStart()
        if (!NetworkUtils.isNetworkConnected(this)) {
            NetworkUtils.openNetworkErrorDialog(this)
        }

        val listView = findViewById<ListView>(R.id.listview_main)
        datasource = MockApiService.lanPartiesWhereCurrentUserIsNotSignedUpYet as ArrayList<LanParty?>

        for (o in datasource!!) {
            if (o != null) {
                Log.d(ContentValues.TAG, "HERE IS A LANPARTY ${o.id}")
            }
        }

        /**
         * Update Filter
         * */
        if (isFilter()) {
            val filter = getInstance()
            val newList = mutableListOf<LanParty>()

            for (lanParty in datasource!!) {
                val matchesFilter = when {
                    filter.amountMaxPlayers != 0 && lanParty?.amountMaxPlayers != filter.amountMaxPlayers -> false
                    filter.city.isNotEmpty() && lanParty?.city != filter.city -> false
                    filter.zipCode.isNotEmpty() && lanParty?.zipCode != filter.zipCode -> false
                    filter.date != null && lanParty?.date != filter.date -> false
                    filter.games.isNotEmpty() && filter.games.firstOrNull() != "Select Game" && !lanParty?.games?.containsAll(filter.games)!! -> false
                    else -> true
                }

                if (matchesFilter) {
                    newList.add(lanParty!!)
                }
            }

            if (newList.isNotEmpty()) {
                datasource = ArrayList(newList)
            }
        }

        val adapter: ArrayAdapter<*> = MainAdapter(this, datasource)
        listView.adapter = adapter
        listView.onItemClickListener = OnItemClickListener { _: AdapterView<*>, _: View?, position: Int, _: Long ->
            val selectedItem = adapter.getItem(position) as LanParty?
            val intent = Intent(applicationContext, Info::class.java)
            val bundle = Bundle()
            bundle.putSerializable("selectedLanParty", selectedItem)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}