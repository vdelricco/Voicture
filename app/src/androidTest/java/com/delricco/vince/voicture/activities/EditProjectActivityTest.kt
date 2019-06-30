package com.delricco.vince.voicture.activities

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.commons.serialization.VoictureProjectSerDes
import com.delricco.vince.voicture.intents.IntentKeys
import com.delricco.vince.voicture.models.Voicture
import com.delricco.vince.voicture.models.VoictureProject
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@LargeTest
@RunWith(AndroidJUnit4::class)
class EditProjectActivityTest {
    @Rule
    @JvmField
    val projectCreationActivityTestRule = ActivityTestRule(EditProjectActivity::class.java, true, false)

    @Test
    fun invalidIntentFinishesActivity() {
        projectCreationActivityTestRule.launchActivity(Intent())
        assertTrue(projectCreationActivityTestRule.activity.isFinishing)
    }

    @Test
    fun intentWithEmptyUriListFinishesActivity() {
        val emptyUriListIntent = Intent()
        emptyUriListIntent.putExtra(IntentKeys.VOICTURE_PROJECT, VoictureProjectSerDes().toJson(VoictureProject(listOf(), "Test")))

        projectCreationActivityTestRule.launchActivity(emptyUriListIntent)
        assertTrue(projectCreationActivityTestRule.activity.isFinishing)
    }

    /* Assuming valid data for all tests below */
    @Test
    fun imageViewerIsDisplayed() {
        launchActivityWithValidIntent()
        onView(withId(R.id.imageViewer)).check(matches(isDisplayed()))
    }

    @Test
    fun circleIndicatorIsDisplayed() {
        launchActivityWithValidIntent()
        onView(withId(R.id.indicator)).check(matches(isDisplayed()))
    }

    @Test
    fun previewVoictureIsDisplayed() {
        launchActivityWithValidIntent()
        onView(withId(R.id.previewVoictureProject)).check(matches(isDisplayed()))
    }

    @Test
    fun recordAudioIsDisplayed() {
        launchActivityWithValidIntent()
        onView(withId(R.id.recordingOnOff)).check(matches(isDisplayed()))
    }

    @Test
    fun playAudioIsNotDisplayed() {
        launchActivityWithValidIntent()
        onView(withId(R.id.playAudio)).check(matches(not(isDisplayed())))
    }

    @Test
    fun playAudioIsShownAfterRecordingAudio() {
        launchActivityWithValidIntent()
        onView(withId(R.id.recordingOnOff)).perform(click())
        PermissionGranter.allowPermissionsIfNeeded(Manifest.permission.RECORD_AUDIO);
        Thread.sleep(1000)
        onView(withId(R.id.recordingOnOff)).perform(click())
        onView(withId(R.id.playAudio)).check(matches(isDisplayed()))
    }

    private fun launchActivityWithValidIntent() {
        val intent = Intent()
        intent.putExtra(IntentKeys.VOICTURE_PROJECT,
                VoictureProjectSerDes().toJson(
                        VoictureProject(listOf(
                                Voicture(Uri.parse("uri://string"),
                                        File(InstrumentationRegistry.getTargetContext().filesDir, "test"))), "Test")))
        projectCreationActivityTestRule.launchActivity(intent)
    }
}
