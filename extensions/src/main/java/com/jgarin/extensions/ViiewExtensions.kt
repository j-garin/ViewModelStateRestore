package com.jgarin.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.addAfterTextChangedListener(listener: (String) -> Unit) {
	this.addTextChangedListener(object: TextWatcher {
		override fun afterTextChanged(s: Editable) {
			listener(s.toString())
		}

		override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
		}

		override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
		}
	})
}