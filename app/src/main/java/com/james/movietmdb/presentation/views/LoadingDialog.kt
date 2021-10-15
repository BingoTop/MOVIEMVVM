package com.james.movietmdb.presentation.views

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.james.movietmdb.databinding.DialogLoadingBinding

class LoadingDialog(context: Context): Dialog(context) {
    private lateinit var binding:DialogLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.8f)
    }

    override fun show() {
        if(!this.isShowing)super.show()
    }
}