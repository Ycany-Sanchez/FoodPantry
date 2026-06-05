package com.ycany.prefinals.utils

import android.app.Activity
import android.widget.EditText
import android.widget.Toast

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.getEditTextValue(editText: EditText): String {
    return editText.text.toString().trim()
}



