package com.example.vocabularybook.controller

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularybook.R
import com.example.vocabularybook.model.listItem
import com.example.vocabularybook.util.MyDBOpenHelper
import java.util.ArrayList

class Search : AppCompatActivity() {
    lateinit var dbOpenHelper: MyDBOpenHelper
    lateinit var db: SQLiteDatabase
    lateinit var context: Context
    lateinit var recyclerView: RecyclerView

    @SuppressLint("Recycle", "Range")
    fun initItem(itemList: ArrayList<listItem>) {
        itemList.clear()
        var search = ""
        search += this.findViewById<EditText>(R.id.search_text).text.toString()
        val cursor: Cursor = db.rawQuery(
            "select * from note where English like '%" +
                    search +
                    "%'", null
        )

        while (cursor.moveToNext()) {
            val listItem = listItem(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("English")),
                cursor.getString(cursor.getColumnIndex("time"))
            )
            itemList.add(listItem)
            Log.d("search items",listItem.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        dbOpenHelper = MyDBOpenHelper(this, null, null, null)
        db = dbOpenHelper.writableDatabase
        this.context = this
        recyclerView = this.findViewById<RecyclerView>(R.id.search_list)

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide()
        }

        Log.d("search", "init")
    }

    override fun onStart() {
        super.onStart()
        val itemList = ArrayList<listItem>()
        val myAdapter = MyAdapter(itemList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btn_search = this.findViewById<Button>(R.id.search_btn)
        btn_search.setOnClickListener(View.OnClickListener {
            this.initItem(itemList)
            recyclerView.adapter = myAdapter
        })

        val obj = object : MyAdapter.OnItemClickLitener {
            override fun onItemClick(view: View?, position: Int) {
                Log.d("OnClickPosition", position.toString())
                val intent = Intent(context, ShowNote::class.java)
                intent.putExtra("id", itemList.get(position).id)
//                Log.d("sendId",itemList.get(position).id.toString())
                startActivity(intent)
            }

            override fun onItemLongClick(view: View?, position: Int) {
                Log.d("LongClickPosition", position.toString())
            }

        }
        myAdapter.setOnItemClickLitener(obj)
        recyclerView.adapter = myAdapter
    }
}