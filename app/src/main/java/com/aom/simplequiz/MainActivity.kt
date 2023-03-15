package com.aom.simplequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.aom.simplequiz.ui.main.MainFragment
import com.aom.simplequiz.ui.main.fragment.*

class MainActivity : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val menuFragment = MenuFragment()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
                .replace(R.id.layout, menuFragment)
                .commitNow()
        }
    }

    override fun emit(action: String, params: Bundle) {
        val currentFragment = when(action) {
            "game" -> GameFragment()
            "result" -> ResultFragment()
            else -> MenuFragment()
        }
        currentFragment.arguments = params;
        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.layout, currentFragment)
        transaction.commit()
    }
}