package com.delricco.vince.voicture.ui.adapters

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.delricco.vince.voicture.models.VoictureProject

class VoictureProjectListAdapter(listener: VoictureProjectDelegateAdapter.OnViewSelectedListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val noProjectsItem = object : ViewType {
        override fun getViewType() = AdapterConstants.NO_PROJECTS
    }

    init {
        delegateAdapters.put(AdapterConstants.NO_PROJECTS, NoSavedProjectsDelegateAdapter())
        delegateAdapters.put(AdapterConstants.PROJECT, VoictureProjectDelegateAdapter(listener))
        items = arrayListOf(noProjectsItem)
    }

    override fun getItemCount() = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = delegateAdapters.get(viewType).onCreateViewHolder(parent)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun clearAndAddProjects(projects: List<VoictureProject>) {
        items.clear()
        notifyItemRangeChanged(0, getLastPosition())

        items.addAll(projects)
        notifyItemRangeChanged(0, items.size)
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}