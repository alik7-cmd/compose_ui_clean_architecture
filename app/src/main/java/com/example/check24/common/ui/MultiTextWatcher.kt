package com.example.check24.common.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MultiTextWatcher {
    private var callback: TextWatcherWithInstance? = null

    fun registerEditText(editText: EditText): MultiTextWatcher {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                callback?.onTextChanged(editText, s, start, before, count)
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        return this
    }

    fun setCallback(callback: TextWatcherWithInstance?): MultiTextWatcher {
        this.callback = callback
        return this
    }
}