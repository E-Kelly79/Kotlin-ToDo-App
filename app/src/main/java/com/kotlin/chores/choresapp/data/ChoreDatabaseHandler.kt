package com.kotlin.chores.choresapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kotlin.chores.choresapp.model.Chore
import com.kotlin.chores.choresapp.model.DATABASE_NAME
import com.kotlin.chores.choresapp.model.DATABASE_VERSION
import com.kotlin.chores.choresapp.model.KEY_CHORE_ASSIGNED_BY
import com.kotlin.chores.choresapp.model.KEY_CHORE_ASSIGNED_TIME
import com.kotlin.chores.choresapp.model.KEY_CHORE_ASSIGNED_TO
import com.kotlin.chores.choresapp.model.KEY_CHORE_NAME
import com.kotlin.chores.choresapp.model.KEY_ID
import com.kotlin.chores.choresapp.model.TABLE_NAME
import java.text.DateFormat
import java.util.Date

class ChoreDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private var db: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_CHORE_TABLE = "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY, " +
                "$KEY_CHORE_NAME TEXT," +
                "$KEY_CHORE_ASSIGNED_BY TEXT, " +
                "$KEY_CHORE_ASSIGNED_TO TEXT, " +
                "$KEY_CHORE_ASSIGNED_TIME LONG)"
        db?.execSQL(CREATE_CHORE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME ")

        //Create table again
        onCreate(db)
    }

    /**
     * CRUD - Create Read Update Delete
     */

    fun createChroe(chore: Chore){
        var values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())
        //Insert values into table
        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun getChore(id: Int): Chore{
        var chore = Chore()
        var cursor: Cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_CHORE_NAME, KEY_CHORE_ASSIGNED_TO,
            KEY_CHORE_ASSIGNED_BY, KEY_CHORE_ASSIGNED_TIME), "$KEY_ID=?", arrayOf(id.toString()),
            null, null, null, null)
        if(cursor != null){
            cursor.moveToFirst()
            chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
            chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
            chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
            chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

            var dateFormat: java.text.DateFormat = DateFormat.getDateInstance()
            var formatDate = dateFormat.format(Date(cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))).time)

        }
        return chore
    }

    fun getChores(): ArrayList<Chore>{
        var db: SQLiteDatabase = readableDatabase
        val list: ArrayList<Chore> = ArrayList()
        var getAllRecords = "SELECT * FROM $TABLE_NAME"
        var cursor: Cursor = db.rawQuery(getAllRecords, null)

        //Loop trough chores
        if(cursor.moveToFirst()){
            do{
                var chore = Chore()
                chore.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
                chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
                chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))
                chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
                list.add(chore)
            }while (cursor.moveToNext())
        }

        return list
    }

    fun updateChore(chore: Chore): Int{
        var values :ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        //Update row
        return db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(chore.id.toString()))
    }

    fun deleteChore(id: Int){
        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun countChores(): Int{
        var db: SQLiteDatabase = readableDatabase
        var countQuery = "SELECT * FROM $TABLE_NAME"
        var cursor: Cursor = db.rawQuery(countQuery, null)
        return cursor.count
    }
}