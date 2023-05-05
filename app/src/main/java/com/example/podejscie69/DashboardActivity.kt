package com.example.podejscie69

import android.os.Bundle


class DashboardActivity : BaseActivity() {

    // Provide the layout ID
    override fun getContentViewId(): Int {
        return R.layout.activity_dashboard
    }

    // Provide the menu item ID
    override fun getNavigationMenuItemId(): Int {
        return R.id.dashboard
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content view to the layout ID returned by getContentViewId()
        setContentView(getContentViewId())

        // Load the web fragment
        loadWebFragment()
    }

    private fun loadWebFragment() {
        // Replace the activity content with the web fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_content, WebFragment())
            .commit()
    }
}

