package at.fhjoanneum.lanfinderkotlin.restapi.models

import android.content.ContentValues.TAG
import android.util.Log
import at.fhjoanneum.lanfinderkotlin.activities.Info
import at.fhjoanneum.lanfinderkotlin.restapi.dataAccess.Rest
import at.fhjoanneum.lanfinderkotlin.restapi.service.api.model.BaseEntity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import okhttp3.OkHttpClient


class AccessLan {
    private val client = OkHttpClient()
    private val baseUrl = Rest.baseUrl

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
        constructor() : this("", "", "", "", 0, "", "", "", "")
    }

    fun lanPartyToJson(lanParty: LanParty): JSONObject {
        val lanJson = JSONObject()
        lanJson.put("name", lanParty.name)
        lanJson.put("zipCode", lanParty.zipCode)
        lanJson.put("city", lanParty.city)
        lanJson.put("date", lanParty.date)
        lanJson.put("amountMaxPlayers", lanParty.amountMaxPlayers)
        lanJson.put("registeredPlayers", lanParty.registeredPlayers)
        lanJson.put("games", lanParty.games)
        lanJson.put("description", lanParty.description)
        lanJson.put("organizer", lanParty.organizer)
        return lanJson
    }

    fun createLan(lan: LanParty) {
        val json = lanPartyToJson(lan)
        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("$baseUrl/addlanparty")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Lan added successfully")
                } else {
                    Log.w(TAG, "Error adding lan. Response code: ${response.code}")
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.w(TAG, "Error adding lan", e)
            }
        })
    }

    fun getLan(callback: (List<LanParty>) -> Unit) {
        val request = Request.Builder()
            .url("$baseUrl/lanparties")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: okhttp3.Call, response: Response) {
                val lanList = parseResponse(response.body)
                callback(lanList)
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Handle request failure or exceptions
                println("API request failed: ${e.message}")
                callback(emptyList())
            }
        })
    }

    fun updateLan(id: String, updatedLan: LanParty) {
        val json = lanPartyToJson(updatedLan)
        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        Log.e(TAG, "ATTTENTION              $updatedLan")

        val request = Request.Builder()
            .url("$baseUrl/lanparty/update?id=$id")
            .put(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Lan updated successfully")
                } else {
                    Log.w(TAG, "Error updating lan. Response code: ${response.code}")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.w(TAG, "Error updating lan", e)
            }
        })
    }

    fun deleteLan(id: String) {
        val request = Request.Builder()
            .url("$baseUrl/lanparty/delete?id=$id")
            .delete()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Lan deleted successfully")
                } else {
                    Log.w(TAG, "Error deleting lan. Response code: ${response.code}")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.w(TAG, "Error deleting lan", e)
            }
        })
    }

    private fun parseResponse(responseBody: ResponseBody?): List<LanParty> {
        val lanList = mutableListOf<LanParty>()

        responseBody?.let {
            val responseJsonArray = JSONArray(it.string())

            for (i in 0 until responseJsonArray.length()) {
                val lanJson = responseJsonArray.getJSONObject(i)
                val lan = LanParty(
                    name = lanJson.getString("name"),
                    zipCode = lanJson.getString("zipCode"),
                    city = lanJson.getString("city"),
                    date = lanJson.getString("date"),
                    amountMaxPlayers = lanJson.getInt("amountMaxPlayers"),
                    registeredPlayers = lanJson.getString("registeredPlayers"),
                    games = lanJson.getString("games"),
                    description = lanJson.getString("description"),
                    organizer = lanJson.getString("organizer")
                )
                lan.id = lanJson.getString("id")
                Log.d(TAG,"LAN:       ${lan.id}")
                lanList.add(lan)
            }
        }
        return lanList
    }
}
