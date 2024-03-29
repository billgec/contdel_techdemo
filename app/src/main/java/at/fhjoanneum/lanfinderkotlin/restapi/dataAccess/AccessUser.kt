package at.fhjoanneum.lanfinderkotlin.restapi.models

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/*
 * AccessUser Model
 * Provides methods for creating, retrieving, updating, and deleting user data from Firestore.
 * May 25, 2023
 */
class AccessUser {
    private val db = Firebase.firestore

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

    fun getUserByEmail(email: String): Task<QuerySnapshot> {
        return db.collection("users")
            .whereEqualTo("email", email)
            .get()
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
