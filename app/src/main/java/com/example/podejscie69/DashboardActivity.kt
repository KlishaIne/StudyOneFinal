package com.example.podejscie69

import android.os.Bundle
import com.example.podejscie69.databinding.ActivityDashboardBinding


class DashboardActivity : BaseActivity() {

    private lateinit var binding: ActivityDashboardBinding
    override fun getContentViewId(): Int {
        return R.layout.activity_dashboard
    }

    override fun getNavigationMenuItemId(): Int {
        return R.id.dashboard
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewId())

        loadWebFragment()
    }

    private fun loadWebFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_content, WebFragment())
            .commit()
    }
}
