package kurly.android.pre.assignment.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kurly.android.pre.assignment.ui.main.component.HorizontalGridProductCard
import kurly.android.pre.assignment.ui.main.component.VerticalProductCard
import org.orbitmvi.orbit.compose.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state = viewModel.collectAsState().value

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = viewModel::refresh,
        modifier = modifier.fillMaxSize(),
    ) {
        val listState = rememberLazyListState()

        LaunchedEffect(listState) {
            snapshotFlow {
                val layoutInfo = listState.layoutInfo
                val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                lastVisible >= layoutInfo.totalItemsCount - 2
            }
                .distinctUntilChanged()
                .filter { it }
                .collect { viewModel.loadNextPage() }
        }

        if (state.isLoading && state.sections.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
            ) {
                itemsIndexed(
                    items = state.sections,
                    key = { _, section -> section.id },
                ) { index, section ->
                    SectionTitle(title = section.title)
                    when (section.type) {
                        "horizontal" -> HorizontalSection(
                            products = section.products,
                            wishlist = state.wishlist,
                            onWishToggle = viewModel::toggleWishlist,
                        )

                        "grid" -> GridSection(
                            products = section.products,
                            wishlist = state.wishlist,
                            onWishToggle = viewModel::toggleWishlist,
                        )

                        "vertical" -> VerticalSection(
                            products = section.products,
                            wishlist = state.wishlist,
                            onWishToggle = viewModel::toggleWishlist,
                        )
                    }
                    if (index < state.sections.lastIndex) {
                        SectionDivider()
                    }
                }

                if (state.isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    )
}

@Composable
private fun SectionDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(Color(0xFFF5F5F5)),
    )
}

@Composable
private fun HorizontalSection(
    products: List<ProductUiModel>,
    wishlist: Set<String>,
    onWishToggle: (Int) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(products, key = { it.id }) { product ->
            HorizontalGridProductCard(
                product = product,
                isWished = product.id.toString() in wishlist,
                onWishToggle = { onWishToggle(product.id) },
                modifier = Modifier.width(150.dp),
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun GridSection(
    products: List<ProductUiModel>,
    wishlist: Set<String>,
    onWishToggle: (Int) -> Unit,
) {
    val items = products.take(6)
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        for (rowIndex in 0 until 2) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (colIndex in 0 until 3) {
                    val index = rowIndex * 3 + colIndex
                    if (index < items.size) {
                        val product = items[index]
                        HorizontalGridProductCard(
                            product = product,
                            isWished = product.id.toString() in wishlist,
                            onWishToggle = { onWishToggle(product.id) },
                            modifier = Modifier.weight(1f),
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun VerticalSection(
    products: List<ProductUiModel>,
    wishlist: Set<String>,
    onWishToggle: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        products.forEach { product ->
            VerticalProductCard(
                product = product,
                isWished = product.id.toString() in wishlist,
                onWishToggle = { onWishToggle(product.id) },
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}
