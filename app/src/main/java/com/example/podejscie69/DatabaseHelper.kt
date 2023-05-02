package com.example.podejscie69
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.podejscie69.User
import java.io.File

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "maindatabase.db"
        private const val TABLE_NAME = "users"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUserTable = "CREATE TABLE $TABLE_NAME (" +
                "$KEY_ID INTEGER PRIMARY KEY," +
                "$KEY_NAME TEXT NOT NULL," +
                "$KEY_EMAIL TEXT UNIQUE NOT NULL," +
                "$KEY_PASSWORD TEXT NOT NULL)"
        db.execSQL(createUserTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addUser(user: User): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, user.name)
        contentValues.put(KEY_EMAIL, user.email)
        contentValues.put(KEY_PASSWORD, user.password)

        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result != -1L
    }
    fun updateUser(user: User): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, user.name)
        contentValues.put(KEY_EMAIL, user.email)
        contentValues.put(KEY_PASSWORD, user.password)

        val result = db.update(TABLE_NAME, contentValues, "$KEY_ID = ?", arrayOf(user.id.toString()))
        db.close()
        return result
    }


    fun getUser(email: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $KEY_EMAIL = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(email))

        var user: User? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD))
            user = User(id, name, email, password)
        }

        cursor.close()
        db.close()
        return user
    }
    fun getAllUsers(): List<User> {
        val userList = ArrayList<User>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD))
                val user = User(id, name, email, password)
                userList.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return userList
    }
    fun deleteUser(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString())).toLong()
        db.close()
        return result > 0
    }



}
