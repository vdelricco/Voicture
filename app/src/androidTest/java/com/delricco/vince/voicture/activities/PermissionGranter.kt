package co.delric.voicture.activities

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.test.InstrumentationRegistry
import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector
import com.github.ajalt.timberkt.Timber

class PermissionGranter {
    companion object {
        private val PERMISSIONS_DIALOG_DELAY = 2000L
        private val PERMISSIONS_DIALOG_ALLOW_ID = "com.android.packageinstaller:id/permission_allow_button"

        fun allowPermissionsIfNeeded(permissionNeeded: String) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        !hasNeededPermission(InstrumentationRegistry.getTargetContext(), permissionNeeded)) {
                    Thread.sleep(PERMISSIONS_DIALOG_DELAY)
                    val device = UiDevice.getInstance(getInstrumentation())
                    val allowPermissions = device.findObject(UiSelector()
                            .clickable(true)
                            .checkable(false)
                            .resourceId(PERMISSIONS_DIALOG_ALLOW_ID))
                    if (allowPermissions.exists()) {
                        allowPermissions.click()
                    }
                }
            } catch (e: UiObjectNotFoundException) {
                Timber.e { "There is no permissions dialog to interact with" }
            }

        }

        private fun hasNeededPermission(context: Context, permissionNeeded: String): Boolean {
            val permissionStatus = checkSelfPermission(context, permissionNeeded)
            return permissionStatus == PackageManager.PERMISSION_GRANTED
        }

        private fun checkSelfPermission(context: Context, permission: String): Int {
            return context.checkPermission(permission, android.os.Process.myPid(), android.os.Process.myUid())
        }
    }
}
