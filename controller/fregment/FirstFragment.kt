package com.example.vocabularybook.controller.fregment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularybook.R
import com.example.vocabularybook.controller.MyAdapter
import com.example.vocabularybook.controller.AddNewNote
import com.example.vocabularybook.util.MyDBOpenHelper
import com.example.vocabularybook.model.listItem

import com.example.vocabularybook.controller.MyAdapter.OnItemClickLitener
import com.example.vocabularybook.util.AppWords.SQL_SELECT_ALL


class FirstFragment : Fragment() {
    lateinit var dbOpenHelper: MyDBOpenHelper
    lateinit var db: SQLiteDatabase

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dbOpenHelper = MyDBOpenHelper(this.context, null, null, null)
        db= dbOpenHelper.writableDatabase
        val view: View = inflater.inflate(R.layout.notelist, container, false)
        val btn_create = view.findViewById<Button>(R.id.btn_new)
        btn_create.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(context,AddNewNote::class.java)
            startActivity(intent)
        })

        Log.d("first_fregment", "init")
        return view
    }

    override fun onStart() {
        super.onStart()
        val itemList = ArrayList<listItem>()
        initItem(itemList)
        val myAdapter = MyAdapter(itemList)
        val listview = view!!.findViewById<RecyclerView>(R.id.main_list)
        listview.layoutManager = LinearLayoutManager(this.context)

        val obj = object : OnItemClickLitener {
            override fun onItemClick(view: View?, position: Int) {
                Log.d("OnClickPosition",position.toString())
                val fragment = SecondFragment()
                fragment.SetMethod(
                    itemList.get(position).id,
                    "update",
                )
                if (isLand()) {
                    activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.second_content, fragment, null)
                        .addToBackStack(null)
                        .commit()
                } else {
                    activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.first_content, fragment, null)
                        .addToBackStack(null)
                        .commit()
                }
            }

            override fun onItemLongClick(view: View?, position: Int) {
                Log.d("LongClickPosition",position.toString())
            }

        }
        myAdapter.setOnItemClickLitener(obj)
        listview.adapter = myAdapter
    }


    @SuppressLint("Recycle", "Range")
    fun initItem(itemList: ArrayList<listItem>) {
        val cursor: Cursor = db.rawQuery(SQL_SELECT_ALL, null)
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

    fun isLand(): Boolean {
        val mConfiguration = this.resources.configuration //获取设置的配置信息

        val ori = mConfiguration.orientation //获取屏幕方向

        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            Log.d("isLand", "横向")
            return true
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            Log.d("isLand", "竖屏")
            return false
        }
        return false
    }
}

