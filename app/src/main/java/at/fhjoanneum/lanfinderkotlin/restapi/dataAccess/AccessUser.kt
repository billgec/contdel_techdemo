package at.fhjoanneum.lanfinderkotlin.restapi.models

import android.content.ContentValues
import android.util.Log
import at.fhjoanneum.lanfinderkotlin.restapi.dataAccess.Rest
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class AccessUser {
    private val client = Rest.client
    private var baseUrl = Rest.baseUrl

    private fun userToJson(user: User): JSONObject {
        val userJson = JSONObject()
        userJson.put("username", user.username)
        userJson.put("email", user.email)
        return userJson
    }

    fun createUser(user: User) {
        val json = userToJson(user)
        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("$baseUrl/user")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "User created successfully")
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.w(TAG, "Error creating user: ${e.message}")
            }
        })
    }

    fun getUser(callback: (List<User>) -> Unit) {
        val request = Request.Builder()
            .url("$baseUrl/users")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val userList = parseUserListResponse(response.body)

                callback(userList)
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.w(TAG, "Error getting users: ${e.message}")
                callback(emptyList())
            }
        })
    }

    fun getUser(userId: String, callback: (User?) -> Unit) {
        val request = Request.Builder()
            .url("$baseUrl/user/$userId")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val user = parseUserResponse(response.body)
                callback(user)
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.w(TAG, "Error getting user: ${e.message}")
                callback(null)
            }
        })
    }

    fun getUserByEmail(email: String) {
        val request = Request.Builder()
            .url("$baseUrl/user?email=$email")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // Handle the response
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.w(TAG, "Error getting user by email: ${e.message}")
            }
        })
    }

    fun updateUser(id: String, updatedUser: User) {
        val request = Request.Builder()
            .url("$baseUrl/user/$id")
            .put(RequestBody.create("application/json".toMediaTypeOrNull(), userToJson(updatedUser).toString()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "User updated successfully")
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.w(TAG, "Error updating user: ${e.message}")
            }
        })
    }

    fun deleteUser(id: String) {
        val request = Request.Builder()
            .url("$baseUrl/user/$id")
            .delete()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "User deleted successfully")
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.w(TAG, "Error deleting user: ${e.message}")
            }
        })
    }

    private fun parseUserListResponse(responseBody: ResponseBody?): List<User> {
        val userList = mutableListOf<User>()

        responseBody?.let {
            val responseJsonArray = JSONArray(it.string())
            for (i in 0 until responseJsonArray.length()) {
                val userJson = responseJsonArray.getJSONObject(i)
                val user = User(
                    username = userJson.getString("username"),
                    email = userJson.getString("email")
                )
                user.id = userJson.getString("id")
                Log.d(ContentValues.TAG,"USER:       ${user.id}")
                userList.add(user)
            }
        }

        return userList
    }

    private fun parseUserResponse(responseBody: ResponseBody?): User? {
        responseBody?.let {
            val responseJson = JSONObject(it.string())
            return User(
                username = responseJson.getString("username"),
                email = responseJson.getString("email")
            )
        }
        return null
    }

    companion object {
        private const val TAG = "AccessUser"
    }
}
