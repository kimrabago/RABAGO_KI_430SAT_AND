package com.ucb.eldroid.newsflash.view

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.Manifest
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ucb.eldroid.newsflash.utils.NotificationHelper
import com.ucb.eldroid.newsflash.R

private const val s = "Settings clicked"

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            // Handle case where user denies permission
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        val toolbar: Toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)

        bottomNavigationView = findViewById(R.id.bottom_nav)

        bottomNavigationView.setOnItemSelectedListener { menu ->
            when(menu.itemId){
                R.id.article -> {
                    replaceFragment(ArticlesFragment())
                    true
                }

                R.id.favorite -> {
                replaceFragment(FavoritesFragment())
                true
            }
                else -> false
            }
        }
        replaceFragment(ArticlesFragment())

        val openDetail = intent.getBooleanExtra("openDetail", false)
        if (openDetail) {
            val title = intent.getStringExtra("title")
            val description = intent.getStringExtra("description")
            val imageRes = intent.getIntExtra("imageRes", R.drawable.article_image)

            openArticleDetailFragment(title, description, imageRes)
        } else {
            replaceFragment(ArticlesFragment())
        }
    }

    private fun openArticleDetailFragment(title: String?, description: String?, imageRes: Int) {
        val fragment = ArticleDetailFragment().apply {
            arguments = Bundle().apply {
                putString("title", title)
                putString("description", description)
                putInt("imageRes", imageRes)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHost, fragment)
            .addToBackStack(null)
            .commit()
    }
    
    private fun replaceFragment(fragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.navHost)

        if (currentFragment != null && currentFragment::class == fragment::class) {
            return
        }

        supportFragmentManager.beginTransaction().replace(R.id.navHost, fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)

        //  Change the color of all menu item titles to black.
        for (i in 0 until menu?.size()!!) {
            val item = menu.getItem(i)
            val spannableString = SpannableString(item.title)
            spannableString.setSpan(
                ForegroundColorSpan(Color.BLACK), 0, spannableString.length, 0
            )
            item.title = spannableString
        }

        return true
    }

    //  Handle the action bar menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                refreshArticles()
                true
            }
            R.id.action_settings -> {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Simulate refreshing articles
    private fun refreshArticles() {
        Toast.makeText(this, "Refreshing articles...", Toast.LENGTH_SHORT).show()

        // Trigger the notification
        val notificationHelper = NotificationHelper(this)

        notificationHelper.createNotificationChannel()

        // Show the notification
        notificationHelper.showNewArticleNotification(
            getString(R.string.new_article_title), // Directly use getString()
            getString(R.string.new_article_description),
            imageRes = R.drawable.success_image
        )
    }
}
