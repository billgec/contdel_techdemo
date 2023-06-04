package at.fhjoanneum.lanfinderkotlin.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
        if (!isNetworkConnected()) {
            openNetworkErrorDialog()
        }

        val listView = findViewById<ListView>(R.id.listview_main)
        datasource = MockApiService.lanPartiesWhereCurrentUserIsNotSignedUpYet as ArrayList<LanParty?>
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

    fun openNetworkErrorDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_network_error)

        // Customize the dialog attributes as desired
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        // Disable the overlapped view
        dialog.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

        // Show the dialog
        dialog.show()

        // Clear the not focusable flag to enable interaction with the dialog
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

    fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
