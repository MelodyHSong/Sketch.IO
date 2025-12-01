/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: MainActivity.kt
☆ Date: 2025-11-30
☆
*/

package com.alexis_benejan.sketchio

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.alexis_benejan.sketchio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            saveDrawingInternal()
        } else {
            Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /** Inicia el proceso de guardado, verificando permisos si es necesario. */
    fun saveDrawing() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                saveDrawingInternal()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        } else {
            saveDrawingInternal()
        }
    }

    /** Ejecuta el guardado de la imagen en la galería. */
    private fun saveDrawingInternal() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
        val mainFragment = navHostFragment?.childFragmentManager?.fragments?.firstOrNull() as? MainActivityFragment

        val drawingView = mainFragment?.binding?.drawingView

        if (drawingView != null) {
            val bitmap: Bitmap = drawingView.getDrawingBitmap()

            val result = MediaStore.Images.Media.insertImage(
                contentResolver,
                bitmap,
                getString(R.string.app_name) + "_" + System.currentTimeMillis(),
                getString(R.string.app_name)
            )

            if (result != null) {
                Toast.makeText(this, R.string.save_success, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.save_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /** Gestiona la navegación hacia arriba (back button en Toolbar). */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}