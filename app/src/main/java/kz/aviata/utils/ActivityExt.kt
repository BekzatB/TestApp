package kz.aviata.utils

import android.app.Activity
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Activity.showOkDialog(message: String, onClick: (d: DialogInterface, which: Int) -> Unit = { _, _ ->  }) {
    MaterialAlertDialogBuilder(this)
        .setMessage(message)
        .setPositiveButton("ok", onClick)
        .show()
}