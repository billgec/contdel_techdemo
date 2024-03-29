package at.fhjoanneum.lanfinderkotlin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import at.fhjoanneum.lanfinderkotlin.R
import at.fhjoanneum.lanfinderkotlin.activities.CreateLan
import at.fhjoanneum.lanfinderkotlin.activities.FilterLan
import at.fhjoanneum.lanfinderkotlin.activities.LoginActivity
import at.fhjoanneum.lanfinderkotlin.activities.MainActivity
import at.fhjoanneum.lanfinderkotlin.activities.MyLans
import at.fhjoanneum.lanfinderkotlin.restapi.models.Filter.Companion.deleteFilter
import at.fhjoanneum.lanfinderkotlin.restapi.models.Filter.Companion.isFilter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

/*
 * BurgerMenu Fragment
 * Fragment for displaying the burger menu with options such as create LAN, filter LANs, view my LANs, and logout.
 * May 25, 2023
 */
class BurgerMenu : Fragment() {
    var fab: FloatingActionButton? = null
    private var my_lans_fab: FloatingActionButton? = null
    private var create_lan_fab: FloatingActionButton? = null
    private var filter_lan_fab: FloatingActionButton? = null
    private var logout_fab: FloatingActionButton? = null
    var isBurgerMenuOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_burger_menu, container, false)
        fab = view.findViewById(R.id.fab)
        my_lans_fab = view.findViewById(R.id.my_lans_fab)
        create_lan_fab = view.findViewById(R.id.create_lan_fab)
        filter_lan_fab = view.findViewById(R.id.filter_lan_fab)
        logout_fab = view.findViewById(R.id.logout_fab)
        my_lans_fab?.hide()
        create_lan_fab?.hide()
        filter_lan_fab?.hide()
        logout_fab?.hide()
        isBurgerMenuOpen = false
        fab?.setOnClickListener { listenerView ->
            val icon: Int
            if (!isBurgerMenuOpen) {
                my_lans_fab?.show()
                create_lan_fab?.show()
                if (activity is MainActivity) {
                    if (isFilter()) {
                        filter_lan_fab?.setImageResource(R.drawable.ic_filter_clear)
                    } else {
                        filter_lan_fab?.setImageResource(R.drawable.ic_filter)
                    }
                    filter_lan_fab?.show()
                }
                logout_fab?.show()
                icon = R.drawable.ic_close
                isBurgerMenuOpen = true
            } else {
                my_lans_fab?.hide()
                create_lan_fab?.hide()
                filter_lan_fab?.hide()
                logout_fab?.hide()
                icon = R.drawable.ic_menu
                isBurgerMenuOpen = false
            }
            fab?.setImageDrawable(
                ContextCompat.getDrawable(
                    activity?.applicationContext ?: return@setOnClickListener,
                    icon
                )
            )
        }

        /*
         * forward to CreateLan activity
         */
        create_lan_fab?.setOnClickListener { listenerView ->
            val intent = Intent(activity, CreateLan::class.java)
            startActivity(intent)
        }

        /*
         * forward to MyLans activity
         */
        my_lans_fab?.setOnClickListener { listenerView ->
            val intent = Intent(activity, MyLans::class.java)
            startActivity(intent)
        }

        /*
         * filter MainActivity list
         */
        filter_lan_fab?.setOnClickListener { listenerView ->
            val intent: Intent

            if (isFilter()) { //if filter is applied it should be deleted and load all lanParties
                deleteFilter()
                intent = Intent(activity, MainActivity::class.java)
            } else { // if no filter is applied it should create one and reduce list of all lanParties
                intent = Intent(activity, FilterLan::class.java)
            }
            startActivity(intent)
        }

        logout_fab?.setOnClickListener { listenerView ->
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
        return view
    }
}
