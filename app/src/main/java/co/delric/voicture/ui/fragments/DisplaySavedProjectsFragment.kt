package co.delric.voicture.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.delric.voicture.R
import co.delric.voicture.models.VoictureProject
import co.delric.voicture.ui.adapters.VoictureProjectDelegateAdapter
import co.delric.voicture.ui.adapters.VoictureProjectListAdapter
import kotlinx.android.synthetic.main.fragment_display_saved_projects.*

class DisplaySavedProjectsFragment : Fragment() {
    interface VoictureListProvider {
        fun getVoictureProjectList(): List<VoictureProject>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_display_saved_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        projectListRecyclerView.setHasFixedSize(true)
        projectListRecyclerView.layoutManager = LinearLayoutManager(this.activity!!.applicationContext)
        projectListRecyclerView.addItemDecoration(
                DividerItemDecoration(projectListRecyclerView.context, DividerItemDecoration.VERTICAL))
        projectListRecyclerView.adapter =
                VoictureProjectListAdapter(this.activity as VoictureProjectDelegateAdapter.OnViewSelectedListener)
    }

    override fun onResume() {
        val projectList = ArrayList((this.activity as VoictureListProvider).getVoictureProjectList())
        if (projectList.size > 0) {
            (projectListRecyclerView.adapter as VoictureProjectListAdapter).clearAndAddProjects(projectList)
        }
        super.onResume()
    }
}