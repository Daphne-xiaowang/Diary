package com.example.vocabularybook.controller


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularybook.R
import com.example.vocabularybook.util.MyDBOpenHelper
import com.example.vocabularybook.model.listItem
import com.example.vocabularybook.util.AppWords

class MainActivity : AppCompatActivity() {
    lateinit var dbOpenHelper: MyDBOpenHelper
    lateinit var db: SQLiteDatabase
    lateinit var context: Context

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        dbOpenHelper = MyDBOpenHelper(this, null, null, null)
        db = dbOpenHelper.writableDatabase
        this.context = this

        super.onCreate(savedInstanceState)
        setContentView(R.layout.notelist)

        val btn_create = this.findViewById<Button>(R.id.btn_new)
        btn_create.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this, AddNewNote::class.java)
            startActivity(intent)
        })

        val btn_search = this.findViewById<Button>(R.id.btn_search)
        btn_search.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this, Search::class.java)
            startActivity(intent)
        })

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide()
        }

        Log.d("MainActivity", "init")
    }

    override fun onStart() {
        super.onStart()
        val itemList = ArrayList<listItem>()
        initItem(itemList)
        val myAdapter = MyAdapter(itemList)
        val listview = this.findViewById<RecyclerView>(R.id.main_list)
        listview.layoutManager = LinearLayoutManager(this)

        val obj = object : MyAdapter.OnItemClickLitener {
            override fun onItemClick(view: View?, position: Int) {
                Log.d("OnClickPosition", position.toString())
                val intent = Intent(context, ShowNote::class.java)
                intent.putExtra("id", itemList.get(position).id)
//                Log.d("sendId",itemList.get(position).id.toString())
                startActivity(intent)
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onItemLongClick(view: View?, position: Int) {
                Log.d("LongClickPosition", position.toString())
                val listItem = itemList.get(position)
                listItem.id
                db.execSQL("delete from note where id = ?", arrayOf(listItem.id))
                itemList.remove(listItem)
                Toast.makeText(context, "删除" + itemList.get(position).english, Toast.LENGTH_SHORT).show()
                myAdapter.notifyDataSetChanged()
                listview.adapter = myAdapter
            }
        }
        myAdapter.setOnItemClickLitener(obj)
        listview.adapter = myAdapter

    }

    @SuppressLint("Recycle", "Range")
    fun initItem(itemList: ArrayList<listItem>) {
        val cursor: Cursor = db.rawQuery(AppWords.SQL_SELECT_ALL, null)
//        db.execSQL("insert into note values(?,?,?)", arrayOf("three","3","2021/10/7"))

        while (cursor.moveToNext()) {
            val listItem = listItem(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("English")),
                cursor.getString(cursor.getColumnIndex("time"))
            )
            itemList.add(listItem)
//            Log.d("listItem", listItem.english + " " + listItem.time)
        }
    }
}