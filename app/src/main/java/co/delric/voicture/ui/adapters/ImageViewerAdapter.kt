package co.delric.voicture.ui.adapters

import android.net.Uri
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import co.delric.voicture.ui.fragments.ImageFragment

class ImageViewerAdapter(fm: FragmentManager, private val imageUriList: List<Uri>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int) = ImageFragment.instance(imageUriList[position])
    override fun getCount() = imageUriList.size
}