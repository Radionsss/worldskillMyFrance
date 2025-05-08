package workwork.company.worldskillstest.domain.models.remote

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: String,
    val title: String,
    val description: String,
    val details: String,
    val imageRes: String,
    val images: List<String>
)