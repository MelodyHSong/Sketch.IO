/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: ConfirmationDialogFragment.kt
☆ Date: 2025-11-30
☆
*/

package com.alexis_benejan.sketchio

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Muestra un diálogo de confirmación genérico para acciones críticas.
 * Utiliza el Fragment Result API para devolver el resultado (confirmado o cancelado).
 */
class ConfirmationDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = arguments?.getString(ARG_MESSAGE) ?: getString(R.string.confirm_action)

        // Crea el AlertDialog usando el Builder
        return AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton(R.string.confirm_yes) { _, _ ->
                // Usar Fragment Result API para enviar confirmación (true)
                val result = Bundle().apply { putBoolean(RESULT_KEY, true) }
                parentFragmentManager.setFragmentResult(REQUEST_KEY, result)
            }
            .setNegativeButton(R.string.confirm_no) { _, _ ->
                // Enviar cancelación (false)
                val result = Bundle().apply { putBoolean(RESULT_KEY, false) }
                parentFragmentManager.setFragmentResult(REQUEST_KEY, result)
            }
            .create()
    }

    /*
    ☆
    ☆ Objeto Companion
    ☆ Contiene claves de Bundle y un método para crear instancias del diálogo.
    ☆
    */
    companion object {
        const val TAG = "ConfirmationDialogFragment"
        // Clave única para el Request
        const val REQUEST_KEY = "CONFIRMATION_REQUEST_KEY"
        // Clave para el valor booleano dentro del Bundle
        const val RESULT_KEY = "CONFIRMATION_RESULT_KEY"
        private const val ARG_MESSAGE = "ARG_MESSAGE"

        /**
         * Método estático para crear una nueva instancia del diálogo con un mensaje.
         */
        fun newInstance(message: String): ConfirmationDialogFragment {
            val fragment = ConfirmationDialogFragment()
            val args = Bundle()
            args.putString(ARG_MESSAGE, message)
            fragment.arguments = args
            return fragment
        }
    }
}