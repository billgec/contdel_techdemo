package at.fhjoanneum.lanfinderkotlin.restapi.dataAccess
import okhttp3.OkHttpClient

object Rest {
    val client = OkHttpClient()
    val baseUrl = "http://10.0.2.2:8080"
}
