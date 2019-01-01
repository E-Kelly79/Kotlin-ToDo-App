package com.kotlin.chores.choresapp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.kotlin.chores.choresapp.R
import com.kotlin.chores.choresapp.data.ChoreDatabaseHandler
import com.kotlin.chores.choresapp.data.ChoreListAdapter
import com.kotlin.chores.choresapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.choreRecyclerView
import kotlinx.android.synthetic.main.popup.view.popAddChoreBtn
import kotlinx.android.synthetic.main.popup.view.popAssignedBy
import kotlinx.android.synthetic.main.popup.view.popAssignedTo
import kotlinx.android.synthetic.main.popup.view.popEnterChore

class ChoreListActivity : AppCompatActivity() {

    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<Chore>? = null
    private var choreListItems: ArrayList<Chore>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null

    var dbHandler: ChoreDatabaseHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)
        dbHandler = ChoreDatabaseHandler(this)

        choreList = ArrayList()
        choreListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = ChoreListAdapter(choreListItems!!, this)

        //setup list for recycler view
        choreRecyclerView.layoutManager = layoutManager
        choreRecyclerView.adapter = adapter

        //Fetch chores and show in recycler view
        choreList = dbHandler!!.getChores()
        choreList!!.reverse()

        adapter!!.notifyDataSetChanged()

        for (c in choreList!!.iterator()){
           val chore = Chore()
            chore.id = c.id
            chore.choreName = c.choreName
            chore.assignedTo  = c.assignedTo
            chore.assignedBy = c.assignedBy
            chore.showHumanDate(c.timeAssigned!!)

            choreListItems!!.add(chore)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.addMenuBtn){
            createPopupDialog()
        }
        return super.onOptionsItemSelected(item)
    }


    fun createPopupDialog() {
        var view = layoutInflater.inflate(R.layout.popup, null)
        var choreName = view.popEnterChore
        var assignedBy = view.popAssignedBy
        var assignedTo = view.popAssignedTo
        var add = view.popAddChoreBtn

        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder!!.create()
        dialog!!.show()

        add.setOnClickListener {
            var name = choreName.text.toString().trim()
            var aBy = assignedBy.text.toString().trim()
            var aTo = assignedTo.text.toString().trim()
            if (!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(aBy)
                && !TextUtils.isEmpty(aTo)
            ) {
                var chore = Chore()
                chore.choreName = name
                chore.assignedBy = aBy
                chore.assignedTo = aTo
                dbHandler!!.createChroe(chore)

                dialog!!.dismiss()
                startActivity(Intent(this, ChoreListActivity::class.java))
                finish()
            }
        }
    }
}
