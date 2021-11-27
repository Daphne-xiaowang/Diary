package com.example.vocabularybook.controller

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vocabularybook.R
import com.example.vocabularybook.util.MyDBOpenHelper
import com.example.vocabularybook.util.AppWords

class ShowNote : AppCompatActivity() {
    lateinit var dbOpenHelper: MyDBOpenHelper
    lateinit var db: SQLiteDatabase

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_page)

        dbOpenHelper = MyDBOpenHelper(this, null, null, null)
        db = dbOpenHelper.writableDatabase
        val intent = getIntent()
        var id: Int = intent.getIntExtra("id",0)
        val english_TextView = this.findViewById<TextView>(R.id.show_english)
        val chinese_EditText = this.findViewById<EditText>(R.id.show_chinese)

        val cursor = db.rawQuery("select * from note where id = " + id, null)
        var text = ""
        var english = ""
        while (cursor.moveToNext()) {
            english += cursor.getString(cursor.getColumnIndex("English"))
            text += cursor.getString(cursor.getColumnIndex("Chinese"))
        }
        english_TextView.text = english
        chinese_EditText.setText(text)

        this.findViewById<Button>(R.id.btn_change).setOnClickListener(View.OnClickListener {
            db.execSQL(AppWords.SQL_UPDATE_DATABASE, arrayOf(chinese_EditText.text, id))
            finish()
        })
    }
}