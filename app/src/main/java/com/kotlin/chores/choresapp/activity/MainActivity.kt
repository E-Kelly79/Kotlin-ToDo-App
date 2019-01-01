package com.kotlin.chores.choresapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.kotlin.chores.choresapp.R.layout
import com.kotlin.chores.choresapp.data.ChoreDatabaseHandler
import com.kotlin.chores.choresapp.model.Chore
import kotlinx.android.synthetic.main.activity_main.addChoreBtn
import kotlinx.android.synthetic.main.activity_main.assignedBy
import kotlinx.android.synthetic.main.activity_main.assignedTo
import kotlinx.android.synthetic.main.activity_main.enterChore

class MainActivity : AppCompatActivity() {

    var dbHandler: ChoreDatabaseHandler? = null
    var progeressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        progeressDialog = ProgressDialog(this)
        dbHandler = ChoreDatabaseHandler(this)

        addChoreBtn.setOnClickListener {
            progeressDialog!!.setMessage("Saving data please wait")
            progeressDialog!!.show()
            if (!TextUtils.isEmpty(enterChore.text.toString())
                &&!TextUtils.isEmpty(assignedBy.text.toString())
                &&!TextUtils.isEmpty(assignedTo.text.toString())){

                //Save chore to database
                var chore = Chore()
                chore.choreName = enterChore.text.toString()
                chore.assignedBy = assignedBy.text.toString()
                chore.assignedTo = assignedTo.text.toString()
                saveToDatabase(chore)
                progeressDialog!!.cancel()

                startActivity(Intent(this, ChoreListActivity::class.java))

            }else{
                Toast.makeText(this, "Please make sure all fields are filled in", Toast.LENGTH_LONG).show()
            }
        }


//        var chore = Chore()
//        chore.choreName = "Testing Database"
//        chore.assignedTo = "Anyone that wants it"
//        chore.assignedBy = "The King"
//
//        dbHandler!!.createChroe(chore)
//
//        //Get chore for database
//        var chores: Chore = dbHandler!!.getChore(1)
//
//        //Log data to see if we are retrieving the data for SQL lite
//        Log.i("DATABASE", "${chore.choreName}, ${chore.assignedTo}, ${chore.assignedBy}, ${chore.timeAssigned} ")
    }

    fun saveToDatabase(chore: Chore){
        dbHandler!!.createChroe(chore)
    }
}
