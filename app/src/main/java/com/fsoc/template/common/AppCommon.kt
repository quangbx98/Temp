package com.fsoc.template.common

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri

object AppCommon {
    const val ACTION_NOTIFICATION_FILTER = "ACTION_NOTIFICATION_FILTER"

    fun getVersionName(context: Context): String {
        return try {
            val pInfo: PackageInfo =
                context.packageManager.getPackageInfo(context.packageName, 0)
            pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }
    }

    fun goToGooglePlay(context: Context, appPackageName: String) {
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: android.content.ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }
}