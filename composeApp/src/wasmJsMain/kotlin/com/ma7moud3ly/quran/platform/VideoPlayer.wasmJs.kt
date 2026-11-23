package com.ma7moud3ly.quran.platform

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import com.ma7moud3ly.quran.model.Video
import kotlinx.browser.document
import org.w3c.dom.HTMLVideoElement

/**
 * Plays a video in a `<video>` element added to the page under the Compose canvas
 * (`z-index: -1`), sized and positioned from this composable's layout bounds.
 * `HtmlElementView` is not used here: it always stacks its element above the canvas,
 * which hides every Compose element behind it.
 *
 * Skiko clears the canvas with opaque white before every frame, which would cover the
 * element. [BlendMode.Clear] erases those pixels again over the player's bounds, leaving
 * the canvas see-through there, so the video shows and Compose content drawn after it
 * (anything with a higher `zIndex`) stays visible and keeps receiving input.
 */
@OptIn(ExperimentalWasmJsInterop::class)
@Composable
actual fun VideoPlayer(
    modifier: Modifier,
    state: VideoPlayerState,
    video: () -> Video
) {
    val currentVideo = video()
    val density = LocalDensity.current.density
    val element = remember {
        (document.createElement("video") as HTMLVideoElement).apply {
            controls = false
            autoplay = true
            loop = true
            muted = true
            setAttribute("playsinline", "")
            style.position = "fixed"
            style.zIndex = "-1"
            style.objectFit = "cover"
        }
    }

    DisposableEffect(element) {
        document.body?.appendChild(element)
        state.play = { element.play() }
        state.pause = { element.pause() }
        onDispose {
            state.play = {}
            state.pause = {}
            element.pause()
            element.removeAttribute("src")
            element.load()
            element.parentNode?.removeChild(element)
        }
    }

    LaunchedEffect(element, currentVideo.path) {
        element.src = currentVideo.path
        element.play()
    }

    Box(
        modifier = modifier
            .drawBehind {
                drawRect(color = Color.Transparent, size = size, blendMode = BlendMode.Clear)
            }
            .onGloballyPositioned { coordinates ->
                // Compose coordinates are device pixels, CSS positions are logical pixels.
                val bounds = coordinates.boundsInWindow()
                element.style.left = "${bounds.left / density}px"
                element.style.top = "${bounds.top / density}px"
                element.style.width = "${bounds.width / density}px"
                element.style.height = "${bounds.height / density}px"
            }
    )
}
