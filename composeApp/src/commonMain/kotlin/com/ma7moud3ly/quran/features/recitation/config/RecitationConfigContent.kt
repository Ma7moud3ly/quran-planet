package com.ma7moud3ly.quran.features.recitation.config

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ma7moud3ly.quran.features.home.reciters.ItemReciter
import com.ma7moud3ly.quran.features.home.reciters.testReciters
import com.ma7moud3ly.quran.features.reading.SuraName
import com.ma7moud3ly.quran.features.recitation.config.background.ItemBackground
import com.ma7moud3ly.quran.features.recitation.config.verses.VerseSelectorDialog
import com.ma7moud3ly.quran.features.search.chapter.ItemChapter
import com.ma7moud3ly.quran.features.settings.SettingsLabel
import com.ma7moud3ly.quran.model.Chapter
import com.ma7moud3ly.quran.model.PlaybackMode
import com.ma7moud3ly.quran.model.RecitationState
import com.ma7moud3ly.quran.model.Reciter
import com.ma7moud3ly.quran.model.ScreenMode
import com.ma7moud3ly.quran.model.TvBackground
import com.ma7moud3ly.quran.model.asVerseNumber
import com.ma7moud3ly.quran.model.isDistributed
import com.ma7moud3ly.quran.model.isRepetitive
import com.ma7moud3ly.quran.model.isSequential
import com.ma7moud3ly.quran.model.isShuffling
import com.ma7moud3ly.quran.model.isSingleReciter
import com.ma7moud3ly.quran.model.testChapter
import com.ma7moud3ly.quran.model.testDownloadedChapter
import com.ma7moud3ly.quran.model.testRecitationState
import com.ma7moud3ly.quran.platform.isAndroid
import com.ma7moud3ly.quran.platform.isMobile
import com.ma7moud3ly.quran.ui.AppTheme
import com.ma7moud3ly.quran.ui.ButtonSmall
import com.ma7moud3ly.quran.ui.LocalPlatform
import com.ma7moud3ly.quran.ui.MyButton
import com.ma7moud3ly.quran.ui.MyScreen
import com.ma7moud3ly.quran.ui.MySurface
import com.ma7moud3ly.quran.ui.MySurfaceColumn
import com.ma7moud3ly.quran.ui.ScreenHeader
import com.ma7moud3ly.quran.ui.hafsSmartFamily
import com.ma7moud3ly.quran.ui.isCompactDevice
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ma7moud3ly.quran.platform.isWasmJs
import com.ma7moud3ly.quran.resources.Res
import com.ma7moud3ly.quran.resources.downloaded
import com.ma7moud3ly.quran.resources.recite
import com.ma7moud3ly.quran.resources.recite_chapter
import com.ma7moud3ly.quran.resources.recite_mode_normal
import com.ma7moud3ly.quran.resources.recite_mode_tv
import com.ma7moud3ly.quran.resources.recite_range
import com.ma7moud3ly.quran.resources.recite_range_from
import com.ma7moud3ly.quran.resources.recite_range_single
import com.ma7moud3ly.quran.resources.recite_range_to
import com.ma7moud3ly.quran.resources.recite_select_begin
import com.ma7moud3ly.quran.resources.recite_select_end
import com.ma7moud3ly.quran.resources.recite_start
import com.ma7moud3ly.quran.resources.recite_verse
import com.ma7moud3ly.quran.resources.reciter
import com.ma7moud3ly.quran.resources.reciter_add_all
import com.ma7moud3ly.quran.resources.reciter_add_multiple
import com.ma7moud3ly.quran.resources.reciters
import com.ma7moud3ly.quran.resources.reciters_clear
import com.ma7moud3ly.quran.resources.reciters_more
import com.ma7moud3ly.quran.resources.settings_change_reciter
import com.ma7moud3ly.quran.resources.settings_mode_distributed
import com.ma7moud3ly.quran.resources.settings_mode_repetitive
import com.ma7moud3ly.quran.resources.settings_mode_sequential
import com.ma7moud3ly.quran.resources.settings_mode_shuffled
import com.ma7moud3ly.quran.resources.settings_play_in_bg
import com.ma7moud3ly.quran.resources.settings_play_in_bg_description
import com.ma7moud3ly.quran.resources.settings_reel
import com.ma7moud3ly.quran.resources.settings_reel_description
import com.ma7moud3ly.quran.resources.settings_tv_bg
import com.ma7moud3ly.quran.resources.settings_tv_bg_add
import com.ma7moud3ly.quran.resources.settings_tv_bg_description


@Preview
@Composable
private fun RecitationConfigScreenPreview() {
    AppTheme(darkTheme = true) {
        RecitationConfigScreenContent(
            snackbarHostState = remember { SnackbarHostState() },
            selectedChapter = { testChapter },
            downloadedChapters = { testDownloadedChapter },
            reciters = { testReciters },
            recitationState = { testRecitationState },
            uiEvents = {}
        )
    }
}

@Preview
@Composable
private fun RecitationConfigScreenPreviewLight() {
    AppTheme(darkTheme = false) {
        RecitationConfigScreenContent(
            snackbarHostState = remember { SnackbarHostState() },
            selectedChapter = { testChapter },
            downloadedChapters = { testDownloadedChapter },
            reciters = { testReciters },
            recitationState = { testRecitationState },
            uiEvents = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RecitationConfigScreenContent(
    snackbarHostState: SnackbarHostState,
    recitationState: () -> RecitationState,
    selectedChapter: () -> Chapter?,
    downloadedChapters: () -> List<Chapter>,
    reciters: () -> List<Reciter>,
    uiEvents: (ConfigEvents) -> Unit,
) {
    val platform = LocalPlatform.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    )

    MyScreen(
        space = 16.dp,
        modifier = Modifier
            .padding(0.dp)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .verticalScroll(rememberScrollState()),
        topBar = {
            ScreenHeader(
                scrollBehavior = scrollBehavior,
                text = stringResource(Res.string.recite),
                textAlign = TextAlign.Center,
                icon = Icons.Default.Close,
                onBack = { uiEvents(ConfigEvents.OnBack) }
            )
        },
        bottomBar = {
            Footer(
                onStart = { uiEvents(ConfigEvents.InitRecitation(it)) },
                enabled = { selectedChapter() != null && reciters().isNotEmpty() }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        if (isCompactDevice()) {
            SectionReciters(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                reciters = reciters,
                recitationState = recitationState,
                onPickReciter = { uiEvents(ConfigEvents.PickReciters) },
                onClearReciters = { uiEvents(ConfigEvents.ClearReciters) },
                onRemoveReciter = { uiEvents(ConfigEvents.RemoveReciter(it)) }
            )
            HorizontalDivider()
            SectionChapter(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                recitationState = recitationState,
                selectedChapter = selectedChapter,
                chapters = downloadedChapters,
                onSelectChapter = { uiEvents(ConfigEvents.SelectChapter(it)) },
                onPickChapter = { uiEvents(ConfigEvents.PickChapters) }
            )
            HorizontalDivider()
            SectionRange(
                selectedChapter = selectedChapter,
                recitationState = recitationState,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(0.5f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    SectionReciters(
                        modifier = Modifier.padding(16.dp),
                        reciters = reciters,
                        recitationState = recitationState,
                        onPickReciter = { uiEvents(ConfigEvents.PickReciters) },
                        onClearReciters = { uiEvents(ConfigEvents.ClearReciters) },
                        onRemoveReciter = { uiEvents(ConfigEvents.RemoveReciter(it)) }
                    )
                }
                MySurfaceColumn(
                    surfaceModifier = Modifier
                        .padding(16.dp)
                        .weight(0.5f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    SectionChapter(
                        modifier = Modifier.padding(16.dp),
                        recitationState = recitationState,
                        selectedChapter = selectedChapter,
                        chapters = downloadedChapters,
                        onSelectChapter = { uiEvents(ConfigEvents.SelectChapter(it)) },
                        onPickChapter = { uiEvents(ConfigEvents.PickChapters) }
                    )
                    SectionRange(
                        selectedChapter = selectedChapter,
                        recitationState = recitationState,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    )
                }
            }
        }


        if (reciters().size > 1) PlaybackMode(recitationState)

        if (platform.isMobile && isCompactDevice()) {
            HorizontalDivider()
            SectionReelMode(
                recitationState().reelModeState
            )
        }

        if (platform.isAndroid) {
            HorizontalDivider()
            SectionTvBackground(
                tvBackgroundState = recitationState().tvBackgroundState,
                onPickTvBackground = { uiEvents(ConfigEvents.PickTvBackground) }
            )
            HorizontalDivider()
            SectionPlayInBackground(recitationState().playInBgState)
        }
    }
}

@Composable
private fun SectionReciters(
    modifier: Modifier = Modifier,
    recitationState: () -> RecitationState,
    reciters: () -> List<Reciter>,
    onPickReciter: () -> Unit,
    onClearReciters: () -> Unit,
    onRemoveReciter: (Reciter) -> Unit
) {
    val state = recitationState()
    val reciters = reciters()
    var playbackMode by remember { state.playbackModeState }

    fun initMultipleReciterMode() {
        if (playbackMode.isSingleReciter) playbackMode = PlaybackMode.Distributed
        onPickReciter()
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = if (reciters.size < 2) Alignment.Start
        else Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text = if (reciters.size <= 1) stringResource(Res.string.reciter)
            else stringResource(Res.string.reciters, reciters.size)
            Text(
                text = "۞ $text",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.weight(1f))
            if (reciters.size > 1) ButtonSmall(
                text = stringResource(Res.string.reciters_clear),
                modifier = Modifier.padding(horizontal = 4.dp),
                color = MaterialTheme.colorScheme.onSecondary,
                background = MaterialTheme.colorScheme.error,
                onClick = onClearReciters
            )
        }
        FlowRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            reciters.forEach { reciter ->
                ItemReciter(
                    name = reciter.shortName,
                    imageUrl = reciter.imageUrl,
                    showRemove = true,
                    modifier = Modifier.width(110.dp).height(130.dp),
                    onRemove = { onRemoveReciter(reciter) },
                    onClick = { onRemoveReciter(reciter) }
                )
            }
            AddItemButton(
                text = if (reciters.isEmpty()) Res.string.reciter_add_all
                else if (reciters.size == 1) Res.string.reciter_add_multiple
                else Res.string.reciters_more,
                modifier = Modifier.width(110.dp).height(130.dp),
                onClick = {
                    if (reciters.isEmpty()) onPickReciter()
                    else initMultipleReciterMode()
                }
            )

        }
    }
}


@Composable
fun AddItemButton(
    text: StringResource,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    MySurface(
        color = Color.Transparent,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = stringResource(text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
    }
}


@Composable
private fun SectionChapter(
    modifier: Modifier = Modifier,
    recitationState: () -> RecitationState,
    selectedChapter: () -> Chapter?,
    chapters: () -> List<Chapter>,
    onSelectChapter: (Chapter) -> Unit,
    onPickChapter: () -> Unit
) {
    val chapter = selectedChapter()
    val state = recitationState()
    val canChangeChapter by remember { state.canChangeChapterState }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SettingsLabel(Res.string.recite_chapter)
        ItemChapter(
            chapter = chapter,
            showArrow = canChangeChapter,
            onClick = if (canChangeChapter) onPickChapter else null
        )
        if (canChangeChapter) {
            if (chapters().isNotEmpty()) Text(
                text = stringResource(Res.string.downloaded),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(start = 16.dp)
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                chapters().forEach {
                    SuraName(
                        chapterName = it.chapterName(),
                        onClick = { onSelectChapter(it) },
                        background = if (it.id == chapter?.id)
                            MaterialTheme.colorScheme.secondary.copy(0.2f)
                        else MaterialTheme.colorScheme.surface,
                        icon = null,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionRange(
    selectedChapter: () -> Chapter?,
    modifier: Modifier = Modifier,
    recitationState: () -> RecitationState
) {
    val chapter = selectedChapter() ?: return
    val state = recitationState()
    var start by remember { state.firstVerseState }
    var end by remember { state.lastVerseState }
    val canChangeVerse = state.canChangeVerseState.value
    var selectStartNumber by remember { mutableStateOf<Boolean?>(null) }
    var singleVerse by rememberSaveable { state.singleVerseState }

    selectStartNumber?.let { selectStart ->
        VerseSelectorDialog(
            start = start,
            end = end,
            selectStart = selectStart,
            title = if (selectStart) Res.string.recite_select_begin
            else Res.string.recite_select_end,
            limit = chapter.count,
            onConfirm = { newStart, newEnd ->
                if (selectStart) start = newStart
                else end = newEnd
                selectStartNumber = null
            },
            onDismiss = { selectStartNumber = null }
        )
    }

    Column(modifier = modifier) {
        SettingsLabel(Res.string.recite_range)
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (singleVerse.not()) Text(
                text = stringResource(Res.string.recite_range_from),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            ) else Text(
                text = stringResource(Res.string.recite_verse),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Spacer(Modifier.weight(1f))
            Surface(
                onClick = { selectStartNumber = true },
                color = Color.Transparent,
                enabled = canChangeVerse
            ) {
                Text(
                    text = start.asVerseNumber(),
                    fontFamily = hafsSmartFamily(),
                    fontSize = 45.sp,
                    color = if (canChangeVerse) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.onPrimary
                )
            }
            if (singleVerse.not()) {
                Text(
                    text = stringResource(Res.string.recite_range_to),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                Surface(
                    onClick = { selectStartNumber = false },
                    color = Color.Transparent
                ) {
                    Text(
                        text = end.asVerseNumber(),
                        fontFamily = hafsSmartFamily(),
                        fontSize = 45.sp,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.recite_range_single),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Spacer(Modifier.weight(1f))
            MySwitch(
                enabled = { singleVerse },
                onToggle = {
                    singleVerse = it
                    if (singleVerse && state.isMultipleReciterMode()) {
                        state.setPlaybackMode(PlaybackMode.Repetitive)
                    }
                }
            )
        }
    }
}

@Composable
private fun PlaybackMode(
    recitationState: () -> RecitationState
) {
    val state = recitationState()
    val singleVerse by remember { state.singleVerseState }
    var playbackMode by remember { state.playbackModeState }
    HorizontalDivider()
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
    ) {
        SettingsLabel(Res.string.settings_change_reciter)
        if (singleVerse.not()) {
            ItemPlaybackMode(
                text = Res.string.settings_mode_distributed,
                enabled = { playbackMode.isDistributed },
                onClick = { playbackMode = PlaybackMode.Distributed }
            )
            ItemPlaybackMode(
                text = Res.string.settings_mode_shuffled,
                enabled = { playbackMode.isShuffling },
                onClick = { playbackMode = PlaybackMode.Shuffling }
            )
            ItemPlaybackMode(
                text = Res.string.settings_mode_sequential,
                enabled = { playbackMode.isSequential },
                onClick = { playbackMode = PlaybackMode.Sequential }
            )
        }
        ItemPlaybackMode(
            text = Res.string.settings_mode_repetitive,
            enabled = { playbackMode.isRepetitive },
            onClick = { playbackMode = PlaybackMode.Repetitive }
        )
    }
}


@Composable
private fun SectionReelMode(reelModeState: MutableState<Boolean>) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsLabel(Res.string.settings_reel)
            Spacer(modifier = Modifier.weight(1f))
            MySwitch(
                enabled = { reelModeState.value },
                onToggle = { reelModeState.value = it }
            )
        }
        Text(
            text = stringResource(Res.string.settings_reel_description),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
private fun SectionTvBackground(
    tvBackgroundState: MutableState<TvBackground?>,
    onPickTvBackground: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            SettingsLabel(Res.string.settings_tv_bg)
            Text(
                text = stringResource(Res.string.settings_tv_bg_description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Start
            )
        }
        val background = tvBackgroundState.value
        if (background != null) ItemBackground(
            modifier = Modifier.size(width = 85.dp, height = 100.dp),
            tvBackground = background,
            static = LocalPlatform.current.isWasmJs,
            onClick = onPickTvBackground
        ) else AddItemButton(
            text = Res.string.settings_tv_bg_add,
            modifier = Modifier.size(width = 80.dp, height = 90.dp),
            onClick = onPickTvBackground
        )
    }
}

@Composable
private fun SectionPlayInBackground(playInBgState: MutableState<Boolean>) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsLabel(Res.string.settings_play_in_bg)
            Spacer(modifier = Modifier.weight(1f))
            MySwitch(
                enabled = { playInBgState.value },
                onToggle = { playInBgState.value = it }
            )
        }
        Text(
            text = stringResource(Res.string.settings_play_in_bg_description),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
private fun Footer(
    enabled: () -> Boolean = { true },
    onStart: (ScreenMode) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isCompactDevice()) Text(
            text = stringResource(Res.string.recite_start),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(start = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyButton(
                text = Res.string.recite_mode_normal,
                modifier = Modifier,
                onClick = { onStart(ScreenMode.Normal) },
                enabled = enabled
            )
            MyButton(
                text = Res.string.recite_mode_tv,
                modifier = Modifier,
                onClick = { onStart(ScreenMode.Tv) },
                enabled = enabled
            )
        }
    }
}

@Composable
private fun ItemPlaybackMode(
    text: StringResource,
    enabled: () -> Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.weight(1f))
        MyRadioButton(
            enabled = enabled,
            onChecked = onClick
        )
    }
}

@Composable
private fun MySwitch(
    enabled: () -> Boolean,
    onToggle: (Boolean) -> Unit
) {
    Switch(
        checked = enabled(),
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = MaterialTheme.colorScheme.secondary,
            uncheckedThumbColor = MaterialTheme.colorScheme.onPrimary,
            uncheckedTrackColor = Color.Transparent,
            uncheckedBorderColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier.scale(0.9f),
        onCheckedChange = onToggle
    )
}


@Composable
private fun MyRadioButton(
    enabled: () -> Boolean,
    onChecked: () -> Unit
) {
    RadioButton(
        selected = enabled(),
        colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colorScheme.secondary,
            unselectedColor = MaterialTheme.colorScheme.onPrimary,
            disabledSelectedColor = Color.Unspecified,
            disabledUnselectedColor = Color.Unspecified
        ),
        modifier = Modifier.scale(1.1f),
        onClick = onChecked
    )
}
