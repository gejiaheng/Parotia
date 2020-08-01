package com.melodie.parotia.api

import com.facebook.flipper.plugins.network.NetworkFlipperPlugin

object NetworkFlipperPluginIns {
    val ins: NetworkFlipperPlugin by lazy {
        NetworkFlipperPlugin()
    }
}
