package at.fhjoanneum.lanfinderkotlin.restapi.models

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AccessUser {
    private val db = Firebase.firestore

    fun createUser(user: User) {
        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    fun readUser(callback: (List<DocumentSnapshot>?, Exception?) -> Unit) {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val documents = result.documents
                callback(documents, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception)
            }
    }

    fun getUser(id: String, callback: (DocumentSnapshot?, Exception?) -> Unit) {
        db.collection("users")
            .document(id)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                callback(documentSnapshot, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception)
            }
    }
}
