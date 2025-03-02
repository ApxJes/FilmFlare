package com.example.flimflare.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.flimflare.R
import com.example.flimflare.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.btmNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val hideBottomNavFragments = setOf(
                R.id.movieDetailsFragment,
                R.id.searchMovieFragment,
                R.id.tvShowDetailsFragment,
                R.id.eachSeasonDetailsFragment,
                R.id.logInFragment,
                R.id.signUpFragment,
                R.id.forgetPasswordFragment,
                R.id.splashFragment,
                R.id.personDetailsFragment,
                R.id.tvShowFragment,
                R.id.eachSeasonDetailsFragment
            )

            if (destination.id in hideBottomNavFragments) {
                binding.btmNav.visibility = View.GONE
            } else {
                binding.btmNav.visibility = View.VISIBLE
            }
        }
    }
}