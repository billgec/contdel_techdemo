package at.fhjoanneum.lanfinderkotlin.restapi.models

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AccessUser {
    private val db = Firebase.firestore

    fun createUser(user: User) {
        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun getUser(callback: (List<User>) -> Unit) {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val userList = mutableListOf<User>()

                for (document in result) {
                    val user = document.toObject(User::class.java)
                    user.id = document.id
                    userList.add(user)
                }

                // Pass the userList to the callback function
                callback(userList)
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
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting user", e)
            }
    }
}
