package at.fhjoanneum.lanfinderkotlin.fragments

import androidx.fragment.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import at.fhjoanneum.lanfinderkotlin.R
import at.fhjoanneum.lanfinderkotlin.activities.CreateLan
import at.fhjoanneum.lanfinderkotlin.activities.MyLans
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BurgerMenu : Fragment() {
    var fab: FloatingActionButton? = null
    private var my_lans_fab: FloatingActionButton? = null
    private var create_lan_fab: FloatingActionButton? = null
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
        my_lans_fab?.hide()
        create_lan_fab?.hide()
        isBurgerMenuOpen = false
        fab?.setOnClickListener { listenerView ->
            val icon: Int
            if (!isBurgerMenuOpen) {
                my_lans_fab?.show()
                create_lan_fab?.show()
                icon = R.drawable.ic_close
                isBurgerMenuOpen = true
            } else {
                my_lans_fab?.hide()
                create_lan_fab?.hide()
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
        return view
    }
}