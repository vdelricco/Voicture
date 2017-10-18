package com.delricco.vince.voicture.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delricco.vince.voicture.R
import kotlinx.android.synthetic.main.fragment_create_project.*

class CreateProjectFragment : Fragment() {
    interface CreateProjectListener {
        fun onProjectCreateClicked()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_project, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        createNewProjectCard.setOnClickListener { (activity as CreateProjectListener).onProjectCreateClicked() }
        super.onViewCreated(view, savedInstanceState)
    }
}
