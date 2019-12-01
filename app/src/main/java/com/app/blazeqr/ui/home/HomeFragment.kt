package com.app.blazeqr.ui.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.blazeqr.R
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class HomeFragment : Fragment(), ZXingScannerView.ResultHandler {

    private lateinit var homeViewModel: HomeViewModel
    private =var mScannerView: ZXingScannerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        mScannerView = ZXingScannerView(activity)

        return root
    }

    override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this)
        mScannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        Toast.makeText(activity, "Contents = " + rawResult?.text, Toast.LENGTH_SHORT)

        val handler = Handler()
        handler.postDelayed(Runnable {
            fun run() {
                mScannerView?.resumeCameraPreview(this)
            }
        }, 2000)
    }
}