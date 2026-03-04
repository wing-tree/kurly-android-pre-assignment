package kurly.android.pre.assignment.ui.main.model

data class SectionUiModel(
    val id: Int,
    val title: String,
    val type: String,
    val products: List<ProductUiModel>,
)
