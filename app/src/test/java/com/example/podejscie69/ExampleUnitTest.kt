package com.example.podejscie69

import android.content.Context
import androidx.test.core.app.ApplicationProvider

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class DatabaseHelperTest {

    private lateinit var databaseHelper: DatabaseHelper
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        databaseHelper = DatabaseHelper(context)
    }

    @After
    fun tearDown() {
        databaseHelper.close()
    }

    @Test
    fun addUser() {
        val user = User(0, "Test User", "testuser@example.com", "password")
        val userId = databaseHelper.addUser(user)

        assertNotEquals(-1, userId)
    }
    @Test
    fun deleteUser() {
        // Add a user to the database
        val user = User(0, "Test User", "testuser@example.com", "password")
        val userId = databaseHelper.addUser(user)
        assertNotEquals(-1, userId)

        // Delete the user from the database
        val isDeleted = databaseHelper.deleteUser(userId.toInt())
        assertEquals(true, isDeleted)

        // Verify that the user was removed
        val deletedUser = databaseHelper.getUserByEmail("testuser@example.com")
        assertNull(deletedUser)
    }

    @Test
    fun getAllUsers() {
        // Add some users to the database
        val user1 = User(0, "Test User 1", "testuser1@example.com", "password1")
        val user2 = User(0, "Test User 2", "testuser2@example.com", "password2")
        val userId1 = databaseHelper.addUser(user1)
        val userId2 = databaseHelper.addUser(user2)
        assertNotEquals(-1, userId1)
        assertNotEquals(-1, userId2)

        // Retrieve all users from the database
        val users = databaseHelper.getAllUsers()

        // Check if the users were retrieved correctly
        assertTrue(users.any { it.email == "testuser1@example.com" })
        assertTrue(users.any { it.email == "testuser2@example.com" })
    }

    @Test
    fun updateUser() {
        // Add a user to the database
        val user = User(0, "Test User", "testuser@example.com", "password")
        val userId = databaseHelper.addUser(user)
        assertNotEquals(-1, userId)

        // Update the user's information
        val updatedUser = User(userId.toInt(), "Updated User", "updateduser@example.com", "new_password")
        val updateResult = databaseHelper.updateUser(updatedUser)
        assertNotEquals(0, updateResult)

        // Retrieve the updated user from the database
        val retrievedUser = databaseHelper.getUserByEmail("updateduser@example.com")

        // Check if the user was updated correctly
        assertNotNull(retrievedUser)
        assertEquals("Updated User", retrievedUser!!.name)
        assertEquals("updateduser@example.com", retrievedUser.email)
        assertEquals("new_password", retrievedUser.password)
    }


    private fun Boolean.toInt(): Int {
        return if (this) 1 else 0
    }



}
