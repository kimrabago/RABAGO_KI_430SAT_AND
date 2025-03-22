package com.ucb.eldroid.newsflash.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ucb.eldroid.newsflash.R

class ConfirmRemoveDialogFragment(
    private val articleTitle: String,
    private val onConfirmRemove: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.remove_from_favorites_title))
            .setMessage(getString(R.string.remove_from_favorites_message, articleTitle))
            .setPositiveButton(getString(R.string.remove_button)) { _, _ ->
                onConfirmRemove()
            }
            .setNegativeButton(getString(R.string.cancel_button), null)
            .create()
    }
}