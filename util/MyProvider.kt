package com.example.vocabularybook.util

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class MyProvider : ContentProvider() {
    private val uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private var myDBOpenHelper: MyDBOpenHelper? = null
    private val AUTHORITY = "com.example.notebook.provider"
    private val TABLE_NAME = "note"

    init {
        uriMatcher.addURI(AUTHORITY, "note", 1)
    }

    override fun onCreate(): Boolean {
        myDBOpenHelper = MyDBOpenHelper(this.context, null, null, null)
        return true
    }

    @SuppressLint("Recycle")
    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        val db: SQLiteDatabase? = myDBOpenHelper?.readableDatabase

        if (uriMatcher.match(p0) == 1) {
            var cursor: Cursor? = null
            cursor = db?.query("note", p1, p2, p3, null, null, p4)
            return cursor
        }
        return null
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        val db: SQLiteDatabase? = myDBOpenHelper?.writableDatabase

        if (uriMatcher.match(p0) == 1) {
            val newNoteID = db?.insert("note", null, p1)
            return Uri.parse("content://$AUTHORITY/note/$newNoteID")
        }
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        val db: SQLiteDatabase = myDBOpenHelper!!.writableDatabase

        if (uriMatcher.match(p0) == 1) {
            return db.delete("note", p1, p2)
        }

        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        val db: SQLiteDatabase = myDBOpenHelper!!.writableDatabase

        if (uriMatcher.match(p0) == 1) {
            return db.update("note", p1, p2, p3)
        }

        return 0
    }
}