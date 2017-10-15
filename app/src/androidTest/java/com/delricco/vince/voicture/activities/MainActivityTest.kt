package com.delricco.vince.voicture.activities

import android.app.Activity.RESULT_OK
import android.app.Instrumentation
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.content.Intent.ACTION_CHOOSER
import android.content.Intent.EXTRA_TITLE
import android.net.Uri
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.NoActivityResumedException
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.intents.IntentKeys.Companion.SELECTED_IMAGE_URI_LIST
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true)

    @Test
    fun createProjectFragmentIsVisible() {
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()))
    }

    @Test
    fun createNewProjectCardViewIsVisible() {
        onView(withId(R.id.createNewProjectCard)).check(matches(isDisplayed()))
    }

    @Test
    fun toolbarIsVisible() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
    }

    @Test(expected = NoActivityResumedException::class)
    fun onBackPressedClosesActivity() {
        Espresso.pressBack()
        assertTrue(activityRule.activity.isFinishing)
    }

    @Test
    fun onClickCreateNewProjectSendsPhotoChooserIntent() {
        Intents.init()

        intending(anyIntent()).respondWith(Instrumentation.ActivityResult(RESULT_OK, Intent()))

        onView(withId(R.id.createNewProjectCard)).perform(click())

        intended(allOf(hasAction(ACTION_CHOOSER), hasExtra(EXTRA_TITLE, "Select Pictures")))

        Intents.release()
    }

    @Test
    fun onActivityResultWithDataOpensProjectCreationActivity() {
        Intents.init()

        val validDataIntent = Intent()
        val testUri = Uri.parse("test://uri")
        validDataIntent.data = testUri
        val expectedImageUriList = arrayListOf(testUri)

        intending(hasAction(ACTION_CHOOSER)).respondWith(Instrumentation.ActivityResult(RESULT_OK, validDataIntent))
        intending(hasComponent(ProjectCreationActivity::javaClass.name)).respondWith(Instrumentation.ActivityResult(RESULT_OK, Intent()))

        onView(withId(R.id.createNewProjectCard)).perform(click())

        intended(allOf(hasAction(ACTION_CHOOSER), hasExtra(EXTRA_TITLE, "Select Pictures")))
        intended(allOf(
                hasComponent(
                        hasClassName(ProjectCreationActivity::class.java.name)),
                hasExtra(SELECTED_IMAGE_URI_LIST, expectedImageUriList)))

        Intents.release()
    }

    @Test
    fun onActivityResultWithClipDataOpensProjectCreationActivity() {
        Intents.init()

        val testUri1 = Uri.parse("test://uri1")
        val testUri2 = Uri.parse("test://uri2")
        val testUri3 = Uri.parse("test://uri3")

        val expectedImageUriList = arrayListOf<Uri>(testUri1, testUri2, testUri3)

        val testClipData1 = ClipData.Item(testUri1)
        val testClipData2 = ClipData.Item(testUri2)
        val testClipData3 = ClipData.Item(testUri3)

        val uriListClipData = ClipData("", arrayOf(ClipDescription.MIMETYPE_TEXT_URILIST), testClipData1)
        uriListClipData.addItem(testClipData2)
        uriListClipData.addItem(testClipData3)

        val validClipDataIntent = Intent()
        validClipDataIntent.clipData = uriListClipData

        intending(hasAction(ACTION_CHOOSER)).respondWith(Instrumentation.ActivityResult(RESULT_OK, validClipDataIntent))
        intending(hasComponent(ProjectCreationActivity::javaClass.name)).respondWith(Instrumentation.ActivityResult(RESULT_OK, Intent()))

        onView(withId(R.id.createNewProjectCard)).perform(click())

        intended(allOf(hasAction(ACTION_CHOOSER), hasExtra(EXTRA_TITLE, "Select Pictures")))
        intended(allOf(
                hasComponent(
                        hasClassName(ProjectCreationActivity::class.java.name)),
                hasExtra(SELECTED_IMAGE_URI_LIST, expectedImageUriList)))

        Intents.release()
    }
}
