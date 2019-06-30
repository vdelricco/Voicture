package com.delricco.vince.voicture.ui.adapters

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.delricco.vince.voicture.ui.fragments.ImageFragment

class ImageViewerAdapter(fm: FragmentManager, private val imageUriList: List<Uri>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return ImageFragment.instance(imageUriList[position])
    }

    override fun getCount(): Int {
        return imageUriList.size
    }
}