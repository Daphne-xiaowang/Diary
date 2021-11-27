package com.example.vocabularybook.controller

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vocabularybook.R
import com.example.vocabularybook.util.MyDBOpenHelper
import com.example.vocabularybook.util.AppWords.SQL_INSERT_DATABASE
import java.util.*

class AddNewNote : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_window)

        val dbOpenHelper = MyDBOpenHelper(this, null, null, null)
        val db = dbOpenHelper.writableDatabase

        //设置确认的点击事件
        this.findViewById<TextView>(R.id.btn_add).setOnClickListener {
            val calendar = Calendar.getInstance()
            val date: String = calendar.get(Calendar.YEAR).toString() + "/" +
                    (calendar.get(Calendar.MONTH) + 1) + "/" +
                    calendar.get(Calendar.DATE)
            Log.d("day",calendar.get(Calendar.DATE).toString())
            db.execSQL(
                SQL_INSERT_DATABASE, arrayOf(
                    this.findViewById<EditText>(R.id.add_confirm_english).text,
                    this.findViewById<EditText>(R.id.add_confirm_chinese).text,
                    date
                )
            )
            finish()
        }
    }
}