package at.fhjoanneum.lanfinderkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import at.fhjoanneum.lanfinderkotlin.R
import at.fhjoanneum.lanfinderkotlin.adapters.MainAdapter
import at.fhjoanneum.lanfinderkotlin.models.LanParty
import at.fhjoanneum.lanfinderkotlin.services.MockApiService

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

        /**
         * Update Filter
         * */
        if (intent != null) {
            plz = intent.getStringExtra("plz").toString()
            city = intent.getStringExtra("city").toString()
            maxPlayers = intent.getStringExtra("maxPlayers").toString()
            errorGames = intent.getStringExtra("errorGames").toString()

            val newList = mutableListOf<LanParty>() // Use a mutable list instead of listOf<LanParty>()

            for (lanParty in datasource!!) {
                if (lanParty?.amountMaxPlayers == maxPlayers.toIntOrNull()) {
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