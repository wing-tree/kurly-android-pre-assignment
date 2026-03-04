package kurly.android.pre.assignment.ui.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kurly.android.pre.assignment.ui.main.model.ProductUiModel

@Composable
fun HorizontalSection(
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
