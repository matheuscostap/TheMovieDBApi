package com.costa.matheus.filmesapi.utils

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog

class AlertUtils {

    fun showErrorAlert(context: Context, onClick: () -> Unit){
        AlertDialog.Builder(context)
            .setTitle("Erro")
            .setMessage("Ocorreu um erro. Tente novamente.")
            .setNeutralButton("Ok") { dialog, which -> onClick() }
            .setCancelable(false)
            .show()
    }
}