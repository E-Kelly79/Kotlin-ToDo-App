package com.kotlin.chores.choresapp.data

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.kotlin.chores.choresapp.R
import com.kotlin.chores.choresapp.model.Chore

class ChoreListAdapter(private val list: ArrayList<Chore>, private val context: Context):
    RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoreListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chore_list_row, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChoreListAdapter.ViewHolder, position: Int) {
        holder?.bindViews(list[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var choreName = itemView.findViewById<TextView>(R.id.listChoreName)
        var assignedBy = itemView.findViewById<TextView>(R.id.listAssignedBy)
        var assignedTo = itemView.findViewById<TextView>(R.id.listAssignedTo)
        var assignedTime = itemView.findViewById<TextView>(R.id.listAssignedTime)
        var editBtn = itemView.findViewById<Button>(R.id.editBtn)
        var deleteBtn = itemView.findViewById<Button>(R.id.deleteBtn)

        fun bindViews(chore: Chore){
            choreName.text = chore.choreName
            assignedBy.text = chore.assignedBy
            assignedTo.text = chore.assignedTo
            //assignedTime.text = chore.showHumanDate(chore.timeAssigned!!)
        }
    }
}