/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: AboutActivity.kt
☆ Date: 2025-11-30
☆
*/

package com.alexis_benejan.sketchio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexis_benejan.sketchio.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.action_about)

        setupLinkListeners()
    }

    private fun setupLinkListeners() {
        // Listener para el botón Ko-fi
        binding.koFiButton.setOnClickListener {
            openUrl(getString(R.string.url_kofi))
        }

        // Listener para el botón GitHub
        binding.githubButton.setOnClickListener {
            openUrl(getString(R.string.url_github))
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}