package com.kotlin.chores.choresapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.kotlin.chores.choresapp.R
import com.kotlin.chores.choresapp.data.ChoreDatabaseHandler
import com.kotlin.chores.choresapp.data.ChoreListAdapter
import com.kotlin.chores.choresapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.choreRecyclerView

class ChoreListActivity : AppCompatActivity() {

    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<Chore>? = null
    private var choreListItems: ArrayList<Chore>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null


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

        adapter!!.notifyDataSetChanged()

        for (c in choreList!!.iterator()){
           val chore = Chore()
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

        }
        return super.onOptionsItemSelected(item)
    }
}
