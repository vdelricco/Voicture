package com.delricco.vince.voicture.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.commons.extensions.loadImg
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment : Fragment() {
    companion object {
        fun instance(uri: Uri): ImageFragment {
            val imageFragment = ImageFragment()
            val imageUriBundle = Bundle()
            imageUriBundle.putString("ImageUri", uri.toString())
            imageFragment.arguments = imageUriBundle
            return imageFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUri = arguments.getString("ImageUri")
        if (imageUri.isNullOrEmpty()) {
            throw IllegalArgumentException("Must provide ImageUri")
        } else {
            imageView.loadImg(imageUri)
        }
    }
}
