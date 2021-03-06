package com.jgarin.base.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jgarin.base.ui.LayoutResId

abstract class BaseScreenFragment : Fragment() {

    /**
     * Layout resource to be used as this fragment's view
     */
    protected abstract val layout: LayoutResId

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout, container, false)
    }

}