package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesResponse(
    @SerialName("next_page")
    val next_page: String? = null,
    @SerialName("page")
    val page: Int? = null,
    @SerialName("per_page")
    val per_page: Int? = null,
    @SerialName("photos")
    val photos: List<Photo>? = null,
    @SerialName("total_results")
    val total_results: Int? = null
)