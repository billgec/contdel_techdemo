package at.fhjoanneum.lanfinderkotlin.restapi.services

import android.util.Log
import at.fhjoanneum.lanfinderkotlin.Constants
import at.fhjoanneum.lanfinderkotlin.activities.LoginActivity
import at.fhjoanneum.lanfinderkotlin.activities.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import at.fhjoanneum.lanfinderkotlin.restapi.models.User

class CloudFirestore {

    private val firestoreInstance = FirebaseFirestore.getInstance()

    fun saveUserInfoOnCloudFirestore(registerActivity: RegisterActivity, currentUser: User){
        firestoreInstance
            .collection(Constants.TABLENAME_USER)
            .document(currentUser.id.toString())
            .set(currentUser, SetOptions.merge())
            .addOnSuccessListener {
                registerActivity.userRegistrationSuccess()
            }
            .addOnFailureListener { exp->
                Log.e(registerActivity.javaClass.name, "error occured", exp)
            }
    }

    /*fun getUserDetails(loginActivity: LoginActivity){
        firestoreInstance
            .collection(Constants.TABLENAME_USER)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener {document ->
                val user:User = document.toObject(User::class.java)!!
                loginActivity.userLoggedInSuccess(user)
            }
            .addOnFailureListener { exp ->
                Log.e(loginActivity.javaClass.name, "error occured", exp)
            }
    }*/

    private fun getCurrentUserID() : String{
        val currentUser = FirebaseAuth.getInstance().currentUser
         if(currentUser != null)
             return currentUser.uid

        return ""
    }

}