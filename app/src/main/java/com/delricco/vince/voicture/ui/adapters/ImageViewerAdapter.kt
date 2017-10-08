package com.delricco.vince.voicture.ui.adapters

import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.delricco.vince.voicture.ui.fragments.ImageFragment

class ImageViewerAdapter(fm: FragmentManager, imageUriList: ArrayList<Uri>) : FragmentPagerAdapter(fm) {
    private val imageUriArrayList = imageUriList

    override fun getItem(position: Int): Fragment {
        return ImageFragment.instance(imageUriArrayList[position])
    }

    override fun getCount(): Int {
        return imageUriArrayList.size
    }
}
