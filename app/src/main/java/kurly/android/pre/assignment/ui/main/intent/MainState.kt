package kurly.android.pre.assignment.ui.main.intent

import kurly.android.pre.assignment.ui.main.model.SectionUiModel

data class MainState(
    val sections: List<SectionUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val nextPage: Int? = 1,
    val wishlist: Set<String> = emptySet(),
)
