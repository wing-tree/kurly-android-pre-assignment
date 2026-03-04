package kurly.android.pre.assignment.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kurly.android.pre.assignment.data.dto.ProductDto
import kurly.android.pre.assignment.data.repository.MockRepository
import kurly.android.pre.assignment.data.repository.WishlistRepository
import kurly.android.pre.assignment.ui.main.intent.MainSideEffect
import kurly.android.pre.assignment.ui.main.intent.MainState
import kurly.android.pre.assignment.ui.main.model.ProductUiModel
import kurly.android.pre.assignment.ui.main.model.SectionUiModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mockRepository: MockRepository,
    private val wishlistRepository: WishlistRepository,
) : ContainerHost<MainState, MainSideEffect>, ViewModel() {

    override val container = container<MainState, MainSideEffect>(MainState())

    init {
        loadNextPage()
        observeWishlist()
    }

    fun loadNextPage() = intent {
        val page = state.nextPage ?: return@intent
        if (state.isLoading || state.isRefreshing) return@intent
        reduce { state.copy(isLoading = true) }

        val result = fetchSections(page)
        if (result != null) {
            reduce {
                state.copy(
                    sections = state.sections + result.first,
                    nextPage = result.second,
                    isLoading = false,
                )
            }
        } else {
            reduce { state.copy(isLoading = false) }
        }
    }

    fun refresh() = intent {
        reduce { state.copy(isRefreshing = true) }

        val result = fetchSections(1)
        if (result != null) {
            reduce {
                state.copy(
                    sections = result.first,
                    nextPage = result.second,
                    isRefreshing = false,
                )
            }
        } else {
            reduce { state.copy(isRefreshing = false) }
        }
    }

    fun toggleWishlist(productId: Int) = intent {
        wishlistRepository.toggle(productId.toString())
    }

    private fun observeWishlist() = intent {
        wishlistRepository.wishlist.collect { result ->
            reduce {
                state.copy(wishlist = result.getOrDefault(emptySet()))
            }
        }
    }

    private suspend fun fetchSections(page: Int): Pair<List<SectionUiModel>, Int?>? {
        return mockRepository.getSections(page).fold(
            onSuccess = { response ->
                val sections = coroutineScope {
                    response.data.map { section ->
                        async {
                            val products = mockRepository.getSectionProducts(section.id)
                                .getOrNull()?.data ?: emptyList()
                            SectionUiModel(
                                id = section.id,
                                title = section.title,
                                type = section.type,
                                products = products.map { it.toUiModel() },
                            )
                        }
                    }.awaitAll()
                }
                sections to response.paging?.nextPage
            },
            onFailure = { null },
        )
    }

    private fun ProductDto.toUiModel() = ProductUiModel(
        id = id,
        name = name,
        image = image,
        originalPrice = originalPrice,
        discountedPrice = discountedPrice,
        isSoldOut = isSoldOut,
    )
}
