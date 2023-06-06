package at.fhjoanneum.lanfinderkotlin.controller

import android.content.Context
import android.widget.Toast
import at.fhjoanneum.lanfinderkotlin.restapi.api.model.LanParty
import at.fhjoanneum.lanfinderkotlin.service.LanPartyService
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class LanPartyController constructor(private val context: Context) {

    private lateinit var lanPartyService: LanPartyService
    private var dbRef = FirebaseDatabase.getInstance().getReference("LanParty")


    fun saveLanParty(lanParty: at.fhjoanneum.lanfinderkotlin.models.LanParty) {
        lanParty.id = dbRef.push().key!!.toInt()
        dbRef.child(lanParty.id.toString()).setValue(lanParty)
            .addOnCompleteListener {
                Toast.makeText(context, "TODO Data in Firebase", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { err ->
                Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    fun getLanParty(id: String): LanParty? {
        val lanParty: Optional<LanParty> = lanPartyService.getLanParty(id)
        return lanParty.orElse(null)
    }

}
