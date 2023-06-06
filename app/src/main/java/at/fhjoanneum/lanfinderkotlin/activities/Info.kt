package at.fhjoanneum.lanfinderkotlin.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import at.fhjoanneum.lanfinderkotlin.R
import at.fhjoanneum.lanfinderkotlin.mockdata.MockUsers
import at.fhjoanneum.lanfinderkotlin.models.LanParty
import at.fhjoanneum.lanfinderkotlin.models.User
import at.fhjoanneum.lanfinderkotlin.services.MockApiService
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Objects
import java.util.stream.Collectors

/**
 * This activity shows the User the information about the lan.
 */
class Info : AppCompatActivity() {
    @SuppressLint("DefaultLocale", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val intent = intent
        val bundle = intent.extras
        val selectedLanParty = bundle!!.getSerializable("selectedLanParty") as LanParty?
        val btnSignUp = findViewById<View>(R.id.btn_signup)
        val messageBox = findViewById<View>(R.id.et_description)

        if (!NetworkUtils.isNetworkConnected(this)) {
            NetworkUtils.openNetworkErrorDialog(this)
            btnSignUp.isEnabled = false
            btnSignUp.alpha = 0.2f
            messageBox.isEnabled = false
        }

        /*
         * Action Bar settings (set logo to action bar and back button)
         */
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setLogo(R.drawable.ic_logo_actionbar)
            setDisplayUseLogoEnabled(true)
           setDisplayHomeAsUpEnabled(true)
        }

        /*
         * Set the information from selected LAN to the textview
         */
        val games = selectedLanParty?.games
        val gamesString = StringBuilder()
        for (game in games!!) {
            if (game == games.toTypedArray()[games.size - 1]) {
                gamesString.append(game)
            } else {
                gamesString.append(game).append(", ")
            }
        }
        val datetime = selectedLanParty.date
        (findViewById<View>(R.id.tv_location) as TextView).text =
            selectedLanParty.zipCode + " " + selectedLanParty.city
        (findViewById<View>(R.id.tv_players) as TextView).text =
            selectedLanParty.registeredPlayers!!.size.toString() + " / " + selectedLanParty.amountMaxPlayers
        (findViewById<View>(R.id.tv_date) as TextView).text = String.format(
            "%2d.%2d.%4d",
            datetime!![Calendar.DAY_OF_MONTH],
            datetime[Calendar.MONTH] + 1,
            datetime[Calendar.YEAR]
        ).replace(' ', '0') + " " + String.format(
            "%2d:%2d",
            datetime[Calendar.HOUR_OF_DAY],
            datetime[Calendar.MINUTE]
        ).replace(' ', '0')
        (findViewById<View>(R.id.tv_games) as TextView).text = gamesString.toString()
        (findViewById<View>(R.id.tv_description) as TextView).text =
            selectedLanParty.description
        (findViewById<View>(R.id.tv_createdBy) as TextView).text =
            String.format("created by: %s", selectedLanParty.organizer!!.username)

        //hide delete or sign off button, when date of lan party is in the past
        if (selectedLanParty.date!!.before(GregorianCalendar())) {
            findViewById<View>(R.id.icon_delete).visibility = View.INVISIBLE
        }
        if (selectedLanParty.description.isEmpty()) {
            findViewById<View>(R.id.icon_description).visibility = View.INVISIBLE
        }

        /*
         * Set the onClickListener for the button
         */
        val currentUser = MockApiService.currentUser
        if (selectedLanParty.registeredPlayers!!.stream()
                .filter { x: User? -> x!!.id == currentUser!!.id }
                .collect(Collectors.toList()).size == 1
        ) {
            findViewById<View>(R.id.btn_signup).visibility = View.INVISIBLE
            findViewById<View>(R.id.view_signup).visibility = View.INVISIBLE
            findViewById<View>(R.id.et_description).visibility = View.INVISIBLE
        } else {
            findViewById<View>(R.id.icon_delete).visibility = View.INVISIBLE
            findViewById<View>(R.id.btn_signup).setOnClickListener { v: View? ->
                val intent1 = Intent(this@Info, MainActivity::class.java)
                startActivity(intent1)
                if (selectedLanParty.registeredPlayers!!.size < selectedLanParty.amountMaxPlayers) {
                    MockApiService.addUserToLanParty(
                        MockUsers.mockUsers[5],
                        selectedLanParty.id
                    )
                    //val etDescription = findViewById<EditText>(R.id.et_description) todo remove or add notification
                    Toast.makeText(
                        this@Info,
                        getString(R.string.signed_up_successfully),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this@Info, getString(R.string.this_lan_is_already_full), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        /*
         * make delete/sign down button
         */
        if (selectedLanParty.organizer!!.id == currentUser!!.id) { //is creator
            findViewById<View>(R.id.icon_delete).setOnClickListener { v: View? ->
                val builder = AlertDialog.Builder(this@Info)
                builder.setTitle(getString(R.string.delete_lan))
                builder.setMessage(getString(R.string.are_you_sure_you_want_to_delete_this_lan))
                builder.setPositiveButton(getString(R.string.delete)) { dialog: DialogInterface?, which: Int ->
                    val deleteIntent = Intent(this@Info, MainActivity::class.java)
                    startActivity(deleteIntent)
                    val receivers =
                        arrayOfNulls<String>(selectedLanParty.registeredPlayers!!.size)
                    var counter = 0
                    for (receiver in selectedLanParty.registeredPlayers!!) {
                        receivers[counter] = receiver!!.email
                        counter++
                    }
                    MockApiService.deleteLanParty(selectedLanParty.id)
                    Toast.makeText(
                        this@Info,
                        getString(R.string.lan_party_deleted_successfully),
                        Toast.LENGTH_LONG
                    ).show()
                }
                builder.setNegativeButton(getString(R.string.no)) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                builder.show()
            }
        } else { //is not creator
            findViewById<View>(R.id.icon_delete).setOnClickListener { v: View? ->
                val builder = AlertDialog.Builder(this@Info)
                builder.setTitle(getString(R.string.delete_lan))
                builder.setMessage(getString(R.string.are_you_sure_you_want_to_sign_off_of_this_lan))
                builder.setPositiveButton(getString(R.string.sign_off)) { dialog: DialogInterface?, which: Int ->
                    val deleteIntent = Intent(this@Info, MainActivity::class.java)
                    startActivity(deleteIntent)
                    MockApiService.removeUserFromLanParty(
                        MockApiService.currentUser,
                        selectedLanParty.id
                    )
                    Toast.makeText(
                        this@Info,
                        getString(R.string.signed_off_successfully),
                        Toast.LENGTH_LONG
                    ).show()
                }
                builder.setNegativeButton(getString(R.string.no)) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                builder.show()
            }
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