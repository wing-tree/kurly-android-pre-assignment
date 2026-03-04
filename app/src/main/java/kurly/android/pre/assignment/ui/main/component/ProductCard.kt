package kurly.android.pre.assignment.ui.main.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kurly.android.pre.assignment.R
import kurly.android.pre.assignment.ui.main.ProductUiModel

private val DiscountColor = Color(0xFFFA622F)

@Composable
fun HorizontalGridProductCard(
    product: ProductUiModel,
    isWished: Boolean,
    onWishToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box {
            AsyncImage(
                model = product.image,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
                    .clip(RoundedCornerShape(4.dp)),
            )
            IconButton(
                onClick = onWishToggle,
                modifier = Modifier.align(Alignment.BottomEnd),
            ) {
                Icon(
                    painter = painterResource(
                        if (isWished) R.drawable.ic_btn_heart_on
                        else R.drawable.ic_btn_heart_off
                    ),
                    contentDescription = "찜하기",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = product.name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(2.dp))
        HorizontalGridPrice(product)
    }
}

@Composable
private fun HorizontalGridPrice(product: ProductUiModel) {
    if (product.discountedPrice != null) {
        val rate = (product.originalPrice - product.discountedPrice) * 100 / product.originalPrice
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${rate}%",
                color = DiscountColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "%,d원".format(product.discountedPrice),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )
        }
        Text(
            text = "%,d원".format(product.originalPrice),
            style = LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough),
            color = Color.Gray,
            fontSize = 12.sp,
        )
    } else {
        Text(
            text = "%,d원".format(product.originalPrice),
            fontSize = 14.sp,
        )
    }
}

@Composable
fun VerticalProductCard(
    product: ProductUiModel,
    isWished: Boolean,
    onWishToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box {
            AsyncImage(
                model = product.image,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f / 4f)
                    .clip(RoundedCornerShape(4.dp)),
            )
            IconButton(
                onClick = onWishToggle,
                modifier = Modifier.align(Alignment.BottomEnd),
            ) {
                Icon(
                    painter = painterResource(
                        if (isWished) R.drawable.ic_btn_heart_on
                        else R.drawable.ic_btn_heart_off
                    ),
                    contentDescription = "찜하기",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = product.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(2.dp))
        VerticalPrice(product)
    }
}

@Composable
private fun VerticalPrice(product: ProductUiModel) {
    if (product.discountedPrice != null) {
        val rate = (product.originalPrice - product.discountedPrice) * 100 / product.originalPrice
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${rate}%",
                color = DiscountColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "%,d원".format(product.discountedPrice),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "%,d원".format(product.originalPrice),
                style = LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough),
                color = Color.Gray,
                fontSize = 12.sp,
            )
        }
    } else {
        Text(
            text = "%,d원".format(product.originalPrice),
            fontSize = 14.sp,
        )
    }
}
