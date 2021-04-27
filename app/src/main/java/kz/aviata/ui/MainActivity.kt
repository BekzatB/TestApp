package kz.aviata.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.aviata.R
import kz.aviata.databinding.ActivityMainBinding
import kz.aviata.utils.visible

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationClickListener)
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }

    private val onNavigationClickListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            navController.navigate(getItemId(item))

            true
        }

    private fun getItemId(item: MenuItem): Int {
        return when (item.itemId) {
            R.id.top_headlines -> R.id.topHeadlinesFragment
            R.id.all_news -> R.id.everythingFragment
            R.id.saved_news -> R.id.saveNewsFragment
            else -> TODO()
        }
    }


    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        val showBottomNav = when (destination.id) {
            R.id.topHeadlinesFragment,
            R.id.everythingFragment,
            R.id.saveNewsFragment -> true
            else -> false
        }
        binding.bottomNavigation.visible(showBottomNav, View.GONE)
    }
}