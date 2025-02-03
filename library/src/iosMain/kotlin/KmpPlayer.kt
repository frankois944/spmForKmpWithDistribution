@file:OptIn(ExperimentalForeignApi::class)

package io.github.kotlin.fibonacci

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIView
import platform.Foundation.NSURL
import appleDeps.MEPlayerController

@Composable
public actual fun KmpPlayer(modifier: Modifier, url: String) {

    val playerController = MEPlayerController()

    val mediaSource = remember(url) {
        NSURL.URLWithString(url)
    }

    LaunchedEffect(url) {
        if (mediaSource == null) {
            throw Exception("Bad input URL")
        }
        playerController.setMediaItemWithVideoUrl(videoUrl = mediaSource)
    }

    DisposableEffect(Unit) {
        onDispose {
            playerController.releasePlayer()
        }
    }

    UIKitView(
        factory = {
            playerController.playerView() as UIView
        },
        modifier = modifier,
        update = {
        }
    )

}