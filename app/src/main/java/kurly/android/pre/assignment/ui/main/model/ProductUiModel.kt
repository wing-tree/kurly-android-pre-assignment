package kurly.android.pre.assignment.ui.main.model

data class ProductUiModel(
    val id: Int,
    val name: String,
    val image: String,
    val originalPrice: Int,
    val discountedPrice: Int? = null,
    val isSoldOut: Boolean,
)
