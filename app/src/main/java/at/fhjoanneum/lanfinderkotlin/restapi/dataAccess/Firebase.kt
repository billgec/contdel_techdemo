package at.fhjoanneum.lanfinderkotlin.restapi.dataAccess

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/*
 * Firebase
 * Provides access to the Firestore database.
 * May 25, 2023
 */
object Firebase {
    val firestore = Firebase.firestore
}
