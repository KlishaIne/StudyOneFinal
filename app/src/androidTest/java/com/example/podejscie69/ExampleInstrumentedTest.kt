package com.example.podejscie69
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testLoginButtonClickOpensDashboardActivity() {
        onView(withId(R.id.bLogin)).perform(click())
        intended(hasComponent(DashboardActivity::class.java.name))
    }

    @Test
    fun testRegisterButtonClickOpensRegisterActivity() {
        onView(withId(R.id.bRegister)).perform(click())
        intended(hasComponent(RegisterActivity::class.java.name))
    }

    @Test
    fun testAdminButtonClickOpensAdminActivity() {
        onView(withId(R.id.bAdmin)).perform(click())
        intended(hasComponent(AdminActivity::class.java.name))
    }
}
