import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import zairat.android.whatsthediff.PullRequest
import zairat.android.whatsthediff.R

class PullRequestAdapter(context: Context,
                         private val pullList: List<PullRequest>)
    : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return pullList.size
    }

    override fun getItem(position: Int): Any {
        return pullList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.pr_list_item, parent, false)

        // Get title element
        val titleTextView = rowView.findViewById(R.id.pr_title) as TextView

        // Get number
        val numberTextView = rowView.findViewById(R.id.pr_number) as TextView

        // Get open summary element
        val openSummaryTextView = rowView.findViewById(R.id.pr_open_summary) as TextView

        //apply all values to views
        val pr = getItem(position) as PullRequest
        val summary = "Opened on ${pullList[position].created_at} by ${pullList[position].user.login}"

        titleTextView.text = pullList[position].title
        numberTextView.text = pullList[position].number.toString()
        openSummaryTextView.text = summary


        return rowView
    }
}