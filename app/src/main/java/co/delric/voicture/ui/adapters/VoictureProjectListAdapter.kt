package co.delric.voicture.ui.adapters

import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import co.delric.voicture.models.VoictureProject

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            delegateAdapters.get(viewType, NoSavedProjectsDelegateAdapter()).onCreateViewHolder(parent)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            delegateAdapters.get(getItemViewType(position), NoSavedProjectsDelegateAdapter()).onBindViewHolder(holder, items[position])
    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun clearAndAddProjects(projects: List<VoictureProject>) {
        items.clear()
        items.addAll(projects)
        notifyItemRangeChanged(0, items.size)
    }
}