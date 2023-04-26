package at.fhjoanneum.lanfinderkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import at.fhjoanneum.lanfinderkotlin.R
import at.fhjoanneum.lanfinderkotlin.models.LanParty
import java.util.Calendar

class MainAdapter(context: Context?, lanParties: ArrayList<LanParty?>?) : ArrayAdapter<Any?>(
    context!!, 0, lanParties!!
) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view = view
        val lanParty = getItem(position) as LanParty?
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_item_main, parent, false)
        }
        val name = view!!.findViewById<TextView>(R.id.name)
        val zipCode = view.findViewById<TextView>(R.id.zipCode)
        val city = view.findViewById<TextView>(R.id.city)
        val date = view.findViewById<TextView>(R.id.date)
        val time = view.findViewById<TextView>(R.id.time)
        val participation = view.findViewById<TextView>(R.id.participationText)
        name.text = lanParty.getName()
        zipCode.text = lanParty.getZipCode()
        city.text = lanParty.getCity()
        val datetime = lanParty.getDate()
        date.text = String.format(
            "%2d.%2d.",
            datetime!![Calendar.DAY_OF_MONTH],
            datetime!![Calendar.MONTH] + 1
        ).replace(' ', '0')
        time.text = String.format(
            "%2d:%2d",
            datetime!![Calendar.HOUR_OF_DAY],
            datetime!![Calendar.MINUTE]
        ).replace(' ', '0')
        participation.text = String.format(
            "%2d/%2d",
            lanParty.getRegisteredPlayers().size,
            lanParty.getAmountMaxPlayers()
        ).replace(' ', '0')
        return view
    }
}