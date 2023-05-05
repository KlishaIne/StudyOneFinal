package com.example.podejscie69
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "maindatabase.db"
        // Users table
        private const val TABLE_NAME = "users"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
        // Activity logs table
        private const val TABLE_ACTIVITY_LOGS = "activity_logs"
        private const val KEY_LOG_ID = "log_id"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_ACTIVITY = "activity"
        private const val KEY_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUserTable = "CREATE TABLE $TABLE_NAME (" +
                "$KEY_ID INTEGER PRIMARY KEY," +
                "$KEY_NAME TEXT NOT NULL," +
                "$KEY_EMAIL TEXT UNIQUE NOT NULL," +
                "$KEY_PASSWORD TEXT NOT NULL)"
        db.execSQL(createUserTable)
        val createActivityLogsTable = "CREATE TABLE $TABLE_ACTIVITY_LOGS (" +
                "$KEY_LOG_ID INTEGER PRIMARY KEY," +
                "$KEY_USER_ID INTEGER NOT NULL," +
                "$KEY_ACTIVITY TEXT NOT NULL," +
                "$KEY_TIMESTAMP INTEGER NOT NULL)"
        db.execSQL(createActivityLogsTable)
    }
    // Upgrade the database when its version is changed
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop the existing tables and create new ones
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ACTIVITY_LOGS")
        onCreate(db)
    }
    // Add a new user to the database
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
    // Update an existing user in the database
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

    // Get all users from the database
    fun getAllUsers(): List<User> {
        val userList = ArrayList<User>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor = db.rawQuery(query, null)
        // Function to choose user when clicked
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
    // Delete clicked user
    fun deleteUser(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString())).toLong()
        db.close()
        return result > 0
    }

    // Function to get all activity logs
    fun getAllActivityLogs(): List<ActivityLog> {
        val activityLogList = ArrayList<ActivityLog>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ACTIVITY_LOGS"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val logId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_LOG_ID))
                val userId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_USER_ID))
                val activity = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ACTIVITY))
                val timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(KEY_TIMESTAMP))
                val activityLog = ActivityLog(logId, userId, activity, timestamp)
                activityLogList.add(activityLog)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return activityLogList
    }
    // Retrieve a user from the database by email
    fun getUserByEmail(email: String): User? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PASSWORD),
            "$KEY_EMAIL=?",
            arrayOf(email),
            null,
            null,
            null,
            null
        )

        val user: User? = if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD))
            User(id, name, email, password)
        } else {
            null
        }

        cursor.close()
        db.close()
        return user
    }


}
