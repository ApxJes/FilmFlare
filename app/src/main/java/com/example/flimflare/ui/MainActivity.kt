package com.example.flimflare.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
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

        checkLoginStatusAndNavigate(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val hideBottomNavFragments = setOf(
                R.id.movieDetailsFragment,
                R.id.searchMovieFragment,
                R.id.tvShowDetailsFragment,
                R.id.eachSeasonDetailsFragment,
                R.id.profileFragment,
                R.id.logInFragment,
                R.id.signUpFragment,
                R.id.forgetPasswordFragment
            )

            if (destination.id in hideBottomNavFragments) {
                binding.btmNav.visibility = View.GONE
            } else {
                binding.btmNav.visibility = View.VISIBLE
            }

            binding.txvTitle.visibility = if (destination.id in hideBottomNavFragments) View.GONE else View.VISIBLE
        }
    }

    private fun checkLoginStatusAndNavigate(navController: NavController) {
        val sharedPreferences = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("Login", false)

        if (isLoggedIn) {
           navController.navigate(R.id.mainMovieFragment)
        }
    }
}