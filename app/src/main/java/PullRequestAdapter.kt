import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import zairat.android.whatsthediff.PullRequest
import zairat.android.whatsthediff.R

//This is a list adapter for the PR listview on the main activity

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

        // Get title
        val titleTextView = rowView.findViewById(R.id.pr_title) as TextView

        // Get number
        val numberTextView = rowView.findViewById(R.id.pr_number) as TextView

        // Get open summary
        val openSummaryTextView = rowView.findViewById(R.id.pr_open_summary) as TextView

        //apply all values to list item views
        val pr = getItem(position) as PullRequest
        val summary = "Opened on ${pr.created_at} by ${pr.user.login}"
        titleTextView.text = pr.title
        numberTextView.text = pr.number.toString()
        openSummaryTextView.text = summary

        return rowView
    }
}