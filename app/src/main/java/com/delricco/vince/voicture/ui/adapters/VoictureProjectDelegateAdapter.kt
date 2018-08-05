package com.delricco.vince.voicture.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.commons.extensions.inflate
import com.delricco.vince.voicture.commons.extensions.loadImg
import com.delricco.vince.voicture.models.VoictureProject
import kotlinx.android.synthetic.main.voicture_project_item.view.*

class VoictureProjectDelegateAdapter(val viewSelectedListener: OnViewSelectedListener) : ViewTypeDelegateAdapter {
    interface OnViewSelectedListener {
        fun onItemSelected(project: VoictureProject)
    }

    override fun onCreateViewHolder(parent: ViewGroup) = VoictureProjectViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as VoictureProjectViewHolder).bind(item as VoictureProject)
    }

    inner class VoictureProjectViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.voicture_project_item)) {
        private val name = itemView.projectName
        private val previewImage = itemView.previewImage

        fun bind(item : VoictureProject) {
            name.text = item.name
            previewImage.loadImg(item.data[0].imageUri.toString())

            super.itemView.setOnClickListener { viewSelectedListener.onItemSelected(item) }
        }
    }
}