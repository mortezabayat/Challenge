package co.meteto.cat.presentation.util.resource.route

sealed class AppScreen(val route: String) {
    data object HomeScreen : AppScreen(ConstantAppScreenName.HOME_SCREEN)
    data object BookmarksScreen : AppScreen(ConstantAppScreenName.BOOK_MARKS_SCREEN)
    data object DetailsScreen : AppScreen(ConstantAppScreenName.DETAILS_SCREEN)
}

object ConstantAppScreenName {
    const val HOME_SCREEN = "home_screen"
    const val DETAILS_SCREEN = "details_screen"
    const val BOOK_MARKS_SCREEN = "book_marks_screen"
}