package at.fhjoanneum.lanfinderkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import at.fhjoanneum.lanfinderkotlin.R
import at.fhjoanneum.lanfinderkotlin.adapters.MainAdapter
import at.fhjoanneum.lanfinderkotlin.restapi.models.Filter.Companion.getInstance
import at.fhjoanneum.lanfinderkotlin.restapi.models.Filter.Companion.isFilter
import at.fhjoanneum.lanfinderkotlin.restapi.models.LanParty
import at.fhjoanneum.lanfinderkotlin.restapi.services.ApiService
import at.fhjoanneum.lanfinderkotlin.restapi.services.UserController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 * MainActivity
 * Activity to show all LAN parties the user can sign up for.
 * May 25, 2023
 */
class MainActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Default) {
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

        lifecycleScope.launch{
            UserController.init()
            var email = intent.extras?.getString("user_info")
            if (email != null) {
                UserController.userId = UserController.getCurrentUser(email).id
                UserController.init()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!NetworkUtils.isNetworkConnected(this)) {
            NetworkUtils.openNetworkErrorDialog(this)
        }

        val listView = findViewById<ListView>(R.id.listview_main)

        launch {
            ApiService.initializeLanParties()
            datasource = ApiService.lanPartiesWhereCurrentUserIsNotSignedUpYet as ArrayList<LanParty?>

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
                        filter.games.isNotEmpty() && filter.games.firstOrNull() != getString(R.string.select_game) && !lanParty?.games?.containsAll(filter.games)!! -> false
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

            launch(Dispatchers.Main) {
                val adapter: ArrayAdapter<*> = MainAdapter(this@MainActivity, datasource)
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
    }
}