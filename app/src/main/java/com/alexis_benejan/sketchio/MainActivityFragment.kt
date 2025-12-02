/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: MainActivityFragment.kt
☆ Date: 2025-11-30
☆
*/

package com.alexis_benejan.sketchio

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.print.PrintHelper
import com.alexis_benejan.sketchio.databinding.FragmentActivityMainBinding
import android.graphics.Color
import com.alexis_benejan.sketchio.MainActivity
import java.io.OutputStream
import android.provider.MediaStore

/**
 * Fragmento principal que aloja [DrawingView] y gestiona las interacciones del usuario.
 */
class MainActivityFragment: Fragment(), MenuProvider {

    private var _binding: FragmentActivityMainBinding? = null
    val binding get() = _binding!!

    private lateinit var drawingView: DrawingView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        drawingView = binding.drawingView

        // Listener para la selección de color
        parentFragmentManager.setFragmentResultListener("colorPickerRequest", viewLifecycleOwner) { _, bundle ->
            val selectedColor = bundle.getInt("selectedColor")
            drawingView.setBrushColor(selectedColor)
        }

        // Listener para la selección de grosor de línea
        parentFragmentManager.setFragmentResultListener("LineWidthPickerRequest", viewLifecycleOwner) { _, bundle ->
            val selectedWidth = bundle.getFloat("selectedWidth")
            drawingView.setBrushSize(selectedWidth)
        }

        setupConfirmationListener()
    }

    private fun setupConfirmationListener() {
        parentFragmentManager.setFragmentResultListener(
            ConfirmationDialogFragment.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val confirmed = bundle.getBoolean(ConfirmationDialogFragment.RESULT_KEY, false)
            if (confirmed) {
                drawingView.clear()
                drawingView.setBrushColor(Color.BLACK)
                drawingView.setBrushSize(10f)
            }
        }
    }

    override fun onCreateMenu (menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_set_color -> {
                ColorDialogFragment().show(parentFragmentManager, ColorDialogFragment.TAG)
                true
            }
            R.id.action_set_line_width -> {
                LineWidthDialogFragment().show(parentFragmentManager, LineWidthDialogFragment.TAG)
                true
            }
            R.id.action_delete_drawing -> {
                val message = getString(R.string.confirm_erase)
                ConfirmationDialogFragment.newInstance(message)
                    .show(parentFragmentManager, ConfirmationDialogFragment.TAG)
                true
            }
            R.id.action_save -> {
                saveDrawing()
                true
            }
            R.id.action_print -> {
                printDrawing()
                true
            }
            R.id.action_about -> {
                startActivity(Intent(requireContext(), AboutActivity::class.java))
                true
            }
            else -> false
        }
    }

    /*
    ☆
    ☆ Metodo: saveDrawing (Guardado Moderno)
    ☆
    */
    private fun saveDrawing() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            val bitmap: Bitmap = drawingView.getDrawingBitmap()
            val filename = "${System.currentTimeMillis()}.jpg"

            val resolver = requireActivity().contentResolver

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")

                // FIX: La carpeta de guardado cambia de "SketchApp" a "Sketch.IO"
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/Sketch.IO")
            }

            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            val fos: OutputStream? = imageUri?.let { resolver.openOutputStream(it) }

            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                Toast.makeText(requireContext(), R.string.save_success, Toast.LENGTH_SHORT).show()
            } ?: run {
                Toast.makeText(requireContext(), R.string.save_error, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), R.string.save_error, Toast.LENGTH_SHORT).show()
        }
    }

    /*
    ☆
    ☆ Metodo: printDrawing (Impresión con PrintHelper)
    ☆
    */
    private fun printDrawing() {
        if (PrintHelper.systemSupportsPrint()) {
            activity?.also { context ->
                PrintHelper(context).apply {
                    scaleMode = PrintHelper.SCALE_MODE_FIT

                    val bitmap = drawingView.getDrawingBitmap()
                    printBitmap("Dibujo de SketchApp", bitmap)
                }
            }
        } else {
            Toast.makeText(requireContext(), R.string.print_unsupported,
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}