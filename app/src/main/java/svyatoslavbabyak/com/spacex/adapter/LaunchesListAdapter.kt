package svyatoslavbabyak.com.spacex.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import svyatoslavbabyak.com.spacex.R
import svyatoslavbabyak.com.spacex.entity.Launch
import svyatoslavbabyak.com.spacex.utility.loadImage
import java.text.DateFormat

class LaunchesListAdapter(private val items : ArrayList<Launch>, private val context : Context, private val onClickListener: CustomPlayListsRecyclerClickListener) : RecyclerView.Adapter<LaunchesListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    fun addLaunchesToTheList(launchesList: List<Launch>?) {
        if(launchesList == null) {
            return
        }

        this.items.addAll(launchesList)
        notifyDataSetChanged()
    }

    fun clearItems() {
        items.clear()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_launches_list, parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener { v -> onClickListener.onItemClick(v, items[viewHolder.adapterPosition]) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currItem = items[position]
        holder.missionName.text = currItem.missionName
        holder.launchDate.text = DateFormat.getInstance().format(currItem.launchDateUTC)
        holder.missionPatch.loadImage(currItem.links.missionPatchSmall)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView
        val missionName : TextView = view.findViewById(R.id.missionName)
        val missionPatch : ImageView = view.findViewById(R.id.missionPatch)
        val launchDate : TextView = view.findViewById(R.id.launchDate)
    }
}

interface CustomPlayListsRecyclerClickListener {
    fun onItemClick(v: View, launch: Launch)
}
