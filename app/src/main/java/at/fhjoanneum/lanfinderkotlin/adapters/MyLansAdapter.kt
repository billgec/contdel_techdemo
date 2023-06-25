package at.fhjoanneum.lanfinderkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import at.fhjoanneum.lanfinderkotlin.R
import at.fhjoanneum.lanfinderkotlin.restapi.models.LanParty
import java.util.Calendar
import java.util.GregorianCalendar

/*
 * MyLansAdapter
 * Custom ArrayAdapter for displaying LanParty items in the "My Lans" list view.
 * May 25, 2023
 */
class MyLansAdapter(context: Context?, lanParties: ArrayList<LanParty?>?) : ArrayAdapter<Any?>(
    context!!, 0, lanParties!! as List<Any?>
) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view = view
        val lanParty = getItem(position) as LanParty?
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_item_mylans, parent, false)
        }
        val name = view!!.findViewById<TextView>(R.id.name)
        val zipCode = view.findViewById<TextView>(R.id.zipCode)
        val city = view.findViewById<TextView>(R.id.city)
        val date = view.findViewById<TextView>(R.id.date)
        val time = view.findViewById<TextView>(R.id.time)
        val participation = view.findViewById<TextView>(R.id.participationText)
        val playerIcon = view.findViewById<ImageView>(R.id.playerIcon)
        val checkIcon = view.findViewById<ImageView>(R.id.check)
        name.text = lanParty!!.name
        zipCode.text = lanParty.zipCode
        city.text = lanParty.city
        val datetime = lanParty.date
        date.text = String.format(
            "%2d.%2d.",
            datetime!![Calendar.DAY_OF_MONTH],
            datetime[Calendar.MONTH] + 1
        ).replace(' ', '0')
        time.text = String.format(
            "%2d:%2d",
            datetime[Calendar.HOUR_OF_DAY],
            datetime[Calendar.MINUTE]
        ).replace(' ', '0')
        participation.text = String.format(
            "%2d/%2d",
            lanParty.registeredPlayers!!.size,
            lanParty.amountMaxPlayers
        ).replace(' ', '0')
        if (lanParty.date!!.before(GregorianCalendar())) {
            name.alpha = 0.5f
            zipCode.alpha = 0.5f
            city.alpha = 0.5f
            date.alpha = 0.5f
            time.alpha = 0.5f
            participation.alpha = 0.5f
            playerIcon.alpha = 0.5f
        } else {
            checkIcon.visibility = View.INVISIBLE
        }
        return view
    }
}