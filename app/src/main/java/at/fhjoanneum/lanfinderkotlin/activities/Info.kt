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

        /*
         *
         * Action Bar settings (set logo to action bar and back button)
         *
         */
        //Objects.requireNonNull(supportActionBar).setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = selectedLanParty!!.name
        supportActionBar!!.setDisplayUseLogoEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /*
         *
         * Set the information from selected LAN to the textview
         *
         */
        val games = selectedLanParty.games
        val gamesString = StringBuilder()
        for (game in games) {
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
         *
         * Set the onClickListener for the button
         *
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
                    val etDescription = findViewById<EditText>(R.id.et_description)
                    val playerText: String
                    playerText = if (etDescription.text.toString().isEmpty()) {
                        ""
                    } else {
                        "The player left you a message: " + etDescription.text.toString()
                    }
                    sendEmail(
                        arrayOf(selectedLanParty.organizer!!.email),
                        getString(R.string.new_player_mail) + " " + selectedLanParty.name,
                        """A new player just joined your Lan party *${selectedLanParty.name}*. Please check your Lan party page for more information.${selectedLanParty.registeredPlayers!!.size + 1} / ${selectedLanParty.amountMaxPlayers} are now registered.$playerText"""
                    )
                    Toast.makeText(
                        this@Info,
                        "Signed up successfully! If your mail app pops up, press SEND.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this@Info, "This LAN is already full!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        /*
         *
         * make delete/sign down button
         *
         */
        if (selectedLanParty.organizer!!.id == currentUser!!.id) { //is creator
            findViewById<View>(R.id.icon_delete).setOnClickListener { v: View? ->
                val builder = AlertDialog.Builder(this@Info)
                builder.setTitle("Delete LAN")
                builder.setMessage("Are you sure you want to delete this LAN?")
                builder.setPositiveButton("Delete") { dialog: DialogInterface?, which: Int ->
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
                    sendEmail(
                        receivers,
                        "The LAN party " + selectedLanParty.name + " has been deleted.",
                        """Unfortunately, the LAN party *${selectedLanParty.name}* has been deleted. Please check your Lan party page for more information.""".trimIndent()
                    )
                    Toast.makeText(
                        this@Info,
                        "Lan party deleted successfully! If your mail app pops up, press SEND.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                builder.setNegativeButton("No") { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                builder.show()
            }
        } else { //is not creator
            findViewById<View>(R.id.icon_delete).setOnClickListener { v: View? ->
                val builder = AlertDialog.Builder(this@Info)
                builder.setTitle("Delete LAN")
                builder.setMessage("Are you sure you want to sign off of this LAN?")
                builder.setPositiveButton("Sign off") { dialog: DialogInterface?, which: Int ->
                    val deleteIntent = Intent(this@Info, MainActivity::class.java)
                    startActivity(deleteIntent)
                    MockApiService.removeUserFromLanParty(
                        MockApiService.currentUser,
                        selectedLanParty.id
                    )
                    sendEmail(
                        arrayOf(selectedLanParty.organizer!!.email),
                        "A player of your LAN party " + " " + selectedLanParty.name + " has signed off.",
                        """The player ${MockApiService.currentUser!!.username} of your LAN party *${selectedLanParty.name}* has just signed off. Please check your Lan party page for more information.
                        ${selectedLanParty.registeredPlayers!!.size - 1} / ${selectedLanParty.amountMaxPlayers} are now registered. """
                    )
                    Toast.makeText(
                        this@Info,
                        "Signed off successfully! If your mail app pops up, press SEND.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                builder.setNegativeButton("No") { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                builder.show()
            }
        }
    }

    /*
     *
     * Action Bar settings (back button)
     *
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun sendEmail(to: Array<String?>, subject: String, message: String) {
        //open default mail app with prefilled receiver, subject and message
        val selectorIntent = Intent(Intent.ACTION_SENDTO)
        selectorIntent.data = Uri.parse("mailto:")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_APP_EMAIL)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.putExtra(Intent.EXTRA_EMAIL, to)
        intent.selector = selectorIntent
        startActivity(intent)
        val s = "Email sent to $to with subject $subject and message $message"
        println(s)
    }
}