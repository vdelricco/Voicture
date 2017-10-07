package com.delricco.vince.voicture.activities

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.delricco.vince.voicture.R

class ProjectCreationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_creation)
        if (intent.extras != null && intent.extras.containsKey("SelectedImageUriList")) {
            val selectedImageUriList = intent.extras.get("SelectedImageUriList") as ArrayList<Uri>
            for (uri in selectedImageUriList) {
                println("Received uri: " + uri)
            }
        }
    }
}
