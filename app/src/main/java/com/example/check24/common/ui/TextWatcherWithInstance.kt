package com.example.check24.common.ui

import android.widget.EditText

interface TextWatcherWithInstance {
    fun onTextChanged(editText: EditText, s: CharSequence, start: Int, before: Int, count: Int)
}