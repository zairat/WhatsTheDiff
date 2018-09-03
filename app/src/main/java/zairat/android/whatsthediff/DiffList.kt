package zairat.android.whatsthediff

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import okhttp3.*
import java.io.IOException

class DiffList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_list)

        //get the url of the diff file from the main activity PullRequests
        val diffUrl  = intent.getStringExtra("diff_url")

        val request = Request.Builder().url(diffUrl).build()
        val client = OkHttpClient()

        //process api request
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("failed to request .diff file")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val diffBody = response?.body()?.string()
                Log.i("api response",diffBody?.substring(0,160))

                //parse the body string here to get all the parts of the diff

            }
        })
    }

}
