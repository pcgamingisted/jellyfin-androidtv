package org.jellyfin.androidtv.ui.navigation

import androidx.core.os.bundleOf
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jellyfin.androidtv.constant.Extras
import org.jellyfin.androidtv.ui.browsing.BrowseGridFragment
import org.jellyfin.androidtv.ui.browsing.BrowseScheduleFragment
import org.jellyfin.androidtv.ui.browsing.BrowseViewFragment
import org.jellyfin.androidtv.ui.browsing.ByGenreFragment
import org.jellyfin.androidtv.ui.browsing.ByLetterFragment
import org.jellyfin.androidtv.ui.browsing.CollectionFragment
import org.jellyfin.androidtv.ui.browsing.DisplayPreferencesScreen
import org.jellyfin.androidtv.ui.browsing.GenericFolderFragment
import org.jellyfin.androidtv.ui.browsing.SuggestedMoviesFragment
import org.jellyfin.androidtv.ui.home.HomeFragment
import org.jellyfin.androidtv.ui.itemdetail.FullDetailsFragment
import org.jellyfin.androidtv.ui.itemdetail.ItemListFragment
import org.jellyfin.androidtv.ui.itemdetail.PhotoPlayerActivity
import org.jellyfin.androidtv.ui.livetv.GuideFiltersScreen
import org.jellyfin.androidtv.ui.livetv.GuideOptionsScreen
import org.jellyfin.androidtv.ui.livetv.LiveTvGuideFragment
import org.jellyfin.androidtv.ui.picture.PictureViewerFragment
import org.jellyfin.androidtv.ui.playback.AudioNowPlayingFragment
import org.jellyfin.androidtv.ui.playback.ExternalPlayerActivity
import org.jellyfin.androidtv.ui.playback.PlaybackOverlayActivity
import org.jellyfin.androidtv.ui.preference.PreferencesActivity
import org.jellyfin.androidtv.ui.preference.dsl.OptionsFragment
import org.jellyfin.androidtv.ui.preference.screen.UserPreferencesScreen
import org.jellyfin.androidtv.ui.search.SearchFragment
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.SeriesTimerInfoDto
import org.jellyfin.sdk.model.api.SortOrder
import java.util.UUID

@Suppress("TooManyFunctions")
object Destinations {
	// Helpers
	private inline fun <reified T : OptionsFragment> preferenceDestination(
		vararg screenArguments: Pair<String, Any?>
	) = activityDestination<PreferencesActivity>(
		PreferencesActivity.EXTRA_SCREEN to T::class.qualifiedName,
		PreferencesActivity.EXTRA_SCREEN_ARGS to bundleOf(*screenArguments),
	)

	// General
	val home = fragmentDestination<HomeFragment>()
	val search = fragmentDestination<SearchFragment>()
	val userPreferences = preferenceDestination<UserPreferencesScreen>()

	// Browsing
	// TODO only pass item id instead of complete JSON to browsing destinations
	fun libraryBrowser(item: BaseItemDto) = fragmentDestination<BrowseGridFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun libraryBrowser(item: BaseItemDto, includeType: String) =
		fragmentDestination<BrowseGridFragment>(
			Extras.Folder to Json.Default.encodeToString(item),
			Extras.IncludeType to includeType,
		)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun librarySmartScreen(item: BaseItemDto) = fragmentDestination<BrowseViewFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun collectionBrowser(item: BaseItemDto) = fragmentDestination<CollectionFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun folderBrowser(item: BaseItemDto) = fragmentDestination<GenericFolderFragment>(
		Extras.Folder to Json.Default.encodeToString(item),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun libraryByGenres(item: BaseItemDto, includeType: String) =
		fragmentDestination<ByGenreFragment>(
			Extras.Folder to Json.Default.encodeToString(item),
			Extras.IncludeType to includeType,
		)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun libraryByLetter(item: BaseItemDto, includeType: String) =
		fragmentDestination<ByLetterFragment>(
			Extras.Folder to Json.Default.encodeToString(item),
			Extras.IncludeType to includeType,
		)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun librarySuggestions(item: BaseItemDto) =
		fragmentDestination<SuggestedMoviesFragment>(
			Extras.Folder to Json.Default.encodeToString(item),
		)

	fun displayPreferences(displayPreferencesId: String, allowViewSelection: Boolean) =
		preferenceDestination<DisplayPreferencesScreen>(
			DisplayPreferencesScreen.ARG_PREFERENCES_ID to displayPreferencesId,
			DisplayPreferencesScreen.ARG_ALLOW_VIEW_SELECTION to allowViewSelection,
		)

	// Item details
	fun itemDetails(item: UUID) = fragmentDestination<FullDetailsFragment>(
		"ItemId" to item.toString(),
	)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun channelDetails(item: UUID, channel: UUID, programInfo: BaseItemDto) =
		fragmentDestination<FullDetailsFragment>(
			"ItemId" to item.toString(),
			"ChannelId" to channel.toString(),
			"ProgramInfo" to Json.Default.encodeToString(programInfo),
		)

	// TODO only pass item id instead of complete JSON to browsing destinations
	fun seriesTimerDetails(item: UUID, seriesTimer: SeriesTimerInfoDto) =
		fragmentDestination<FullDetailsFragment>(
			"ItemId" to item.toString(),
			"SeriesTimer" to Json.Default.encodeToString(seriesTimer),
		)

	fun itemList(item: UUID) = fragmentDestination<ItemListFragment>(
		"ItemId" to item.toString(),
	)

	fun itemList(item: UUID, parent: UUID) = fragmentDestination<ItemListFragment>(
		"ItemId" to item.toString(),
		"ParentId" to parent.toString(),
	)

	// Live TV
	val liveTvGuide = fragmentDestination<LiveTvGuideFragment>()
	val liveTvSchedule = fragmentDestination<BrowseScheduleFragment>()
	val liveTvGuideFilterPreferences = preferenceDestination<GuideFiltersScreen>()
	val liveTvGuideOptionPreferences = preferenceDestination<GuideOptionsScreen>()

	// Playback
	val nowPlaying = fragmentDestination<AudioNowPlayingFragment>()
	val photoPlayer = activityDestination<PhotoPlayerActivity>()
	fun pictureViewer(item: UUID, autoPlay: Boolean, albumSortBy: String?, albumSortOrder: SortOrder?) =
		fragmentDestination<PictureViewerFragment>(
			PictureViewerFragment.ARGUMENT_ITEM_ID to item.toString(),
			PictureViewerFragment.ARGUMENT_ALBUM_SORT_BY to albumSortBy,
			PictureViewerFragment.ARGUMENT_ALBUM_SORT_ORDER to albumSortOrder?.let { Json.Default.encodeToString(it) },
			PictureViewerFragment.ARGUMENT_AUTO_PLAY to autoPlay,
		)

	fun externalPlayer(position: Int?) = activityDestination<ExternalPlayerActivity>(
		"Position" to (position ?: 0)
	)

	fun videoPlayer(position: Int?) = activityDestination<PlaybackOverlayActivity>(
		"Position" to (position ?: 0)
	)
}
