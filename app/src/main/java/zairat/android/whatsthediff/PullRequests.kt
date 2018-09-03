package zairat.android.whatsthediff

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import PullRequestAdapter
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

class PullRequests : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_requests)

        //api url for the list of PRs of a repo
        val url = ("https://api.github.com/repos/trekhleb/javascript-algorithms/pulls")

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        //process api request
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                println("failed to request JSON")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()

                //convert json to list of PullRequest objects
                val pullList: List<PullRequest> = gson.fromJson(body, object : TypeToken<List<PullRequest>>() {}.type)

                //apply values from the PullRequest objects to the listview on the main thread
                val adapter = PullRequestAdapter(applicationContext, pullList)
                runOnUiThread {
                    findViewById<ListView>(R.id.pr_list_view).adapter = adapter

                    //set onclick listener for each item in the list
                    findViewById<ListView>(R.id.pr_list_view).setOnItemClickListener{
                    parent: AdapterView<*>, view: View?, position: Int, id: Long ->
                        //change activity
                        val intent = Intent(applicationContext, DiffList::class.java)
                        intent.putExtra("diff_url", pullList[position].diff_url)
                        startActivity(intent)

                    }
                }

            }
        })
    }
}

//classes created to ingest data from json for PR list api request
class PullRequest(val title: String,
                  val number: Int,
                  val user: User,
                  val created_at: String,
                  val diff_url: String)

class User(val login: String)
