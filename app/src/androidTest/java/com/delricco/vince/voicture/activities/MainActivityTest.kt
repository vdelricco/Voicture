package co.delric.voicture.activities

import android.app.Activity.RESULT_OK
import android.app.Instrumentation
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.content.Intent.ACTION_CHOOSER
import android.content.Intent.EXTRA_TITLE
import android.net.Uri
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import co.delric.voicture.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true)

    @Test
    fun displayProjectsFragmentIsVisible() {
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()))
    }

    @Test
    fun projectListRecyclerViewIsVisible() {
        onView(withId(R.id.projectListRecyclerView)).check(matches(isDisplayed()))
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
    fun onClickCreateProjectFabProjectNameDialogIsShown() {
        onView(withId(R.id.createProjectFab)).perform(click())
        onView(withText(R.string.choose_project_name)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickCreateNewProjectSendsPhotoChooserIntent() {
        Intents.init()

        intending(anyIntent()).respondWith(Instrumentation.ActivityResult(RESULT_OK, Intent()))

        createNewProjectWithDefaultName()

        intended(allOf(hasAction(ACTION_CHOOSER), hasExtra(EXTRA_TITLE, "Select Pictures")))

        Intents.release()
    }

    @Test
    fun onActivityResultWithDataOpensProjectCreationActivity() {
        Intents.init()

        val validDataIntent = Intent()
        val testUri = Uri.parse("test://uri")
        validDataIntent.data = testUri

        intending(hasAction(ACTION_CHOOSER)).respondWith(Instrumentation.ActivityResult(RESULT_OK, validDataIntent))
        intending(hasComponent(EditProjectActivity::javaClass.name)).respondWith(Instrumentation.ActivityResult(RESULT_OK, Intent()))

        createNewProjectWithDefaultName()

        intended(allOf(hasAction(ACTION_CHOOSER), hasExtra(EXTRA_TITLE, "Select Pictures")))
        intended(allOf(hasComponent(hasClassName(EditProjectActivity::class.java.name))))

        Intents.release()
    }

    @Test
    fun onActivityResultWithClipDataOpensProjectCreationActivity() {
        Intents.init()

        val testUri1 = Uri.parse("test://uri1")
        val testUri2 = Uri.parse("test://uri2")
        val testUri3 = Uri.parse("test://uri3")

        val testClipData1 = ClipData.Item(testUri1)
        val testClipData2 = ClipData.Item(testUri2)
        val testClipData3 = ClipData.Item(testUri3)

        val uriListClipData = ClipData("", arrayOf(ClipDescription.MIMETYPE_TEXT_URILIST), testClipData1)
        uriListClipData.addItem(testClipData2)
        uriListClipData.addItem(testClipData3)

        val validClipDataIntent = Intent()
        validClipDataIntent.clipData = uriListClipData

        intending(hasAction(ACTION_CHOOSER)).respondWith(Instrumentation.ActivityResult(RESULT_OK, validClipDataIntent))
        intending(hasComponent(EditProjectActivity::javaClass.name)).respondWith(Instrumentation.ActivityResult(RESULT_OK, Intent()))

        createNewProjectWithDefaultName()

        intended(allOf(hasAction(ACTION_CHOOSER), hasExtra(EXTRA_TITLE, "Select Pictures")))
        intended(allOf(hasComponent(hasClassName(EditProjectActivity::class.java.name))))

        Intents.release()
    }

    private fun createNewProjectWithDefaultName() {
        onView(withId(R.id.createProjectFab)).perform(click())
        onView(withId(android.R.id.button1)).perform(click())
    }
}
