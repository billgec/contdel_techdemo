package at.fhjoanneum.lanfinderkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import at.fhjoanneum.lanfinderkotlin.R
import at.fhjoanneum.lanfinderkotlin.adapters.MyLansAdapter
import at.fhjoanneum.lanfinderkotlin.restapi.models.LanParty
import at.fhjoanneum.lanfinderkotlin.restapi.services.ApiService

/*
 * MyLansActivity
 * Activity to show all LAN parties the user is a member of.
 * May 25, 2023
 */
class MyLans : AppCompatActivity() {
    var datasource: ArrayList<LanParty?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_lans)

        /*
         * Action Bar settings (set logo to action bar and back button)
         */
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.my_lans)
            setDisplayUseLogoEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

    }

    override fun onStart() {
        super.onStart()
        if (!NetworkUtils.isNetworkConnected(this)) {
            NetworkUtils.openNetworkErrorDialog(this)
        }
        val listView = findViewById<ListView>(R.id.listview_myLans)
        datasource = ApiService.lanPartiesForCurrentUser as ArrayList<LanParty?>
        val adapter: ArrayAdapter<*> = MyLansAdapter(this, datasource)
        listView.adapter = adapter
        listView.onItemClickListener = OnItemClickListener { adapterView, view, position, id ->
            val selectedItem = adapter.getItem(position) as LanParty?
            val intent = Intent(applicationContext, Info::class.java)
            val bundle = Bundle()
            bundle.putSerializable("selectedLanParty", selectedItem)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    /*
     * Action Bar settings (back button)
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}