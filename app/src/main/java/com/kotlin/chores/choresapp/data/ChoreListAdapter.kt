package com.kotlin.chores.choresapp.data

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.kotlin.chores.choresapp.R
import com.kotlin.chores.choresapp.model.Chore
import kotlinx.android.synthetic.main.popup.view.popAddChoreBtn
import kotlinx.android.synthetic.main.popup.view.popAssignedBy
import kotlinx.android.synthetic.main.popup.view.popAssignedTo
import kotlinx.android.synthetic.main.popup.view.popEnterChore

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

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
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
            assignedTime.text = chore.showHumanDate(System.currentTimeMillis())
            editBtn.setOnClickListener(this)
            deleteBtn.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            var position: Int = adapterPosition
            var chore = list[position]
            when(v!!.id){
                editBtn.id -> {
                    editChore(chore)
                }
                deleteBtn.id -> {
                    deleteChore(chore.id!!)
                    list.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }

        fun deleteChore(id: Int){
            var db: ChoreDatabaseHandler = ChoreDatabaseHandler(context)
            db.deleteChore(id)
        }

        fun editChore(chore: Chore){
            fun createPopupDialog() {
                var dialogBuilder: AlertDialog.Builder?
                var dialog: AlertDialog?
                var dbHandler: ChoreDatabaseHandler = ChoreDatabaseHandler(context)
                var view = LayoutInflater.from(context).inflate(R.layout.popup, null)
                var choreName = view.popEnterChore
                var assignedBy = view.popAssignedBy
                var assignedTo = view.popAssignedTo
                var add = view.popAddChoreBtn

                dialogBuilder = AlertDialog.Builder(context).setView(view)
                dialog = dialogBuilder!!.create()
                dialog!!.show()

                add.setOnClickListener {
                    var name = choreName.text.toString().trim()
                    var aBy = assignedBy.text.toString().trim()
                    var aTo = assignedTo.text.toString().trim()
                    if (!TextUtils.isEmpty(name)
                        && !TextUtils.isEmpty(aBy)
                        && !TextUtils.isEmpty(aTo)) {
                        chore.choreName = name
                        chore.assignedBy = aBy
                        chore.assignedTo = aTo
                        dbHandler!!.updateChore(chore)
                        notifyItemChanged(adapterPosition, chore)
                        dialog!!.dismiss()

                    }
                }
            }
        }
    }
}