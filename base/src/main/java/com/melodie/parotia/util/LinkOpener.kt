package com.melodie.parotia.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

fun toMap(context: Context, location: String) {
    val gmmIntentUri = Uri.parse("geo:0,0?q=$location")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}

fun toTwitter(context: Context, twitterId: String) {
    var intent = Intent(Intent.ACTION_VIEW)
    try {
        // get the Twitter app if possible
        context.packageManager.getPackageInfo("com.twitter.android", 0)
        intent.data = Uri.parse("twitter://user?screen_name=$twitterId")
    } catch (e: PackageManager.NameNotFoundException) {
        // no Twitter app, revert to browser
        intent.data = Uri.parse("https://twitter.com/$twitterId")
    }
    context.startActivity(intent)
}

fun toInstagram(context: Context, insId: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    try {
        context.packageManager.getPackageInfo("com.instagram.android", 0)
        intent.data = Uri.parse("http://instagram.com/_u/$insId")
        intent.setPackage("com.instagram.android")
    } catch (e: PackageManager.NameNotFoundException) {
        intent.data = Uri.parse("http://instagram.com/$insId")
    }
    context.startActivity(intent)
}
