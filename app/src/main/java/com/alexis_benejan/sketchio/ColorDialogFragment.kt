/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: ColorDialogFragment.kt
☆ Date: 2025-11-30
☆
*/

package com.alexis_benejan.sketchio

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import com.alexis_benejan.sketchio.databinding.FragmentColorDialogBinding

/**
 * Fragmento de diálogo para seleccionar el color de dibujo.
 * Utiliza el Fragment Result API para enviar el color seleccionado a MainActivityFragment.
 */
class ColorDialogFragment : DialogFragment() {

    private var _binding: FragmentColorDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setTitle(R.string.choose_color)
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
        _binding = FragmentColorDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colorButtons = listOf(
            binding.colorBlack, binding.colorRed, binding.colorGreen,
            binding.colorBlue, binding.colorYellow, binding.colorWhite,
            binding.colorPurple, binding.colorOrange, binding.colorCyan
        )

        colorButtons.forEach { button ->
            button.setOnClickListener { colorButtonView ->
                handleColorSelection(colorButtonView)
            }
        }
    }

    /*
    ☆
    ☆ Metodo: handleColorSelection
    ☆ Descripcion: Procesa la selección del color y devuelve el resultado.
    ☆
    */
    private fun handleColorSelection(view: View) {
        val colorTag = (view as ImageButton).tag.toString()
        val selectedColor = if (colorTag.startsWith("#")) {
            Color.parseColor(colorTag)
        } else {
            // Maneja los colores definidos con @color/
            Color.parseColor(colorTag.removePrefix("@color/"))
        }

        // Utiliza las claves requeridas por el PDF: 'colorPickerRequest' y 'selectedColor'
        val result = Bundle().apply {
            putInt("selectedColor", selectedColor)
        }
        parentFragmentManager.setFragmentResult("colorPickerRequest", result)

        dismiss()
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
        const val TAG = "ColorDialogFragment"
    }
}