package zairat.android.whatsthediff

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import PullRequestAdapter
import android.widget.ListView
import zairat.android.whatsthediff.R.id.pr_list_view
import java.security.AccessController.getContext

class PullRequests : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_requests)

        val url = ("https://api.github.com/repos/trekhleb/javascript-algorithms/pulls")

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                println("failed to request JSON")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val pullList: List<PullRequest> = gson.fromJson(body, object : TypeToken<List<PullRequest>>() {}.type)
                //loadPulls(pullList)
                val adapter = PullRequestAdapter(applicationContext, pullList)
                runOnUiThread {
                    findViewById<ListView>(R.id.pr_list_view).adapter = adapter
                }
            }
        })
    }
}

class PullRequest(val title: String,
                  val number: Int,
                  val user: User,
                  val created_at: String)

class User(val login: String)


//loads up the content in the UI from a list of PullRequests
//fun loadPulls(pulList: List<PullRequest>) {
//    val adapter = PullRequestAdapter(getContext(this))
//    pr_list_view.adapter = adapter
//}