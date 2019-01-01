package com.kotlin.chores.choresapp.model

class Chore() {
    var choreName: String? = null
    var assignedTo: String? = null
    var assignedBy: String? = null
    var timeAssigned: Long? = null
    var id: Int? = null

    constructor(choreName: String, assignedTo: String, assignedBy: String, timeAssigned: Long, id: Int):this(){
        this.choreName = choreName
        this.assignedTo = assignedTo
        this.assignedBy = assignedBy
        this.timeAssigned = timeAssigned
        this.id = id
    }
}