/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: LineWidthDialogFragment.kt
☆ Date: 2025-11-30
☆
*/

package com.alexis_benejan.sketchio

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import com.alexis_benejan.sketchio.databinding.FragmentLineWidthDialogBinding

/**
 * Fragmento de diálogo para seleccionar el grosor de línea.
 * Utiliza el Fragment Result API para enviar el grosor seleccionado a MainActivityFragment.
 */
class LineWidthDialogFragment : DialogFragment() {

    private var _binding: FragmentLineWidthDialogBinding? = null
    private val binding get() = _binding!!

    private var currentProgress: Int = 10

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setTitle(R.string.choose_line_width)
        return dialog
    }

    /*
    ☆
    ☆ Metodo: onStart
    ☆ Descripcion: Ajusta el tamaño del diálogo para que ocupe más espacio en la pantalla.
    ☆
    */
    override fun onStart() {
        super.onStart()
        // FIX: Establece el ancho a MATCH_PARENT para que el diálogo sea grande.
        dialog?.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLineWidthDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*
    ☆
    ☆ Metodo: onViewCreated
    ☆ Descripcion: Configura el SeekBar y su listener, y actualiza la vista previa.
    ☆
    */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.widthSeekBar.progress = currentProgress

        (binding.brushPreviewView as? BrushPreviewView)?.setLineWidth(currentProgress.toFloat())

        binding.widthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                currentProgress = progress
                (binding.brushPreviewView as? BrushPreviewView)?.setLineWidth(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Al detener el toque, se considera la selección final.
                val selectedWidth = currentProgress.toFloat()

                // Utiliza las claves requeridas por el PDF: 'LineWidthPickerRequest' y 'selectedWidth'
                val result = Bundle().apply {
                    putFloat("selectedWidth", selectedWidth)
                }
                parentFragmentManager.setFragmentResult("LineWidthPickerRequest", result)

                dismiss()
            }
        })

        // El botón "Acceptar" simplemente cierra el diálogo y la selección se maneja en onStopTrackingTouch
        binding.acceptButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    ☆
    ☆ Objeto Companion
    ☆ Descripcion: Contiene constantes estáticas para esta clase.
    ☆
    */
    companion object {
        /**
         * Etiqueta utilizada para identificar este fragmento al mostrarlo.
         * FIX: Define el TAG para resolver el error de compilación.
         */
        const val TAG = "LineWidthDialogFragment"
    }
}