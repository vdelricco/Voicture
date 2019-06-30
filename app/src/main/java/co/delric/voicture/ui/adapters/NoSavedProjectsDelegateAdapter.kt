package co.delric.voicture.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import co.delric.voicture.R
import co.delric.voicture.commons.extensions.inflate

class NoSavedProjectsDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) = NoSavedProjectsViewHolder(parent)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) = Unit

    class NoSavedProjectsViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.no_saved_projects_item))
}