package kurly.android.pre.assignment.ui.main.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kurly.android.pre.assignment.ui.main.model.ProductUiModel

@Composable
fun GridSection(
    products: List<ProductUiModel>,
    wishlist: Set<String>,
    onWishToggle: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        for (rowIndex in 0 until 2) {
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(state = rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (colIndex in 0 until 3) {
                    val index = rowIndex * 3 + colIndex
                    if (index < products.size) {
                        val product = products[index]
                        HorizontalGridProductCard(
                            product = product,
                            isWished = product.id.toString() in wishlist,
                            onWishToggle = { onWishToggle(product.id) },
                            modifier = Modifier.width(150.dp),
                        )
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}
