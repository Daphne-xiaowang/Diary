package com.example.vocabularybook.controller.fregment

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.vocabularybook.R
import com.example.vocabularybook.controller.ShowNote
import com.example.vocabularybook.model.listItem
import com.example.vocabularybook.util.AppWords
import com.example.vocabularybook.util.MyDBOpenHelper
import com.example.vocabularybook.util.AppWords.SQL_UPDATE_DATABASE

class SecondFragment : Fragment() {
    lateinit var dbOpenHelper: MyDBOpenHelper
    lateinit var db: SQLiteDatabase
    private var ID = -1
    private var method:String = ""

    @SuppressLint("Range", "Recycle")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.note_page, container, false)
        Log.d("second_fregment", "init")
        ShowNote()

        dbOpenHelper = MyDBOpenHelper(this.context, null, null, null)
        db = dbOpenHelper.writableDatabase
        val english_TextView = view.findViewById<TextView>(R.id.show_english)
        val chinese_EditText = view.findViewById<EditText>(R.id.show_chinese)

        val cursor = db.rawQuery("select * from note where id = " + ID, null)
        var text = ""
        var english = ""
        while (cursor.moveToNext()) {
            english += cursor.getString(cursor.getColumnIndex("English"))
            text += cursor.getString(cursor.getColumnIndex("Chinese"))
        }
        Log.d("id", ID.toString())
        Log.d("english", english)
        english_TextView.text = english
        chinese_EditText.setText(text)

        view.findViewById<Button>(R.id.btn_change).setOnClickListener(View.OnClickListener {
            db.execSQL(AppWords.SQL_UPDATE_DATABASE, arrayOf(chinese_EditText.text, ID))
            view.refreshDrawableState()
        })
        return view
    }

    fun SetMethod(id: Int, method: String) {
        this.ID = id
        this.method = method
    }

}