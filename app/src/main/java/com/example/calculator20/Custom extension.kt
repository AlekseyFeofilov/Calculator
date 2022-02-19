package com.example.calculator20

import android.view.View
import androidx.constraintlayout.widget.Group

fun Group.setGroupOnClickListener(listener: View.OnClickListener?){
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}