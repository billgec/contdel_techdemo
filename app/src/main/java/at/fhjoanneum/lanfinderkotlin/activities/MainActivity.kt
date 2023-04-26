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
import java.util.Objects

/**
 * This activity shows all lans the user can sign up for.
 */
class MainActivity : AppCompatActivity() {
    var datasource: ArrayList<LanParty?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         *
         * Action Bar settings
         *
         */Objects.requireNonNull(supportActionBar).setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.drawable.ic_logo_actionbar)
        supportActionBar!!.setDisplayUseLogoEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        val listView = findViewById<ListView>(R.id.listview_main)
        datasource = MockApiService.getLanPartiesWhereCurrentUserIsNotSignedUpYet()
        val adapter: ArrayAdapter<*> = MainAdapter(this, datasource)
        listView.adapter = adapter
        listView.onItemClickListener =
            OnItemClickListener { adapterView: AdapterView<*>?, view: View?, position: Int, id: Long ->
                val selectedItem = adapter.getItem(position) as LanParty?
                val intent = Intent(applicationContext, Info::class.java)
                val bundle = Bundle()
                bundle.putSerializable("selectedLanParty", selectedItem)
                intent.putExtras(bundle)
                startActivity(intent)
            }
    }
}