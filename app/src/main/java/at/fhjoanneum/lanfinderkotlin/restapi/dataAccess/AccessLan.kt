package at.fhjoanneum.lanfinderkotlin.restapi.models

import android.content.ContentValues.TAG
import android.util.Log
import at.fhjoanneum.lanfinderkotlin.restapi.service.api.model.BaseEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/*
 * LanParty Model
 * Represents a LAN party event.
 * May 25, 2023
 */
class AccessLan {
    private val db = Firebase.firestore

    data class LanParty(
        var name: String = "",
        var zipCode: String = "",
        var city: String = "",
        var date: String = "",
        var amountMaxPlayers: Int = 0,
        var registeredPlayers: String = "",
        var games:String = "",
        var description: String = "",
        var organizer: String = ""
    ) : BaseEntity() {
        // No-argument constructor required by Firestore
        constructor() : this("", "", "", "", 0, "", "", "", "")
    }

    fun createLan(lan: LanParty) {
        // Add a new document with a generated ID
        db.collection("lans")
            .add(lan)
            .addOnSuccessListener { documentReference ->
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun getLan(callback: (List<LanParty>) -> Unit) {
        db.collection("lans")
            .get()
            .addOnSuccessListener { result ->
                val lanList = mutableListOf<LanParty>()
                for (document in result) {
                    val lan = document.toObject(LanParty::class.java)
                    lan.id = document.id
                    lanList.add(lan)
                }
                callback(lanList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
                callback(emptyList())
            }
    }

    fun getLan(lanId: String, callback: (LanParty?) -> Unit) {
        db.collection("lans").document(lanId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val lan = document.toObject(LanParty::class.java)
                    callback(lan)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting lan", exception)
                callback(null)
            }
    }

    fun updateLan(id: String, updatedLan: LanParty) {
        db.collection("lans")
            .document(id)
            .set(updatedLan)
            .addOnSuccessListener {
                Log.d(TAG, "Lan updated successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating lan", e)
            }
    }

    fun deleteLan(id: String) {
        db.collection("lans")
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Lan deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting lan", e)
            }
    }
}
