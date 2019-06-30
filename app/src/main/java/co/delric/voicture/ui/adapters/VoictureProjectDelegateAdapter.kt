package co.delric.voicture.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import co.delric.voicture.R
import co.delric.voicture.commons.extensions.inflate
import co.delric.voicture.commons.extensions.loadImg
import co.delric.voicture.models.VoictureProject
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