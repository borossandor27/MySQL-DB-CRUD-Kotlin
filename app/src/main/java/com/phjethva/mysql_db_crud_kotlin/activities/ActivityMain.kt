package com.phjethva.mysql_db_crud_kotlin.activities

/**
 * @author PJET APPS (Pratik Jethva)
 * Check Out My Other Repositories On Github: https://github.com/phjethva
 * Visit My Website: https://www.pjetapps.com
 * Follow My Facebook Page: https://www.facebook.com/pjetapps
 */

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.phjethva.mysql_db_crud_kotlin.R
import com.phjethva.mysql_db_crud_kotlin.adapters.AdapterTask
import com.phjethva.mysql_db_crud_kotlin.db.DBHelper
import com.phjethva.mysql_db_crud_kotlin.models.ModelTask
import com.phjethva.mysql_db_crud_kotlin.utils.Utils
import java.util.*

class ActivityMain : AppCompatActivity(), View.OnClickListener, AdapterTask.ItemClick {

    private var btnAddNewTsk: Button? = null
    private var tasks: List<ModelTask> = ArrayList()
    private var adapterTask: AdapterTask? = null
    private var recyclerViewTask: RecyclerView? = null
    private var dbHelper: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setView()
        setClickListen()

        dbHelper = DBHelper(this)

        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewTask!!.layoutManager = layoutManager

        tasks = dbHelper!!.readAllTask()
        adapterTask = AdapterTask(this, tasks)
        recyclerViewTask!!.adapter = adapterTask

        readDataBase()

    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.btn_add_new_task -> dialogTaskAddNew(INSERT_TASK, 0, "")
        }
    }

    private fun setView() {
        btnAddNewTsk = findViewById(R.id.btn_add_new_task)
        recyclerViewTask = findViewById(R.id.recycle_view_task)
    }

    private fun setClickListen() {
        btnAddNewTsk!!.setOnClickListener(this)
    }

    private fun readDataBase() {
        tasks = dbHelper!!.readAllTask()
        adapterTask!!.notifyData(tasks)
    }

    private fun dialogTaskInfo(task: ModelTask) {
        val dialog = Dialog(this, R.style.DialogFullScreen)
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window!!.setDimAmount(0.5f)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.BOTTOM
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.dialog_task_info)

        val tvInfoDate = dialog.findViewById<TextView>(R.id.tv_info_date)
        val tvInfoName = dialog.findViewById<TextView>(R.id.tv_info_name)
        tvInfoDate.text = "Date: " + Utils.formatDateTime(task.taskDateTime!!)
        tvInfoName.text = task.taskName
        val btnInfoClose = dialog.findViewById<Button>(R.id.btn_info_close)
        btnInfoClose.setOnClickListener { dialog.dismiss() }

        dialog.show()
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window!!.setDimAmount(0.5f)
        dialog.window!!.attributes = lp

    }

    private fun dialogTaskAddNew(type: Int, id: Int, msg: String) {
        val dialog = Dialog(this, R.style.DialogFullScreen)
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window!!.setDimAmount(0.5f)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        lp.gravity = Gravity.BOTTOM
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.dialog_task_add_new)

        val etAddNewName = dialog.findViewById<EditText>(R.id.et_add_new_name)
        val btnAddNewClose = dialog.findViewById<Button>(R.id.btn_add_new_close)
        val btnAddNewAdd = dialog.findViewById<Button>(R.id.btn_add_new_add)

        if (type == UPDATE_TASK) {
            etAddNewName.setText(msg)
            btnAddNewAdd.text = getString(R.string.update)
        } else {
            etAddNewName.setText("")
            btnAddNewAdd.text = getString(R.string.add)
        }

        btnAddNewAdd.setOnClickListener {
            if (type == UPDATE_TASK) {
                val message = etAddNewName.text.toString().trim { it <= ' ' }
                dbHelper!!.updateTask(id.toLong(), message)
                readDataBase()
            } else if (type == INSERT_TASK) {
                val message = etAddNewName.text.toString().trim { it <= ' ' }
                dbHelper!!.createTask(message)
                readDataBase()
            }
            dialog.dismiss()
        }

        btnAddNewClose.setOnClickListener { dialog.dismiss() }

        dialog.show()
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window!!.setDimAmount(0.5f)
        dialog.window!!.attributes = lp

    }

    override fun callbackItemClick(task: ModelTask, imageButtonMenu: ImageButton) {
        val popup = PopupMenu(this@ActivityMain, imageButtonMenu)
        popup.menuInflater.inflate(R.menu.menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.info -> dialogTaskInfo(task)
                R.id.update -> dialogTaskAddNew(UPDATE_TASK, task.id, task.taskName!!)
                R.id.delete -> {
                    dbHelper!!.deleteTask(task.id.toLong())
                    readDataBase()
                }
            }
            true
        }
        popup.show()
    }

    companion object {

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }

        private val INSERT_TASK = 1
        private val UPDATE_TASK = 2
    }

}