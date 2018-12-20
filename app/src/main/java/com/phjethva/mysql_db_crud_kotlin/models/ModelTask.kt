package com.phjethva.mysql_db_crud_kotlin.models

/**
 * @author PJET APPS (Pratik Jethva)
 * Check Out My Other Repositories On Github: https://github.com/phjethva
 * Visit My Website: https://www.pjetapps.com
 * Follow My Facebook Page: https://www.facebook.com/pjetapps
 */

class ModelTask {

    var id: Int = 0
    var taskName: String? = null
    var taskDateTime: String? = null

    constructor() {}

    constructor(taskName: String, taskDateTime: String) {
        this.taskName = taskName
        this.taskDateTime = taskDateTime
    }

    constructor(id: Int, taskName: String, taskDateTime: String) {
        this.id = id
        this.taskName = taskName
        this.taskDateTime = taskDateTime
    }

}