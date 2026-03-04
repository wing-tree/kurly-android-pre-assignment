package kurly.android.pre.assignment.ui.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kurly.android.pre.assignment.ui.main.model.ProductUiModel

@Composable
fun VerticalSection(
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