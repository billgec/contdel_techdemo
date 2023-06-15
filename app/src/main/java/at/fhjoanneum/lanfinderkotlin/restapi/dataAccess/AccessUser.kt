package at.fhjoanneum.lanfinderkotlin.restapi.models

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AccessUser {
    private val db = Firebase.firestore
/*

    data class User(
        var username: String = "",
        var email: String = ""
    ) {
        // No-argument constructor required by Firestore
        constructor() : this("", "")
    }
*/

    fun createUser(user: User) {
        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun getUser() {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getUser(userId: String, callback: (User?) -> Unit) {
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    callback(user)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting user", exception)
                callback(null)
            }
    }

    fun updateUser(id: String, updatedUser: User) {
        db.collection("users")
            .document(id)
            .set(updatedUser)
            .addOnSuccessListener {
                Log.d(TAG, "User updated successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating user", e)
            }
    }

    fun deleteUser(id: String) {
        db.collection("users")
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "User deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting user", e)
            }
    }
}
