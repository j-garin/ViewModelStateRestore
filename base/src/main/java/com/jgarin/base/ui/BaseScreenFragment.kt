package com.jgarin.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseScreenFragment : Fragment() {

    protected abstract val layout: LayoutResId

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout, container, false)
    }

}